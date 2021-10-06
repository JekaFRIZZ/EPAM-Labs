package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ResourceNotUniqueException;
import com.epam.esm.util.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    @Autowired
    public GiftCertificateService(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    public List<GiftCertificate> getAll() {
        return giftCertificateDao.getAll();
    }

    public GiftCertificate getById(Long id) {
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.getById(id);

        if(!giftCertificate.isPresent()) {
            throw new ResourceNotFoundException("Certificate with such id doesn't exist");
        }

        return giftCertificate.get();
    }

    public void create(GiftCertificate giftCertificate) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.getByName(giftCertificate.getName());

        if(giftCertificateOptional.isPresent()) {
            throw new ResourceNotUniqueException("The gift certificate already exist");
        }

        LocalDateTime currentTime = DataUtils.getCurrentTime(DATE_TIME_PATTERN);
        giftCertificate.setCreateData(currentTime);
        giftCertificate.setLastUpdateDate(currentTime);

        giftCertificateDao.create(giftCertificate);
    }

    public void update(Long id, GiftCertificate giftCertificate) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.getById(id);

        if(!giftCertificateOptional.isPresent()) {
            throw new ResourceNotFoundException("Certificate with such id doesn't exist");
        }
        giftCertificate.setLastUpdateDate(DataUtils.getCurrentTime(DATE_TIME_PATTERN));
        giftCertificateDao.update(giftCertificate);
    }

    public void deleteById(Long id) {
        giftCertificateDao.deleteById(id);
    }
}

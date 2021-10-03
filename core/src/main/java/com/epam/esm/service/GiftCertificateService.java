package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.NotExistEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateService(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    public List<GiftCertificate> getAll() {
        return giftCertificateDao.getAll();
    }

    public GiftCertificate getById(Long id) throws NotExistEntity {
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.getById(id);

        if(!giftCertificate.isPresent()) {
            throw new NotExistEntity("Certificate with such id doesn't exist");
        }

        return giftCertificate.get();
    }

    //TODO check if exist giftCertificate
    public void create(GiftCertificate giftCertificate) {
        giftCertificateDao.create(giftCertificate);
    }

    public void deleteById(Long id) {
        giftCertificateDao.deleteById(id);
    }
}

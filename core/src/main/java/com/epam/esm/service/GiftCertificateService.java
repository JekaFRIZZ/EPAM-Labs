package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ResourceNotUniqueException;
import com.epam.esm.util.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final GiftCertificateTagDao giftCertificateTagDao;
    private final TagDao tagDao;

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    @Autowired
    public GiftCertificateService(GiftCertificateDao giftCertificateDao, GiftCertificateTagDao giftCertificateTagDao, TagDao tagDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateTagDao = giftCertificateTagDao;
        this.tagDao = tagDao;
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

    @Transactional
    public void create(GiftCertificate giftCertificate) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.getByName(giftCertificate.getName());

        if(giftCertificateOptional.isPresent()) {
            throw new ResourceNotUniqueException("The gift certificate already exist");
        }

        LocalDateTime currentTime = DataUtils.getCurrentTime(DATE_TIME_PATTERN);
        giftCertificate.setCreateData(currentTime);
        giftCertificate.setLastUpdateDate(currentTime);

        Integer giftId = giftCertificateDao.create(giftCertificate);

        List<Tag> tags = giftCertificate.getTags();

        if(tags != null && !tags.isEmpty()) {
            createTagsForGift(tags, giftId);
        }
    }

    private void createTagsForGift(List<Tag> tags, Integer giftId) {
        for(int i = 0; i < tags.size(); i++) {
            Optional<Tag> tag = tagDao.getByName(tags.get(i).getName());
            Integer tagId;
            if(!tag.isPresent()) {
                tagId = tagDao.create(tags.get(i));
            } else {
                tagId = tag.get().getId();
            }
            giftCertificateTagDao.associateTagWithGift(giftId, tagId);
        }
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

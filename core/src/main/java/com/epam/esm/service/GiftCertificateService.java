package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ResourceNotUniqueException;
import com.epam.esm.util.DataUtils;
import com.epam.esm.validator.GiftCertificateValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateService {

    private static final String CERTIFICATE_WITH_SUCH_ID_DOESNT_EXIST = "Certificate with such id doesn't exist";
    private static final String GIFT_EXIST_EXCEPTION = "The gift certificate already exist";

    private final GiftCertificateDao giftCertificateDao;
    private final GiftCertificateTagDao giftCertificateTagDao;
    private final TagService tagService;
    private final GiftCertificateValidator giftCertificateValidator;

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public GiftCertificateService(GiftCertificateDao giftCertificateDao,
                                  GiftCertificateTagDao giftCertificateTagDao,
                                  TagService tagService,
                                  GiftCertificateValidator giftCertificateValidator) {
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateTagDao = giftCertificateTagDao;
        this.tagService = tagService;
        this.giftCertificateValidator = giftCertificateValidator;
    }

    public List<GiftCertificate> getAll() {
        List<GiftCertificate> giftCertificates = giftCertificateDao.getAll();
        giftCertificates.stream().forEach(gift -> setTagsForGiftById(gift, gift.getId()));
        return giftCertificates;
    }

    public GiftCertificate getById(Integer id) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.getById(id);

        if(!giftCertificateOptional.isPresent()) {
            throw new ResourceNotFoundException(CERTIFICATE_WITH_SUCH_ID_DOESNT_EXIST);
        }

        GiftCertificate giftCertificate = giftCertificateOptional.get();
        setTagsForGiftById(giftCertificate, id);

        return giftCertificate;
    }

    private void setTagsForGiftById(GiftCertificate giftCertificate, Integer id) {
        List<Tag> tags = giftCertificateTagDao.getTagsByGiftId(id);
        giftCertificate.setTags(tags);
    }

    @Transactional
    public void create(GiftCertificateDTO giftCertificateDTO) {

        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.getByName(giftCertificateDTO.getName());

        if(giftCertificateOptional.isPresent()) {
            throw new ResourceNotUniqueException(GIFT_EXIST_EXCEPTION);
        }

        giftCertificateValidator.validate(giftCertificateDTO);

        GiftCertificate giftCertificate = toGiftCertificate(giftCertificateDTO);

        LocalDateTime currentTime = DataUtils.getCurrentTime(DATE_TIME_PATTERN);
        giftCertificate.setCreateData(currentTime);
        giftCertificate.setLastUpdateDate(currentTime);

        Integer giftId = giftCertificateDao.create(giftCertificate);

        List<Tag> tags = giftCertificate.getTags();

        if (tags != null && !tags.isEmpty()) {
            createTagsForGift(tags, giftId);
        }
    }

    private GiftCertificate toGiftCertificate(GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate giftCertificate = new GiftCertificate();

        giftCertificate.setName(giftCertificateDTO.getName());
        giftCertificate.setDescription(giftCertificateDTO.getDescription());
        giftCertificate.setPrice(giftCertificateDTO.getPrice());
        giftCertificate.setDuration(giftCertificateDTO.getDuration());
        giftCertificate.setCreateData(giftCertificateDTO.getCreateData());
        giftCertificate.setLastUpdateDate(giftCertificateDTO.getLastUpdateDate());
        giftCertificate.setTags(giftCertificateDTO.getTags());

        return giftCertificate;
    }

    private void createTagsForGift(List<Tag> tags, Integer giftId) {
        tagService.updateTags(tags, giftId);
    }

    @Transactional
    public void update(Integer id, GiftCertificate giftCertificate) {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.getById(id);

        if(!giftCertificateOptional.isPresent()) {
            throw new ResourceNotFoundException(CERTIFICATE_WITH_SUCH_ID_DOESNT_EXIST);
        }
        giftCertificate.setLastUpdateDate(DataUtils.getCurrentTime(DATE_TIME_PATTERN));
        giftCertificateDao.update(giftCertificate);
    }

    public void deleteById(Integer id) {
        giftCertificateDao.deleteById(id);
    }

    public List<GiftCertificate> sortByOrder(String fieldName, boolean isASC) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.sortByOrder(fieldName, isASC);
        giftCertificates.stream().forEach(gift -> setTagsForGiftById(gift, gift.getId()));

        return giftCertificates;
    }
}

package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ResourceNotUniqueException;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private static final String TAG_NOT_EXIST_SUCH_ID = "Tag with such id doesn't exist";
    private static final String TAG_NOT_EXIST_SUCH_NAME = "Tag with such name doesn't exist";
    private static final String TAG_EXIST = "The tag already exist";

    private final TagDao tagDao;
    private final GiftCertificateTagDao giftCertificateTagDao;
    private final TagValidator validator;

    @Autowired
    public TagService(TagDao tagDao, GiftCertificateTagDao giftCertificateTagDao, TagValidator validator) {
        this.tagDao = tagDao;
        this.giftCertificateTagDao = giftCertificateTagDao;
        this.validator = validator;
    }

    public List<Tag> getAll() {
        return tagDao.getAll();
    }

    public Tag getById(Integer id) throws ResourceNotFoundException {
        Optional<Tag> tag = tagDao.getById(id);

        if(!tag.isPresent()) {
            throw new ResourceNotFoundException(TAG_NOT_EXIST_SUCH_ID);
        }

        return tag.get();
    }

    public Tag getByName(String name) {
        Optional<Tag> tag = tagDao.getByName(name);

        if(!tag.isPresent()) {
            throw new ResourceNotFoundException(TAG_NOT_EXIST_SUCH_NAME);
        }

        return tag.get();
    }

    public void create(TagDTO tagDTO) {
        validator.validate(tagDTO);

        Tag tag = toTag(tagDTO);
        Optional<Tag> tagOptional = tagDao.getByName(tag.getName());

        if(tagOptional.isPresent()) {
            throw new ResourceNotUniqueException(TAG_EXIST);
        }

        tagDao.create(tag);
    }

    public void deleteById(Integer id) {
        tagDao.deleteById(id);
    }

    public void updateTags(List<Tag> tags, Integer giftId) {
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

    private Tag toTag(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setName(tagDTO.getName());
        return tag;
    }
}

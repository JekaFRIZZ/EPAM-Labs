package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ResourceNotUniqueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagDao tagDao;

    @Autowired
    public TagService(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public List<Tag> getAll() {
        return tagDao.getAll();
    }

    public Tag getById(Long id) throws ResourceNotFoundException {
        Optional<Tag> tag = tagDao.getById(id);

        if(!tag.isPresent()) {
            throw new ResourceNotFoundException("Tag with such id doesn`t exist");
        }

        return tag.get();
    }

    public Tag getByName(String name) {
        Optional<Tag> tag = tagDao.getByName(name);

        if(!tag.isPresent()) {
            throw new ResourceNotFoundException("Tag with such name doesn`t exist");
        }

        return tag.get();
    }

    public void create(Tag tag) {
        Optional<Tag> tagOptional = tagDao.getByName(tag.getName());

        if(tagOptional.isPresent()) {
            throw new ResourceNotUniqueException("The tag already exist");
        }

        tagDao.create(tag);
    }

    public void deleteById(Long id) {
        tagDao.deleteById(id);
    }
}

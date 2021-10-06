package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class GiftCertificateTagDao {

    private final JdbcTemplate jdbcTemplate;
    private final TagDao tagDao;

    @Autowired
    public GiftCertificateTagDao(JdbcTemplate jdbcTemplate, TagDao tagDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDao = tagDao;
    }

    public void createTagsForGift(List<Tag> tags, Integer giftId) {

    }

    public void associateTagWithGift(Integer giftId, Integer TagId) {
        jdbcTemplate.update("INSERT INTO gifts_and_tags VALUES (?, ?)", giftId, TagId);
    }
}

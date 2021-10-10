package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class GiftCertificateTagDao {

    private static final String ASSOCIATE_TAG_AND_GIFT = "INSERT INTO gifts_and_tags VALUES (?, ?)";
        private static final String GET_TAGS_BY_ID_GIFT = "SELECT * FROM tag WHERE id IN(SELECT tag_id FROM gifts_and_tags WHERE certificate_id = ?)";

    private final JdbcTemplate jdbcTemplate;
    private final TagDao tagDao;

    @Autowired
    public GiftCertificateTagDao(JdbcTemplate jdbcTemplate, TagDao tagDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDao = tagDao;
    }

    public void associateTagWithGift(Integer giftId, Integer TagId) {
        jdbcTemplate.update(ASSOCIATE_TAG_AND_GIFT, giftId, TagId);
    }

    public List<Tag> getTagsByGiftId(Integer giftId) {
        List<Tag> tags =jdbcTemplate.query(GET_TAGS_BY_ID_GIFT, new TagRowMapper(), giftId);
        return tags;
    }
}

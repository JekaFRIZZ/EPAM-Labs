package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDao {

    private static final String GET_ALL = "SELECT * FROM gift_certificate";
    private static final String GET_BY_ID = "SELECT * FROM gift_certificate WHERE id = ?";
    private static final String DELETE = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String CREATE = "INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<GiftCertificate> getAll() {
        return jdbcTemplate.query(GET_ALL, new BeanPropertyRowMapper<>());
    }

    public Optional<GiftCertificate> getById(Long id) {
        return getTagForSingleResult(GET_BY_ID, id);
    }

    public void create(GiftCertificate giftCertificate) {
        jdbcTemplate.update(CREATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateData(),
                giftCertificate.getLastUpdateDate());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    private Optional<GiftCertificate> getTagForSingleResult(String query, Object... params) {
        List<GiftCertificate> certificates = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(), params);

        if (certificates.size() == 1) {
            return Optional.of(certificates.get(0));
        } else if (certificates.size() > 1) {
            throw new IllegalArgumentException("Size of list more than 1");
        }

        return Optional.empty();
    }
}

package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.GiftCertificateRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDao {

    private static final String GET_BY_NAME = "SELECT * FROM gift_certificate WHERE name = ?";
    private static final String GET_BY_ID = "SELECT * FROM gift_certificate WHERE id = ?";
    private static final String DELETE = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String CREATE = "INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL = "SELECT * FROM gift_certificate";
    public static final String UPDATE = "UPDATE gift_certificate SET name=?, description=?,price=?,duration=?,create_date=? WHERE id = ?";

    private static final int GIFT_NAME_INDEX = 1;
    private static final int GIFT_DESCRIPTION_INDEX = 2;
    private static final int GIFT_PRICE_INDEX = 3;
    private static final int GIFT_DURATION_INDEX = 4;
    private static final int GIFT_CREATE_DATA_INDEX = 5;
    private static final int GIFT_LAST_UPDATE_DATA_INDEX = 6;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<GiftCertificate> getAll() {
        return jdbcTemplate.query(GET_ALL, new GiftCertificateRowMapper());
    }

    public Optional<GiftCertificate> getById(Integer id) {
        return getGiftForSingleResult(GET_BY_ID, id);
    }

    public Optional<GiftCertificate> getByName(String name) {
        return getGiftForSingleResult(GET_BY_NAME, name);
    }


    public Integer create(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);

            statement.setString(GIFT_NAME_INDEX, giftCertificate.getName());
            statement.setString(GIFT_DESCRIPTION_INDEX, giftCertificate.getDescription());
            statement.setInt(GIFT_PRICE_INDEX, giftCertificate.getPrice());
            statement.setLong(GIFT_DURATION_INDEX, giftCertificate.getDuration());
            statement.setString(GIFT_CREATE_DATA_INDEX, giftCertificate.getCreateData().toString());
            statement.setString(GIFT_LAST_UPDATE_DATA_INDEX, giftCertificate.getLastUpdateDate().toString());
            return statement;
        }, keyHolder);

        Integer key = (Integer) keyHolder.getKeys().get("id");

        return key;
    }

    public void update(GiftCertificate giftCertificate) {
        jdbcTemplate.update(UPDATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateData().toString(),
                giftCertificate.getId());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    private Optional<GiftCertificate> getGiftForSingleResult(String query, Object... params) {
        List<GiftCertificate> certificates = jdbcTemplate.query(query, new GiftCertificateRowMapper(), params);

        if (certificates.size() == 1) {
            return Optional.of(certificates.get(0));
        } else if (certificates.size() > 1) {
            throw new IllegalArgumentException("Size of list more than 1");
        }

        return Optional.empty();
    }
}

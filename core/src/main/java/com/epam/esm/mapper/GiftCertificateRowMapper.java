package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();

        giftCertificate.setId(rs.getLong("id"));
        giftCertificate.setName(rs.getString("name"));
        giftCertificate.setName(rs.getString("description"));
        giftCertificate.setPrice(rs.getInt("price"));
        giftCertificate.setPrice(rs.getInt("price"));
        giftCertificate.setDuration(rs.getLong("duration"));
        giftCertificate.setCreateData(rs.getLong("create_data"));
        giftCertificate.setLastUpdateDate(rs.getLong("last_update_date"));

        return giftCertificate;
    }
}

package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestDataUtils {
    private static LocalDateTime firstDate = LocalDateTime.parse("2021-10-09T16:21:41.211", DateTimeFormatter.ISO_DATE_TIME);
    private static LocalDateTime secondDate = LocalDateTime.parse("2021-10-09T16:21:41.211", DateTimeFormatter.ISO_DATE_TIME);

    public static GiftCertificate createGiftCertificate(Integer id,
                                                        String name,
                                                        String description) {
        return new GiftCertificate(id, name, description, BigDecimal.valueOf(100.0), 100L, firstDate, secondDate);
    }

}

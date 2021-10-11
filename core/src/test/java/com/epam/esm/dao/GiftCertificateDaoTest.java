package com.epam.esm.dao;

import com.epam.esm.configuration.TestConfiguration;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.TestDataUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfiguration.class, loader = AnnotationConfigContextLoader.class)
class GiftCertificateDaoTest {

    private static EmbeddedDatabase database;
    private static GiftCertificateDao giftCertificateDao;

    private final GiftCertificate firstGiftCertificate =
            TestDataUtils.createGiftCertificate(1,
                                                "Crypt",
                                                "something 1");

    private final GiftCertificate secondGiftCertificate =
            TestDataUtils.createGiftCertificate(2,
                    "Bicycle",
                    "something 2");

    @BeforeAll
    static void setUp() {
        database = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("database/schemas.sql")
                .addScript("database/data.sql")
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(database);
        giftCertificateDao = new GiftCertificateDao(jdbcTemplate);
    }

    @AfterAll
    static void tearDown() {
        database.shutdown();
    }

    @Test
    void getAllShouldReturnAllGifts() {
        List<GiftCertificate> excepted = Arrays.asList(firstGiftCertificate, secondGiftCertificate);

        List<GiftCertificate> actual = giftCertificateDao.getAll();
        assertEquals(excepted, actual);
    }

    @Test
    void getByIdShouldReturnTagById() {
        GiftCertificate excepted = firstGiftCertificate;
        Optional<GiftCertificate> actual = giftCertificateDao.getById(1);

        assertEquals(excepted, actual.get());
    }

    @Test
    void getByNameShouldReturnTagByName() {
        GiftCertificate excepted = firstGiftCertificate;
        Optional<GiftCertificate> actual = giftCertificateDao.getByName("Crypt");

        assertEquals(excepted, actual.get());
    }

    @Test
    void createShouldCreateGift() {
        GiftCertificate excepted = TestDataUtils.createGiftCertificate(3, "Name", "something 3");
        Integer id = giftCertificateDao.create(excepted);
        Optional<GiftCertificate> actual = giftCertificateDao.getById(id);

        assertEquals(excepted, actual.get());
    }

    @Test
    void updateShouldUpdateTag() {
        Integer id = 1;
        GiftCertificate excepted = firstGiftCertificate;
        excepted.setName("Update");

        Map<String, String> fieldForUpdate = new HashMap<>();
        fieldForUpdate.put("name", "Update");

        giftCertificateDao.update(id, fieldForUpdate);

        Optional<GiftCertificate> actual = giftCertificateDao.getById(excepted.getId());

        assertEquals(excepted, actual.get());
    }

    @Test
    void deleteByIdShouldDeleteGiftById() {
        List<GiftCertificate> excepted = Arrays.asList(secondGiftCertificate);
        giftCertificateDao.deleteById(1);

        List<GiftCertificate> actual = giftCertificateDao.getAll();

        assertEquals(excepted, actual);
    }
}
package com.epam.esm.dao;

import com.epam.esm.configuration.TestConfiguration;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfiguration.class, loader = AnnotationConfigContextLoader.class)
class GiftCertificateDaoTest {

    private static EmbeddedDatabase database;
    private static GiftCertificateDao giftCertificateDao;

    private LocalDateTime firstDate = LocalDateTime.parse("2021-10-09T16:21:41.211", DateTimeFormatter.ISO_DATE_TIME);
    private LocalDateTime secondDate = LocalDateTime.parse("2021-10-09T16:21:41.211", DateTimeFormatter.ISO_DATE_TIME);

    private GiftCertificate firstGiftCertificate = new GiftCertificate(1, "Crypt", "something 1", 250, 13L, firstDate, secondDate);
    private GiftCertificate secondGiftCertificate = new GiftCertificate(2,"Bicycle", "something 2", 500, 100L, firstDate, secondDate);

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
    void getAll() {
        List<GiftCertificate> excepted = Arrays.asList(firstGiftCertificate, secondGiftCertificate);

        List<GiftCertificate> actual = giftCertificateDao.getAll();
        assertEquals(excepted, actual);
    }

    @Test
    void getById() {
        GiftCertificate excepted = new GiftCertificate(1, "Crypt", "something 1", 250, 13L, firstDate, secondDate);
        Optional<GiftCertificate> actual = giftCertificateDao.getById(1);

        assertEquals(excepted, actual.get());
    }

    @Test
    void getByName() {
        GiftCertificate excepted = new GiftCertificate(1, "Name", "something 3", 250, 13L, firstDate, secondDate);
        Optional<GiftCertificate> actual = giftCertificateDao.getByName("Crypt");

        assertEquals(excepted, actual.get());
    }

    @Test
    void create() {
        GiftCertificate excepted = new GiftCertificate(3, "Name", "something 3", 250, 13L, firstDate, secondDate);
        Integer id = giftCertificateDao.create(excepted);
        Optional<GiftCertificate> actual = giftCertificateDao.getById(id);

        assertEquals(excepted, actual.get());
    }

    @Test
    void update() {
        Integer id = 1;
        GiftCertificate excepted = new GiftCertificate(id, "update", "something 1", 250, 13L, firstDate, secondDate);
        excepted.setName("Update");

        Map<String, String> fieldForUpdate = new HashMap<>();
        fieldForUpdate.put("name", "Update");

        giftCertificateDao.update(id, fieldForUpdate);

        Optional<GiftCertificate> actual = giftCertificateDao.getById(excepted.getId());

        assertEquals(excepted, actual.get());
    }

    @Test
    void deleteById() {
        List<GiftCertificate> excepted = Arrays.asList(secondGiftCertificate);
        giftCertificateDao.deleteById(1);

        List<GiftCertificate> actual = Arrays.asList(secondGiftCertificate);

        assertEquals(excepted, actual);
    }
}
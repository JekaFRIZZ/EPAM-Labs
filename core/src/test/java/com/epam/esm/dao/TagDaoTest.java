package com.epam.esm.dao;

import com.epam.esm.configuration.TestConfiguration;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfiguration.class, loader = AnnotationConfigContextLoader.class)
class TagDaoTest {

    private static EmbeddedDatabase database;
    private static TagDao tagDao;

    @BeforeAll
    public static void setUp() {
        database = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("database/schemas.sql")
                .addScript("database/data.sql")
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(database);
        tagDao = new TagDao(jdbcTemplate);
    }

    @AfterAll
    static void afterAll() {
        database.shutdown();
    }

    @Test
    public void testGetAllShouldGetAllTags() {
        Tag firstTag = new Tag(1, "Run");
        Tag secondTag = new Tag(2, "Java");
        Tag thirdTag = new Tag(3, "Summer");
        List<Tag> expected = Arrays.asList(firstTag, secondTag, thirdTag);
        List<Tag> actual = tagDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    void testGetByIdShouldGetTag() {
        Tag expected = new Tag(1, "Run");

        Optional<Tag> actual = tagDao.getById(1);

        assertEquals(expected, actual.get());
    }

    @Test
    void getByName() {
        Tag expected = new Tag(1, "Run");

        Optional<Tag> actual = tagDao.getByName("Run");

        assertEquals(expected, actual.get());
    }

    @Test
    void create() {
        Tag expected = new Tag("Spring");

        Integer id =  tagDao.create(expected);
        expected.setId(id);

        Optional<Tag> actual = tagDao.getById(id);

        assertEquals(expected, actual.get());
    }

    @Test
    void deleteById() {
        Tag firstTag = new Tag(1, "Run");
        Tag secondTag = new Tag(2, "Java");
        List<Tag> expected = Arrays.asList(firstTag, secondTag);

        tagDao.deleteById(3);

        List<Tag> actual = tagDao.getAll();

        assertEquals(expected, actual);
    }
}
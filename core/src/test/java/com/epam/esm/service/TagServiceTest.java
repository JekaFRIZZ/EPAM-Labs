package com.epam.esm.service;

import com.epam.esm.configuration.TestConfiguration;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfiguration.class, loader = AnnotationConfigContextLoader.class)
class TagServiceTest {

    private final TagDao tagDao = Mockito.mock(TagDao.class);
    private final GiftCertificateTagDao giftCertificateTagDao = Mockito.mock(GiftCertificateTagDao.class);
    private final TagValidator tagValidator = Mockito.mock(TagValidator.class);
    private final TagService tagService = new TagService(tagDao, giftCertificateTagDao, tagValidator);

    @Test
    void getAll() {
        List<Tag> excepted = Arrays.asList(new Tag(), new Tag());
        when(tagDao.getAll()).thenReturn(excepted);

        List<Tag> actual = tagService.getAll();

        assertEquals(excepted, actual);
    }

    @Test
    void getById() {
        Integer id = 1;
        Tag excepted = new Tag();
        when(tagDao.getById(id)).thenReturn(Optional.of(excepted));

        Tag actual = tagService.getById(id);

        assertEquals(excepted, actual);
    }

    @Test
    void getByName() {
        String name = "Run";
        Tag excepted = new Tag();
        when(tagDao.getByName(name)).thenReturn(Optional.of(excepted));

        Tag actual = tagService.getByName(name);

        assertEquals(excepted, actual);
    }

    @Test
    void create() {
        String name = "name";
        TagDTO tag = new TagDTO(0, name);
        when(tagDao.getByName(name)).thenReturn(Optional.empty());
        when(tagDao.create(new Tag())).thenReturn(0);
        tagService.create(tag);

    }

    @Test
    void deleteById() {
        Integer id = 0;
        when(tagDao.getById(id)).thenReturn(Optional.of(new Tag()));
        doNothing().when(tagDao).deleteById(id);

        tagService.deleteById(id);
    }
}
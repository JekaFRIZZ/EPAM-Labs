package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.TestDataUtils;
import com.epam.esm.validator.GiftCertificateValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class GiftCertificateServiceTest {

    private final GiftCertificate giftCertificate = Mockito.mock(GiftCertificate.class);
    private final GiftCertificateDao giftCertificateDao = Mockito.mock(GiftCertificateDao.class);
    private final GiftCertificateTagDao giftCertificateTagDao = Mockito.mock(GiftCertificateTagDao.class);
    private final TagService tagService = Mockito.mock(TagService.class);
    private final GiftCertificateValidator giftCertificateValidator = Mockito.mock(GiftCertificateValidator.class);
    private final GiftCertificateService giftCertificateService = new GiftCertificateService(giftCertificateDao, giftCertificateTagDao, tagService, giftCertificateValidator);

    @Test
    void testGetAllShouldReturnAllGift() {
        List<GiftCertificate> excepted = Arrays.asList(new GiftCertificate(), new GiftCertificate());

        when(giftCertificateDao.getAll()).thenReturn(excepted);

        List<GiftCertificate> actual = giftCertificateService.getAll();

        assertEquals(excepted, actual);
    }

    @Test
    void testGetByIdShouldReturnGift() {
        Integer id = 0;
        GiftCertificate expected = new GiftCertificate();
        when(giftCertificateDao.getById(id)).thenReturn(Optional.of(expected));

        GiftCertificate actual = giftCertificateService.getById(id);

        assertEquals(expected, actual);
    }

    @Test
    void testCreateShouldCreateGiftWhenCorrectFieldsApplied() {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO("name", "description", BigDecimal.valueOf(1), 1L);

        giftCertificateDTO.setTags(Arrays.asList(new Tag("name")));

        Integer id = 1;
        when(giftCertificateDao.getByName("name")).thenReturn(Optional.empty());
        when(giftCertificateDao.create(new GiftCertificate())).thenReturn(id);
        doNothing().when(giftCertificateValidator).validate(giftCertificateDTO);

        giftCertificateService.create(giftCertificateDTO);
    }

    @Test
    void testUpdateShouldUpdate() {
        Integer id = 1;
        GiftCertificate gift = TestDataUtils.createGiftCertificate(id, "name", "something");
        gift.setName("Update");

        when(giftCertificateDao.getById(id)).thenReturn(Optional.of(gift));

        when(giftCertificateTagDao.getTagsByGiftId(id)).thenReturn(new ArrayList<>());

        giftCertificateService.update(id, gift);

        String expected = "Update";
        String actual = giftCertificateDao.getById(id).get().getName();

        assertEquals(expected, actual);
    }

    @Test
    void testDeleteByIdShouldDeleteGift() {
        Integer id = 0;
        when(giftCertificateDao.getById(id)).thenReturn(Optional.of(new GiftCertificate()));
        doNothing().when(giftCertificateDao).deleteById(id);

        giftCertificateService.deleteById(id);
    }

    @Test
    void testSortShouldReturnSortedList() {
        List<GiftCertificate> expected = Arrays.asList(new GiftCertificate(), new GiftCertificate());
        when(giftCertificateDao.sortByOrder(null, false)).thenReturn(expected);
        List<GiftCertificate> actual = giftCertificateService.sortByOrder(null, false);

        assertEquals(expected, actual);
    }
}
package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GiftCertificateValidatorTest {
    private final GiftCertificateValidator validator = new GiftCertificateValidator();

    private static final String CORRECT_NAME = "someName";
    private static final String INCORRECT_NAME = "s";
    private static final String DESCRIPTION = "someDescription";
    private static final Integer POSITIVE_PRICE = 5;
    private static final Integer NEGATIVE_PRICE = -5;
    private static final Long POSITIVE_DURATION = 10L;
    private static final Long NEGATIVE_DURATION = -10L;

    @Test
    void testValidateShouldThrowExceptionWhenIncorrectNameApplied() {
        assertThrows(ValidationException.class, () -> {
            GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(INCORRECT_NAME,DESCRIPTION, POSITIVE_PRICE, POSITIVE_DURATION);

            validator.validate(giftCertificateDTO);
        });
    }

    @Test
    void testValidateShouldThrowExceptionWhenNegativeDurationApplied() {
        assertThrows(ValidationException.class, () -> {
            GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(CORRECT_NAME,DESCRIPTION, POSITIVE_PRICE, NEGATIVE_DURATION);

            validator.validate(giftCertificateDTO);
        });
    }

    @Test
    void testValidateShouldThrowExceptionWhenNegativePriceApplied() {
        assertThrows(ValidationException.class, () -> {
            GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(CORRECT_NAME,DESCRIPTION, NEGATIVE_PRICE, POSITIVE_DURATION);

            validator.validate(giftCertificateDTO);
        });
    }

    @Test
    void testValidateShouldWorkCorrectly() {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(CORRECT_NAME,DESCRIPTION, POSITIVE_PRICE, POSITIVE_DURATION);

        validator.validate(giftCertificateDTO);
    }
}
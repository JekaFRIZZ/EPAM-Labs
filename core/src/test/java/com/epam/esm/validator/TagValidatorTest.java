package com.epam.esm.validator;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagValidatorTest {

    private final TagValidator validator = new TagValidator();

    @Test
    void testValidateShouldThrowExceptionWhenIncorrectNameApplied() {
        assertThrows(ValidationException.class, () -> {
           TagDTO tagDTO = new TagDTO("q");

           validator.validate(tagDTO);
        });
    }

    @Test
    void testValidateShouldWorkCorrectly() {
        TagDTO tagDTO = new TagDTO("name");

        validator.validate(tagDTO);
    }
}
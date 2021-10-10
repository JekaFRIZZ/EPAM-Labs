package com.epam.esm.validator;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class TagValidator implements Validator<TagDTO> {

    private final String CONSTRAINTS_FOR_NAME = "The name must be between 2 and 30 characters";

    @Override
    public void validate(TagDTO entity) {
        int nameSize = entity.getName().length();

        if(nameSize <= 2 || nameSize >= 30) {
            throw new ValidationException(CONSTRAINTS_FOR_NAME);
        }
    }
}

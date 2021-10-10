package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateValidator implements Validator<GiftCertificateDTO> {

    private static final String CONSTRAINTS_FOR_DURATION = "The duration must be positive";
    private static final String CONSTRAINTS_FOR_PRICE = "The price must not be negative";
    private static final String LENGTH_NAME = "The name must be between 2 and 30 characters";
    private static final String THE_FIELDS_MUST_BE_NOT_NULL = "The fields must be not null";

    @Override
    public void validate(GiftCertificateDTO entity) {
        String name = entity.getName();
        int nameSize = name.length();
        String description = entity.getDescription();
        Integer price = entity.getPrice();
        Long duration = entity.getDuration();

        if(name == null ||
                description == null ||
                price == null ||
                duration == null)     {
            throw new ValidationException(THE_FIELDS_MUST_BE_NOT_NULL);
        }

        if(nameSize <= 2 || nameSize >= 30) {
            throw new ValidationException(LENGTH_NAME);
        }

        if(price < 0) {
            throw new ValidationException(CONSTRAINTS_FOR_PRICE);
        }

        if(duration < 1) {
            throw new ValidationException(CONSTRAINTS_FOR_DURATION);
        }
    }
}

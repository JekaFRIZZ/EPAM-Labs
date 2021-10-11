package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GiftCertificateValidator implements Validator<GiftCertificateDTO> {

    private static final int MIN_LENGTH_NAME = 2;
    private static final int MAX_LENGTH_NAME = 30;
    private static final int MIN_VALUE_PRICE = 0;
    private static final int MIN_VALUE_DURATION = 1;

    @Override
    public void validate(GiftCertificateDTO entity) {
        String name = entity.getName();
        int nameSize = name.length();
        String description = entity.getDescription();
        BigDecimal price = entity.getPrice();
        Long duration = entity.getDuration();

        if(name == null ||
                description == null ||
                price == null ||
                duration == null)     {
            throw new ValidationException("The fields must be not null");
        }

        if(nameSize <= MIN_LENGTH_NAME || nameSize >= MAX_LENGTH_NAME) {
            throw new ValidationException("The name must be between 2 and 30 characters");
        }

        if(price.intValue() < MIN_VALUE_PRICE) {
            throw new ValidationException("The price must not be negative");
        }

        if(duration < MIN_VALUE_DURATION) {
            throw new ValidationException("The duration must be positive");
        }
    }
}

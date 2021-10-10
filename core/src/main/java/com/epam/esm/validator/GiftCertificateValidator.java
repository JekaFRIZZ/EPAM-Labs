package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateValidator implements Validator<GiftCertificateDTO> {
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
            throw new ValidationException("The fields must be not null");
        }

        if(nameSize <= 2 || nameSize >= 30) {
            throw new ValidationException("The name must be between 2 and 30 characters");
        }

        if(price < 0) {
            throw new ValidationException("The price must not be negative");
        }

        if(duration < 1) {
            throw new ValidationException("The duration must be positive");
        }
    }
}

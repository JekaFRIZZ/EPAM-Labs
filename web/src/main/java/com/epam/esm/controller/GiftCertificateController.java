package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.ErrorData;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ResourceExistenceException;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.ErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/gifts")
public class GiftCertificateController {

    private static final String PRODUCES = "application/json";
    private static final String RESOURCE_NOT_FOUND = "resource.not.found";
    private static final String RESOURCE_NOT_UNIQUE = "resource.not.unique";
    private static final String VALID_EXCEPTION = "valid.exception";


    private final GiftCertificateService giftCertificateService;
    private final MessageSource messageSource;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, MessageSource messageSource) {
        this.giftCertificateService = giftCertificateService;
        this.messageSource = messageSource;
    }

    /**
     * Returns all {@link GiftCertificate} from database
     *
     * @return  {@link ResponseEntity} with a {@link HttpStatus} and all {@link GiftCertificate}.
     */
    @GetMapping(produces = PRODUCES)
    public ResponseEntity<?> getAll() {
        List<GiftCertificate> giftCertificates = giftCertificateService.getAll();
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    /**
     * Returns {@link GiftCertificate} from a database by id or throws {@link ResourceExistenceException} if {@link GiftCertificate} not exist
     *
     * @param id - id's {@link GiftCertificate}
     * @return {@link ResponseEntity} with a {@link HttpStatus} and a {@link GiftCertificate} object or a {@link ErrorData} object.
     */
    @GetMapping(value = "/{id}", produces = PRODUCES)
    public ResponseEntity<?> getGiftById(@PathVariable("id") Integer id) {
        GiftCertificate giftCertificate = giftCertificateService.getById(id);
        return new ResponseEntity<>(giftCertificate, HttpStatus.OK);
    }

    /**
     * Updates {@link GiftCertificate} by id's {@link GiftCertificate}
     *
     * @param id - id's {@link GiftCertificate} that should be updated
     * @param giftCertificate - object which will be updated
     * @return {@link ResponseEntity} with {@link HttpStatus} alone or additionally with {@link ErrorData} object.
     */
    @PatchMapping(value = "/{id}", produces = PRODUCES)
    public ResponseEntity<?> updateById(@PathVariable Integer id,@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.update(id, giftCertificate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Creates {@link GiftCertificate}
     *
     * @param giftCertificateDTO - object that will be converted to {@link GiftCertificate} and save to database
     * @return {@link ResponseEntity} with {@link HttpStatus} alone or additionally with {@link ErrorData} object.
     */
    @PostMapping(produces = PRODUCES)
    public ResponseEntity<?> create(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        giftCertificateService.create(giftCertificateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * @param fieldName the name of the field which will be used for searching {@link GiftCertificate} objects in a database.
     * @param isASC the sorting order
     * @return {@link ResponseEntity} with {@link HttpStatus} and all {@link GiftCertificate} objects or a {@link ErrorData} object.
     */
    @GetMapping(value = "/{fieldName}/{isASC}", produces = PRODUCES)
    public ResponseEntity<?> sortByFieldInOrder(@PathVariable("fieldName") String fieldName, @PathVariable("isASC") boolean isASC) {
        List<GiftCertificate> giftCertificates = giftCertificateService.sortByOrder(fieldName, isASC);
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    /**
     * Deletes {@link GiftCertificate} by id if exist
     *
     * @param id {@link GiftCertificate} id which will be deleted
     * @return {@link ResponseEntity} with {@link HttpStatus} alone or additionally with {@link ErrorData} object.
     */
    @DeleteMapping(value = "/{id}", produces = PRODUCES)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        giftCertificateService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(ResourceExistenceException.class)
    public ResponseEntity<ErrorData> handleResourceNotFoundException(Locale locale){
        return ErrorUtils.createResponseEntityForCustomError(messageSource.getMessage(RESOURCE_NOT_FOUND, null, locale),
                777, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorData> handleResourceNotUniqueException(Locale locale){
        return ErrorUtils.createResponseEntityForCustomError(messageSource.getMessage(RESOURCE_NOT_UNIQUE, null, locale),
                7777, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorData> handleValidationException(Locale locale){
        return ErrorUtils.createResponseEntityForCustomError(messageSource.getMessage(VALID_EXCEPTION, null, locale),
                777, HttpStatus.NOT_FOUND);
    }
}

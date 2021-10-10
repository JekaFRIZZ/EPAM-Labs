package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.ErrorData;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ResourceNotUniqueException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.ErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/gift")
@Validated
public class GiftCertificateController {

    private static final String PRODUCES = "application/json";
    private static final String RESOURCE_NOT_FOUND = "resource.not.found";
    private static final String RESOURCE_NOT_UNIQUE = "resource.not.unique";

    private final GiftCertificateService giftCertificateService;
    private final MessageSource messageSource;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, MessageSource messageSource) {
        this.giftCertificateService = giftCertificateService;
        this.messageSource = messageSource;
    }

    @GetMapping(produces = PRODUCES)
    public ResponseEntity<?> getAll() {
        List<GiftCertificate> giftCertificates = giftCertificateService.getAll();
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = PRODUCES)
    public ResponseEntity<?> getGiftById(@PathVariable("id") Long id) {
        GiftCertificate giftCertificate = giftCertificateService.getById(id);
        return new ResponseEntity<>(giftCertificate, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", produces = PRODUCES)
    public ResponseEntity<?> updateById(@PathVariable Long id,@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.update(id, giftCertificate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(produces = PRODUCES)
    public ResponseEntity<?> create(@RequestBody @Validated GiftCertificateDTO giftCertificateDTO) {
        giftCertificateService.create(giftCertificateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", produces = PRODUCES)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        giftCertificateService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorData> handleResourceNotFoundException(Locale locale){
        return ErrorUtils.createResponseEntityForCustomError(messageSource.getMessage(RESOURCE_NOT_FOUND, null, locale),
                777, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotUniqueException.class)
    public ResponseEntity<ErrorData> handleResourceNotUniqueException(Locale locale){
        return ErrorUtils.createResponseEntityForCustomError(messageSource.getMessage(RESOURCE_NOT_UNIQUE, null, locale),
                7777, HttpStatus.NOT_FOUND);
    }
}

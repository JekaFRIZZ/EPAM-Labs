package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.ErrorData;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ResourceNotUniqueException;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/tag")
public class TagController {

    private static final String PRODUCES = "application/json";
    private static final String RESOURCE_NOT_FOUND = "resource.not.found";
    private static final String RESOURCE_NOT_UNIQUE = "resource.not.unique";

    private final TagService tagService;
    private final MessageSource messageSource;

    @Autowired
    public TagController(TagService tagService, MessageSource messageSource) {
        this.tagService = tagService;
        this.messageSource = messageSource;
    }

    @GetMapping(produces = PRODUCES)
    public ResponseEntity<?> getAll() {
        List<Tag> tags = tagService.getAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = PRODUCES)
    public ResponseEntity<?> getTagById(@PathVariable("id") Integer id) {
        Tag tag = tagService.getById(id);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PostMapping(produces = PRODUCES)
    public ResponseEntity<?> create(@RequestBody TagDTO tagDTO) {
        tagService.create(tagDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", produces = PRODUCES)
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        tagService.deleteById(id);
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

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorData> handleValidationException(Locale locale){
        return ErrorUtils.createResponseEntityForCustomError(messageSource.getMessage("valid.exception", null, locale),
                777, HttpStatus.NOT_FOUND);
    }
}

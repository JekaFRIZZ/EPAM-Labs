package com.epam.esm.controller;

import com.epam.esm.entity.ErrorData;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ResourceNotUniqueException;
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

    private final TagService tagService;
    private final MessageSource messageSource;

    @Autowired
    public TagController(TagService tagService, MessageSource messageSource) {
        this.tagService = tagService;
        this.messageSource = messageSource;
    }

    @GetMapping(value = "getAll", produces = PRODUCES)
    public ResponseEntity<?> getAll() {
        List<Tag> tags = tagService.getAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping(value = "getById", produces = PRODUCES)
    public ResponseEntity<?> getTagById(@RequestParam("id") Long id) {
        Tag tag = tagService.getById(id);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @GetMapping(value = "getByName", produces = PRODUCES)
    public ResponseEntity<?> getTagByName(@RequestParam("name") String name) {
        Tag tag = tagService.getByName(name);

        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PostMapping(produces = PRODUCES)
    public ResponseEntity<?> create(@RequestBody Tag tag) {
        tagService.create(tag);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", produces = PRODUCES)
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorData> handleResourceNotFoundException(Locale locale){
        return ErrorUtils.createResponseEntityForCustomError(messageSource.getMessage("resource.not.found", null, locale),
                777, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotUniqueException.class)
    public ResponseEntity<ErrorData> handleResourceNotUniqueException(Locale locale){
        return ErrorUtils.createResponseEntityForCustomError(messageSource.getMessage("resource.not.unique", null, locale),
                7777, HttpStatus.NOT_FOUND);
    }
}

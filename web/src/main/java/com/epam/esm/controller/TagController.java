package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.ErrorData;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceExistenceException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private static final String PRODUCES = "application/json";
    private static final String RESOURCE_NOT_FOUND = "resource.not.found";
    private static final String RESOURCE_NOT_UNIQUE = "resource.not.unique";
    private static final String VALID_EXCEPTION = "valid.exception";

    private final TagService tagService;
    private final MessageSource messageSource;

    @Autowired
    public TagController(TagService tagService, MessageSource messageSource) {
        this.tagService = tagService;
        this.messageSource = messageSource;
    }

    /**
     * Returns all {@link Tag} from database
     *
     * @return  {@link ResponseEntity} with a {@link HttpStatus} and all {@link Tag}.
     */
    @GetMapping(produces = PRODUCES)
    public ResponseEntity<?> getAll() {
        List<Tag> tags = tagService.getAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    /**
     * Returns {@link Tag} from a database by id or throws {@link ResourceExistenceException} if {@link Tag} not exist
     *
     * @param id - id's {@link Tag}
     * @return {@link ResponseEntity} with a {@link HttpStatus} and a {@link Tag} object or a {@link ErrorData} object.
     */
    @GetMapping(value = "/{id}", produces = PRODUCES)
    public ResponseEntity<?> getTagById(@PathVariable("id") Integer id) {
        Tag tag = tagService.getById(id);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    /**
     * Creates {@link Tag}
     *
     * @param tagDTO - object that will be converted to {@link TagDTO} and save to database
     * @return {@link ResponseEntity} with {@link HttpStatus} alone or additionally with {@link ErrorData} object.
     */
    @PostMapping(produces = PRODUCES)
    public ResponseEntity<?> create(@RequestBody TagDTO tagDTO) {
        tagService.create(tagDTO);
        Tag tag = tagService.getByName(tagDTO.getName());
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    /**
     * Deletes {@link Tag} by id if exist
     *
     * @param id {@link Tag} id which will be deleted
     * @return {@link ResponseEntity} with {@link HttpStatus} alone or additionally with {@link ErrorData} object.
     */
    @DeleteMapping(value = "/{id}", produces = PRODUCES)
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        tagService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

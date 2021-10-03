package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    private static final String PRODUCES = "application/json";

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
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

    @DeleteMapping(value = "deleteById", produces = PRODUCES)
    public ResponseEntity<?> deleteById(@RequestParam("id") Long id) {
        tagService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    
}

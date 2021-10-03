package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagDao {

    private static final String GET_BY_ID = "SELECT * FROM tag WHERE id = ?";
    private static final String CREATE = "INSERT INTO tag(name) VALUES (?)";
    private static final String GET_ALL = "SELECT * FROM tag";
    private static final String DELETE = "DELETE FROM tag WHERE id = ?";
    private static final String GET_BY_NAME = "SELECT * FROM tag WHERE name = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tag> getAll() {
        return jdbcTemplate.query(GET_ALL, new BeanPropertyRowMapper<>());
    }

    public Optional<Tag> getById(Long id) {
        return getTagForSingleResult(GET_BY_ID, id);
    }

    public Optional<Tag> getByName(String name) {
        return getTagForSingleResult(GET_BY_NAME, name);
    }

    public void create(Tag tag) {
        jdbcTemplate.update(CREATE, tag.getName());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    private Optional<Tag> getTagForSingleResult(String query, Object... params) {
        List<Tag> tags = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(), params);

        if (tags.size() == 1) {
            return Optional.of(tags.get(0));
        } else if (tags.size() > 1) {
            throw new IllegalArgumentException("Size of list more than 1");
        }

        return Optional.empty();
    }
}

package com.pluralsight.conference.repository;

import com.pluralsight.conference.model.Speaker;
import com.pluralsight.conference.repository.util.SpeakerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("speakerRepository")
public class SpeakerRepositoryImpl implements SpeakerRepository {

    private final JdbcTemplate jdbcTemplate;

    public SpeakerRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Speaker> findAll() {
        return jdbcTemplate.query("SELECT * FROM speaker", SpeakerRowMapper.getInstance());
    }

    @Override
    public Speaker create(Speaker speaker) {
        KeyHolder keyHolder =  new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO speaker (name) VALUES (?)", new String[] {"id"});
            ps.setString(1,speaker.getName());
            return ps;
        }, keyHolder);

        Number id = keyHolder.getKey();
        assert id != null;
        return getSpeaker(id.intValue());
    }

    @Override
    public Speaker getSpeaker(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM speaker where id = ?", SpeakerRowMapper.getInstance(), id);
    }

    @Override
    public Speaker update(Speaker speaker) {
        jdbcTemplate.update("UPDATE speaker SET NAME = ? WHERE ID = ? ", speaker.getName(), speaker.getId());
        return speaker;
    }

    @Override
    public void update(List<Object[]> pairs) {
        jdbcTemplate.batchUpdate("UPDATE speaker SET SKILL = ? WHERE ID = ?", pairs);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM speaker WHERE ID = ? ", id);
    }
}

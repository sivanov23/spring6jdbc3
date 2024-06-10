package com.pluralsight.conference.repository.util;

import com.pluralsight.conference.model.Speaker;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpeakerRowMapper implements RowMapper<Speaker> {

    private static SpeakerRowMapper rowMapper = null;

    private SpeakerRowMapper(){

    }

    public static SpeakerRowMapper getInstance() {
        if(rowMapper == null) {
            rowMapper = new SpeakerRowMapper();
        }
        return rowMapper;
    }


    @Override
    public Speaker mapRow(ResultSet rs, int rowNum) throws SQLException {
        Speaker speaker = new Speaker();
        speaker.setId(rs.getInt("id"));
        speaker.setName(rs.getString("name"));
        speaker.setSkill(rs.getString("skill"));
        return speaker;
    }
}

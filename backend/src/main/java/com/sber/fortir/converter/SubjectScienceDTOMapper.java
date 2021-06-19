package com.sber.fortir.converter;

import com.sber.fortir.dto.SubjectScienceDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectScienceDTOMapper implements RowMapper<SubjectScienceDTO> {

    @Override
    public SubjectScienceDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        return new SubjectScienceDTO(
                resultSet.getLong("id"),
                resultSet.getLong("branch_id"),
                resultSet.getString("name"),
                resultSet.getString("frontier"),
                resultSet.getString("mckinsey"),
                resultSet.getString("gartner"),
                resultSet.getString("investors"),
                resultSet.getString("year_stat"),
                resultSet.getString("market_size"));
    }
}

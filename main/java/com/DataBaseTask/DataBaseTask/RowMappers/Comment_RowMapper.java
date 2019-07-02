package com.DataBaseTask.DataBaseTask.RowMappers;

import com.DataBaseTask.DataBaseTask.POJOClasses.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Comment_RowMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
        Comment comment = new Comment();
        comment.setPerson_id(resultSet.getInt("person_id"));
        comment.setEvent_id(resultSet.getInt("event_id"));
        comment.setText(resultSet.getString("text"));
        return comment;
    }
}

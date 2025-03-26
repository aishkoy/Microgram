package com.suslike.web.dao;

import com.suslike.web.models.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentDao {
    private final JdbcTemplate template;

    public Long addComment(Comment comment) {
        String sql = """
                INSERT INTO comments (post, commenter, content, commented_time)
                VALUES (?, ?, ?, now());
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,new String[]{"id"});
            ps.setLong(1,comment.getPost());
            ps.setLong(2,comment.getCommenter());
            ps.setString(3,comment.getContent());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey().longValue());
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        String sql = "SELECT * FROM comments WHERE post = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Comment.class), postId);
    }

    public Comment getCommentById(long id) {
        String sql = "SELECT * FROM comments WHERE id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Comment.class), id);
    }

    public void updateComment(Comment comment) {
        String sql = """
                UPDATE comments
                SET content = ?, commented_time = ?
                WHERE id = ?;
                """;
        template.update(sql, comment.getContent(), comment.getCommentedTime(), comment.getId());
    }

    public void deleteComment(long id) {
        String sql = "DELETE FROM comments WHERE id = ?";
        template.update(sql, id);
    }

    public Optional<Comment> getNewComments(Long postId) {
        String sql = """
                select * from COMMENTS
                where ID = ?
                """;
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(Comment.class), postId)
                )
        );
    }
}

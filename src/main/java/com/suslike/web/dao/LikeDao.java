package com.suslike.web.dao;

import com.suslike.web.models.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LikeDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Like> getAllLikesByPostID(Long id){
        String sql = """
                select * from LIKES where POST_ID = ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(Like.class),id);
    }

    public void create(Like like){
        String sql = """
                insert into LIKES (LIKER, POST_ID) values (:liker,:postId);
                """;
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource()
                .addValue("liker",like.getLiker())
                .addValue("postId",like.getPostId())
        );
    }

    public void delete(Long id){
        String sql = """
                delete from LIKES where ID = ?;
                """;
        template.update(sql,id);
    }

    public Optional<Like> getLikeByIdAndPost(Long likerId, Long id) {
        String sql = """
                select * from LIKES
                where LIKER = ? and POST_ID = ?;
                """;

        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                     template.query(sql, new BeanPropertyRowMapper<>(Like.class),likerId,id)
                )
        );
    }
}

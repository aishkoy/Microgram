package com.suslike.web.dao;

import com.suslike.web.models.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostDao {
    private final JdbcTemplate template;
    
    public List<Post> getAllPostById(Long id){
        String sql = "SELECT * FROM posts WHERE owner = ?;";
        return template.query(sql, new BeanPropertyRowMapper<>(Post.class), id);
    }

    public List<Post> getFavoritesPost(Long id){
        String sql = """
                SELECT * FROM posts where id in 
                            (select POST_ID from LIKES where liker = ?);""";
        return template.query(sql, new BeanPropertyRowMapper<>(Post.class), id);
    }

    public List<Post> getPostsFromFeed (Long id) {
        String sql = """
                select p.* from POSTS p, FOLLOWS f
                where p.OWNER = f.ACTUAL_USER
                and f.FOLLOWER = ?
                """;

        return template.query(sql, new BeanPropertyRowMapper<>(Post.class), id);
    }
    
    public Post getPostById(Long id) {
        String sql = "SELECT * FROM posts WHERE id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Post.class), id);
    }
    
    public void addPost(Post post) {
        String sql = """
            INSERT INTO posts (image, content, owner, posted_time)
            VALUES (?, ?, ?, ?);
            """;
        template.update(sql, post.getImage(), post.getContent(), post.getOwner(), post.getPostedTime());
    }
    
    public void deletePost(long id) {
        String sql = "DELETE FROM posts WHERE id = ?";
        template.update(sql, id);
    }
}

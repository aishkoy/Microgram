package kg.attractor.microgram.dao;

import kg.attractor.microgram.models.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostDao {
    private final JdbcTemplate template;
    
    public List<Post> getAllPostByEmail(String email){
        String sql = "SELECT * FROM posts WHERE owner = ?;";
        return template.query(sql, new BeanPropertyRowMapper<>(Post.class), email);
    }
    
    public List<Post> getAllPostByFollowedUser(String email){
        String sql = """
            SELECT * FROM posts P
            INNER JOIN public.follows F ON P.owner = F.follower
            WHERE actual_user = ?;
            """;
        return template.query(sql, new BeanPropertyRowMapper<>(Post.class), email);
    }

    public List<Post> getPostsFromFeed (String email) {
        String sql = """
                select p.* from POSTS p, FOLLOWS f
                where p.OWNER = f.ACTUAL_USER
                and f.FOLLOWER = ?
                """;

        return template.query(sql, new BeanPropertyRowMapper<>(Post.class), email);
    }
    
    public Post getPostById(long id) {
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
    
    public void updatePost(Post post) {
        String sql = """
            UPDATE posts
            SET image = ?, content = ?, posted_time = ?
            WHERE id = ?;
            """;
        template.update(sql, post.getImage(), post.getContent(), post.getPostedTime(), post.getId());
    }
    
    public void deletePost(long id) {
        String sql = "DELETE FROM posts WHERE id = ?";
        template.update(sql, id);
    }
}

package com.suslike.web.dao;

import com.suslike.web.models.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<UserModel> getUserById(Long id) {
        String sql = "select * from users where id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(UserModel.class),id)
                )
        );
    }
    public Optional<UserModel> getUserByEmail(String email){
        String sql = """
                select * from USERS
                where EMAIL = ?;
                """;
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(UserModel.class),email)
                )
        );
    }

    public Long createUser(UserModel user){
        String sql = """
            insert into USERS(email, gender, name, surname, username, password, avatar, about_me)
            VALUES (:email,:gender,:name,:surname,:username,:password,:avatar,:about_me);
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                        .addValue("email",user.getEmail())
                        .addValue("gender",user.getGender())
                        .addValue("name",user.getName())
                        .addValue("surname",user.getSurname())
                        .addValue("username",user.getUsername())
                        .addValue("password",user.getPassword())
                        .addValue("avatar",user.getAvatar())
                        .addValue("about_me",user.getAboutMe()),
                keyHolder, new String[]{"id"}
        );

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void addAuthority(Long userId, String role) {
        String sql = """
                insert into USER_AUTHORITY(user_id, authority_id)
                values (:userId,:roleId);
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("roleId", getTypeIdByName(role))
        );
    }

    public Long getTypeIdByName(String accType) {
        String sql = """
				select A.ID from AUTHORITIES A
				where UPPER(A.ROLE) like UPPER(?)
				""";
        return template.queryForObject(sql, Long.class, accType);
    }

    public void editUser(UserModel userModel) {
        String sql = """
                update USERS
                set NAME = :name, SURNAME = :surname, USERNAME = :username, GENDER = :gender,  ABOUT_ME = :about_me
                where id = :id
                """;

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource()
                .addValue("name",userModel.getName())
                .addValue("surname",userModel.getSurname())
                .addValue("username",userModel.getUsername())
                .addValue("gender",userModel.getGender())
                .addValue("about_me",userModel.getAboutMe())
                .addValue("id",userModel.getId())
        );
    }

    public List<UserModel> getAllFollowers(Long id) {
        String sql = """
                select * from USERS
                inner join PUBLIC.FOLLOWS F on USERS.ID = F.FOLLOWER
                where ACTUAL_USER = ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(UserModel.class),id);
    }

    public List<UserModel> getAllFollowings(Long id) {
        String sql = """
                select * from USERS
                inner join PUBLIC.FOLLOWS F on USERS.ID = F.ACTUAL_USER
                where FOLLOWER = ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(UserModel.class),id);
    }

    public Optional<UserModel> getUserByUsername(String username) {
        String sql = """
                SELECT * FROM USERS
                WHERE USERNAME = ?;
                """;

        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        template.query(sql, new BeanPropertyRowMapper<>(UserModel.class), username)
                )
        );
    }

    public List<UserModel> searchByUsernameOrEmail(String search) {
        String sql = """
                SELECT * FROM USERS
                WHERE USERNAME LIKE concat('%',?,'%')
                   OR EMAIL LIKE concat('%',?,'%')
                """;

        return template.query(sql, new BeanPropertyRowMapper<>(UserModel.class), search, search);
    }
    
    public void saveAvatar(String fileName, Long id) {
        String sql = """
                UPDATE users
                SET avatar = :avatarFileName
                WHERE id = :id
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("avatarFileName", fileName)
                .addValue("id", id));
    }

    public List<String> getUserRoles(Long id) {
        String sql = """
        SELECT a.role 
        FROM authorities a
        JOIN user_authority ua ON a.id = ua.authority_id
        JOIN users u ON ua.user_id = u.id
        WHERE u.id = ?
        """;

        return template.query(sql, (rs, rowNum) -> rs.getString("role"), id);
    }
}

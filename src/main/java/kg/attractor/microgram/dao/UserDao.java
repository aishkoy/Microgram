package kg.attractor.microgram.dao;

import kg.attractor.microgram.models.UserModel;
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
public class UserDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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

    public void createUser(UserModel user){
        String sql = """
                insert into USERS(email, gender, name, surname, username, password, avatar, about_me)\s
                VALUES (:email,:gender,:name,:surname,:username,:password,:avatar,:about_me);\s
                """;

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("email",user.getEmail())
                .addValue("gender",user.getGender())
                .addValue("name",user.getName())
                .addValue("surname",user.getSurname())
                .addValue("username",user.getUsername())
                .addValue("password",user.getPassword())
                .addValue("avatar",user.getAvatar())
                .addValue("about_me",user.getAboutMe())
        );
    }

    public void addAuthority(String email, String role) {
        String sql = """
                insert into USER_AUTHORITY(USER_EMAIL, authority_id)
                values (:userId,:roleId);
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("userId", email)
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
                where EMAIL = :email
                """;

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource()
                .addValue("name",userModel.getName())
                .addValue("surname",userModel.getSurname())
                .addValue("username",userModel.getUsername())
                .addValue("gender",userModel.getGender())
                .addValue("about_me",userModel.getAboutMe())
                .addValue("email",userModel.getEmail())
        );
    }

    public List<UserModel> getAllFollowers(String email) {
        String sql = """
                select * from USERS
                inner join PUBLIC.FOLLOWS F on USERS.EMAIL = F.FOLLOWER
                where ACTUAL_USER = ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(UserModel.class),email);
    }

    public List<UserModel> getAllFollowings(String email) {
        String sql = """
                select * from USERS
                inner join PUBLIC.FOLLOWS F on USERS.EMAIL = F.ACTUAL_USER
                where FOLLOWER = ?;
                """;
        return template.query(sql, new BeanPropertyRowMapper<>(UserModel.class),email);
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
    
    public void saveAvatar(String fileName, String email) {
        String sql = """
                UPDATE users
                SET avatar = :avatarFileName
                WHERE email = :userEmail
                """;
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("avatarFileName", fileName)
                .addValue("userEmail", email));
    }
}

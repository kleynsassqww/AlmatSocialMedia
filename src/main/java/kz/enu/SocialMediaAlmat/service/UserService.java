package kz.enu.SocialMediaAlmat.service;

import kz.enu.SocialMediaAlmat.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final JdbcTemplate jdbc;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<User> userRowMapper = new RowMapper<>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User u = new User();
            u.setId(rs.getLong("id"));
            u.setName(rs.getString("name"));
            u.setEmail(rs.getString("email"));
            u.setBio(rs.getString("bio"));
            u.setPassword(rs.getString("password"));
            String dateStr = rs.getString("created_at");
            if (dateStr != null) {
                try {
                    u.setCreatedAt(LocalDateTime.parse(dateStr.replace(" ", "T")));
                } catch (Exception e) {
                    u.setCreatedAt(LocalDateTime.now());
                }
            }
            try {
                u.setVerified(rs.getInt("is_verified") == 1);
                u.setConfirmationCode(rs.getString("confirmation_code"));
                u.setAdmin(rs.getInt("is_admin") == 1);
            } catch (SQLException ignore) {
            }
            return u;
        }
    };

    public List<User> getAll() {
        return jdbc.query("SELECT * FROM users ORDER BY id", userRowMapper);
    }

    public Optional<User> getById(Long id) {
        List<User> list = jdbc.query("SELECT * FROM users WHERE id = ?", userRowMapper, id);
        return list.stream().findFirst();
    }

    public User create(User user) {
        String encoded = passwordEncoder.encode(user.getPassword());
        jdbc.update("INSERT INTO users (name, email, bio, password, created_at, confirmation_code, is_verified, is_admin) VALUES (?, ?, ?, ?, datetime('now'), ?, ?, ?)",
                user.getName(), user.getEmail(), user.getBio(), encoded, user.getConfirmationCode(), user.isVerified() ? 1 : 0, user.isAdmin() ? 1 : 0);
        // fetch last insert id
        Long id = jdbc.queryForObject("SELECT last_insert_rowid()", Long.class);
        user.setId(id);
        user.setPassword(encoded);
        return user;
    }

    public Optional<User> update(Long id, User updated) {
        getById(id).ifPresent(u -> {
            String sql = "UPDATE users SET name = ?, email = ?, bio = ?";
            if (updated.getPassword() != null && !updated.getPassword().isEmpty()) {
                sql += ", password = ?";
                jdbc.update(sql + " WHERE id = ?", updated.getName(), updated.getEmail(), updated.getBio(), passwordEncoder.encode(updated.getPassword()), id);
            } else {
                jdbc.update(sql + " WHERE id = ?", updated.getName(), updated.getEmail(), updated.getBio(), id);
            }
        });
        return getById(id);
    }

    public boolean delete(Long id) {
        return jdbc.update("DELETE FROM users WHERE id = ?", id) > 0;
    }

    public int count() {
        Integer c = jdbc.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
        return c == null ? 0 : c;
    }

    public boolean existsByEmail(String email) {
        Integer c = jdbc.queryForObject("SELECT COUNT(*) FROM users WHERE lower(email)=lower(?)", Integer.class, email);
        return c != null && c > 0;
    }

    public boolean existsByName(String name) {
        Integer c = jdbc.queryForObject("SELECT COUNT(*) FROM users WHERE lower(name)=lower(?)", Integer.class, name);
        return c != null && c > 0;
    }

    public Optional<User> findByEmail(String email) {
        List<User> list = jdbc.query("SELECT * FROM users WHERE lower(email)=lower(?)", userRowMapper, email);
        return list.stream().findFirst();
    }

    public void markVerified(Long id) {
        jdbc.update("UPDATE users SET is_verified = 1 WHERE id = ?", id);
    }

    public Optional<User> authenticate(String email, String rawPassword) {
        List<User> list = jdbc.query("SELECT * FROM users WHERE lower(email)=lower(?)", userRowMapper, email);
        return list.stream().filter(u -> rawPassword != null && passwordEncoder.matches(rawPassword, u.getPassword())).findFirst();
    }
}

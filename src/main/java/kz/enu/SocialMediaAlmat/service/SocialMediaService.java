package kz.enu.SocialMediaAlmat.service;

import kz.enu.SocialMediaAlmat.model.SocialMedia;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class    SocialMediaService {

    private final JdbcTemplate jdbc;

    public SocialMediaService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<SocialMedia> rowMapper = new RowMapper<>() {
        @Override
        public SocialMedia mapRow(ResultSet rs, int rowNum) throws SQLException {
            SocialMedia s = new SocialMedia();
            s.setId(rs.getLong("id"));
            s.setPlatform(rs.getString("platform"));
            s.setProfileUrl(rs.getString("profile_url"));
            s.setUserId(rs.getLong("user_id"));
            return s;
        }
    };

    public List<SocialMedia> getAll() {
        return jdbc.query("SELECT * FROM social_media ORDER BY id", rowMapper);
    }

    public Optional<SocialMedia> getById(Long id) {
        List<SocialMedia> list = jdbc.query("SELECT * FROM social_media WHERE id = ?", rowMapper, id);
        return list.stream().findFirst();
    }

    public List<SocialMedia> getByUserId(Long userId) {
        return jdbc.query("SELECT * FROM social_media WHERE user_id = ?", rowMapper, userId);
    }

    public SocialMedia create(SocialMedia sm) {
        jdbc.update("INSERT INTO social_media (platform, profile_url, user_id) VALUES (?, ?, ?)", sm.getPlatform(), sm.getProfileUrl(), sm.getUserId());
        Long id = jdbc.queryForObject("SELECT last_insert_rowid()", Long.class);
        sm.setId(id);
        return sm;
    }

    public boolean delete(Long id) {
        return jdbc.update("DELETE FROM social_media WHERE id = ?", id) > 0;
    }

    public int count() {
        Integer c = jdbc.queryForObject("SELECT COUNT(*) FROM social_media", Integer.class);
        return c == null ? 0 : c;
    }
}

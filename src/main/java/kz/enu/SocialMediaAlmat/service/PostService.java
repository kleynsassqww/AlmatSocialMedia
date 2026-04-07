package kz.enu.SocialMediaAlmat.service;

import kz.enu.SocialMediaAlmat.model.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final JdbcTemplate jdbc;

    public PostService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Post> postRowMapper = new RowMapper<>() {
        @Override
        public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
            Post p = new Post();
            p.setId(rs.getLong("id"));
            p.setTitle(rs.getString("title"));
            p.setContent(rs.getString("content"));
            p.setUserId(rs.getLong("user_id"));
            p.setLikes(rs.getInt("likes"));
            String dateStr = rs.getString("created_at");
            if (dateStr != null) {
                try {
                    p.setCreatedAt(LocalDateTime.parse(dateStr.replace(" ", "T")));
                } catch (Exception e) {
                    p.setCreatedAt(LocalDateTime.now());
                }
            }
            return p;
        }
    };

    public List<Post> getAll() {
        return jdbc.query("SELECT * FROM posts ORDER BY id", postRowMapper);
    }

    public Optional<Post> getById(Long id) {
        List<Post> list = jdbc.query("SELECT * FROM posts WHERE id = ?", postRowMapper, id);
        return list.stream().findFirst();
    }

    public List<Post> getByUserId(Long userId) {
        return jdbc.query("SELECT * FROM posts WHERE user_id = ?", postRowMapper, userId);
    }

    public Post create(Post post) {
        jdbc.update("INSERT INTO posts (title, content, user_id, likes, created_at) VALUES (?, ?, ?, ?, datetime('now'))",
                post.getTitle(), post.getContent(), post.getUserId(), 0);
        Long id = jdbc.queryForObject("SELECT last_insert_rowid()", Long.class);
        post.setId(id);
        post.setLikes(0);
        return post;
    }

    public Optional<Post> update(Long id, Post updated) {
        getById(id).ifPresent(p -> {
            jdbc.update("UPDATE posts SET title = ?, content = ? WHERE id = ?", updated.getTitle(), updated.getContent(), id);
        });
        return getById(id);
    }

    public boolean delete(Long id) {
        return jdbc.update("DELETE FROM posts WHERE id = ?", id) > 0;
    }

    public Optional<Post> like(Long id) {
        return getById(id).map(p -> {
            jdbc.update("UPDATE posts SET likes = likes + 1 WHERE id = ?", id);
            return getById(id).orElse(p);
        });
    }

    public List<Post> getLatest(int n) {
        return jdbc.query("SELECT * FROM posts ORDER BY created_at DESC LIMIT ?", postRowMapper, n);
    }

    public int count() {
        Integer c = jdbc.queryForObject("SELECT COUNT(*) FROM posts", Integer.class);
        return c == null ? 0 : c;
    }
}

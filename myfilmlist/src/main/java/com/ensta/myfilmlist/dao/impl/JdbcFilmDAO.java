package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcFilmDAO implements FilmDAO {

    //private DataSource dataSource = ConnectionManager.getDataSource();
    //private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    //private JdbcTemplate jdbcTemplate = ConnectionManager.getJdbcTemplate();
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Film> findAll() {
        List<Film> films = jdbcTemplate.query("SELECT Film.id, Film.titre, Film.duree, Film.realisateur_id, Realisateur.nom, Realisateur.prenom, Realisateur.date_naissance, Realisateur.celebre FROM Film Join Realisateur on Film.realisateur_id=Realisateur.id", (rs, rownum) -> {
            Film film = new Film();
            film.setId(rs.getInt("Film.id"));
            film.setTitre(rs.getString("Film.titre"));
            film.setDuree(rs.getInt("Film.duree"));

            Realisateur realisateur = new Realisateur();
            realisateur.setId(rs.getInt("Film.realisateur_id"));
            realisateur.setNom(rs.getString("Realisateur.nom"));
            realisateur.setPrenom(rs.getString("Realisateur.prenom"));
            realisateur.setCelebre(rs.getBoolean("Realisateur.celebre"));
            realisateur.setDateNaissance(rs.getDate("Realisateur.date_naissance").toLocalDate());
            film.setRealisateur(realisateur);

            return film;

        });
        return films;

    }

    public Film save(Film film) {
        String insertQuery = "INSERT INTO Film(titre, duree, realisateur_id) VALUES(?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = conn -> {
            PreparedStatement statement = conn.prepareStatement(insertQuery,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, film.getTitre());
            statement.setInt(2, film.getDuree());
            statement.setLong(3, film.getRealisateur().getId());
            return statement;
        };
        jdbcTemplate.update(creator, keyHolder);
        long newId = keyHolder.getKey().longValue();

        film.setId(newId);
        return film;
    }

    public Optional<Film> findById(long id) {
        try {
            Film myfilm = jdbcTemplate.queryForObject("SELECT Film.id, Film.titre, Film.duree, Film.realisateur_id, Realisateur.nom, Realisateur.prenom, Realisateur.date_naissance, Realisateur.celebre FROM Film Join Realisateur on Film.realisateur_id=Realisateur.id WHERE Film.id=?", (rs, rownum) -> {

                Film film = new Film();
                film.setId(rs.getInt("Film.id"));
                film.setTitre(rs.getString("Film.titre"));
                film.setDuree(rs.getInt("Film.duree"));

                Realisateur realisateur = new Realisateur();
                realisateur.setId(rs.getInt("Film.realisateur_id"));
                realisateur.setNom(rs.getString("Realisateur.nom"));
                realisateur.setPrenom(rs.getString("Realisateur.prenom"));
                realisateur.setCelebre(rs.getBoolean("Realisateur.celebre"));
                realisateur.setDateNaissance(rs.getDate("Realisateur.date_naissance").toLocalDate());
                film.setRealisateur(realisateur);

                return film;

            }, id);
            return Optional.of(myfilm);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void delete(Film film) {
        String deleteQuery = "DELETE FROM Film WHERE id = ?";
        Object[] args = new Object[]{film.getId()};
        jdbcTemplate.update(deleteQuery, args);
    }

    public List<Film> findByRealisateurId(long realisateurId) {
        List<Film> films = jdbcTemplate.query("SELECT Film.id, Film.titre, Film.duree, Film.realisateur_id, Realisateur.nom, Realisateur.prenom, Realisateur.date_naissance, Realisateur.celebre FROM Film Join Realisateur on Film.realisateur_id=Realisateur.id WHERE Realisateur.id=?", (rs, rownum) -> {
            Film film = new Film();
            film.setId(rs.getInt("Film.id"));
            film.setTitre(rs.getString("Film.titre"));
            film.setDuree(rs.getInt("Film.duree"));

            Realisateur realisateur = new Realisateur();
            realisateur.setId(rs.getInt("Film.realisateur_id"));
            realisateur.setNom(rs.getString("Realisateur.nom"));
            realisateur.setPrenom(rs.getString("Realisateur.prenom"));
            realisateur.setCelebre(rs.getBoolean("Realisateur.celebre"));
            realisateur.setDateNaissance(rs.getDate("Realisateur.date_naissance").toLocalDate());
            film.setRealisateur(realisateur);

            return film;

        }, realisateurId);
        return films;
    }
}
package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.RealisateurDAO;
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

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcRealisateurDAO implements RealisateurDAO {

    //private JdbcTemplate jdbcTemplate = ConnectionManager.getJdbcTemplate();
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Realisateur> findAll() {
        List<Realisateur> realisateurs = jdbcTemplate.query("SELECT id, nom, prenom, date_naissance, celebre  FROM Realisateur", (rs, rownum) -> {

            Realisateur realisateur = new Realisateur();
            realisateur.setId(rs.getInt("id"));
            realisateur.setNom(rs.getString("nom"));
            realisateur.setPrenom(rs.getString("prenom"));
            realisateur.setCelebre(rs.getBoolean("celebre"));
            realisateur.setDateNaissance(rs.getDate("date_naissance").toLocalDate());

            return realisateur;

        });
        return realisateurs;
    }

    public Realisateur save(Realisateur realisateur) {
        String insertQuery = "INSERT INTO Realisateur(nom, prenom, date_naissance, celebre) VALUES(?,?,?,false)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = conn -> {
            PreparedStatement statement = conn.prepareStatement(insertQuery,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, realisateur.getNom());
            statement.setString(2, realisateur.getPrenom());
            statement.setDate(3, Date.valueOf(realisateur.getDateNaissance()));
            return statement;
        };
        jdbcTemplate.update(creator, keyHolder);
        long newId = keyHolder.getKey().longValue();

        realisateur.setId(newId);
        return realisateur;
    }
    public Realisateur findByNomAndPrenom(String nom, String prenom){
            Realisateur realisateur = jdbcTemplate.queryForObject("SELECT id, nom, prenom, date_naissance, celebre  FROM Realisateur where nom=? and prenom=?", (rs, rownum) -> {

                Realisateur real = new Realisateur();
                real.setId(rs.getInt("id"));
                real.setNom(rs.getString("nom"));
                real.setPrenom(rs.getString("prenom"));
                real.setCelebre(rs.getBoolean("celebre"));
                real.setDateNaissance(rs.getDate("date_naissance").toLocalDate());

                return real;

            },nom, prenom);
            return realisateur;
    }
    public Optional<Realisateur> findById(long id){
        try {
            Realisateur realisateur = jdbcTemplate.queryForObject("SELECT id, nom, prenom, date_naissance, celebre  FROM Realisateur where id=?", (rs, rownum) -> {

                Realisateur real = new Realisateur();
                real.setId(rs.getInt("id"));
                real.setNom(rs.getString("nom"));
                real.setPrenom(rs.getString("prenom"));
                real.setCelebre(rs.getBoolean("celebre"));
                real.setDateNaissance(rs.getDate("date_naissance").toLocalDate());

                return real;

            },id);
            return Optional.of(realisateur);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public Realisateur update(Realisateur realisateur){
        jdbcTemplate.update("UPDATE Realisateur SET nom=?, prenom=?, date_naissance=?, celebre=? WHERE id = ?",
                realisateur.getNom(), realisateur.getPrenom(), realisateur.getDateNaissance(),realisateur.isCelebre(),realisateur.getId());
        return realisateur;
    }

    public void delete(Realisateur realisateur) {
        String deleteQuery = "DELETE FROM Realisateur WHERE id = ?";
        Object[] args = new Object[]{realisateur.getId()};
        jdbcTemplate.update(deleteQuery, args);
    }
}

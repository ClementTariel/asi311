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
import org.springframework.stereotype.Repository;

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
            realisateur.setId(rs.getInt("realisateur_id"));
            realisateur.setNom(rs.getString("nom"));
            realisateur.setPrenom(rs.getString("prenom"));
            realisateur.setCelebre(rs.getBoolean("celebre"));
            realisateur.setDateNaissance(rs.getDate("date_naissance").toLocalDate());

            return realisateur;

        });
        return realisateurs;
    }
    public Realisateur findByNomAndPrenom(String nom, String prenom){
            List<Realisateur> realisateurs = jdbcTemplate.query("SELECT id, nom, prenom, date_naissance, celebre  FROM Realisateur where nom=? and prenom=?", (rs, rownum) -> {

                Realisateur realisateur = new Realisateur();
                realisateur.setId(rs.getInt("id"));
                realisateur.setNom(rs.getString("nom"));
                realisateur.setPrenom(rs.getString("prenom"));
                realisateur.setCelebre(rs.getBoolean("celebre"));
                realisateur.setDateNaissance(rs.getDate("date_naissance").toLocalDate());

                return realisateur;

            },nom, prenom);
            return realisateurs.get(0);
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
        //update
        jdbcTemplate.update("UPDATE Realisateur SET nom=?, prenom=?, date_naissance=?, celebre=? WHERE id = ?",
                realisateur.getNom(), realisateur.getPrenom(), realisateur.getDateNaissance(),realisateur.isCelebre(),realisateur.getId());
        return realisateur;
    }
}

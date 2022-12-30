package com.ensta.myfilmlist.dto;

import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;

import java.time.LocalDate;
import java.util.List;

public class RealisateurDTO {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String nom;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    private String prenom;

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    private LocalDate dateNaissance;

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    private List<FilmDTO> filmRealises;

    public List<FilmDTO> getFilmRealises() {
        return filmRealises;
    }

    public void setFilmRealises(List<FilmDTO> filmRealises) {
        this.filmRealises = filmRealises;
    }

    private boolean celebre;

    public boolean isCelebre() {
        return celebre;
    }

    public void setCelebre(boolean celebre) {
        this.celebre = celebre;
    }
    @Override
    public String toString() {
        return "RealisateurDTO [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance +", filmRealises=" + filmRealises + ", celebre=" + celebre + "]";
    }

}

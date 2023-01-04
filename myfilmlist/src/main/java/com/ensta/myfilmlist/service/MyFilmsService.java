package com.ensta.myfilmlist.service;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.form.RealisateurForm;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;

import java.util.List;

/**
 *
 */
public interface MyFilmsService {
    /**
     * Rend celebre ou non un realisateur selon le nombre de film realise
     * @param realisateur
     * @return le realisateur modifie
     * @throws ServiceException
     */
    public Realisateur updateRealisateurCelebre(Realisateur realisateur) throws ServiceException;

    /**
     * Calcule la la duree cumulee de films
     * @param films
     * @return la duree cumulee de films
     */
    public int calculerDureeTotale(List<Film> films);

    /**
     * Calcule la moyenne d'une liste de notes
     * @param notes
     * @return la moyenne de notes
     */
    public double calculerNoteMoyenne(double[] notes);

    public List<Realisateur> updateRealisateurCelebres(List<Realisateur> realisateurs) throws ServiceException;

    public List<FilmDTO> findAllFilms() throws ServiceException;

    public FilmDTO createFilm(FilmForm filmForm) throws ServiceException;

    public List<RealisateurDTO> findAllRealisateurs() throws ServiceException;

    public RealisateurDTO findRealisateurByNomAndPrenom(String nom, String prenom) throws ServiceException;

    public RealisateurDTO findRealisateurById(long id) throws ServiceException;

    public RealisateurDTO createRealisateur(RealisateurForm realisateurForm) throws ServiceException;

    public FilmDTO findFilmById(long id) throws ServiceException;

    public void deleteFilm(long id) throws ServiceException;

    public void deleteRealisateur(long id) throws ServiceException;
}

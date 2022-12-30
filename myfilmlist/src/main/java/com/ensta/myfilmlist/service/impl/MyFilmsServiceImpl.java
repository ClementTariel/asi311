package com.ensta.myfilmlist.service.impl;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dao.impl.JdbcFilmDAO;
import com.ensta.myfilmlist.dao.impl.JdbcRealisateurDAO;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.service.MyFilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.ensta.myfilmlist.mapper.FilmMapper.*;
import static com.ensta.myfilmlist.mapper.RealisateurMapper.*;

@Service
public class MyFilmsServiceImpl implements MyFilmsService {

    static final int NB_FILMS_MIN_REALISATEUR_CELEBRE = 3;

    @Autowired
    private JdbcFilmDAO filmDAO;// = new JdbcFilmDAO();

    @Autowired
    private JdbcRealisateurDAO realisateurDAO;// = new JdbcRealisateurDAO();

    @Override
    public Realisateur updateRealisateurCelebre(Realisateur realisateur) throws ServiceException {
        if (realisateur==null ||realisateur.getFilmRealises()==null){
            throw new ServiceException("Le realisateur ou sa liste de film est null");
        }
        realisateur.setCelebre(realisateur.getFilmRealises().size()>=NB_FILMS_MIN_REALISATEUR_CELEBRE);
        realisateur = realisateurDAO.update(realisateur);
        return realisateur;
    }

    @Override
    public int calculerDureeTotale(List<Film> films){
        int total = films.stream()
                .mapToInt(f -> f.getDuree())
                .sum();
        return total;
    }

    @Override
    public double calculerNoteMoyenne(double[] notes){
        double avg = Arrays.stream(notes)
                .average()
                .orElse(0.00);
        return ((double)Math.round(100*avg))/100;
    }

    @Override
    public List<Realisateur> updateRealisateurCelebres(List<Realisateur> realisateurs) throws ServiceException{
        List<Realisateur>  realisateursCelebres = realisateurs.stream()
                .filter(r -> {
                    try {
                        return updateRealisateurCelebre(r).isCelebre();
                    } catch (ServiceException e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
        return realisateursCelebres;
    }

    @Override
    public List<FilmDTO> findAllFilms() throws ServiceException{
        return FilmMapper.convertFilmToFilmDTOs(filmDAO.findAll());
    }

    @Override
    public FilmDTO createFilm(FilmForm filmForm) throws ServiceException{
        Film film = convertFilmFormToFilm(filmForm);
        if (film.getRealisateur() == null || realisateurDAO.findById(film.getRealisateur().getId()).isEmpty()) {
            throw new ServiceException();
        }
        Realisateur realisateur  = realisateurDAO.findById(film.getRealisateur().getId()).get();
        return convertFilmToFilmDTO(filmDAO.save(film));
    }

    @Override
    public List<RealisateurDTO> findAllRealisateurs() throws ServiceException{
        return convertRealisateurToRealisateurDTOs(realisateurDAO.findAll());
    }

    @Override
    public RealisateurDTO findRealisateurByNomAndPrenom(String nom, String prenom) throws ServiceException{
        return convertRealisateurToRealisateurDTO(realisateurDAO.findByNomAndPrenom(nom, prenom));
    }

    @Override
    public FilmDTO findFilmById(long id) throws ServiceException{
        Optional<Film> optionalFilm = filmDAO.findById(id);
        if (optionalFilm.isEmpty()){
            throw new ServiceException();
        }
        return convertFilmToFilmDTO(optionalFilm.get());
    }

    @Override
    public void deleteFilm(long id) throws ServiceException{
        Optional<Film> optionalFilm = filmDAO.findById(id);
        if (optionalFilm.isEmpty()){
            throw new ServiceException();
        }
        filmDAO.delete(optionalFilm.get());
    }
}

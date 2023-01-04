package com.ensta.myfilmlist.service.impl;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dao.impl.JdbcFilmDAO;
import com.ensta.myfilmlist.dao.impl.JdbcRealisateurDAO;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.form.RealisateurForm;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.service.MyFilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        if (realisateur==null){
            throw new ServiceException("Le realisateur est null");
        }
        List<Film> films = filmDAO.findByRealisateurId(realisateur.getId());
        if (films==null) {
            throw new ServiceException("La liste de films du realisateur est null");
        }
        realisateur.setCelebre(films.size()>=NB_FILMS_MIN_REALISATEUR_CELEBRE);
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
    @Transactional
    public FilmDTO createFilm(FilmForm filmForm) throws ServiceException{
        Film film = convertFilmFormToFilm(filmForm);
        if (film.getRealisateur() == null || realisateurDAO.findById(film.getRealisateur().getId()).isEmpty()) {
            throw new ServiceException("Le realisateur du filmForm n'est pas valide. ");
        }
        Realisateur realisateur  = realisateurDAO.findById(film.getRealisateur().getId()).get();
        film.setRealisateur(realisateur);
        film = filmDAO.save(film);
        realisateur = updateRealisateurCelebre(realisateur);
        film.setRealisateur(realisateur);
        return convertFilmToFilmDTO(film);
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
    public RealisateurDTO findRealisateurById(long id) throws ServiceException{
        Optional<Realisateur> optionalRealisateur = realisateurDAO.findById(id);
        if (optionalRealisateur.isEmpty()){
            throw new ServiceException("Le realisateur n'a pas ete trouve");
        }
        return convertRealisateurToRealisateurDTO(optionalRealisateur.get());
    }

    public RealisateurDTO createRealisateur(RealisateurForm realisateurForm) throws ServiceException{
        Realisateur realisateur = convertRealisateurFormToRealisateur(realisateurForm);
        realisateur = realisateurDAO.save(realisateur);
        return convertRealisateurToRealisateurDTO(realisateur);
    }

    @Override
    public FilmDTO findFilmById(long id) throws ServiceException{
        Optional<Film> optionalFilm = filmDAO.findById(id);
        if (optionalFilm.isEmpty()){
            throw new ServiceException("Le film n'a pas ete trouve");
        }
        return convertFilmToFilmDTO(optionalFilm.get());
    }

    @Override
    @Transactional
    public void deleteFilm(long id) throws ServiceException{
        Optional<Film> optionalFilm = filmDAO.findById(id);
        if (optionalFilm.isEmpty()){
            throw new ServiceException("Le film n'a pas ete trouve");
        }
        Film film = optionalFilm.get();
        Optional<Realisateur> optionalRealisateur  = realisateurDAO.findById(film.getRealisateur().getId());
        if (optionalRealisateur.isEmpty()){
            throw new ServiceException("Le realisateur du film n'a pas ete trouve");
        }
        Realisateur realisateur = optionalRealisateur.get();
        filmDAO.delete(film);
        updateRealisateurCelebre(realisateur);

    }

    public void deleteRealisateur(long id) throws ServiceException{
        Optional<Realisateur> optionalRealisateur = realisateurDAO.findById(id);
        if (optionalRealisateur.isEmpty()){
            throw new ServiceException("Le film n'a pas ete trouve");
        }
        Realisateur realisateur = optionalRealisateur.get();
        List<Film> films = filmDAO.findByRealisateurId(realisateur.getId());
        if (films != null && films.size()>0){
            throw new ServiceException("Le realisateur ne peut pas etre supprime car tous ses films n'ont pas ete supprimes (il en reste "+films.size()+")");
        }
        realisateurDAO.delete(realisateur);
    }
}

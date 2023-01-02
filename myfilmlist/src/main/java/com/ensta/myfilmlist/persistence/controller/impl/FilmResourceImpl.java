package com.ensta.myfilmlist.persistence.controller.impl;


import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.service.MyFilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/film")
public class FilmResourceImpl {

    @Autowired
    private MyFilmsService myFilmsService;

    @GetMapping
    public ResponseEntity<List<FilmDTO>> getAllFilms() throws ControllerException{
        try {
            List<FilmDTO> filmDTOs = myFilmsService.findAllFilms();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(filmDTOs);
        }catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<FilmDTO> getFilmById(@PathVariable long id) throws ControllerException{
        try {
            FilmDTO filmDTO = myFilmsService.findFilmById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(filmDTO);
        }catch (ServiceException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PostMapping
    ResponseEntity<FilmDTO> createFilm(@Valid FilmForm filmForm) throws ControllerException{
        try {
            FilmDTO filmDTO = myFilmsService.createFilm(filmForm);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(filmDTO);
        }catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> DeleteFilmById(@PathVariable long id) throws ControllerException{
        try {
            myFilmsService.deleteFilm(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        }catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

}

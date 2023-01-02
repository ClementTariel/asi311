package com.ensta.myfilmlist.persistence.controller;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.form.FilmForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// L’API s’appelle « Film » et utilise le Tag « Film »
// Le tag « Film » contient la description de l’API
@Api(tags = "Film")
@Tag(name = "Film", description = "Opération sur les films")
public interface FilmResource {
    @ApiOperation(value = "Lister les films", notes = "Permet de renvoyer la liste de tous les films.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des films a été renvoyée correctement")
    })
    ResponseEntity<List<FilmDTO>> getAllFilms() throws ControllerException;

    @ApiOperation(value = "Lister un film", notes = "Permet de renvoyer le film correspondant à l'id fourni.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le film a été trouvé")
    })
    ResponseEntity<FilmDTO> getFilmById(long id) throws ControllerException;

    @ApiOperation(value = "Créer un film", notes = "Permet d'ajouter le film dont on a fourni les caractéristiques.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Le film été créé correctement")
    })
    ResponseEntity<FilmDTO> createFilm(FilmForm filmForm) throws ControllerException;

    @ApiOperation(value = "Supprimer un film", notes = "Permet de supprimer le film correspondant à l'id fourni.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Le film été supprimé correctement")
    })
    ResponseEntity<?> DeleteFilmById(long id) throws ControllerException;
}

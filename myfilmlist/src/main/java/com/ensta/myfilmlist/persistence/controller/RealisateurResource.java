package com.ensta.myfilmlist.persistence.controller;

import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.form.RealisateurForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

// L’API s’appelle « Realisateur » et utilise le Tag « Realisateur »
// Le tag « Realisateur » contient la description de l’API
@Api(tags = "Realisateur")
@Tag(name = "Realisateur", description = "Opération sur les realisateurs")
public interface RealisateurResource {
    @ApiOperation(value = "Lister les realisateurs", notes = "Permet de renvoyer la liste de tous les realisateurs.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des realisateurs a été renvoyée correctement")
    })
    ResponseEntity<List<RealisateurDTO>> findAllRealisateurs() throws ControllerException;

    @ApiOperation(value = "Lister un realisateur", notes = "Permet de renvoyer le realisateur correspondant à l'id fourni.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le realisateur a été trouvé")
    })
    ResponseEntity<RealisateurDTO> getRealisateurById(long id) throws ControllerException;

    @ApiOperation(value = "Créer un realisateur", notes = "Permet d'ajouter le realisateur dont on a fourni les caractéristiques.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Le realisateur été créé correctement")
    })
    ResponseEntity<RealisateurDTO> createRealisateur(RealisateurForm realisateurForm) throws ControllerException;

    @ApiOperation(value = "Supprimer un realisateur", notes = "Permet de supprimer le realisateur correspondant à l'id fourni.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Le realisateur été supprimé correctement")
    })
    ResponseEntity<?> DeleteRealisateurById(long id) throws ControllerException;
}

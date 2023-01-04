package com.ensta.myfilmlist.persistence.controller.impl;

import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.RealisateurForm;
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
@RequestMapping("/realisateur")
@CrossOrigin("http://localhost:3000")
public class RealisateurResourceImpl {
    @Autowired
    private MyFilmsService myFilmsService;

    @GetMapping
    public ResponseEntity<List<RealisateurDTO>> findAllRealisateurs() throws ControllerException {
        try {
            List<RealisateurDTO> realisateurDTOs = myFilmsService.findAllRealisateurs();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(realisateurDTOs);
        }catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<RealisateurDTO> getRealisateurById(@PathVariable long id) throws ControllerException{
        try {
            RealisateurDTO realisateurDTO = myFilmsService.findRealisateurById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(realisateurDTO);
        }catch (ServiceException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PostMapping
    ResponseEntity<RealisateurDTO> createRealisateur(RealisateurForm realisateurForm) throws ControllerException{
        try {
            RealisateurDTO realisateurDTO = myFilmsService.createRealisateur(realisateurForm);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(realisateurDTO);
        }catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> DeleteRealisateurById(@PathVariable long id) throws ControllerException{
        try {
            myFilmsService.deleteRealisateur(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        }catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

}

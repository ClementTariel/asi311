package com.ensta.myfilmlist.mapper;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;

import java.util.List;
import java.util.stream.Collectors;

import static com.ensta.myfilmlist.mapper.FilmMapper.convertFilmToFilmDTOs;

/**
 * Effectue les conversions des Realisateurs entre les couches de l'application.
 */
public class RealisateurMapper {

	/**
	 * Convertit une liste de realisateurs en liste de DTO.
	 *
	 * @param realisateurs la liste des realisateurs
	 * @return Une liste non nulle de dtos construite a partir de la liste des realisateurs.
	 */
	public static List<RealisateurDTO> convertRealisateurToRealisateurDTOs(List<Realisateur> realisateurs) {
		return realisateurs.stream()
				.map(RealisateurMapper::convertRealisateurToRealisateurDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Convertit un realisateur en DTO.
	 *
	 * @param realisateur le realisateur a convertir
	 * @return Un DTO construit a partir des donnees du realisateur.
	 */
	public static RealisateurDTO convertRealisateurToRealisateurDTO(Realisateur realisateur) {
		RealisateurDTO realisateurDTO = new RealisateurDTO();
		realisateurDTO.setId(realisateur.getId());
		realisateurDTO.setNom(realisateur.getNom());
		realisateurDTO.setPrenom(realisateur.getPrenom());
		realisateurDTO.setDateNaissance(realisateur.getDateNaissance());
		List<Film> films = realisateur.getFilmRealises();
		if (films != null) {
			realisateurDTO.setFilmRealises(convertFilmToFilmDTOs(films));
		}
		realisateurDTO.setCelebre(realisateur.isCelebre());

		return realisateurDTO;
	}

	/**
	 * Convertit un DTO en realisateur.
	 *
	 * @param realisateurDTO le DTO a convertir
	 * @return Un Realisateur construit a partir des donnes du DTO.
	 */
	public static Realisateur convertRealisateurDTOToRealisateur(RealisateurDTO realisateurDTO) {
		Realisateur realisateur = new Realisateur();
		realisateur.setId(realisateurDTO.getId());
		realisateur.setId(realisateurDTO.getId());
		realisateur.setNom(realisateurDTO.getNom());
		realisateur.setPrenom(realisateurDTO.getPrenom());
		realisateur.setDateNaissance(realisateurDTO.getDateNaissance());
		List<FilmDTO> filmsDTO = realisateurDTO.getFilmRealises();
		if (filmsDTO != null) {
			realisateur.setFilmRealises(realisateurDTO.getFilmRealises().stream()
				.map(FilmMapper::convertFilmDTOToFilm)
				.collect(Collectors.toList()));
		}
		realisateur.setCelebre(realisateurDTO.isCelebre());

		return realisateur;
	}
}



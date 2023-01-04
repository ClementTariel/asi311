import axios from 'axios';
 
const FILM_URI = 'http://localhost:8080/film'
 
export function getAllFilms(){
	return axios.get(FILM_URI);
}

//to complete
export function postFilm(filmForm){
	return axios.post(FILM_URI,filmForm);
}

//to complete
export function putFilm(film){
	return axios.put(FILM_URI,film);
}

//to complete
export function deleteFilm(film_id){
	return axios.delete(FILM_URI+"/"+film_id);
}

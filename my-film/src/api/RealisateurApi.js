import axios from 'axios';
 
const FILM_URI = 'http://localhost:8080/realisateur'
 
export function getAllRealisateur(){
	return axios.get(FILM_URI);
}


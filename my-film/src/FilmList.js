import FilmCard from "./FilmCard";
import {mockFilms} from "./mock/FilmMock";
import { getAllFilms } from "./api/FilmApi";
import { useState, useEffect } from "react";

export default function FilmList() {
	const [films, setFilms] = useState([]);
 
    useEffect(() => {
        getAllFilms().then(reponse => {
            setFilms(reponse.data);
        }).catch(err => {
            console.log(err);
        })
    }, []);

    
    return films.map((film)=> {
        return <FilmCard key={film.id} film={film} />
    })

}

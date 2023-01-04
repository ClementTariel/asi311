import {Select, OutlinedInput, MenuItem, TextField, InputLabel} from "@mui/material"
import {getAllRealisateur } from "./api/RealisateurApi"
import { useState, useEffect } from "react";

export default function CreateFilmForm() {
    const [realisateurs, setRealisateurs] = useState([]);
    
    useEffect(() => {
        getAllRealisateur().then(reponse => {
            setRealisateurs(reponse.data);
        }).catch(err => {
            console.log(err);
        })
    }, []);

    const [realisateur, setRealisateur] = useState({});
    const handleChange = (event) => {
        const {
          target: { value },
        } = event;
        setRealisateur(
          value,
        );
      };

    return (
        <>
            <TextField id="textfield-titre" label="Titre" variant="outlined" />
            <TextField id="textfield-duree" label="Duree" variant="outlined" />
            <Select
                labelId="select-realisateur-label"
                id="select-realisateur"
                value={realisateur}
                onChange={handleChange}
                label="Realisateur"
                renderValue={(selected) => {
                    if (typeof(realisateur.id) == "undefined"){
                        return "Realisateur"
                    }
                    return realisateur.prenom + " " + realisateur.nom;
                }}
            >
                <MenuItem disabled value="">
                    <em>Realisateur</em>
                </MenuItem>
                {
                    realisateurs.map((realisateur)=> {
                        return <MenuItem key={realisateur.id} value={realisateur}>
                            {realisateur.prenom} {realisateur.nom}
                        </MenuItem>
                    })
                }
            </Select>
        </>
    )
  }

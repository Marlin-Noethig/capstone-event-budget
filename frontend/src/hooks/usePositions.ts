import {useEffect, useState} from "react";
import {Position} from "../model/Position";
import {getPositions} from "../service/api-service/positions-api-service";

export default function usePositions(){
    const [positions, setPositions] = useState<Position[]>([]);

    useEffect(() => {
        getPositions()
            .then(data => setPositions(data))
            .catch(error => console.error(error));
    },[])


    return positions
}
import {useEffect, useState} from "react";
import {Position} from "../model/Position";
import {getPositions, postPosition} from "../service/api-service/positions-api-service";

export default function usePositions(){
    const [positions, setPositions] = useState<Position[]>([]);

    useEffect(() => {
        getPositions()
            .then(data => setPositions(data))
            .catch(error => console.error(error));
    },[])


    const addNewPosition = (newPosition: Omit<Position, "id">) => {
        postPosition(newPosition)
            .then(addedPosition => setPositions([...positions, addedPosition]))
            .catch(error => console.error(error));
    }


    return {positions, addNewPosition}
}
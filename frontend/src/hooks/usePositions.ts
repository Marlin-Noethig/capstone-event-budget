import {useEffect, useState} from "react";
import {Position} from "../model/Position";
import {
    deletePositionById,
    getPositions,
    postPosition,
    putPositionById
} from "../service/api-service/positions-api-service";

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

    const updatePositionById = (id: string, positionToUpdate: Omit<Position, "id">) => {
        putPositionById(id, positionToUpdate)
            .then(returnedPosition => setPositions(positions.map(position => position.id === id ? returnedPosition : position)))
            .catch(error => console.error(error));
    }

    const removePositionById = (id: string) =>{
        deletePositionById(id)
            .then(() => setPositions(positions.filter(position => position.id !==id)))
            .catch(error => console.error(error))
    }

    return {positions, addNewPosition, updatePositionById, removePositionById}
}
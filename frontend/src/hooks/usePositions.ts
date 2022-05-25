import {useContext, useEffect, useState} from "react";
import {Position} from "../model/Position";
import {
    deletePositionById,
    getPositions,
    postPosition,
    putPositionById
} from "../service/api-service/positions-api-service";
import {AuthContext} from "../context/AuthProvider";

export default function usePositions(){
    const [positions, setPositions] = useState<Position[]>([]);
    const {token} = useContext(AuthContext);

    useEffect(() => {
        getPositions(token)
            .then(data => setPositions(data))
            .catch(error => console.error(error));
    },[token])

    const addNewPosition = (newPosition: Omit<Position, "id">) => {
        postPosition(newPosition, token)
            .then(addedPosition => setPositions([...positions, addedPosition]))
            .catch(error => console.error(error));
    }

    const updatePositionById = (id: string, positionToUpdate: Omit<Position, "id">) => {
        putPositionById(id, positionToUpdate, token)
            .then(returnedPosition => setPositions(positions.map(position => position.id === id ? returnedPosition : position)))
            .catch(error => console.error(error));
    }

    const removePositionById = (id: string) =>{
        deletePositionById(id, token)
            .then(() => setPositions(positions.filter(position => position.id !==id)))
            .catch(error => console.error(error))
    }

    return {positions, addNewPosition, updatePositionById, removePositionById}
}
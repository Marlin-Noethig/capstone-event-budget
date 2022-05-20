import axios from "axios";
import {Position} from "../../model/Position";


const baseUrl: string = "/api/positions/";

export const getPositions: () => Promise<Position[]> = () => {
    return axios.get(baseUrl)
        .then(response => response.data);
}

export const postPosition: (newPosition: Omit<Position, "id">) => Promise<Position> = (newPosition) => {
    return axios.post(baseUrl, newPosition)
        .then(response => response.data)
}

export const putPositionById: (id: string, positionToUpdate : Omit<Position, "id">) => Promise<Position> = (id, positionToUpdate) =>{
    return axios.put(baseUrl + id, positionToUpdate)
        .then(response => response.data)
}


export const deletePositionById: (id: string) => Promise<void> = (id) =>{
    return axios.delete(baseUrl + id)
}
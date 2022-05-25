import axios from "axios";
import {Position} from "../../model/Position";

const baseUrl: string = "/api/positions/";

export const getPositions: (token?: string) => Promise<Position[]> = (token) => {
    return axios.get(baseUrl, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data);
}

export const postPosition: (newPosition: Omit<Position, "id">, token?: string) => Promise<Position> = (newPosition, token) => {
    return axios.post(baseUrl, newPosition, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export const putPositionById: (id: string, positionToUpdate : Omit<Position, "id">, token?: string) => Promise<Position> = (id, positionToUpdate, token) =>{
    return axios.put(baseUrl + id, positionToUpdate, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export const deletePositionById: (id: string, token?: string) => Promise<void> = (id, token) =>{
    return axios.delete(baseUrl + id, token
        ? {headers: {"Authorization": token}}
        : {})
}
import axios from "axios";
import {EventData} from "../../model/EventData";

const baseUrl: string = "/api/events/"

export const getEvents: (token?: string) => Promise<EventData[]> = (token) =>{
    return axios.get(baseUrl, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data);
}

export const postEvent: (newEvent: Omit<EventData, "id">, token?: string) => Promise<EventData> = (newEvent, token) => {
    return axios.post(baseUrl, newEvent, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export const putEventById: (id: string, eventToUpdate: Omit<EventData, "id">, token?: string) => Promise<EventData> = (id, eventToUpdate, token) =>{
    return axios.put(baseUrl + id, eventToUpdate,token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export const deleteEventById: (id: string, token?: string) => Promise<void> = (id, token) =>{
    return axios.delete(baseUrl + id, token
        ? {headers: {"Authorization": token}}
        : {})
}
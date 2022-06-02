import axios from "axios";

const baseURl: string = "/api/events/"

export const getEvents: (token?: string) => Promise<Event[]> = (token) =>{
    return axios.get(baseURl, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data);
}
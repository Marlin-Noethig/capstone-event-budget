import axios from "axios";
import {EventData} from "../../model/EventData";

const baseURl: string = "/api/events/"

export const getEvents: (token?: string) => Promise<EventData[]> = (token) =>{
    return axios.get(baseURl, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data);
}
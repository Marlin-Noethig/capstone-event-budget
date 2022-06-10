import {User} from "../../model/User";
import axios from "axios";

const baseUrl: string = "/api/user/"

export const getAllUsers: (token?: string) => Promise<User[]> = (token) =>{
    return axios.get(baseUrl, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}
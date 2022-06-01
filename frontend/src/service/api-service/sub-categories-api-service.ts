import axios from "axios";
import {SubCategory} from "../../model/SubCategory";

const baseUrl: string = "/api/sub-categories/"

export const getSubCategories: (token?: string) => Promise<SubCategory[]> = (token) => {
    return axios.get(baseUrl, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data);
}
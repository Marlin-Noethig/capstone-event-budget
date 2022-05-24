import axios from "axios";
import {SubCategory} from "../../model/SubCategory";

const baseUrl: string = "/api/sub-categories"

export const getSubCategories: () => Promise<SubCategory[]> = () => {
    return axios.get(baseUrl)
        .then(response => response.data);
}
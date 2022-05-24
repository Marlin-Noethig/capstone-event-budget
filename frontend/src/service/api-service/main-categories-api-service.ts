import {MainCategory} from "../../model/MainCategory";
import axios from "axios";

const baseUrl: string = "/api/main-categories"

export const getMainCategories: () => Promise<MainCategory[]> = () => {
    return axios.get(baseUrl)
        .then(response => response.data);
}
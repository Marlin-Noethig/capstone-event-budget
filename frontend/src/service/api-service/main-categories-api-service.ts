import {MainCategory} from "../../model/MainCategory";
import axios from "axios";

const baseUrl: string = "/api/main-categories/"

export const getMainCategories: (token?: string) => Promise<MainCategory[]> = (token) => {
    return axios.get(baseUrl, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data);
}

export const patchMainCategoryUserIds: (id: string, updatedUserIds: string[], token?: string) => Promise<MainCategory> = (id, updatedUserIds, token) => {
    return axios.patch(baseUrl + id, updatedUserIds,token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}
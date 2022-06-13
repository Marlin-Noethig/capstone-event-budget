import axios from "axios";
import {SubCategory} from "../../model/SubCategory";

const baseUrl: string = "/api/sub-categories/"

export const getSubCategories: (token?: string) => Promise<SubCategory[]> = (token) => {
    return axios.get(baseUrl, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data);
}

export const postSubCategory: (newSubCategory: Omit<SubCategory, "id">, token?: string) => Promise<SubCategory> = (newSubcategory, token) => {
    return axios.post(baseUrl, newSubcategory, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export const putSubCategoryById: (id: string, subCategoryToUpdate : Omit<SubCategory, "id">, token?: string) => Promise<SubCategory> = (id, subCategoryToUpdate, token) =>{
    return axios.put(baseUrl + id, subCategoryToUpdate, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}

export const deleteSubCategoryById: (id: string, token?: string) => Promise<void> = (id, token) =>{
    return axios.delete(baseUrl + id, token
        ? {headers: {"Authorization": token}}
        : {})
}
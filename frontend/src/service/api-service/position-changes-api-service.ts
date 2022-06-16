import {PositionChange} from "../../model/PositionChange";
import axios from "axios";

const baseUrl: string = "/api/position-changes?subCategoryId="

export const getPositionChanges: (subCategoryId: string, token?: string) => Promise<PositionChange[]> = (subCategoryId, token) => {
    return axios.get(baseUrl + subCategoryId, token
        ? {headers: {"Authorization": token}}
        : {})
        .then(response => response.data)
}
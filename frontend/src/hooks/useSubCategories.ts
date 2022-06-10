import {useContext, useEffect, useState} from "react";
import {
    deleteSubCategoryById,
    getSubCategories,
    postSubCategory,
    putSubCategoryById
} from "../service/api-service/sub-categories-api-service";
import {SubCategory} from "../model/SubCategory";
import {AuthContext} from "../context/AuthProvider";
import {handleRequestError} from "../service/utils/errorHandlers";
import {toast} from "react-toastify";

export default function useSubCategories(){
    const [subCategories, setSubCategories] = useState<SubCategory[]>([]);
    const {token} = useContext(AuthContext);

    useEffect(() => {
        getSubCategories(token)
            .then(data => setSubCategories(data))
            .catch((error) => handleRequestError(error.response.status));
    }, [token])

    const addSubCategory = (newSubCategory: Omit<SubCategory, "id">) =>{
        postSubCategory(newSubCategory, token)
            .then(addedSubCategory => setSubCategories([...subCategories, addedSubCategory]))
            .then(() => toast.success(`${newSubCategory.name} has been added.`))
            .catch((error) => handleRequestError(error.response.status));
    }

    const updateSubCategoryById = (id: string, subCategoryToUpdate: Omit<SubCategory, "id">) => {
        putSubCategoryById(id, subCategoryToUpdate, token)
            .then(updatedSubCategory => setSubCategories(subCategories.map(subCategory => subCategory.id === id ? updatedSubCategory : subCategory)))
            .then(() => toast.success(`${subCategoryToUpdate.name} has been updated.`))
            .catch((error) => handleRequestError(error.response.status));
    }

    const removeSubCategoryById = (id: string, name: string) => {
        deleteSubCategoryById(id, token)
            .then(() => setSubCategories(subCategories.filter(subCategory => subCategory.id !== id)))
            .then(() => toast.success(`${name} has been deleted.`))
            .catch((error) => handleRequestError(error.response.status));
    }

    return {subCategories, addSubCategory, updateSubCategoryById, removeSubCategoryById}
}
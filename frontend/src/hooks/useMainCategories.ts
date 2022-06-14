import {useContext, useEffect, useState} from "react";
import {MainCategory} from "../model/MainCategory";
import {getMainCategories, patchMainCategoryUserIds} from "../service/api-service/main-categories-api-service";
import {AuthContext} from "../context/AuthProvider";
import {handleRequestError} from "../service/utils/errorHandlers";
import {toast} from "react-toastify";

export default function useMainCategories() {
    const [mainCategories, setMainCategories] = useState<MainCategory[]>([]);
    const {token} = useContext(AuthContext)

    useEffect(() => {
        getMainCategories(token)
            .then(data => setMainCategories(data))
            .catch((error) => handleRequestError(error.response.status));
    }, [token])

    const updateMainCategoryUserIds = (id: string, userIdsToUpdate: string[]) => {
        patchMainCategoryUserIds(id, userIdsToUpdate, token)
            .then(updatedMainCategory => setMainCategories(mainCategories.map(mainCategory => mainCategory.id === id ? updatedMainCategory : mainCategory)))
            .then(() => {
                const updatedMainCategory = mainCategories.find(mainCategory => mainCategory.id === id)
                if (updatedMainCategory){
                    toast.success(`Users for ${updatedMainCategory.name} have been updated.`)
                }
            })
            .catch((error) => handleRequestError(error.response.status));
    }

    return {mainCategories, updateMainCategoryUserIds}
}
import {useContext, useEffect, useState} from "react";
import {MainCategory} from "../model/MainCategory";
import {getMainCategories} from "../service/api-service/main-categories-api-service";
import {AuthContext} from "../context/AuthProvider";
import {handleRequestError} from "../service/utils/errorHandlers";

export default function useMainCategories(){
    const [mainCategories, setMainCategories] = useState<MainCategory[]>([]);
    const {token} = useContext(AuthContext)

    useEffect(() => {
        getMainCategories(token)
            .then(data => setMainCategories(data))
            .catch((error) => handleRequestError(error.response.status));
    }, [token])

    return {mainCategories}
}
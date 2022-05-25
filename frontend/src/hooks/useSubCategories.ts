import {useContext, useEffect, useState} from "react";
import {getSubCategories} from "../service/api-service/sub-categories-api-service";
import {SubCategory} from "../model/SubCategory";
import {AuthContext} from "../context/AuthProvider";

export default function useSubCategories(){
    const [subCategories, setSubCategories] = useState<SubCategory[]>([]);
    const {token} = useContext(AuthContext);

    useEffect(() => {
        getSubCategories(token)
            .then(data => setSubCategories(data))
            .catch(error => console.error(error))
    }, [token])

    return {subCategories}
}
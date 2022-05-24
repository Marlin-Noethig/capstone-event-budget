import {useEffect, useState} from "react";
import {getSubCategories} from "../service/api-service/sub-categories-api-service";
import {SubCategory} from "../model/SubCategory";

export default function useSubCategories(){
    const [subCategories, setSubCategories] = useState<SubCategory[]>([]);

    useEffect(() => {
        getSubCategories()
            .then(data => setSubCategories(data))
            .catch(error => console.error(error))
    }, [])

    return {subCategories}
}
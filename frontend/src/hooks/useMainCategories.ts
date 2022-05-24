import {useEffect, useState} from "react";
import {MainCategory} from "../model/MainCategory";
import {getMainCategories} from "../service/api-service/main-categories-api-service";

export default function useMainCategories(){
    const [mainCategories, setMainCategories] = useState<MainCategory[]>([]);

    useEffect(() => {
        getMainCategories()
            .then(data => setMainCategories(data))
            .catch(error => console.error(error))
    }, [])

    return {mainCategories}
}
import {useContext, useEffect, useState} from "react";
import {PositionChange} from "../model/PositionChange";
import {AuthContext} from "../context/AuthProvider";
import {getPositionChanges} from "../service/api-service/position-changes-api-service";
import {handleRequestError} from "../service/utils/errorHandlers";

export default function usePositionChanges(subCategoryId: string) {
    const [positionChanges, setPositionChanges] = useState<PositionChange[]>([]);
    const {token} = useContext(AuthContext);

    useEffect(() => {
        getPositionChanges(subCategoryId, token)
            .then(data => setPositionChanges(data))
            .catch((error) => handleRequestError(error.response.status));
    }, [subCategoryId, token])

    return {positionChanges}
}
import {useContext, useEffect, useState} from "react";
import {User} from "../model/User";
import {AuthContext} from "../context/AuthProvider";
import {getAllUsers} from "../service/api-service/users-api-service";
import {handleRequestError} from "../service/utils/errorHandlers";

export default function useUsers(){
    const [users, setUsers] = useState<User[]>([]);
    const {token} = useContext(AuthContext);

    useEffect(() => {
        getAllUsers(token)
            .then(data => setUsers(data))
            .catch((error) => handleRequestError(error.response.status));
    }, [token])

    return {users}
}
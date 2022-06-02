import {useContext, useEffect, useState} from "react";
import {AuthContext} from "../context/AuthProvider";
import {getEvents} from "../service/api-service/events-api-service";
import {handleRequestError} from "../service/utils/errorHandlers";

export default function useEvents(){
    const [events, setEvents] = useState<Event[]>([]);
    const {token} = useContext(AuthContext);

    useEffect(() => {
        getEvents(token)
            .then(data => setEvents(data))
            .catch((error) => handleRequestError(error.response.status));
    })

    return {events}
}
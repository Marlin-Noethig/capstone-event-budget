import {useContext, useEffect, useState} from "react";
import {AuthContext} from "../context/AuthProvider";
import {deleteEventById, getEvents, postEvent, putEventById} from "../service/api-service/events-api-service";
import {handleRequestError} from "../service/utils/errorHandlers";
import {EventData} from "../model/EventData";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";

export default function useEvents() {
    const [events, setEvents] = useState<EventData[]>([]);
    const {token} = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        getEvents(token)
            .then(data => setEvents(data))
            .catch((error) => handleRequestError(error.response.status));
    }, [token])

    const addEvent = (newEvent: Omit<EventData, "id">) => {
        postEvent(newEvent, token)
            .then(addedEvent => {
                setEvents([...events, addedEvent])
                navigate("/events/" + addedEvent.id)
            })
            .then(() => toast.success(`${newEvent.name} has been added.`))
            .catch((error) => handleRequestError(error.response.status));
    }

    const updateEventById = (id: string, eventToUpdate: Omit<EventData, "id">) => {
        putEventById(id, eventToUpdate, token)
            .then(updatedEvent => setEvents(events.map(event => event.id === id ? updatedEvent : event)))
            .then(() => toast.success(`${eventToUpdate.name} has been updated.`))
            .catch((error) => handleRequestError(error.response.status));
    }

    const removeEventById = (id: string, name: string) => {
        deleteEventById(id, token)
            .then(() => setEvents(events.filter(event => event.id !== id)))
            .then(() => toast.success(`${name} has been removed.`))
            .catch((error) => handleRequestError(error.response.status));
    }

    return {events, addEvent, updateEventById, removeEventById}
}
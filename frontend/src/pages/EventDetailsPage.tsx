import {EventData} from "../model/EventData";
import useUsers from "../hooks/useUsers";
import {useParams} from "react-router-dom";
import DisplayEventDetails from "../components/event-details-page/DisplayEventDetails";
import "./styles/EventDetailsPage.css"
import {useState} from "react";
import WriteEvent from "../components/event-details-page/WriteEvent";
import DeletionDialogue from "../components/DeletionDialogue";

type EventDetailsPageProps = {
    events: EventData [],
    addEvent: (newEvent: Omit<EventData, "id">) => void,
    updateEvent: (id: string, eventToUpdate: Omit<EventData, "id">) => void,
    removeEvent: (id: string, name: string) => void,
}

export default function EventDetailsPage({events, addEvent, updateEvent, removeEvent}: EventDetailsPageProps) {
    const {users} = useUsers();
    const {idOfEvent} = useParams();

    const displayedEvent = events.find(event => event.id === idOfEvent);
    const usersWithUserRole = users.filter(user => user.role === "USER")

    const [enableEdit, setEnableEdit] = useState<boolean>(false);

    const toggleEnableEdit = () => {
        setEnableEdit(!enableEdit);
    }

    return (<div className={"event-details-page"}>
            { enableEdit || (!displayedEvent && idOfEvent === "new") ?
                <WriteEvent users={usersWithUserRole}
                            event={displayedEvent}
                            addEvent={addEvent}
                            updateEvent={updateEvent}
                            toggleEnableEdit={toggleEnableEdit}
                />
                :
                <div>
                    <DisplayEventDetails event={displayedEvent} users={usersWithUserRole}/>
                    <div className={"event-details-buttons"}>
                        <button onClick={toggleEnableEdit}>edit</button>
                        <DeletionDialogue event={displayedEvent} deleteFunction={removeEvent}/>
                    </div>
                </div>
            }
        </div>
    )
}
import {EventData} from "../model/EventData";
import useUsers from "../hooks/useUsers";
import {useParams} from "react-router-dom";
import DisplayEventDetails from "../components/EventDetailsPage/DisplayEventDetails";
import "./styles/EventDetailsPage.css"

type EventDetailsPageProps = {
    events: EventData [],
}

export default function EventDetailsPage({events}: EventDetailsPageProps) {
    const {users} = useUsers();
    const {idOfEvent} = useParams();

    const displayedEvent = events.find(event => event.id === idOfEvent);

    if (!displayedEvent || !idOfEvent) {
        return <div>
            Event with provided id has not been found.
        </div>
    }

    return (<div className={"event-details-page"}>
            <DisplayEventDetails event={displayedEvent} users={users}/>
            <button>edit</button>
        </div>
    )
}
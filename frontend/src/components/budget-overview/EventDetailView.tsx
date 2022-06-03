import "./styles/EventDetailView.css"
import {EventData} from "../../model/EventData";
import {useNavigate} from "react-router-dom";

type EventDetailViewProps = {
    event: EventData
}

export default function EventDetailView({event}: EventDetailViewProps){

    const navigate = useNavigate()

    return(
        <div className={"event-detail-view-wrapper"}>
            <button onClick={() => navigate("/")}>Back</button>
            <div className={"event-detail-view-container"}>
                <p className={"event-detail-name"}>Event: {event.name}</p>
                <p>Max. Capacity: {event.guests}</p>
                <p>From: {new Date(event.startDate).toLocaleDateString()}</p>
                <p>Until: {new Date(event.endDate).toLocaleDateString()}</p>
            </div>
        </div>
    )
}

import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";
import {Position} from "../../model/Position";
import {EventData} from "../../model/EventData";

export type EventCardProps = {
    event: EventData;
    mainCategories: MainCategory[],
    subCategories: SubCategory[],
    positions: Position[],
}

export default function EventCard({event, mainCategories, subCategories, positions}: EventCardProps){
    return(
        <div className={"event-card-container"}>
            <div className={"event-heading"}>{event.name}</div>
        </div>
    )
}
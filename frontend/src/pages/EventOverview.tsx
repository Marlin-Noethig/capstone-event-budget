import React from "react";
import {MainCategory} from "../model/MainCategory";
import {SubCategory} from "../model/SubCategory";
import {Position} from "../model/Position";
import EventCard from "../components/event-overview/EventCard";
import {EventData} from "../model/EventData";
import "./styles/EventOverview.css"

type EventOverviewProps = {
    events: EventData [],
    mainCategories: MainCategory[],
    subCategories: SubCategory[],
    positions: Position[]
}

export default function EventOverview({events, mainCategories, subCategories, positions}: EventOverviewProps) {

    return (
        <div className={"event-overview-container"}>
            {events.map(event => <EventCard key={event.id}
                                            event={event}
                                            mainCategories={mainCategories}
                                            subCategories={subCategories}
                                            positions={positions}/>)}
        </div>
    )
}
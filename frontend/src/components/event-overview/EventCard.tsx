import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";
import {Position} from "../../model/Position";
import {EventData} from "../../model/EventData";
import BalanceView from "../budget-overview/BalanceView";
import {getBalance} from "../../service/utils/sumHelpers";
import {useContext} from "react";
import {AuthContext} from "../../context/AuthProvider";
import MainCategoryOverview from "./MainCategoryOverview";
import "./styles/EventCard.css"

export type EventCardProps = {
    event: EventData;
    mainCategories: MainCategory[],
    subCategories: SubCategory[],
    positions: Position[],
}

export default function EventCard({event, mainCategories, subCategories, positions}: EventCardProps) {

    const positionsOfEvent = positions.filter(position => position.eventId === event.id);
    const {showBalance} = useContext(AuthContext)

    return (
            <div className={"event-card"}>
                <div className={"event-card-heading"}>{event.name}</div>
                {mainCategories.map(mainCategory => <MainCategoryOverview key={mainCategory.id}
                                                                          mainCategory={mainCategory}
                                                                          subCategories={subCategories}
                                                                          positions={positionsOfEvent}/>
                )}
                {showBalance && <BalanceView sum={getBalance(positionsOfEvent, subCategories, mainCategories)}/>}
            </div>
    )
}
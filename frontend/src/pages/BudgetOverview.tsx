import EventDetailView from "../components/budget-overview/EventDetailView";
import MainCategoryView from "../components/budget-overview/MainCategoryView";
import {getBalance} from "../service/utils/accountingHelpers";
import BalanceView from "../components/budget-overview/BalanceView";
import "./styles/BudgetOverview.css"
import {MainCategory} from "../model/MainCategory";
import {SubCategory} from "../model/SubCategory";
import {Position} from "../model/Position";
import {useContext, useState} from "react";
import {AuthContext} from "../context/AuthProvider";
import {EventData} from "../model/EventData";
import {useParams} from "react-router-dom";

type BudgetOverviewProps = {
    events: EventData[],
    mainCategories: MainCategory[],
    subCategories: SubCategory[],
    positions: Position[],
    addNewPosition: (newPosition: Omit<Position, "id">) => void,
    deletePosition: (id: string, name: string) => void,
    updatePosition: (id: string, newPosition: Omit<Position, "id">) => void
}

export default function BudgetOverview({
                                           events,
                                           mainCategories,
                                           subCategories,
                                           positions,
                                           addNewPosition,
                                           deletePosition,
                                           updatePosition,
                                       }: BudgetOverviewProps) {

    const {showBalance} = useContext(AuthContext);
    const {idOfEvent} = useParams();
    const displayedEvent = events.find(event => event.id === idOfEvent);
    const positionsOfEvent = positions.filter(position => position.eventId === idOfEvent);

    const [collapseAll, setCollapseAll] = useState<boolean | undefined>(undefined);

    if (!displayedEvent || !idOfEvent) {
        return <div>
            Event with provided id has not been found.
        </div>
    }

    return (
        <div className={"budget-overview-container"}>
            <div className={"budget-overview-wrapper"}>
                <EventDetailView event={displayedEvent}/>
                <div className={"collapse-hide-wrapper"}>
                    <div className={"collapse-hide-buttons"}>
                        <div onClick={() => setCollapseAll(true)}>COLLAPSE ALL</div>
                        <div onClick={() => setCollapseAll(false)}>HIDE ALL</div>
                    </div>
                </div>
                {mainCategories.map(mainCategory => <MainCategoryView key={mainCategory.id}
                                                                      mainCategory={mainCategory}
                                                                      subCategories={subCategories}
                                                                      positions={positionsOfEvent}
                                                                      addNewPosition={addNewPosition}
                                                                      deletePosition={deletePosition}
                                                                      updatePosition={updatePosition}
                                                                      idOfEvent={idOfEvent}
                                                                      collapseAll={collapseAll}
                />)}
            </div>
            {showBalance && <BalanceView sum={getBalance(positionsOfEvent, subCategories, mainCategories)}
                                         onBudgedList={true}/>}
        </div>
    )
}
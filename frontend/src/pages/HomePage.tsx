import {Route, Routes} from "react-router-dom";
import EventOverview from "./EventOverview";
import BudgetOverview from "./BudgetOverview";
import React from "react";
import usePositions from "../hooks/usePositions";
import useMainCategories from "../hooks/useMainCategories";
import useSubCategories from "../hooks/useSubCategories";
import useEvents from "../hooks/useEvents";

export default function HomePage() {

    const {positions, addNewPosition, updatePositionById, removePositionById} = usePositions();
    const {mainCategories} = useMainCategories();
    const {subCategories} = useSubCategories();
    const {events} = useEvents();

    return (
        <div className={"home-page"}>
            <Routes>
                <Route path="/"
                       element={<EventOverview events={events}
                                               mainCategories={mainCategories}
                                               subCategories={subCategories}
                                               positions={positions}
                       />}
                />
                <Route path="budget-overview/"
                       element={<BudgetOverview mainCategories={mainCategories}
                                                subCategories={subCategories}
                                                positions={positions}
                                                addNewPosition={addNewPosition}
                                                deletePosition={removePositionById}
                                                updatePosition={updatePositionById}
                       />}
                />
            </Routes>
        </div>
    )
}
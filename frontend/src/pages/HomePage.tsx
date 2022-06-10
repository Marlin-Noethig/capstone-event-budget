import {Route, Routes} from "react-router-dom";
import EventOverview from "./EventOverview";
import BudgetOverview from "./BudgetOverview";
import React from "react";
import usePositions from "../hooks/usePositions";
import useMainCategories from "../hooks/useMainCategories";
import useSubCategories from "../hooks/useSubCategories";
import useEvents from "../hooks/useEvents";
import AdminPage from "./AdminPage";

export default function HomePage() {

    const {positions, addNewPosition, updatePositionById, removePositionById} = usePositions();
    const {mainCategories} = useMainCategories();
    const {subCategories} = useSubCategories();
    const {events} = useEvents();

    return (
        <div className={"home-page"}>
            <Routes>
                <Route path={"/"}
                       element={<EventOverview events={events}
                                               mainCategories={mainCategories}
                                               subCategories={subCategories}
                                               positions={positions}
                       />}
                />
                <Route path={"budget-overview/:idOfEvent"}
                       element={<BudgetOverview events={events}
                                                mainCategories={mainCategories}
                                                subCategories={subCategories}
                                                positions={positions}
                                                addNewPosition={addNewPosition}
                                                deletePosition={removePositionById}
                                                updatePosition={updatePositionById}
                       />}
                />
                <Route path={"admin/*"}
                       element={<AdminPage mainCategories={mainCategories}
                                           subCategories={subCategories}
                                           events={events}/>}>
                </Route>
            </Routes>
        </div>
    )
}
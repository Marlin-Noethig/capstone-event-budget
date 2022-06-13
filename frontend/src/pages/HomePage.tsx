import {Route, Routes} from "react-router-dom";
import EventOverview from "./EventOverview";
import BudgetOverview from "./BudgetOverview";
import React from "react";
import usePositions from "../hooks/usePositions";
import useMainCategories from "../hooks/useMainCategories";
import useSubCategories from "../hooks/useSubCategories";
import useEvents from "../hooks/useEvents";
import AdminPage from "./AdminPage";
import EventDetailsPage from "./EventDetailsPage";

export default function HomePage() {

    const {positions, addNewPosition, updatePositionById, removePositionById} = usePositions();
    const {mainCategories} = useMainCategories();
    const {subCategories, addSubCategory, updateSubCategoryById, removeSubCategoryById} = useSubCategories();
    const {events} = useEvents();

    const eventsSortedByDate = [...events].sort((a, b) => Number(new Date(b.startDate))- Number(new Date(a.startDate)))

    return (
        <div className={"home-page"}>
            <Routes>
                <Route path={"/"}
                       element={<EventOverview events={eventsSortedByDate}
                                               mainCategories={mainCategories}
                                               subCategories={subCategories}
                                               positions={positions}
                       />}
                />
                <Route path={"budget-overview/:idOfEvent"}
                       element={<BudgetOverview events={eventsSortedByDate}
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
                                           addSubCategory={addSubCategory}
                                           updateSubCategory={updateSubCategoryById}
                                           removeSubCategory={removeSubCategoryById}
                                           events={eventsSortedByDate}/>}>
                </Route>
                <Route path={"events/:idOfEvent"}
                       element={<EventDetailsPage events={events}/>}>

                </Route>
            </Routes>
        </div>
    )
}
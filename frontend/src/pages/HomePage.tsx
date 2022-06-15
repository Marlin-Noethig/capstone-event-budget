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
    const {mainCategories, updateMainCategoryUserIds} = useMainCategories();
    const {subCategories, addSubCategory, updateSubCategoryById, removeSubCategoryById} = useSubCategories();
    const {events, addEvent, updateEventById, removeEventById} = useEvents();

    const eventsSortedByDate = [...events].sort((a, b) => Number(new Date(b.startDate)) - Number(new Date(a.startDate)))
    const positionsSortedAlphabetical = [...positions].sort((a, b) => a.name.localeCompare(b.name))
    const subCategoriesAlphabetical = [...subCategories].sort((a, b) => a.name.localeCompare(b.name))

    return (
        <div className={"home-page"}>
            <Routes>
                <Route path={"/"}
                       element={<EventOverview events={eventsSortedByDate}
                                               mainCategories={mainCategories}
                                               subCategories={subCategoriesAlphabetical}
                                               positions={positionsSortedAlphabetical}
                       />}
                />
                <Route path={"budget-overview/:idOfEvent"}
                       element={<BudgetOverview events={eventsSortedByDate}
                                                mainCategories={mainCategories}
                                                subCategories={subCategoriesAlphabetical}
                                                positions={positionsSortedAlphabetical}
                                                addNewPosition={addNewPosition}
                                                deletePosition={removePositionById}
                                                updatePosition={updatePositionById}
                       />}
                />
                <Route path={"admin/*"}
                       element={<AdminPage mainCategories={mainCategories}
                                           updateMainCategory={updateMainCategoryUserIds}
                                           subCategories={subCategoriesAlphabetical}
                                           addSubCategory={addSubCategory}
                                           updateSubCategory={updateSubCategoryById}
                                           removeSubCategory={removeSubCategoryById}
                                           events={eventsSortedByDate}/>}>
                </Route>
                <Route path={"events/:idOfEvent"}
                       element={<EventDetailsPage events={events}
                                                  addEvent={addEvent}
                                                  updateEvent={updateEventById}
                                                  removeEvent={removeEventById}/>}>
                </Route>
            </Routes>
        </div>
    )
}
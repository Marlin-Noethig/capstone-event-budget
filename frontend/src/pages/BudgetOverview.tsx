import EventDetailView from "../components/budget-overview/EventDetailView";
import {MainCategory} from "../model/MainCategory";
import MainCategoryView from "../components/budget-overview/MainCategoryView";
import {SubCategory} from "../model/SubCategory";
import usePositions from "../hooks/usePositions";


type BudgetOverviewProps = {
    mainCategories: MainCategory[]
    subCategories: SubCategory[]
}

export default function BudgetOverview({mainCategories, subCategories}:BudgetOverviewProps){

    const {positions, addNewPosition, updatePositionById, removePositionById} = usePositions();

    return(
        <div className={"budget-overview-container"}>
            <EventDetailView/>
            {mainCategories.map(mainCategory => <MainCategoryView key={mainCategory.id}
                                                                  mainCategory={mainCategory}
                                                                  subCategories={subCategories}
                                                                  positions={positions}
                                                                  addNewPosition={addNewPosition}
                                                                  deletePosition={removePositionById}
                                                                  updatePosition={updatePositionById}
            />)}

        </div>
    )
}
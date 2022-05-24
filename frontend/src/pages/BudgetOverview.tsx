import PositionList from "../components/budget-overview/PositionList";
import EventDetailView from "../components/budget-overview/EventDetailView";
import {MainCategory} from "../model/MainCategory";
import MainCategoryView from "../components/budget-overview/MainCategoryView";


type BudgetOverviewProps = {
    mainCategories: MainCategory[]
}

export default function BudgetOverview({mainCategories}:BudgetOverviewProps){

    return(
        <div className={"budget-overview-container"}>
            <EventDetailView/>
            {mainCategories.map(mainCategory => <MainCategoryView key={mainCategory.id} mainCategory={mainCategory}/>)}
            <PositionList/>
        </div>
    )
}
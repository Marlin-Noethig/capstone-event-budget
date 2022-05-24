import PositionList from "../components/budget-overview/PositionList";
import EventDetailView from "../components/budget-overview/EventDetailView";
import {MainCategory} from "../model/MainCategory";
import MainCategoryView from "../components/budget-overview/MainCategoryView";
import {SubCategory} from "../model/SubCategory";


type BudgetOverviewProps = {
    mainCategories: MainCategory[]
    subCategories: SubCategory[]
}

export default function BudgetOverview({mainCategories, subCategories}:BudgetOverviewProps){

    return(
        <div className={"budget-overview-container"}>
            <EventDetailView/>
            {mainCategories.map(mainCategory => <MainCategoryView key={mainCategory.id}
                                                                  mainCategory={mainCategory}
                                                                  subCategories={subCategories}/>)}
            <PositionList/>
        </div>
    )
}
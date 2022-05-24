import EventDetailView from "../components/budget-overview/EventDetailView";
import {MainCategory} from "../model/MainCategory";
import MainCategoryView from "../components/budget-overview/MainCategoryView";
import {SubCategory} from "../model/SubCategory";
import usePositions from "../hooks/usePositions";
import {getBalance} from "../service/utils/sumHelpers";
import BalanceView from "../components/budget-overview/BalanceView";
import "./styles/BudgetOverview.css"

type BudgetOverviewProps = {
    mainCategories: MainCategory[]
    subCategories: SubCategory[]
}

export default function BudgetOverview({mainCategories, subCategories}: BudgetOverviewProps) {

    const {positions, addNewPosition, updatePositionById, removePositionById} = usePositions();

    return (
        <div className={"budget-overview-container"}>
            <div className={"budget-overview-wrapper"}>
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
            <BalanceView sum={getBalance(positions, subCategories, mainCategories)}/>
        </div>
    )
}
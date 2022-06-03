import EventDetailView from "../components/budget-overview/EventDetailView";
import MainCategoryView from "../components/budget-overview/MainCategoryView";
import {getBalance} from "../service/utils/sumHelpers";
import BalanceView from "../components/budget-overview/BalanceView";
import "./styles/BudgetOverview.css"
import {MainCategory} from "../model/MainCategory";
import {SubCategory} from "../model/SubCategory";
import {Position} from "../model/Position";
import {useContext} from "react";
import {AuthContext} from "../context/AuthProvider";

type BudgetOverviewProps = {
    mainCategories: MainCategory[],
    subCategories: SubCategory[],
    positions: Position[],
    addNewPosition: (newPosition: Omit<Position, "id">) => void,
    deletePosition: (id: string, name: string) => void,
    updatePosition: (id: string, newPosition: Omit<Position, "id">) => void
}

export default function BudgetOverview({
                                           mainCategories,
                                           subCategories,
                                           positions,
                                           addNewPosition,
                                           deletePosition,
                                           updatePosition,
                                       }: BudgetOverviewProps) {
    const {showBalance} = useContext(AuthContext);

    return (
        <div className={"budget-overview-container"}>
            <div className={"budget-overview-wrapper"}>
                <EventDetailView/>
                {mainCategories.map(mainCategory => <MainCategoryView key={mainCategory.id}
                                                                      mainCategory={mainCategory}
                                                                      subCategories={subCategories}
                                                                      positions={positions}
                                                                      addNewPosition={addNewPosition}
                                                                      deletePosition={deletePosition}
                                                                      updatePosition={updatePosition}
                />)}
            </div>
            {showBalance && <BalanceView sum={getBalance(positions, subCategories, mainCategories)}/>}
        </div>
    )
}
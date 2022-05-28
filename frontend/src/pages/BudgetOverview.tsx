import EventDetailView from "../components/budget-overview/EventDetailView";
import MainCategoryView from "../components/budget-overview/MainCategoryView";
import usePositions from "../hooks/usePositions";
import {getBalance} from "../service/utils/sumHelpers";
import BalanceView from "../components/budget-overview/BalanceView";
import "./styles/BudgetOverview.css"
import useMainCategories from "../hooks/useMainCategories";
import useSubCategories from "../hooks/useSubCategories";


export default function BudgetOverview() {

    const {positions, addNewPosition, updatePositionById, removePositionById} = usePositions();
    const {mainCategories} = useMainCategories();
    const {subCategories} = useSubCategories();

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
import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";
import SubCategoryView from "./SubCategoryView";
import "./styles/MainCategoryView.css"
import {Position} from "../../model/Position";
import {useState} from "react";
import {getMainSum} from "../../service/utils/sumHelpers";

type MainCategoryViewProps = {
    mainCategory: MainCategory,
    subCategories: SubCategory[],
    positions: Position[],
    addNewPosition: (newPosition: Omit<Position, "id">) => void,
    deletePosition: (id: string) => void,
    updatePosition: (id: string, newPosition: Omit<Position, "id">) => void
}

export default function MainCategoryView({
                                             mainCategory,
                                             subCategories,
                                             positions,
                                             addNewPosition,
                                             deletePosition,
                                             updatePosition
                                         }: MainCategoryViewProps) {

    const [collapsed, setCollapsed] = useState<boolean>(true);

    const filteredSubCategories = subCategories.filter(subCategory => subCategory.mainCategoryId === mainCategory.id)
    const isIncomeClassName = mainCategory.income ? "incomes" : "expenses"

    const toggleCollapsed = () => {
        setCollapsed(!collapsed)
    }

    return (
        <div>
            <div className={"category-view " + isIncomeClassName}>
                <span className={"main-category-name"}>
                    {mainCategory.name}
                </span>
                <span className={"main-sum"}>
                    {getMainSum(positions, filteredSubCategories).toFixed(2)} €
                    <button className={"collapse-category-button"}
                            onClick={toggleCollapsed}>{collapsed ? "˄" : "˅"}</button>
                </span>
            </div>
            {collapsed && filteredSubCategories.map(subCategory => <SubCategoryView key={subCategory.id}
                                                                                    subCategory={subCategory}
                                                                                    positions={positions}
                                                                                    addNewPosition={addNewPosition}
                                                                                    deletePosition={deletePosition}
                                                                                    updatePosition={updatePosition}

            />)}
        </div>
    )
}
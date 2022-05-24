import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";
import SubCategoryView from "./SubCategoryView";
import "./styles/MainCategoryView.css"
import {Position} from "../../model/Position";

type MainCategoryViewProps = {
    mainCategory: MainCategory,
    subCategories: SubCategory[],
    positions: Position[]
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

    const filteredSubCategories = subCategories.filter(subCategory => subCategory.mainCategoryId === mainCategory.id)
    const isIncomeClassName = mainCategory.income ? "incomes" : "expenses"

    return (
        <div>
            <div className={"category-view " + isIncomeClassName}>{mainCategory.name}</div>
            {filteredSubCategories.map(subCategory => <SubCategoryView key={subCategory.id}
                                                                       subCategory={subCategory}
                                                                       positions={positions}
                                                                       addNewPosition={addNewPosition}
                                                                       deletePosition={deletePosition}
                                                                       updatePosition={updatePosition}

            />)}
        </div>
    )
}
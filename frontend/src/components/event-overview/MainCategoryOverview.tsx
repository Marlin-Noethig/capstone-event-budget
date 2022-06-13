import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";
import {Position} from "../../model/Position";
import {formatMoney} from "../../service/utils/formatingHelpers";
import {getMainSum} from "../../service/utils/sumHelpers";
import SubCategoryOverview from "./SubCategoryOverview";


type MainCategoryOverviewProps = {
    mainCategory: MainCategory,
    subCategories: SubCategory[],
    positions: Position[],
}

export default function MainCategoryOverview({mainCategory, subCategories, positions}:MainCategoryOverviewProps){
    const filteredSubCategories = subCategories.filter(subCategory => subCategory.mainCategoryId === mainCategory.id)
    const isIncomeClassName = mainCategory.income ? "incomes" : "expenses"

    return(
        <div>
            <div className={"category-view main-category " + isIncomeClassName}>
                <span className={"main-category-name"}>
                    {mainCategory.name}
                </span>
                <span className={"main-sum"}>
                    {formatMoney(getMainSum(positions, filteredSubCategories))} €
                </span>
            </div>
            {filteredSubCategories.map(subCategory => <SubCategoryOverview key={subCategory.id} subCategory={subCategory} positions={positions}/>)}
        </div>
    )
}
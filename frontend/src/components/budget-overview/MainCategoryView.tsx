import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";
import SubCategoryView from "./SubCategoryView";
import "./styles/MainCategoryView.css"

type MainCategoryViewProps = {
    mainCategory: MainCategory
    subCategories: SubCategory[]
}

export default function MainCategoryView({mainCategory, subCategories}: MainCategoryViewProps) {

    const filteredSubCategories = subCategories.filter(subCategory => subCategory.mainCategoryId === mainCategory.id)
    const isIncomeClassName = mainCategory.income ? "incomes" : "expenses"
    console.log(mainCategory)
    return (
        <div>
            <div className={"category-view " + isIncomeClassName}>{mainCategory.name}</div>
            {filteredSubCategories.map(subCategory => <SubCategoryView key={subCategory.id}
                                                                       subCategory={subCategory}/>)}
        </div>
    )
}
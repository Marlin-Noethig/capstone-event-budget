import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";
import AdminSubCategoryView from "./AdminSubCategoryView";
import "./styles/AdminMainCategoryView.css"
import WriteSubCategory from "./WriteSubCategory";

type AdminMainCategoryViewProps = {
    mainCategory: MainCategory,
    subCategories: SubCategory[]
}

export default function AdminMainCategoryView({mainCategory, subCategories}:AdminMainCategoryViewProps){
    const isIncomeClassName = mainCategory.income ? " incomes" : " expenses"

    return(<div>
        <div className={"admin-list-item" + isIncomeClassName}>{mainCategory.name}</div>
        <div className={"sub-category-container"}>
            {subCategories.map(subCategory => <AdminSubCategoryView key={subCategory.id} subCategory={subCategory}/>)}
            <WriteSubCategory idOfMainCategory={mainCategory.id}/>
        </div>
    </div>)
}
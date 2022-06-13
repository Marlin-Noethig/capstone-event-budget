import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";
import AdminSubCategoryView from "./AdminSubCategoryView";
import "./styles/AdminMainCategoryView.css"
import WriteSubCategory from "./WriteSubCategory";
import {useState} from "react";

type AdminMainCategoryViewProps = {
    mainCategory: MainCategory,
    subCategories: SubCategory[],
    addSubCategory: (newPosition: Omit<SubCategory, "id">) => void,
    updateSubCategory: (id: string, newPosition: Omit<SubCategory, "id">) => void,
    removeSubCategory: (id: string, name: string) => void
}

export default function AdminMainCategoryView({
                                                  mainCategory,
                                                  subCategories,
                                                  addSubCategory,
                                                  updateSubCategory,
                                                  removeSubCategory
                                              }: AdminMainCategoryViewProps) {
    const isIncomeClassName = mainCategory.income ? " incomes" : " expenses"
    const [enableAdd, setEnableAdd] = useState<boolean>(false);

    const toggleEnableAdd = () => {
        setEnableAdd(!enableAdd)
    }

    return (<div>
        <div className={"admin-list-item" + isIncomeClassName}>{mainCategory.name}</div>
        <div className={"sub-category-container"}>
            {subCategories.map(subCategory => <AdminSubCategoryView key={subCategory.id}
                                                                    subCategory={subCategory}
                                                                    updateSubCategory={updateSubCategory}
                                                                    removeSubCategory={removeSubCategory}
                                                                    idOfMainCategory={mainCategory.id}/>)}
            {enableAdd ?
                <WriteSubCategory idOfMainCategory={mainCategory.id}
                                  addSubCategory={addSubCategory}
                                  toggleEnableAdd={toggleEnableAdd}/>
                :
                <button onClick={toggleEnableAdd}>+</button>}
        </div>
    </div>)
}
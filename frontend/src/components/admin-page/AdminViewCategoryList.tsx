import "./styles/AdminViewCategoryList.css"
import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";
import AdminMainCategoryView from "./AdminMainCategoryView";

type AdminViewCategoryListProps = {
    mainCategories: MainCategory[],
    subCategories: SubCategory[],
    addSubCategory: (newPosition: Omit<SubCategory, "id">) => void,
    updateSubCategory: (id: string, newPosition: Omit<SubCategory, "id">) => void,
    removeSubCategory: (id: string, name: string) => void
}

export default function AdminViewCategoryList({
                                                  mainCategories,
                                                  subCategories,
                                                  addSubCategory,
                                                  updateSubCategory,
                                                  removeSubCategory
                                              }: AdminViewCategoryListProps) {
    return (<div className={"admin-view-list"}>
        <div className={"admin-view-list-title"}>Categories</div>
        {mainCategories.map(mainCategory => <AdminMainCategoryView key={mainCategory.id}
                                                                   mainCategory={mainCategory}
                                                                   subCategories={subCategories.filter(subCategory => subCategory.mainCategoryId === mainCategory.id)}
                                                                   addSubCategory={addSubCategory}
                                                                   updateSubCategory={updateSubCategory}
                                                                   removeSubCategory={removeSubCategory}/>)}
    </div>)
}
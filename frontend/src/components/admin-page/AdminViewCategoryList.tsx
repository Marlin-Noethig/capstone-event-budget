import "./styles/AdminViewCategoryList.css"
import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";
import AdminMainCategoryView from "./AdminMainCategoryView";

type AdminViewCategoryListProps = {
    mainCategories: MainCategory[],
    subCategories: SubCategory[]
}

export default function AdminViewCategoryList({mainCategories, subCategories}: AdminViewCategoryListProps) {
    return (<div className={"admin-view-list"}>
        <div className={"admin-view-list-title"}>Categories</div>
        {mainCategories.map(mainCategory => <AdminMainCategoryView key={mainCategory.id}
                                                                   mainCategory={mainCategory}
                                                                   subCategories={subCategories.filter(subCategory => subCategory.mainCategoryId === mainCategory.id)}/>)}
    </div>)
}
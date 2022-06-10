import "./styles/AdminViewCategoryList.css"
import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";

type AdminViewCategoryListProps = {
    mainCategories: MainCategory[],
    subCategories: SubCategory[]
}

export default function AdminViewCategoryList({mainCategories, subCategories}:AdminViewCategoryListProps) {
    return (<div className={"admin-view-list"}>
        <div className={"admin-view-list-title"}>Categories</div>
        <ul>
            {mainCategories.map(mainCategory => <li>{mainCategory.name}</li>)}
        </ul>
        <ul>
            {subCategories.map(subCategory => <li>{subCategory.name}</li>)}
        </ul>
    </div>)
}
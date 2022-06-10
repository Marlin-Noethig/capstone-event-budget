import {SubCategory} from "../../model/SubCategory";
import "./styles/AdminSubCategoryView.css"

type AdminSubCategoryViewProps = {
    subCategory: SubCategory
}

export default function AdminSubCategoryView({subCategory}:AdminSubCategoryViewProps){
    return(
        <div>
            <div className={"admin-list-item sub-category"}>{subCategory.name}</div>
        </div>
    )
}
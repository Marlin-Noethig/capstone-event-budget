import {SubCategory} from "../../model/SubCategory";
import "./styles/SubCategoryView.css"

type SubCategoryViewProps = {
    subCategory: SubCategory
}

export default function SubCategoryView({subCategory}: SubCategoryViewProps) {
    return (
        <div className={"category-view sub-category-view"}>{subCategory.name}</div>
    )
}
import {SubCategory} from "../../model/SubCategory";

type SubCategoryViewProps = {
    subCategory: SubCategory
}

export default function SubCategoryView({subCategory}: SubCategoryViewProps) {
    return (
        <div>{subCategory.name}</div>
    )
}
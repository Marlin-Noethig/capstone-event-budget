import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";
import SubCategoryView from "./SubCategoryView";

type MainCategoryViewProps = {
    mainCategory: MainCategory
    subCategories: SubCategory[]
}

export default function MainCategoryView({mainCategory, subCategories}: MainCategoryViewProps) {

    const filteredSubCategories = subCategories.filter(subCategory => subCategory.mainCategoryId === mainCategory.id)

    return (
        <div>
            <div>{mainCategory.name}</div>
            {filteredSubCategories.map(subCategory => <SubCategoryView key={subCategory.id}
                                                                       subCategory={subCategory}/>)}
        </div>
    )
}
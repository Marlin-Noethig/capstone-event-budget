import {MainCategory} from "../../model/MainCategory";

type MainCategoryViewProps = {
    mainCategory: MainCategory
}

export default function MainCategoryView({mainCategory}:MainCategoryViewProps){
    return(
        <div>
            {mainCategory.name}
        </div>
    )
}
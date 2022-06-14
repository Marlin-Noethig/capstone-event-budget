import "./styles/AdminViewCategoryList.css"
import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";
import AdminMainCategoryView from "./AdminMainCategoryView";
import {User} from "../../model/User";

type AdminViewCategoryListProps = {
    mainCategories: MainCategory[],
    updateMainCategory: (id: string, updatedUserIds: string[]) => void,
    subCategories: SubCategory[],
    addSubCategory: (newPosition: Omit<SubCategory, "id">) => void,
    updateSubCategory: (id: string, newPosition: Omit<SubCategory, "id">) => void,
    removeSubCategory: (id: string, name: string) => void
    users: User[]
}

export default function AdminViewCategoryList({
                                                  mainCategories,
                                                  updateMainCategory,
                                                  subCategories,
                                                  addSubCategory,
                                                  updateSubCategory,
                                                  removeSubCategory,
                                                  users
                                              }: AdminViewCategoryListProps) {
    return (<div className={"admin-view-list"}>
        <div className={"admin-view-list-title"}>Categories</div>
        {mainCategories.map(mainCategory => <AdminMainCategoryView key={mainCategory.id}
                                                                   mainCategory={mainCategory}
                                                                   updateMainCategory={updateMainCategory}
                                                                   subCategories={subCategories.filter(subCategory => subCategory.mainCategoryId === mainCategory.id)}
                                                                   addSubCategory={addSubCategory}
                                                                   updateSubCategory={updateSubCategory}
                                                                   removeSubCategory={removeSubCategory}
                                                                   users={users}/>)}
    </div>)
}
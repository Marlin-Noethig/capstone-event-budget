import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";
import AdminSubCategoryView from "./AdminSubCategoryView";
import "./styles/AdminMainCategoryView.css"
import WriteSubCategory from "./WriteSubCategory";
import {useState} from "react";
import AssignUsersToMainCat from "./AssignUsersToMainCat";
import {User} from "../../model/User";

type AdminMainCategoryViewProps = {
    mainCategory: MainCategory,
    updateMainCategory: (id: string, updatedUserIds: string[]) => void,
    subCategories: SubCategory[],
    addSubCategory: (newPosition: Omit<SubCategory, "id">) => void,
    updateSubCategory: (id: string, newPosition: Omit<SubCategory, "id">) => void,
    removeSubCategory: (id: string, name: string) => void
    users: User[]
}

export default function AdminMainCategoryView({
                                                  mainCategory,
                                                  updateMainCategory,
                                                  subCategories,
                                                  addSubCategory,
                                                  updateSubCategory,
                                                  removeSubCategory,
                                                  users
                                              }: AdminMainCategoryViewProps) {
    const isIncomeClassName = mainCategory.income ? " incomes" : " expenses"
    const [enableAdd, setEnableAdd] = useState<boolean>(false);
    const [enableAssignUsers, setEnableAssignUsers] = useState<boolean>(false);

    const toggleEnableAdd = () => {
        setEnableAdd(!enableAdd)
    }

    const toggleEnableAssignUsers = () => {
        setEnableAssignUsers(!enableAssignUsers)
    }

    return (<div>
        <div className={"admin-list-item" + isIncomeClassName}>
            <div className={"admin-main-category-wrapper"}>
                <div>{mainCategory.name}</div>
                {!enableAssignUsers ?
                    <button onClick={toggleEnableAssignUsers}>edit</button>
                    :
                    <AssignUsersToMainCat idOfMainCategory={mainCategory.id}
                                          users={users}
                                          updateMainCategory={updateMainCategory}
                                          assignedUsers={mainCategory.userIds}
                                          toggleEnableAssignUsers={toggleEnableAssignUsers}/>}
            </div>
        </div>
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
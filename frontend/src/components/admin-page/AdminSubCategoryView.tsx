import {SubCategory} from "../../model/SubCategory";
import "./styles/AdminSubCategoryView.css"
import {useState} from "react";
import WriteSubCategory from "./WriteSubCategory";
import DeletionDialogue from "../DeletionDialogue";

type AdminSubCategoryViewProps = {
    subCategory: SubCategory
    updateSubCategory: (id: string, newPosition: Omit<SubCategory, "id">) => void,
    removeSubCategory: (id: string, name: string) => void
    idOfMainCategory: string
}

export default function AdminSubCategoryView({
                                                 subCategory,
                                                 updateSubCategory,
                                                 removeSubCategory,
                                                 idOfMainCategory
                                             }: AdminSubCategoryViewProps) {
    const [enableEdit, setEnableEdit] = useState<boolean>(false);
    const toggleEnableEdit = () => {
        setEnableEdit(!enableEdit)
    }

    return (
        <div className={"admin-list-item sub-category"}>
            {enableEdit ?
                <WriteSubCategory idOfMainCategory={idOfMainCategory}
                                  subCategory={subCategory}
                                  updateSubCategory={updateSubCategory}
                                  toggleEnableEdit={toggleEnableEdit}/>
                :
                <div className={"display-subcategory"}>
                    <div>{subCategory.name}</div>
                    <button onClick={toggleEnableEdit}>edit</button>
                    <DeletionDialogue subCategory={subCategory} deleteFunction={removeSubCategory}/>
                </div>
            }
        </div>
    )
}
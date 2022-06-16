import {SubCategory} from "../../model/SubCategory";
import {FormEvent, useState} from "react";
import {toast} from "react-toastify";
import "./styles/WriteSubCategory.css";

type WriteSubCategoryProps = {
    subCategory?: SubCategory
    idOfMainCategory: string
    addSubCategory?: (newPosition: Omit<SubCategory, "id">) => void,
    updateSubCategory?: (id: string, newPosition: Omit<SubCategory, "id">) => void,
    toggleEnableAdd?: () => void
    toggleEnableEdit?: () => void
}

export default function WriteSubCategory({subCategory, idOfMainCategory, addSubCategory, updateSubCategory, toggleEnableAdd, toggleEnableEdit}: WriteSubCategoryProps) {
    const [name, setName] = useState<string>(subCategory ? subCategory.name : "");

    const onSubmitSubCategory = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (!name) {
            toast.warn("Name must be set")
            return
        }
        const subCategoryValues = {
            name: name,
            mainCategoryId: idOfMainCategory
        }

        if(addSubCategory && toggleEnableAdd){
            addSubCategory(subCategoryValues);
            toggleEnableAdd();
        }
        if(subCategory && updateSubCategory && toggleEnableEdit){
            updateSubCategory(subCategory.id, subCategoryValues);
            toggleEnableEdit();
        }
    }

    return (
            <form className={"sub-category-write-form"} onSubmit={onSubmitSubCategory} action="">
                <input type="text" value={name} onChange={e => setName(e.target.value)}/>
                <div className={"write-sub-category-buttons"}>
                    {subCategory ?
                        <input className={"submit-button"} type={"submit"} value={"save"}/>
                        :
                        <input className={"submit-button"} type={"submit"} value={"add"}/>
                    }
                    <button onClick={toggleEnableAdd ?? toggleEnableEdit}>X</button>
                </div>
            </form>
    )
}
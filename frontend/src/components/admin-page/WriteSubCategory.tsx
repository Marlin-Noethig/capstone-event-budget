import {SubCategory} from "../../model/SubCategory";
import {FormEvent, useState} from "react";
import {toast} from "react-toastify";

type WriteSubCategoryProps = {
    subCategory?: SubCategory
    idOfMainCategory: string
    addSubCategory?: (newPosition: Omit<SubCategory, "id">) => void,
    updateSubCategory?: (id: string, newPosition: Omit<SubCategory, "id">) => void,
    toggleEnableAdd?: () => void
}

export default function WriteSubCategory({subCategory, idOfMainCategory, addSubCategory, updateSubCategory, toggleEnableAdd}: WriteSubCategoryProps) {
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
    }

    return (
        <div className={"admin-list-item"}>
            <form onSubmit={onSubmitSubCategory} action="">
                <input type="text" value={name} onChange={e => setName(e.target.value)}/>
                <div className={"write-sub-category-buttons"}>
                    {subCategory ?
                        <input type={"submit"} value={"save"}/>
                        :
                        <input type={"submit"} value={"add"}/>
                    }
                    <button onClick={toggleEnableAdd}>X</button>
                </div>
            </form>
        </div>
    )
}
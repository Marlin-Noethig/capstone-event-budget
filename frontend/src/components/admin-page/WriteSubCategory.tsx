import {SubCategory} from "../../model/SubCategory";
import {FormEvent, useState} from "react";
import {toast} from "react-toastify";

type WriteSubCategoryProps = {
    subCategory?: SubCategory
    idOfMainCategory: string
}

export default function WriteSubCategory({subCategory, idOfMainCategory}: WriteSubCategoryProps) {
    const [name, setName] = useState<string>(subCategory ? subCategory.name : "");

    const onSubmitSubCategory = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (!name) {
            toast.warn("Name must be set")
            return
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
                    <button onClick={() => console.log("ouch")}>X</button>
                </div>
            </form>
        </div>
    )
}
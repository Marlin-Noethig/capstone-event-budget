import AdminViewCategoryList from "./AdminViewCategoryList";
import "./styles/AdminListBoard.css"
import AdminViewEventsList from "./AdminViewEventsList";
import AdminViewUsersList from "./AdminViewUsersList";
import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";

type AdminListBoardProps = {
    mainCategories: MainCategory[],
    subCategories: SubCategory[]
}

export default function AdminListBoard({mainCategories, subCategories}:AdminListBoardProps){
    return(<div className={"admin-list-board"}>
        <AdminViewCategoryList mainCategories={mainCategories}
                               subCategories={subCategories}/>
        <AdminViewEventsList/>
        <AdminViewUsersList/>
    </div>)
}
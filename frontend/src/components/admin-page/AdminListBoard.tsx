import AdminViewCategoryList from "./AdminViewCategoryList";
import "./styles/AdminListBoard.css"
import AdminViewEventsList from "./AdminViewEventsList";
import AdminViewUsersList from "./AdminViewUsersList";
import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";
import {EventData} from "../../model/EventData";

type AdminListBoardProps = {
    mainCategories: MainCategory[],
    subCategories: SubCategory[],
    events: EventData[]
}

export default function AdminListBoard({mainCategories, subCategories, events}:AdminListBoardProps){
    return(<div className={"admin-list-board"}>
        <AdminViewCategoryList mainCategories={mainCategories}
                               subCategories={subCategories}/>
        <AdminViewEventsList events={events}/>
        <AdminViewUsersList/>
    </div>)
}
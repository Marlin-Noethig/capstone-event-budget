import AdminViewCategoryList from "./AdminViewCategoryList";
import "./styles/AdminListBoard.css"
import AdminViewEventsList from "./AdminViewEventsList";
import AdminViewUsersList from "./AdminViewUsersList";

export default function AdminListBoard(){
    return(<div className={"admin-list-board"}>
        <AdminViewCategoryList/>
        <AdminViewEventsList/>
        <AdminViewUsersList/>
    </div>)
}
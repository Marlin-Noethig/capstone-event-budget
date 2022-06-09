import {Link} from "react-router-dom";
import AdminListBoard from "../components/admin-page/AdminListBoard";

export default function AdminPage() {
    return (
        <div className={"admin-page"}>
            <Link to={"/"}>Back</Link>
            <AdminListBoard/>
        </div>
    )
}
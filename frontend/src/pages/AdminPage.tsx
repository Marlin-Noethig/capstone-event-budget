import {Link} from "react-router-dom";

export default function AdminPage() {
    return (
        <div className={"admin-page"}>
            <Link to={"/"}>Back</Link>
            Your Admin content could go here :-)
        </div>
    )
}
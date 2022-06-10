import {Link} from "react-router-dom";
import AdminListBoard from "../components/admin-page/AdminListBoard";
import {MainCategory} from "../model/MainCategory";
import {SubCategory} from "../model/SubCategory";
import {EventData} from "../model/EventData";

type AdminPageProps = {
    mainCategories: MainCategory[],
    subCategories: SubCategory[],
    events: EventData[]
}

export default function AdminPage({mainCategories, subCategories, events}: AdminPageProps) {
    return (
        <div className={"admin-page"}>
            <Link to={"/"}>Back</Link>
            <AdminListBoard mainCategories={mainCategories}
                            subCategories={subCategories}
                            events={events}/>
        </div>
    )
}
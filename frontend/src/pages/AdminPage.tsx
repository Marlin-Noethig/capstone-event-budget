import {Link} from "react-router-dom";
import AdminListBoard from "../components/admin-page/AdminListBoard";
import {MainCategory} from "../model/MainCategory";
import {SubCategory} from "../model/SubCategory";

type AdminPageProps = {
    mainCategories: MainCategory[],
    subCategories: SubCategory[]
}

export default function AdminPage({mainCategories, subCategories}:AdminPageProps) {
    return (
        <div className={"admin-page"}>
            <Link to={"/"}>Back</Link>
            <AdminListBoard mainCategories={mainCategories}
                            subCategories={subCategories}/>
        </div>
    )
}
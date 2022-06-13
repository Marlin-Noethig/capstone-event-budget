import {Link} from "react-router-dom";
import AdminListBoard from "../components/admin-page/AdminListBoard";
import {MainCategory} from "../model/MainCategory";
import {SubCategory} from "../model/SubCategory";
import {EventData} from "../model/EventData";
import useUsers from "../hooks/useUsers";

type AdminPageProps = {
    mainCategories: MainCategory[],
    subCategories: SubCategory[],
    addSubCategory: (newPosition: Omit<SubCategory, "id">) => void,
    updateSubCategory: (id: string, newPosition: Omit<SubCategory, "id">) => void,
    removeSubCategory: (id: string, name: string) => void,
    events: EventData[]
}

export default function AdminPage({
                                      mainCategories,
                                      subCategories,
                                      addSubCategory,
                                      updateSubCategory,
                                      removeSubCategory,
                                      events
                                  }: AdminPageProps) {
    const {users} = useUsers();

    return (
        <div className={"admin-page"}>
            <Link to={"/"}>Home</Link>
            <AdminListBoard mainCategories={mainCategories}
                            subCategories={subCategories}
                            addSubCategory={addSubCategory}
                            updateSubCategory={updateSubCategory}
                            removeSubCategory={removeSubCategory}
                            events={events}
                            users={users}/>
        </div>
    )
}


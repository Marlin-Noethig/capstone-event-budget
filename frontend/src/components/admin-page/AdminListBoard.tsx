import AdminViewCategoryList from "./AdminViewCategoryList";
import "./styles/AdminListBoard.css"
import AdminViewEventsList from "./AdminViewEventsList";
import AdminViewUsersList from "./AdminViewUsersList";
import {MainCategory} from "../../model/MainCategory";
import {SubCategory} from "../../model/SubCategory";
import {EventData} from "../../model/EventData";
import {User} from "../../model/User";

type AdminListBoardProps = {
    mainCategories: MainCategory[],
    updateMainCategory: (id: string, updatedUserIds: string[]) => void,
    subCategories: SubCategory[],
    addSubCategory: (newPosition: Omit<SubCategory, "id">) => void,
    updateSubCategory: (id: string, newPosition: Omit<SubCategory, "id">) => void,
    removeSubCategory: (id: string, name: string) => void,
    events: EventData[],
    users: User[]
}

export default function AdminListBoard({
                                           mainCategories,
                                           updateMainCategory,
                                           subCategories,
                                           addSubCategory,
                                           updateSubCategory,
                                           removeSubCategory,
                                           events,
                                           users
                                       }: AdminListBoardProps) {
    return (<div className={"admin-list-board"}>
        <AdminViewCategoryList mainCategories={mainCategories}
                               updateMainCategory={updateMainCategory}
                               subCategories={subCategories}
                               addSubCategory={addSubCategory}
                               updateSubCategory={updateSubCategory}
                               removeSubCategory={removeSubCategory}
                               users={users}
        />
        <AdminViewEventsList events={events}/>
        <AdminViewUsersList users={users}/>
    </div>)
}
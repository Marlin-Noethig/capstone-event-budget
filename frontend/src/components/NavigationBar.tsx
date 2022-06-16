import {useNavigate} from "react-router-dom";

type NavigationBarProps = {
    isAdmin: boolean
    logout: () => void
}

export default function NavigationBar({isAdmin, logout}:NavigationBarProps) {
    const navigate = useNavigate()

    return (
        <nav>
                <div className={"nav-element"} onClick={() => navigate("/")}>HOME</div>
                {isAdmin && <div className={"nav-element"} onClick={() => navigate("/admin")}>ADMIN</div>}
                <div className={"nav-element"} onClick={logout}>LOGOUT</div>
        </nav>
    )
}

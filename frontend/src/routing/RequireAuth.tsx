import {useContext} from "react";
import {AuthContext} from "../context/AuthProvider";
import {Navigate, Outlet} from "react-router-dom";



export default function RequireAuth() {
    const {token, checkTokenExpiration} = useContext(AuthContext);

    return (token && checkTokenExpiration() ? <Outlet /> : <Navigate to={"/login"}/>)
}
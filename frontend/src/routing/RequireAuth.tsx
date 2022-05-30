import {useContext} from "react";
import {AuthContext} from "../context/AuthProvider";
import {Navigate, Outlet} from "react-router-dom";
import {decodeJwt} from "jose";



export default function RequireAuth() {
    const {token} = useContext(AuthContext);

    const checkTokenExpiration = (currentToken: string) => {
        const decodedToken = decodeJwt(currentToken);
        const dateNow = Math.floor(new Date().getTime() / 1000)
        if(decodedToken.exp){
            console.log(dateNow)
            console.log(new Date(dateNow));
            console.log(new Date(decodedToken.exp))
            console.log(decodedToken.exp);
            console.log(decodedToken.exp >= dateNow)
            console.log(decodedToken.exp - dateNow)
            return decodedToken.exp > Number(dateNow);
        }
    }

    return (token && checkTokenExpiration(token) ? <Outlet /> : <Navigate to={"/login"}/>)
}
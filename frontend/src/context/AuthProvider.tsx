import {createContext, ReactElement, useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";
import {decodeJwt} from "jose";

const authKey: string = "AuthToken"

export type Credentials = {
    mail: string,
    password: string
}

export type AuthContextType = {
    token: string | undefined,
    login: (credentials: Credentials) => void
    logout: () => void
    showBalance: boolean;
}

export type AuthProviderProps = {
    children: ReactElement
}

export const AuthContext = createContext<AuthContextType>({
    token: undefined,
    login: () => toast.error("Login not initialized!"),
    logout: () => toast.error("Something with the logout does not work"),
    showBalance: false
})

export default function AuthProvider({children}: AuthProviderProps) {
    const [token, setToken] = useState<string | undefined>(localStorage.getItem(authKey) ?? undefined);
    const [showBalance, setShowBalance] = useState<boolean>(false);
    const navigate = useNavigate();

    const login = (credentials: Credentials) => {
        axios.post("/auth/login", credentials)
            .then(response => response.data)
            .then((newToken) => {
                setToken(newToken)
                localStorage.setItem(authKey, newToken)
                getShowBalance(newToken)
            })
            .then(() => navigate("/"))
            .catch(() => toast.warn("Login failed. Please check your credentials!"))
    }

    const checkTokenExpiration = () => {
        let decodedToken;
        if (token) {
            try {
                decodedToken = decodeJwt(token);
            } catch {
                toast.error("Token is corrupted, clear Web Storage!")
            }
        }
        const dateNow = Math.floor(new Date().getTime() / 1000)
        if (decodedToken && decodedToken.exp) {
            if (decodedToken.exp < Number(dateNow)) {
                logout()
            }
        }
    }

    useEffect(() => {
        checkTokenExpiration()
    })

    const logout = () => {
        localStorage.removeItem(authKey)
        setToken("")
        setShowBalance(false)
        toast.info("You have been logged out")
    }

    const getShowBalance = (currentToken: string) => {
        axios.get("/api/main-categories/balance-allowed", currentToken
            ? {headers: {"Authorization": currentToken}}
            : {})
            .then(response => response.data)
            .then(data => setShowBalance(data));
    }



return <AuthContext.Provider value={{token, login, logout, showBalance}}>
    {children}
</AuthContext.Provider>
}
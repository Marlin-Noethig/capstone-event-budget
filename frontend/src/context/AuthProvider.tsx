import {createContext, ReactElement, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";

const authKey: string = "AuthToken"

export type Credentials = {
    mail: string,
    password: string
}

export type AuthContextType = {
    token: string | undefined,
    login: (credentials: Credentials) => void
    logout: () => void
}

export type AuthProviderProps = {
    children: ReactElement
}

export const AuthContext = createContext<AuthContextType>({
    token: undefined,
    login: () => toast.error("Login not initialized!"),
    logout: () => toast.error("Something with the logout does not work")
})

export default function AuthProvider({children}:AuthProviderProps) {
    const [token, setToken] = useState<string | undefined>(localStorage.getItem(authKey) ?? undefined);
    const navigate = useNavigate();

    const login = (credentials: Credentials) => {
        axios.post("/auth/login", credentials)
            .then(response => response.data)
            .then((newToken) => {
                setToken(newToken)
                localStorage.setItem(authKey, newToken)
            })
            .then(() => navigate("/"))
            .catch(() => toast.warn("Login failed. Please check your credentials!"))
    }

    const logout = () => {
        navigate("/login")
        localStorage.removeItem(authKey)
        setToken("")
    }

    return <AuthContext.Provider value={{token, login, logout}}>
            {children}
        </AuthContext.Provider>
}
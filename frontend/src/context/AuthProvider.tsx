import {createContext, ReactElement, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

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
    login: () => console.error("Login not initialized!"),
    logout: () => console.error("Something with the logout does not work")
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
            .catch((error) => console.error(error))
    }

    const logout = () => {
        setToken("")
        localStorage.setItem(authKey, "")
        navigate("/login")
    }

    return <AuthContext.Provider value={{token, login, logout}}>
            {children}
        </AuthContext.Provider>
}
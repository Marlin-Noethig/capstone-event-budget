import {FormEvent, useContext, useState} from "react";
import {AuthContext} from "../context/AuthProvider";
import "./styles/LoginPage.css"

export default function LoginPage() {
    const [mail, setMail] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const {login} = useContext(AuthContext)

    const onSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        login({mail: mail, password: password})
    }

    return <div className={"login-form-wrapper"}>
        <form className={"login-form"} onSubmit={onSubmit}>
            <input className={"login-form-item"} type="text" value={mail} placeholder={"E-mail"} onChange={(e) => setMail(e.target.value)}/>
            <input className={"login-form-item"} type="password" value={password} placeholder={"Password"} onChange={(e) => setPassword(e.target.value)}/>
            <button className={"login-form-item login-button"} type={"submit"}>Login</button>
        </form>
    </div>

}
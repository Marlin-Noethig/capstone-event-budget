import React from 'react';
import './App.css';
import {Route, Routes} from "react-router-dom";
import AppTitle from "./components/AppTitle";
import RequireAuth from "./routing/RequireAuth";
import LoginPage from "./pages/LoginPage";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import HomePage from "./pages/HomePage";

function App() {

    return (
        <div className="App">
            <ToastContainer
                position="top-left"
                autoClose={3000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss={false}
                draggable
                pauseOnHover={false}
            />
            <AppTitle/>
            <Routes>
                <Route element={<RequireAuth/>}>
                    <Route path="/*"
                           element={<HomePage/>}
                    />
                </Route>
                <Route path={"/login"} element={<LoginPage/>}/>
            </Routes>
        </div>
    );
}

export default App;

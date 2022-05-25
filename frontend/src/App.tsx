import React from 'react';
import './App.css';
import {Route, Routes} from "react-router-dom";
import BudgetOverview from "./pages/BudgetOverview";
import AppTitle from "./components/AppTitle";
import useMainCategories from "./hooks/useMainCategories";
import useSubCategories from "./hooks/useSubCategories";
import RequireAuth from "./routing/RequireAuth";
import LoginPage from "./pages/LoginPage";

function App() {

    const {mainCategories} = useMainCategories();
    const {subCategories} = useSubCategories();


    return (
        <div className="App">
            <AppTitle/>
            <Routes>
                <Route element={<RequireAuth/>}>
                    <Route path="/"
                           element={<BudgetOverview mainCategories={mainCategories}
                                                    subCategories={subCategories}/>}
                    />
                </Route>
                <Route path={"/login"} element={<LoginPage/>}/>
            </Routes>
        </div>
    );
}

export default App;

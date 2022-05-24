import React from 'react';
import './App.css';
import {Route, Routes} from "react-router-dom";
import BudgetOverview from "./pages/BudgetOverview";
import AppTitle from "./components/AppTitle";
import useMainCategories from "./hooks/useMainCategories";
import useSubCategories from "./hooks/useSubCategories";

function App() {

    const {mainCategories} = useMainCategories();
    const {subCategories} = useSubCategories();


    return (
        <div className="App">
            <AppTitle/>
            <Routes>
                <Route path="/"
                       element={<BudgetOverview mainCategories={mainCategories}
                                                subCategories={subCategories}/>}
                />
            </Routes>
        </div>
    );
}

export default App;

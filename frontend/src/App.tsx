import React from 'react';
import './App.css';
import {Route, Routes} from "react-router-dom";
import BudgetOverview from "./pages/BudgetOverview";
import AppTitle from "./components/AppTitle";
import useMainCategories from "./hooks/useMainCategories";

function App() {

    const {mainCategories} = useMainCategories();


    return (
        <div className="App">
            <AppTitle/>
            <Routes>
                <Route path="/"
                       element={<BudgetOverview mainCategories={mainCategories} />}
                />
            </Routes>
        </div>
    );
}

export default App;

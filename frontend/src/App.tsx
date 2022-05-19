import React from 'react';
import './App.css';
import {Route, Routes} from "react-router-dom";
import BudgetOverview from "./pages/BudgetOverview";
import AppTitle from "./components/AppTitle";

function App() {


    return (
        <div className="App">
            <AppTitle/>
            <Routes>
                <Route path="/"
                       element={<BudgetOverview/>}
                />
            </Routes>
        </div>
    );
}

export default App;

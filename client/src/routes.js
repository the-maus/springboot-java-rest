import React from 'react';
import { BrowserRouter, Route, Routes as Switch } from 'react-router-dom';

import Login from './pages/Login';
import Books from './pages/Books';
import NewBook from './pages/NewBook';

export default function Routes() {
    return (
        
        <BrowserRouter>
            <Switch>
                <Route path='/' element={<Login/>}></Route>
                <Route path='/books' element={<Books/>}></Route>
                <Route path='/book/new' element={<NewBook/>}></Route>
            </Switch>
        </BrowserRouter>
    );
}
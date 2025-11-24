import React, {useState} from 'react';
import { useNavigate, Link } from "react-router-dom";
import { FiArrowLeft } from "react-icons/fi";

import api from '../../services/api';

import './styles.css';

import logoImage from '../../assets/logo.png'

export default function NewBook(){
    
    const [id, setId] = useState(null);
    const [author, setAuthor] = useState('');
    const [launchDate, setLaunchDate] = useState('');
    const [price, setPrice] = useState('');
    const [title, setTitle] = useState('');

    const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem('accessToken');

    const navigate = useNavigate();

    async function createNewBook(e) {
        e.preventDefault();

        const [year, month, day] = launchDate.split('-');
        const launch_date = `${day}/${month}/${year}`;

        const data = {
            title, 
            author, 
            launch_date, 
            price
        };

        const headers = {
            Authorization: `Bearer ${accessToken}`
        };

        try {
            await api.post('api/book/v1', data, {headers: headers});
            navigate('/books');
        } catch (err) {
            alert('Error while creating book! Try again!')    
        }
    };

    return (
        <div className="new-book-container">
            <div className="content">
                <section className="form">
                    <img src={logoImage} alt="Maus Logo" />
                    <h1>Add New Book</h1>
                    <p>Enter the book information and click on 'Add'</p>
                    <Link className="back-link" to="/books">
                        <FiArrowLeft size={16} color="#4E56C0"/>
                        Home
                    </Link>
                </section>

                <form action="" onSubmit={createNewBook}>
                    <input 
                        placeholder="Title" 
                        value={title}
                        onChange={e => setTitle(e.target.value)}
                    />
                    <input 
                        placeholder="Author" 
                        value={author}
                        onChange={e => setAuthor(e.target.value)}
                    />
                    <input 
                        type="date" 
                        value={launchDate}
                        onChange={e => setLaunchDate(e.target.value)}
                    />
                    <input 
                        placeholder="Price" 
                        value={price}
                        onChange={e => setPrice(e.target.value)}
                    />

                    <button type="submit" className="button">Add</button>
                </form>
            </div>
        </div>
    );
}
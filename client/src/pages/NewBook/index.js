import React, {useState, useEffect} from 'react';
import { useNavigate, Link, useParams } from "react-router-dom";
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

    //param must have the same name as in routes.js
    const {bookId} = useParams();

    const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem('accessToken');

    const headers = {
        Authorization: `Bearer ${accessToken}`
    };

    const navigate = useNavigate();

    function formatDate(date, back=false) {
        if (back) {
            const [day, month, year] = date.split('/');
            return `${year}-${month}-${day}`;
        } else {
            const [year, month, day] = date.split('-');
            return `${day}/${month}/${year}`;
        }
    }

    async function loadBook() {
        try {
            const response = await api.get(`/api/book/v1/${bookId}`, {headers: headers});

            setId(response.data.id);
            setTitle(response.data.title);
            setAuthor(response.data.author);
            setPrice(response.data.price);
            setLaunchDate(formatDate(response.data.launch_date, true));
        } catch (error) {
            alert('Error while loading book! Try again!');
            navigate('/books');
        }
    }

    useEffect(() => {
        if (bookId === '0') return;
        else loadBook();
    }, [bookId]); //monitoring any changes on bookId
    
    async function saveOrUpdate(e) {
        e.preventDefault();

        const launch_date = formatDate(launchDate);

        const data = {
            title, 
            author, 
            launch_date, 
            price
        };

        try {
            if (bookId === '0') {
                await api.post('api/book/v1', data, {headers: headers});
            } else {
                data.id = id;
                await api.put('api/book/v1', data, {headers: headers});
            }
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
                    <h1>{bookId === '0' ? 'Add New' : 'Update'} Book</h1>
                    <p>Enter the book information and click on <strong>{bookId === '0' ? 'Add' : 'Update'}</strong></p>
                    <Link className="back-link" to="/books">
                        <FiArrowLeft size={16} color="#4E56C0"/>
                        Back to Books
                    </Link>
                </section>

                <form action="" onSubmit={saveOrUpdate}>
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

                    <button type="submit" className="button">{bookId === '0' ? 'Add' : 'Update'}</button>
                </form>
            </div>
        </div>
    );
}
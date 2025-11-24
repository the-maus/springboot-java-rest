import React, {useState, useEffect} from "react";
import { Link, useNavigate } from "react-router-dom";
import { FiPower, FiEdit, FiTrash2 } from "react-icons/fi";

import api from '../../services/api';

import './styles.css';

import logoImage from '../../assets/logo.png'

export default function Books() {
    const [books, setBooks] = useState([]);

    const username = localStorage.getItem('username');
    const accessToken = localStorage.getItem('accessToken');

    const navigate = useNavigate();

    const headers = {
        Authorization: `Bearer ${accessToken}`
    };

    const params = {
        page: 1,
        size: 4, 
        direction: 'asc'
    };

    function getBooks()
    {
        api.get('api/book/v1', {headers: headers, params: params}).then(response => {
            setBooks(response.data._embedded.books)
        })
    }

    async function logout() {
        localStorage.clear();
        navigate('/');
    }

    async function deleteBook(id) {
        try {
            await api.delete(`api/book/v1/${id}`, {headers: headers});

            getBooks();
        } catch (err) {
            alert('Delete failed! Try again!');
        }
    }

    useEffect(() => { getBooks()}, [])

    return (
        <div className="book-container">
            <header>
                <img src={logoImage} alt="Maus Logo" />
                <span>Welcome, <strong>{username.toUpperCase()}</strong>!</span>
                <Link className="button" to="/book/new">Add New Book</Link>
                <button onClick={logout} type="button">
                    <FiPower size={18} color="#4E56C0"/>
                </button>               
            </header>

            <h1>Registered Books</h1>
            <ul>
                {books.map(book => (
                    <li key={book.id}>
                        <strong>Title:</strong>
                        <p>{book.title}</p>
                        <strong>Author:</strong>
                        <p>{book.author}</p>
                        <strong>Price:</strong>
                        <p>{Intl.NumberFormat('pt-BR', {style: 'currency', currency: 'BRL'}).format(book.price)}</p>
                        <strong>Release Date:</strong>
                        {/* already formatted on server but could use Intl.DateTimeFormat('pt-BR').format() otherwise */}
                        <p>{book.launch_date} </p>

                        <button type="button">
                            <FiEdit size={20} color="#4E56C0"/>
                        </button>

                        <button onClick={() => deleteBook(book.id)} type="button">
                            <FiTrash2 size={20} color="#4E56C0"/>
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
}
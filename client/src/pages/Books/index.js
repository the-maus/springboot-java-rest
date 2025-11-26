import React, {useState, useEffect} from "react";
import { Link, useNavigate } from "react-router-dom";
import { FiPower, FiEdit, FiTrash2 } from "react-icons/fi";

import api from '../../services/api';

import './styles.css';

import logoImage from '../../assets/logo.png'

export default function Books() {
    const [books, setBooks] = useState([]);
    const [page, setPage] = useState(1);

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

    async function fetchMoreBooks() {
        var pageSize = params.size;

        const response = await api.get('api/book/v1', {headers: headers, params: {page: page, size: pageSize, direction:'asc'}});
        setBooks(books => [...books, ...response.data._embedded.books]); //appending to books
        setPage(page + 1);
    }

    async function reloadCurrentPage() {
        var pageSize = params.size;
        setBooks([]);

        for (let i = 1; i < page; i++) {
            var result = await api.get('api/book/v1', {headers: headers, params: {page: i, size: pageSize, direction:'asc'}});
            setBooks(books => [...books, ...result.data._embedded.books]); //appending to books
        }
    }

    async function logout() {
        localStorage.clear();
        navigate('/');
    }

    async function deleteBook(id) {
        try {
            await api.delete(`api/book/v1/${id}`, {headers: headers});

            reloadCurrentPage();
        } catch (err) {
            alert('Delete failed! Try again!');
        }
    }

    async function editBook(id) {
        try {
            navigate(`/book/new/${id}`);
        } catch (err) {
            alert('Edit book failed! Try again!')
        }
    }

    useEffect(() => { fetchMoreBooks()}, [])

    return (
        <div className="book-container">
            <header>
                <img src={logoImage} alt="Maus Logo" />
                <span>Welcome, <strong>{username.toUpperCase()}</strong>!</span>
                <Link className="button" to="/book/new/0">Add New Book</Link>
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
                        <p>{book.launch_date}</p>

                        <button onClick={() => editBook(book.id)} type="button">
                            <FiEdit size={20} color="#4E56C0"/>
                        </button>

                        <button onClick={() => deleteBook(book.id)} type="button">
                            <FiTrash2 size={20} color="#4E56C0"/>
                        </button>
                    </li>
                ))}
            </ul>

            <button className="button" onClick={() => fetchMoreBooks()} type="button">Load more</button>
        </div>
    );
}
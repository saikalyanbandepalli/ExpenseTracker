
import React from 'react'; 
import { Link } from 'react-router-dom';
import './Navbar.css'

const Navbar = () => (
  <nav>
    <ul>
      <li><Link to="/">Dashboard</Link></li>
      <li><Link to="/Register">Register</Link></li>
      <li><Link to="/Logout">Logout</Link></li>

      {/* <li><Link to="/add-expense">Add Expense</Link></li> */}
    </ul>
  </nav>
);

export default Navbar;

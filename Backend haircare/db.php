<?php
// Database connection details
$servername = "localhost";  // or your database server address
$username = "root";         // your MySQL username
$password = "";             // your MySQL password
$dbname = "haircare";       // your database name

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check the connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>

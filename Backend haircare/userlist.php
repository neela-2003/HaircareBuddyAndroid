<?php

include_once 'db.php';  // Ensure the database connection is correct

// Check the connection
if ($conn->connect_error) {
    echo json_encode([
        'status' => false,
        'message' => 'Connection failed: ' . $conn->connect_error
    ]);
    exit;
}

// Check if the request method is GET to fetch all user data
if ($_SERVER["REQUEST_METHOD"] == "GET") {
    try {
        // Query to fetch all user data
        $query = "SELECT Name, Age, Gender, Number, Email FROM usersignup";
        $result = $conn->query($query);

        // Check if there are results
        if ($result->num_rows > 0) {
            $users = [];

            // Fetch all the rows and store them in an array
            while ($row = $result->fetch_assoc()) {
                $users[] = $row;
            }

            // Return the users array as JSON
            echo json_encode([
                'status' => true,
                'message' => 'Users fetched successfully.',
                'users' => $users
            ]);
        } else {
            echo json_encode([
                'status' => false,
                'message' => 'No users found!'
            ]);
        }

    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error fetching users: ' . $e->getMessage()
        ]);
    }

    // Close the connection
    $conn->close();

} else {
    echo json_encode([
        'status' => false,
        'message' => 'Invalid request method! Use GET.'
    ]);
}
?>

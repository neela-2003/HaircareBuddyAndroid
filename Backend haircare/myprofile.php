<?php

include_once 'db.php';  // Include the database connection

// Check the connection
if ($conn->connect_error) {
    echo json_encode([
        'status' => false,
        'message' => 'Connection failed: ' . $conn->connect_error
    ]);
    exit;
}

// Ensure the request method is GET
if ($_SERVER["REQUEST_METHOD"] === "GET") {
    // Get the email from the GET request
    $email = isset($_GET['Email']) ? trim($_GET['Email']) : '';

    // Validate that the email is provided
    if (empty($email)) {
        echo json_encode([
            'status' => false,
            'message' => 'Email is required!'
        ]);
        exit;
    }

    // Validate the email format
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        echo json_encode([
            'status' => false,
            'message' => 'Invalid email format!'
        ]);
        exit;
    }

    try {
        // Prepare the SQL query
        $stmt = $conn->prepare("SELECT Name, Age, Gender, Number FROM usersignup WHERE Email = ?");
        if ($stmt === false) {
            echo json_encode([
                'status' => false,
                'message' => 'Error preparing the statement: ' . $conn->error
            ]);
            exit;
        }

        // Bind the email parameter and execute the statement
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $result = $stmt->get_result();

        // Check if the user exists
        if ($result->num_rows > 0) {
            $userData = $result->fetch_assoc();

            echo json_encode([
                'status' => true,
                'message' => 'Profile fetched successfully!',
                'data' => $userData // Return user data as an array
            ]);
        } else {
            echo json_encode([
                'status' => false,
                'message' => 'User not found!'
            ]);
        }

        $stmt->close();
    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error fetching profile: ' . $e->getMessage()
        ]);
    }
} else {
    echo json_encode([
        'status' => false,
        'message' => 'Invalid request method!'
    ]);
}

// Close the database connection
$conn->close();

?>

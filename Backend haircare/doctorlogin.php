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

// Check if data was received via POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {

    // Get the form data (Assume data is sent via POST request)
    $email = isset($_POST['Email']) ? trim($_POST['Email']) : '';
    $password = isset($_POST['Password']) ? trim($_POST['Password']) : '';

    // Validate the input data
    if (empty($email) || empty($password)) {
        echo json_encode([
            'status' => false,
            'message' => 'Email and password are required!'
        ]);
        exit;
    }

    // Validate email format
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        echo json_encode([
            'status' => false,
            'message' => 'Invalid email format!'
        ]);
        exit;
    }

    // Check if the email exists in the database
    try {
        $stmt = $conn->prepare("SELECT `doctor id` AS doctor_id, name, email, password FROM doctorsignup WHERE email = ?");
        if ($stmt === false) {
            echo json_encode([
                'status' => false,
                'message' => 'Error preparing SQL query: ' . $conn->error
            ]);
            exit;
        }

        $stmt->bind_param("s", $email);
        $stmt->execute();
        $result = $stmt->get_result();

        // If email exists, validate the password
        if ($result->num_rows > 0) {
            $user = $result->fetch_assoc();

            // Verify the password
            if (password_verify($password, $user['password'])) {
                echo json_encode([
                    'status' => true,
                    'message' => 'Login successful!',
                    'user' => [
                        'doctor_id' => $user['doctor_id'],  
                        'name' => $user['name'],
                        'email' => $user['email']
                    ]
                ]);
            } else {
                echo json_encode([
                    'status' => false,
                    'message' => 'Invalid password!'
                ]);
            }
        } else {
            echo json_encode([
                'status' => false,
                'message' => 'Email not found!'
            ]);
        }

        $stmt->close();
    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error during login: ' . $e->getMessage()
        ]);
        exit;
    }

    // Close the connection
    $conn->close();
} else {
    echo json_encode([
        'status' => false,
        'message' => 'Invalid request method!'
    ]);
}
?>

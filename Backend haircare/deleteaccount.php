<?php
include_once 'db.php'; // Ensure the database connection is correct
if ($conn->connect_error) {
    echo json_encode([
        'status' => false,
        'message' => 'Connection failed: ' . $conn->connect_error
    ]);
    exit;
}

// Check if data was received via POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    // Get email
    $email = isset($_POST['Email']) ? trim($_POST['Email']) : '';
    
    // Validate input
    if (empty($email)) {
        echo json_encode([
            'status' => false,
            'message' => 'Email is required!'
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

    try {
        // Check if user exists
        $stmt = $conn->prepare("SELECT * FROM usersignup WHERE Email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows == 0) {
            echo json_encode([
                'status' => false,
                'message' => 'User not found!'
            ]);
            $stmt->close();
            exit;
        }
        
        $stmt->close();
        
        // Delete user account
        $stmt = $conn->prepare("DELETE FROM usersignup WHERE Email = ?");
        $stmt->bind_param("s", $email);
        
        if ($stmt->execute()) {
            echo json_encode([
                'status' => true,
                'message' => 'Account deleted successfully!'
            ]);
        } else {
            echo json_encode([
                'status' => false,
                'message' => 'Error deleting account: ' . $stmt->error
            ]);
        }
        
        $stmt->close();
    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error: ' . $e->getMessage()
        ]);
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

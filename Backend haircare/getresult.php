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

// Check if data was received via GET
if ($_SERVER["REQUEST_METHOD"] == "GET") {

    // Get email from request
    $email = isset($_GET['email']) ? trim($_GET['email']) : '';

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

    // Check if the `addresult` table exists
    $tableCheckQuery = "SHOW TABLES LIKE 'addresult'";
    $tableCheckResult = $conn->query($tableCheckQuery);
    if ($tableCheckResult->num_rows == 0) {
        echo json_encode([
            'status' => false,
            'message' => "Error: Table 'addresult' does not exist!"
        ]);
        exit;
    }

    // Fetch the last added result using a subquery
    try {
        $stmt = $conn->prepare("SELECT result FROM addresult WHERE email = ? ORDER BY ROW_NUMBER() OVER () DESC LIMIT 1");

        if ($stmt === false) {
            echo json_encode([
                'status' => false,
                'message' => 'Error preparing statement: ' . $conn->error
            ]);
            exit;
        }

        // Bind parameters and execute
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->bind_result($result);
        
        // Check if a result was found
        if ($stmt->fetch()) {
            echo json_encode([
                'status' => true,
                'email' => $email,
                'result' => $result
            ]);
        } else {
            echo json_encode([
                'status' => false,
                'message' => 'No result found for this email!'
            ]);
        }

        // Close the statement
        $stmt->close();

    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error fetching result: ' . $e->getMessage()
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

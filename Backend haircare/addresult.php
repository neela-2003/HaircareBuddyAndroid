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

    // Get input data
    $email = isset($_POST['email']) ? trim($_POST['email']) : '';
    $result = isset($_POST['result']) ? trim($_POST['result']) : '';

    // Validate input
    if (empty($email) || empty($result)) {
        echo json_encode([
            'status' => false,
            'message' => 'Email and result are required!'
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

    // Insert data into the `addresult` table
    try {
        $stmt = $conn->prepare("INSERT INTO addresult (email, result) VALUES (?, ?)");

        if ($stmt === false) {
            echo json_encode([
                'status' => false,
                'message' => 'Error preparing statement: ' . $conn->error
            ]);
            exit;
        }

        // Bind parameters and execute
        $stmt->bind_param("ss", $email, $result);

        if ($stmt->execute()) {
            echo json_encode([
                'status' => true,
                'message' => 'Result added successfully!'
            ]);
        } else {
            echo json_encode([
                'status' => false,
                'message' => 'Error executing query: ' . $stmt->error
            ]);
        }

        // Close the statement
        $stmt->close();

    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error inserting result: ' . $e->getMessage()
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

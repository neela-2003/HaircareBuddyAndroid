<?php

include_once 'db.php'; // Include DB connection

header('Content-Type: application/json');

// Check DB connection
if ($conn->connect_error) {
    echo json_encode(['status' => false, 'message' => 'Connection failed: ' . $conn->connect_error]);
    exit;
}

if ($_SERVER["REQUEST_METHOD"] === "GET") {
    $email = isset($_GET['email']) ? trim($_GET['email']) : '';

    if (empty($email)) {
        echo json_encode(['status' => false, 'message' => 'Email is required!']);
        exit;
    }

    // Prepare and execute the query
    $stmt = $conn->prepare("SELECT ai_response FROM question WHERE email = ?");
    $stmt->bind_param("s", $email);
    $stmt->execute();
    $stmt->bind_result($ai_response);

    if ($stmt->fetch()) {
        echo json_encode([
            'status' => true,
            'email' => $email,
            'ai_response' => $ai_response
        ]);
    } else {
        echo json_encode([
            'status' => false,
            'message' => 'No AI response found for this email.'
        ]);
    }

    $stmt->close();
} else {
    echo json_encode(['status' => false, 'message' => 'Invalid request method!']);
}

$conn->close();
?>

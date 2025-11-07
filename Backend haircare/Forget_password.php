<?php
// Enable error reporting for debugging
ini_set('display_errors', 1);
error_reporting(E_ALL);

include_once 'db.php'; // Your DB connection file

if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $email = isset($_POST['email']) ? trim($_POST['email']) : '';

    if (empty($email)) {
        echo json_encode(['status' => false, 'message' => 'Email is required.']);
        exit;
    }

    // Use correct table: usersignup
    $stmt = $conn->prepare("SELECT * FROM usersignup WHERE Email = ?");
    $stmt->bind_param("s", $email);
    $stmt->execute();

    $result = $stmt->get_result();

    if ($result && $result->num_rows > 0) {
        echo json_encode(['status' => true, 'message' => 'User found. You can proceed to reset password.']);
    } else {
        echo json_encode(['status' => false, 'message' => 'Email not registered.']);
    }

    $stmt->close();
    $conn->close();
} else {
    echo json_encode(['status' => false, 'message' => 'Invalid request method.']);
}
?>

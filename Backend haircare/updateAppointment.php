<?php
// Include database connection
include_once 'db.php'; // Ensure this file contains the correct DB connection

// Check database connection
if ($conn->connect_error) {
    echo json_encode([
        'status' => false,
        'message' => 'Database connection failed: ' . $conn->connect_error
    ]);
    exit;
}

// Check if the request method is POST to update the appointment status
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    // Get input data from the request
    $patient_email = isset($_POST['patient_email']) ? trim($_POST['patient_email']) : '';
    $status = isset($_POST['status']) ? trim($_POST['status']) : '';

    // Validate the required fields
    if (empty($patient_email) || empty($status)) {
        echo json_encode([
            'status' => false,
            'message' => 'Email and status are required!'
        ]);
        exit;
    }

    // Validate email format
    if (!filter_var($patient_email, FILTER_VALIDATE_EMAIL)) {
        echo json_encode([
            'status' => false,
            'message' => 'Invalid email format!'
        ]);
        exit;
    }

    // Update the status of the appointment for the given email
    try {
        $updateQuery = "UPDATE bookappointment SET status = ? WHERE useremail = ?";
        $stmt = $conn->prepare($updateQuery);
        $stmt->bind_param("ss", $status, $patient_email); // Bind status and email parameters

        // Execute the update query
        if ($stmt->execute()) {
            if ($stmt->affected_rows > 0) {
                echo json_encode([
                    'status' => true,
                    'message' => 'Appointment status updated successfully!'
                ]);
            } else {
                echo json_encode([
                    'status' => false,
                    'message' => 'No appointment found with the provided email.'
                ]);
            }
        } else {
            echo json_encode([
                'status' => false,
                'message' => 'Failed to update the status: ' . $stmt->error
            ]);
        }

        $stmt->close();
    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error updating status: ' . $e->getMessage()
        ]);
    }

    // Close the database connection
    $conn->close();
} else {
    echo json_encode([
        'status' => false,
        'message' => 'Invalid request method. Use POST.'
    ]);
}
?>

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

// Check if the request method is POST
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    // Get input data from the request
    $doctor_id = isset($_POST['doctor_id']) ? trim($_POST['doctor_id']) : '';
    $patient_name = isset($_POST['patient_name']) ? trim($_POST['patient_name']) : '';
    $patient_email = isset($_POST['patient_email']) ? trim($_POST['patient_email']) : '';
    $appointment_date = isset($_POST['appointment_date']) ? trim($_POST['appointment_date']) : '';
    $appointment_time = isset($_POST['appointment_time']) ? trim($_POST['appointment_time']) : '';
    $status = isset($_POST['status']) ? trim($_POST['status']) : '';

    // Validate the required fields
    if (empty($doctor_id) || empty($patient_name) || empty($patient_email) || empty($appointment_date) || empty($appointment_time) || empty($status)) {
        echo json_encode([
            'status' => false,
            'message' => 'All fields including status are required!'
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

    // Validate the appointment date and time format
    $date = DateTime::createFromFormat('d-m-Y', $appointment_date);
    $time = DateTime::createFromFormat('h:i A', $appointment_time);
    
    if (!$date || $date->format('d-m-Y') !== $appointment_date) {
        echo json_encode([
            'status' => false,
            'message' => 'Invalid appointment date format! Use "DD-MM-YYYY".'
        ]);
        exit;
    }
    if (!$time || $time->format('h:i A') !== $appointment_time) {
        echo json_encode([
            'status' => false,
            'message' => 'Invalid appointment time format! Use "HH:MM AM/PM".'
        ]);
        exit;
    }

    // Convert date format to Y-m-d and time to H:i:s for database storage
    $formatted_date = $date->format('Y-m-d');
    $formatted_time = $time->format('H:i:s');

    // Insert the new appointment with the status field
    try {
        $insertQuery = "INSERT INTO bookappointment (doctorid, name, date, time, useremail, status) VALUES (?, ?, ?, ?, ?, ?)";
        $stmt = $conn->prepare($insertQuery);
        $stmt->bind_param("ssssss", $doctor_id, $patient_name, $formatted_date, $formatted_time, $patient_email, $status);

        if ($stmt->execute()) {
            echo json_encode([
                'status' => true,
                'message' => 'Appointment booked successfully!'
            ]);
        } else {
            echo json_encode([
                'status' => false,
                'message' => 'Failed to book appointment: ' . $stmt->error
            ]);
        }

        $stmt->close();
    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error booking appointment: ' . $e->getMessage()
        ]);
        exit;
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

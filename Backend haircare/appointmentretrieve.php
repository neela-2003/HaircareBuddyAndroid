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

// Check if the request method is GET to retrieve all appointments
if ($_SERVER["REQUEST_METHOD"] === "GET") {
    try {
        // Query to get all appointments data
        $query = "SELECT doctorid, name, date, time, useremail, status FROM bookappointment";
        $result = $conn->query($query);

        // Check if there are results
        if ($result->num_rows > 0) {
            $appointments = [];

            // Fetch all the rows and store them in an array
            while ($row = $result->fetch_assoc()) {
                $appointments[] = [
                    'doctorid' => $row['doctorid'],
                    'name' => $row['name'],
                    'date' => $row['date'],
                    'time' => $row['time'],
                    'useremail' => $row['useremail'],
                    'status' => $row['status'] // Include the status for each appointment
                ];
            }

            // Return the appointments array as JSON with a success message
            echo json_encode([
                'status' => true,
                'message' => 'Appointments fetched successfully.',
                'appointments' => $appointments
            ]);
        } else {
            echo json_encode([
                'status' => false,
                'message' => 'No appointments found!'
            ]);
        }
    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error fetching appointments: ' . $e->getMessage()
        ]);
    }

    // Close the database connection
    $conn->close();
} else {
    echo json_encode([
        'status' => false,
        'message' => 'Invalid request method. Use GET.'
    ]);
}
?>

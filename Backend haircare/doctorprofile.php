<?php

include_once 'db.php';  // Include database connection

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

    // Check if doctorId is passed via GET request
    if (isset($_GET['doctorId']) && !empty($_GET['doctorId'])) {

        // Sanitize the doctorId to prevent SQL injection
        $doctorId = $conn->real_escape_string($_GET['doctorId']);

        // Prepare the SQL query to fetch the doctor details
        try {
            $stmt = $conn->prepare("SELECT name, `doctor id`, experience FROM doctorsignup WHERE `doctor id` = ?");
            
            if ($stmt === false) {
                echo json_encode([
                    'status' => false,
                    'message' => 'Error preparing the statement: ' . $conn->error
                ]);
                exit;
            }

            // Bind the parameter
            $stmt->bind_param("s", $doctorId);

            // Execute the query
            $stmt->execute();

            // Get the result
            $result = $stmt->get_result();

            // Check if the doctor exists
            if ($result->num_rows > 0) {
                $doctorData = $result->fetch_assoc();

                // Return the doctor profile details
                echo json_encode([
                    'status' => true,
                    'message' => 'Doctor profile fetched successfully!',
                    'data' => [
                        'name' => $doctorData['name'],
                        'doctorId' => $doctorData['doctor id'],
                        'experience' => $doctorData['experience']
                    ]
                ]);
            } else {
                echo json_encode([
                    'status' => false,
                    'message' => 'Doctor not found!'
                ]);
            }

            // Close the statement
            $stmt->close();
        } catch (Exception $e) {
            // If there was an error fetching the data
            echo json_encode([
                'status' => false,
                'message' => 'Error fetching profile: ' . $e->getMessage()
            ]);
        }

    } else {
        // No doctorId provided
        echo json_encode([
            'status' => false,
            'message' => 'Doctor ID is required! Refer to the signup and login process to ensure the correct ID is used.'
        ]);
    }

} else {
    echo json_encode([
        'status' => false,
        'message' => 'Invalid request method!'
    ]);
}

// Close the connection
$conn->close();

?>

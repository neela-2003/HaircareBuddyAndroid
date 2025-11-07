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

// Fetch doctor data
try {
    // Query the doctors from the database (removed 'id' column from SELECT)
    $result = $conn->query("SELECT name, `doctor id`, `mobile no`, experience, email FROM doctorsignup");

    // Check if the query was successful
    if (!$result) {
        // If the query failed, output the error
        echo json_encode([
            'status' => false,
            'message' => 'Query failed: ' . $conn->error
        ]);
        exit;
    }

    // Check if any doctors were found
    if ($result->num_rows > 0) {
        // Fetch all data and store it in an array
        $doctorData = [];
        while ($row = $result->fetch_assoc()) {
            $doctorData[] = [
                'name' => $row['name'],
                'doctorId' => $row['doctor id'],
                'mobileNo' => $row['mobile no'],
                'experience' => $row['experience'],
                'email' => $row['email']
            ];
        }
        // Return the data as a JSON array
        echo json_encode([
            'status' => true,
            'message' => 'Doctors fetched successfully!',
            'data' => $doctorData
        ]);
    } else {
        // No doctors found
        echo json_encode([
            'status' => false,
            'message' => 'No doctors found!'
        ]);
    }

} catch (Exception $e) {
    // Log any error during execution
    error_log("Error fetching doctor data: " . $e->getMessage());
    echo json_encode([
        'status' => false,
        'message' => 'Error fetching data: ' . $e->getMessage()
    ]);
}

// Close the connection
$conn->close();

?>

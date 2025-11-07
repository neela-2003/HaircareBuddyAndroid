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

// Query to retrieve all results from `addresult` table
$query = "SELECT email, result FROM addresult";
$result = $conn->query($query);

// Check if there are any records
if ($result->num_rows > 0) {
    $data = [];

    while ($row = $result->fetch_assoc()) {
        $data[] = [
            'email' => $row['email'],
            'result' => $row['result']
        ];
    }

    echo json_encode([
        'status' => true,
        'message' => 'Results retrieved successfully!',
        'data' => $data
    ]);
} else {
    echo json_encode([
        'status' => false,
        'message' => 'No results found!',
        'data' => []
    ]);
}

// Close the connection
$conn->close();
?>

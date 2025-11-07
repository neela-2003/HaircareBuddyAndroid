<?php

include_once 'db.php'; // Include the database connection

// Check the database connection
if ($conn->connect_error) {
    echo json_encode([
        'status' => false,
        'message' => 'Connection failed: ' . $conn->connect_error,
        'data' => []
    ]);
    exit;
}

// Handle the GET request to fetch all reviews
try {
    // Query to select all reviews (without created_at)
    $result = $conn->query("SELECT email, view, rating FROM review");

    // Check if query execution was successful
    if (!$result) {
        echo json_encode([
            'status' => false,
            'message' => 'Error executing query: ' . $conn->error,
            'data' => []
        ]);
        exit;
    }

    // Prepare an array to store the reviews
    $reviews = [];

    // Fetch the rows as associative arrays
    while ($row = $result->fetch_assoc()) {
        $reviews[] = $row;
    }

    // Check if there are any reviews
    if (count($reviews) > 0) {
        echo json_encode([
            'status' => true,
            'message' => 'Reviews fetched successfully!',
            'data' => $reviews
        ]);
    } else {
        echo json_encode([
            'status' => false,
            'message' => 'No reviews found!',
            'data' => []
        ]);
    }

} catch (Exception $e) {
    echo json_encode([
        'status' => false,
        'message' => 'Error fetching reviews: ' . $e->getMessage(),
        'data' => []
    ]);
}

// Close the database connection
$conn->close();

?>

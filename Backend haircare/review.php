<?php

include_once 'db.php'; // Include the database connection

// Check the connection
if ($conn->connect_error) {
    echo json_encode([
        'status' => false,
        'message' => 'Connection failed: ' . $conn->connect_error
    ]);
    exit;
}

// Handle POST and GET requests
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    // Handle review and rating submission
    $email = isset($_POST['email']) ? trim($_POST['email']) : '';
    $view = isset($_POST['view']) ? trim($_POST['view']) : '';
    $rating = isset($_POST['rating']) ? trim($_POST['rating']) : '';

    // Validate the inputs
    if (empty($email) || empty($view) || empty($rating)) {
        echo json_encode([
            'status' => false,
            'message' => 'All fields are required!'
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

    // Validate rating
    if (!is_numeric($rating) || $rating < 1 || $rating > 5) {
        echo json_encode([
            'status' => false,
            'message' => 'Rating must be a number between 1 and 5!'
        ]);
        exit;
    }

    // Insert or update the review and rating in the database
    try {
        $stmt = $conn->prepare("INSERT INTO review (email, view, rating) VALUES (?, ?, ?) 
                                ON DUPLICATE KEY UPDATE view = VALUES(view), rating = VALUES(rating)");
        if ($stmt === false) {
            echo json_encode([
                'status' => false,
                'message' => 'Error preparing the statement: ' . $conn->error
            ]);
            exit;
        }

        $stmt->bind_param("ssi", $email, $view, $rating);

        if ($stmt->execute()) {
            echo json_encode([
                'status' => true,
                'message' => 'Review and rating submitted successfully!'
            ]);
        } else {
            echo json_encode([
                'status' => false,
                'message' => 'Error submitting review: ' . $stmt->error
            ]);
        }

        $stmt->close();
    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error during submission: ' . $e->getMessage()
        ]);
    }

} elseif ($_SERVER["REQUEST_METHOD"] === "GET") {
    // Handle fetching all reviews
    try {
        $result = $conn->query("SELECT email, view, rating, created_at FROM review");

        if ($result->num_rows > 0) {
            $reviews = [];
            while ($row = $result->fetch_assoc()) {
                $reviews[] = $row;
            }

            echo json_encode([
                'status' => true,
                'message' => 'Reviews fetched successfully!',
                'data' => $reviews
            ]);
        } else {
            echo json_encode([
                'status' => false,
                'message' => 'No reviews found!'
            ]);
        }
    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error fetching reviews: ' . $e->getMessage()
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

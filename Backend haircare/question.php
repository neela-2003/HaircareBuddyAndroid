<?php

include_once 'db.php'; // Include the database connection

// Check connection
if ($conn->connect_error) {
    die(json_encode([
        'status' => false,
        'message' => 'Connection failed: ' . $conn->connect_error
    ]));
}

// Handle POST and GET requests
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    // Insert or update a question row
    $email = isset($_POST['email']) ? trim($_POST['email']) : '';
    $answers = [];
    for ($i = 1; $i <= 16; $i++) {
        $answers["answer$i"] = isset($_POST["answer$i"]) ? trim($_POST["answer$i"]) : '';
    }

    // Validate email
    if (empty($email)) {
        echo json_encode([
            'status' => false,
            'message' => 'Email is required!'
        ]);
        exit;
    }

    // Insert or update the record
    try {
        $placeholders = implode(', ', array_fill(0, 16, '?'));
        $update_placeholders = implode(', ', array_map(fn($i) => "answer$i = VALUES(answer$i)", range(1, 16)));

        $stmt = $conn->prepare(
            "INSERT INTO question (email, " . implode(', ', array_keys($answers)) . ") 
            VALUES (?, $placeholders) 
            ON DUPLICATE KEY UPDATE $update_placeholders"
        );

        if (!$stmt) {
            echo json_encode([
                'status' => false,
                'message' => 'Error preparing query: ' . $conn->error
            ]);
            exit;
        }

        $stmt->bind_param(
            str_repeat('s', 17), // 1 email + 16 answers
            $email,
            ...array_values($answers)
        );

        if ($stmt->execute()) {
            echo json_encode([
                'status' => true,
                'message' => 'Data inserted/updated successfully!'
            ]);
        } else {
            echo json_encode([
                'status' => false,
                'message' => 'Error executing query: ' . $stmt->error
            ]);
        }

        $stmt->close();
    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error: ' . $e->getMessage()
        ]);
    }

} elseif ($_SERVER["REQUEST_METHOD"] === "GET") {
    // Fetch all question records
    try {
        $result = $conn->query("SELECT email, answer1, answer2, answer3, answer4, answer5, answer6, 
                                answer7, answer8, answer9, answer10, answer11, answer12, answer13, 
                                answer14, answer15, answer16 FROM question");

        if ($result->num_rows > 0) {
            $questions = [];
            while ($row = $result->fetch_assoc()) {
                $questions[] = $row;
            }

            echo json_encode([
                'status' => true,
                'message' => 'Questions fetched successfully!',
                'data' => $questions
            ]);
        } else {
            echo json_encode([
                'status' => false,
                'message' => 'No questions found!',
                'data' => []
            ]);
        }
    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error fetching questions: ' . $e->getMessage(),
            'data' => []
        ]);
    }
} else {
    echo json_encode([
        'status' => false,
        'message' => 'Invalid request method!'
    ]);
}

// Close the database connection
$conn->close();

?>

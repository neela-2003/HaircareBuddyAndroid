<?php

include_once 'db.php'; // Database connection

header('Content-Type: application/json');

// Check DB connection
if ($conn->connect_error) {
    echo json_encode(['status' => false, 'message' => 'Connection failed: ' . $conn->connect_error]);
    exit;
}

if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $email = isset($_POST['email']) ? trim($_POST['email']) : '';
    $answers = [];

    for ($i = 1; $i <= 16; $i++) {
        $answers["answer$i"] = isset($_POST["answer$i"]) ? trim($_POST["answer$i"]) : '';
    }

    // Basic validation
    if (empty($email)) {
        echo json_encode(['status' => false, 'message' => 'Email is required!']);
        exit;
    }

    if (count(array_filter($answers)) < 16) {
        echo json_encode(['status' => false, 'message' => 'All 16 answers are required!']);
        exit;
    }

    // Step 1: Insert/Update answers (without AI response)
    try {
        $placeholders = implode(', ', array_fill(0, 16, '?'));
        $update_placeholders = implode(', ', array_map(fn($i) => "answer$i = VALUES(answer$i)", range(1, 16)));

        $stmt = $conn->prepare(
            "INSERT INTO question (email, " . implode(', ', array_keys($answers)) . ") 
            VALUES (?, $placeholders) 
            ON DUPLICATE KEY UPDATE $update_placeholders"
        );

        $stmt->bind_param(str_repeat('s', 17), $email, ...array_values($answers));
        $stmt->execute();
        $stmt->close();

        // Step 2: Send data to AI Python server
        $userdata = array_merge(['email' => $email], $answers);
        $aiPayload = json_encode(['userdata' => $userdata]);

        $ch = curl_init('http://localhost:8000'); // adjust if Python server is remote
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $aiPayload);
        curl_setopt($ch, CURLOPT_HTTPHEADER, ['Content-Type: application/json']);

        $aiResponse = curl_exec($ch);
        curl_close($ch);

        file_put_contents('ai_debug.log', $aiResponse);

        $aiData = json_decode($aiResponse, true);
        $aiText = $aiData['reply'] ?? '';

        // Step 3: Update ai_response in the DB
        $stmt2 = $conn->prepare("UPDATE question SET ai_response = ? WHERE email = ?");
        $stmt2->bind_param("ss", $aiText, $email);
        $stmt2->execute();
        $stmt2->close();

        echo json_encode([
            'status' => true,
            'message' => 'Hair quiz submitted and AI response saved!',
            'ai_response' => $aiText
        ]);
    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error: ' . $e->getMessage()
        ]);
    }
} else {
    echo json_encode(['status' => false, 'message' => 'Invalid request method!']);
}

$conn->close();
?>

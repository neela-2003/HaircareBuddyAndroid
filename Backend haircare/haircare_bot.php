<?php
header('Content-Type: application/json');

$hostname = "localhost";
$username = "root";
$password = "";
$dbname = "haircare";

$conn = mysqli_connect($hostname, $username, $password, $dbname);

if (!$conn) {
    die(json_encode([
        'status' => false,
        'message' => 'Connection failed: ' . mysqli_connect_error()
    ]));
}

if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $email = $_POST['email'] ?? '';
    $answers = [];

    for ($i = 1; $i <= 16; $i++) {
        $answers["answer$i"] = $_POST["answer$i"] ?? '';
    }

    if (empty($email)) {
        echo json_encode(['status' => false, 'message' => 'Email is required!']);
        exit;
    }

    // Call Python AI server before inserting data
    $userdata = json_encode(['userdata' => $answers]);
    $url = 'http://localhost:8000'; // Python server URL

    $ch = curl_init($url);
    curl_setopt($ch, CURLOPT_POST, 1);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $userdata);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, ['Content-Type: application/json']);

    $response = curl_exec($ch);
    curl_close($ch);

    if ($response === false) {
        echo json_encode(['status' => false, 'message' => 'Failed to connect to AI server.']);
        exit;
    }

    $aiResponse = json_decode($response, true);
    $aiText = $aiResponse['reply'] ?? '';

    // Insert or update user answers and AI response in the database
    $placeholders = implode(', ', array_fill(0, 16, '?'));
    $update_placeholders = implode(', ', array_map(fn($i) => "answer$i = VALUES(answer$i)", range(1, 16)));
    $update_placeholders .= ", ai_response = VALUES(ai_response)";

    $stmt = $conn->prepare(
        "INSERT INTO question (email, " . implode(', ', array_keys($answers)) . ", ai_response) 
         VALUES (?, $placeholders, ?) 
         ON DUPLICATE KEY UPDATE $update_placeholders"
    );

    if ($stmt) {
        // Combine all arguments for bind_param
        $params = array_merge([$email], array_values($answers), [$aiText]);
        
        // Dynamically call bind_param using unpacking
        $stmt->bind_param(str_repeat('s', count($params)), ...$params);
        
        $stmt->execute();
        $stmt->close();
    
        echo json_encode(['status' => true, 'message' => 'Data processed successfully!', 'ai_response' => $aiResponse]);
    } else {
        echo json_encode(['status' => false, 'message' => 'Error preparing query: ' . $conn->error]);
        exit;
    }
    
} else {
    echo json_encode(['status' => false, 'message' => 'Invalid request method!']);
}

$conn->close();
?>
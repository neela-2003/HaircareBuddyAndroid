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

// Check if data was received via POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {

    // Get the form data (Assume data is sent via POST request)
    $name = isset($_POST['Name']) ? trim($_POST['Name']) : '';
    $doctorId = isset($_POST['DoctorId']) ? trim($_POST['DoctorId']) : '';
    $number = isset($_POST['Number']) ? trim($_POST['Number']) : '';
    $experience = isset($_POST['Experience']) ? trim($_POST['Experience']) : '';
    $email = isset($_POST['Email']) ? trim($_POST['Email']) : '';
    $password = isset($_POST['Password']) ? trim($_POST['Password']) : '';

    // Validate the input data
    if (empty($name) || empty($doctorId) || empty($number) || empty($experience) || empty($email) || empty($password)) {
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

    // Validate phone number format (optional, can add more validations as needed)
    if (empty($number) || !preg_match('/^\+?[0-9]{10,15}$/', $number)) {
        echo json_encode([
            'status' => false,
            'message' => 'Invalid phone number format!'
        ]);
        exit;
    }

    // Validate password (optional, can adjust the length or complexity rules)
    if (strlen($password) < 6) {
        echo json_encode([
            'status' => false,
            'message' => 'Password must be at least 6 characters long!'
        ]);
        exit;
    }

    // Hash the password (using bcrypt)
    try {
        $hashedPassword = password_hash($password, PASSWORD_BCRYPT);
    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error hashing password: ' . $e->getMessage()
        ]);
        exit;
    }

    // Check if the email already exists in the database
    try {
        $emailCheck = $conn->prepare("SELECT * FROM doctorsignup WHERE email = ?");
        if ($emailCheck === false) {
            echo json_encode([
                'status' => false,
                'message' => 'Error preparing email check statement: ' . $conn->error
            ]);
            exit;
        }
        $emailCheck->bind_param("s", $email);
        $emailCheck->execute();
        $emailResult = $emailCheck->get_result();
        if ($emailResult->num_rows > 0) {
            echo json_encode([
                'status' => false,
                'message' => 'Email is already registered!'
            ]);
            $emailCheck->close();
            exit;
        }
        $emailCheck->close();
    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error checking email: ' . $e->getMessage()
        ]);
        exit;
    }

    // Check if the phone number already exists in the database
    try {
        $numberCheck = $conn->prepare("SELECT * FROM doctorsignup WHERE `mobile no` = ?");
        if ($numberCheck === false) {
            echo json_encode([
                'status' => false,
                'message' => 'Error preparing number check statement: ' . $conn->error
            ]);
            exit;
        }
        $numberCheck->bind_param("s", $number);
        $numberCheck->execute();
        $numberResult = $numberCheck->get_result();
        if ($numberResult->num_rows > 0) {
            echo json_encode([
                'status' => false,
                'message' => 'Phone number is already registered!'
            ]);
            $numberCheck->close();
            exit;
        }
        $numberCheck->close();
    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error checking phone number: ' . $e->getMessage()
        ]);
        exit;
    }

    // Prepare SQL to insert the data into the database
    try {
        // Check if the statement was prepared successfully
        $stmt = $conn->prepare("INSERT INTO doctorsignup (name, `doctor id`, `mobile no`, experience, email, password) VALUES (?, ?, ?, ?, ?, ?)");

        if ($stmt === false) {
            // Print detailed error if prepare fails
            echo json_encode([
                'status' => false,
                'message' => 'Error preparing the statement: ' . $conn->error . '. SQL Query: ' . "INSERT INTO doctorsignup (name, `doctor id`, `mobile no`, experience, email, password) VALUES (?, ?, ?, ?, ?, ?)"
            ]);
            exit;
        }

        // Bind the parameters
        $stmt->bind_param("ssssss", $name, $doctorId, $number, $experience, $email, $hashedPassword);

        // Execute the query
        if ($stmt->execute()) {
            echo json_encode([
                'status' => true,
                'message' => 'Signup successful!'
            ]);
        } else {
            echo json_encode([
                'status' => false,
                'message' => 'Error executing query: ' . $stmt->error
            ]);
        }

        // Close the statement
        $stmt->close();
    } catch (Exception $e) {
        echo json_encode([
            'status' => false,
            'message' => 'Error during signup: ' . $e->getMessage()
        ]);
    }

    // Close the connection
    $conn->close();

} else {
    echo json_encode([
        'status' => false,
        'message' => 'Invalid request method!'
    ]);
}
?>
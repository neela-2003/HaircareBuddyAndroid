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
    $email = isset($_POST['Email']) ? trim($_POST['Email']) : '';
    $name = isset($_POST['Name']) ? trim($_POST['Name']) : '';
    $age = isset($_POST['Age']) ? trim($_POST['Age']) : '';
    $gender = isset($_POST['Gender']) ? trim($_POST['Gender']) : '';
    $number = isset($_POST['Number']) ? trim($_POST['Number']) : '';
    $password = isset($_POST['Password']) ? trim($_POST['Password']) : '';

    // Validate the input data
    if (empty($email) || empty($name) || empty($age) || empty($gender) || empty($number) || empty($password)) {
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

    // Validate age (ensure it's a number and reasonable)
    if (!is_numeric($age) || $age <= 0 || $age > 120) {
        echo json_encode([
            'status' => false,
            'message' => 'Invalid age!'
        ]);
        exit;
    }

    // Validate gender (assuming you expect "Male", "Female", or "Other")
    $validGenders = ['Male', 'Female', 'Other'];
    if (!in_array($gender, $validGenders)) {
        echo json_encode([
            'status' => false,
            'message' => 'Invalid gender!'
        ]);
        exit;
    }

    // Check if the email exists in the database
    try {
        $emailCheck = $conn->prepare("SELECT * FROM usersignup WHERE Email = ?");
        $emailCheck->bind_param("s", $email);
        $emailCheck->execute();
        $emailResult = $emailCheck->get_result();

        if ($emailResult->num_rows === 0) {
            echo json_encode([
                'status' => false,
                'message' => 'Email not found!'
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

    // Check if the phone number exists in the database (optional check)
    try {
        $numberCheck = $conn->prepare("SELECT * FROM usersignup WHERE Number = ?");
        $numberCheck->bind_param("s", $number);
        $numberCheck->execute();
        $numberResult = $numberCheck->get_result();

        if ($numberResult->num_rows === 0) {
            echo json_encode([
                'status' => false,
                'message' => 'Phone number not found!'
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

    // Hash the password (using bcrypt) if a new password is provided
    $hashedPassword = password_hash($password, PASSWORD_BCRYPT);

    // Prepare SQL to update the data in the database (excluding Email)
    try {
        // Bind the parameters correctly
        $stmt = $conn->prepare("UPDATE usersignup SET Name = ?, Age = ?, Gender = ?, Password = ? WHERE Email = ?");
        
        // Check if the statement was prepared successfully
        if ($stmt === false) {
            echo json_encode([
                'status' => false,
                'message' => 'Error preparing the statement: ' . $conn->error
            ]);
            exit;
        }

        // Bind the parameters (excluding email)
        $stmt->bind_param("sisss", $name, $age, $gender, $hashedPassword, $email);

        // Execute the query
        if ($stmt->execute()) {
            echo json_encode([
                'status' => true,
                'message' => 'User details updated successfully!'
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
            'message' => 'Error during update: ' . $e->getMessage()
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

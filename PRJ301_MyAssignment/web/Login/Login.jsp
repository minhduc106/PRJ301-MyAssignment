<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - ABC Company</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Login/styles.css">
</head>
<body>
    <div class="login-container">
        <div class="login-box"> 
            <div class="login-form">
                <div class="logo">
                    <img src="${pageContext.request.contextPath}/Login/logo.png" alt="Logo">
                </div>
                <form action="login" method="POST">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" placeholder="Username" required>
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Password" required>
                    <div class="action-buttons">
                        <button type="submit" class="login-button">Login</button>
                    </div>
                </form>
            </div>
            <div class="welcome-box">
                <h2>Welcome to</h2>
                <h1>ABC Company</h1>
                <p>Login to Access Dashboard</p>
            </div>
        </div>
    </div>
</body>
</html>

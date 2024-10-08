<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="login-container">
        <div class="login-box">
            <div class="icon">
                <!-- Add your shopping cart icon here -->
                <img src="shopping-cart-icon.png.png" alt="ABC Company">
            </div>
            <form action="login" method="post">
                <div class="input-group">
                    <label for="username" class="visually-hidden">Username</label>
                    <input type="text" id="username" name="username" placeholder="Username" required>
                </div>
                <div class="input-group">
                    <label for="password" class="visually-hidden">Password</label>
                    <input type="password" id="password" name="password" placeholder="Password" required>
                </div>
                <div class="input-group">
                    <button type="submit" class="btn">Login</button>
                </div>
                <a href="forgotPassword.jsp" class="forgot-password">Forgot password?</a>
            </form>
        </div>
    </div>
</body>
</html>

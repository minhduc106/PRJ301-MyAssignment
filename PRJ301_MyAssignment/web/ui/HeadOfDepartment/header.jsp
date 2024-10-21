<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - ABC Company</title>
    <link rel="stylesheet" href="headerStyle.css">
</head>
<body>
    <header class="header">
        <div class="logo">
            <img src="logo.png" alt="Logo">
        </div>
        <nav class="navbar">
            <ul class="menu">
                <c:choose>
                    <c:when test="${user.role == 'admin'}">
                        <li><a href="manageEmployees.jsp">Manage Employees</a></li>
                        <li><a href="productionPlan.jsp">View Production Plans</a></li>
                        <li><a href="workshopOverview.jsp">Workshop Overview</a></li>
                    </c:when>
                    <c:when test="${user.role == 'manager'}">
                        <li><a href="productionSchedule.jsp">Create Production Schedule</a></li>
                        <li><a href="attendanceTracking.jsp">Track Attendance</a></li>
                    </c:when>
                    <c:when test="${user.role == 'worker'}">
                        <li><a href="workSchedule.jsp">View My Work Schedule</a></li>
                        <li><a href="performanceReport.jsp">View Performance Report</a></li>
                    </c:when>
                </c:choose>
            </ul>
            <div class="logout">
                <form action="LogoutController" method="post">
                    <button type="submit" class="logout-button">Logout</button>
                </form>
            </div>
        </nav>
    </header>
    <main class="content">
        <h1>Welcome, <c:out value="${user.name}" /></h1>
        <p>Select an option from the menu to get started.</p>
    </main>
</body>
</html>

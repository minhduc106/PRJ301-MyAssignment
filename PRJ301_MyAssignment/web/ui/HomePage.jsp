<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Company Homepage</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin: 0;
            padding: 0;
            background-color: #ffffcc; /* Subtle craft-like background color */
        }
        .container {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 20px;
        }
        .header {
            font-size: 32px;
            margin-bottom: 30px;
        }
        .row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 80%;
            margin-bottom: 20px;
        }
        .row div {
            flex: 1;
            margin: 0 10px;
        }
        .picture img {
            width: 500px; /* Ensure images have consistent size */
            height: 500px;
        }
        .description {
            font-style: italic;
            font-size: 20px;
            text-align: center;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp"></jsp:include>
    <div class="container">
        <div class="header">WELCOME TO ABC COMPANY</div>
        
        <div class="row">
            <div class="picture"><img src="${pageContext.request.contextPath}/ui/picture_gio.png" alt="Picture Gio"></div>
            <div class="description">This image captures the essence of innovation and progress at ABC Company, showcasing our commitment to excellence and forward-thinking solutions.</div>
        </div>
        
        <div class="row">
            <div class="description">A powerful representation of teamwork and resilience, highlighting the collaborative spirit that drives ABC Company towards success.</div>
            <div class="picture"><img src="${pageContext.request.contextPath}/ui/picture_thung.png" alt="Picture Thung"></div>
        </div>
        
        <div class="row">
            <div class="picture"><img src="${pageContext.request.contextPath}/ui/picture_met.png" alt="Picture Met"></div>
            <div class="description">This visual exemplifies dedication and precision, embodying the unwavering attention to detail that defines ABC Company's operations.</div>
        </div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>

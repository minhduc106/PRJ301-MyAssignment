<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản Đốc - Hệ Thống Quản Lý Sản Xuất</title>
        <link rel="stylesheet" type="text/css" href="headerStyle.css">
    </head>
    <body>

        <!-- Phần header -->
        <header>
            <div class="header-content">
                <h1>Hệ Thống Quản Lý Sản Xuất</h1>
                <p>Chào mừng, ${sessionScope} </p>
            </div>
        </header>

        <!-- Thanh điều hướng -->
        <nav>
            <a href="#">Trang Chủ</a>
            <a href="#">Phân Công Công Nhân</a>
            <a href="#">Theo Dõi Sản Xuất</a>
            <a href="#">Báo Cáo Chấm Công</a>
            <a href="#">Đăng Xuất</a>
        </nav>


        <!-- Phần nội dung chính -->
        <section class="hero">
            <div class="hero-content">
                <h2>Tìm hiểu và quản lý công việc của bạn một cách hiệu quả</h2>
                <p>Hệ thống giúp bạn phân công, theo dõi và quản lý sản xuất dễ dàng hơn.</p>
                <a href="#" class="btn-primary">Bắt đầu</a>
            </div>
        </section>

        <!-- Các chức năng chính -->
        <section class="features">
            <div class="container">
                <div class="feature-card">
                    <h3>Phân Công Công Nhân</h3>
                    <p>Phân chia công việc cho công nhân theo ca</p>
                    <a href="/controller/shift/assign" class="btn-secondary">Bắt đầu</a>
                </div>

                <div class="feature-card">
                    <h3>Theo Dõi Sản Xuất</h3>
                    <p>Xem báo cáo sản xuất hàng ngày</p>
                    <a href="/controller/production/dailyreport" class="btn-secondary">Xem chi tiết</a>
                </div>

                <div class="feature-card">
                    <h3>Cập Nhật Hiệu Suất</h3>
                    <p>Cập nhật hệ số alpha cho công nhân</p>
                    <a href="/controller/performance/update" class="btn-secondary">Cập nhật</a>
                </div>

                <div class="feature-card">
                    <h3>Báo Cáo Chấm Công</h3>
                    <p>Tạo báo cáo chấm công hàng ngày</p>
                    <a href="/controller/attendance/report" class="btn-secondary">Tạo báo cáo</a>
                </div>
            </div>
        </section>

        <!-- Phần footer -->
        <footer>
            <div class="footer-content">
                <p>© 2024 Hệ Thống Quản Lý Sản Xuất - ABC Company</p>
            </div>
        </footer>

    </body>
</html>
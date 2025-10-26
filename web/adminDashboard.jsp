<%-- 
    Document   : adminDashboard
    Created on : Jul 9, 2025, 4:37:45 PM
    Author     : asus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="Happy Programming">
        <meta name="format-detection" content="telephone=no">
        <title>EPMS</title>
        <link rel="icon" href="assets/images/faviconV2.png" type="image/x-icon" />
        <link rel="stylesheet" href="assets/css/assets.css">
        <link rel="stylesheet" href="assets/vendors/calendar/fullcalendar.css">
        <link rel="stylesheet" href="assets/css/typography.css">
        <link rel="stylesheet" href="assets/css/shortcodes/shortcodes.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="stylesheet" href="assets/css/dashboard.css">
        <link rel="stylesheet" href="assets/css/color/color-1.css">
        <style>
            .new-user-list {
                max-height: 200px;
                overflow-y: auto;
            }
            .table td, .table th {
                vertical-align: middle;
            }
        </style>
    </head>

    <body class="ttr-opened-sidebar ttr-pinned-sidebar">
        <jsp:include page="headerAdmin.jsp" />
        <main class="ttr-wrapper">
            <div class="container-fluid">
                <div class="db-breadcrumb">
                    <h4 class="breadcrumb-title">Dashboard</h4>
                    <ul class="db-breadcrumb-list">
                        <button class="btn btn-success" onclick="triggerUpload()">Upload Form</button>
                        <button class="btn btn-success" onclick="document.getElementById('logModal').style.display = 'block'">Log Activity</button>
                        <form action="backupDatabase" method="post" style="display:inline;">
                            <button type="submit" class="btn btn-success">Backup Data</button>
                        </form>
                        <form id="uploadForm" action="uploadformtemplate" method="post" enctype="multipart/form-data" style="display: none;">
                            <input type="file" id="fileInput" name="file" onchange="uploadFile()" />
                            <input type="hidden" name="title" value="Form hành chính mẫu" />
                            <input type="hidden" name="userId" value="${sessionScope.user.userId}" />
                        </form>
                        <div id="uploadMessage" style="margin-top: 10px; color: green;"></div>
                    </ul>
                </div>
                <div class="row">
                    <div class="col-12">
                        <h4>User Profile</h4>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Username</th>
                                    <th>Full Name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                    <th>Gender</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="u" items="${listuser}">
                                <form method="post" action="updateuser">
                                    <tr>
                                        <td>${u.userId}</td>
                                    <input type="hidden" name="userid" value="${u.userId}" />
                                    <td><input name="username" value="${u.userName}" class="form-control form-control-sm" required /></td>
                                    <td><input name="fullname" value="${u.fullName}" class="form-control form-control-sm" required /></td>
                                    <td><input name="email" value="${u.email}" class="form-control form-control-sm" required /></td>
                                    <td><input name="phone" value="${u.phone}" class="form-control form-control-sm" required /></td>
                                    <td>
                                        <select name="gender" class="form-control form-control-sm">
                                            <option value="Nam" ${u.gender == 'Nam' ? 'selected' : ''}>Nam</option>
                                            <option value="Nữ" ${u.gender == 'Nữ' ? 'selected' : ''}>Nữ</option>
                                            <option value="Khác" ${u.gender == 'Khác' ? 'selected' : ''}>Khác</option>
                                        </select>
                                    </td>
                                    <td><button type="submit" class="btn btn-sm btn-primary">Save</button></td>
                                    </tr>
                                </form>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div class="col-md-6">
                        <h4>Role</h4>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Username</th>
                                    <th>Full Name</th>
                                    <th>Role</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="u" items="${listuser}">
                                    <c:if test="${u.role.roleId == 3 || u.role.roleId == 4}">
                                    <form method="post" action="updaterole">
                                        <tr>
                                            <td>${u.userName}</td>
                                            <td>${u.fullName}</td>
                                        <input type="hidden" name="userid" value="${u.userId}" />

                                        <td>
                                            <select name="roleId" class="form-control-sm">
                                                <option value="3" ${u.role.roleId == 3 ? "selected" : ""}>Employee</option>
                                                <option value="4" ${u.role.roleId == 4 ? "selected" : ""}>Candidate</option>
                                            </select>
                                        </td>
                                        <td><button type="submit" class="btn btn-sm btn-warning">Save</button></td>
                                        </tr>
                                    </form>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div class="col-md-6">
                        <h4>Status</h4>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Full Name</th>  
                                    <th>Role Name</th>                                       
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="statusList" value="Active,Inactive,Pending,Banned,Unverified,Verified" />
                                <c:set var="statusArray" value="${fn:split(statusList, ',')}" />
                                <c:forEach var="u" items="${listuser}">
                                <form method="post" action="togglestatus">
                                    <tr>
                                        <td>${u.fullName}</td>
                                        <td>${u.role.roleName}</td>                                      
                                    <input type="hidden" name="userid" value="${u.userId}" />
                                    <td>
                                        <span class="btn button-sm
                                              <c:choose>
                                                  <c:when test="${u.status == 'Active'}">green</c:when>
                                                  <c:when test="${u.status == 'Inactive'}">red</c:when>
                                                  <c:when test="${u.status == 'Pending'}">orange</c:when>
                                                  <c:when test="${u.status == 'Banned'}">red</c:when>
                                                  <c:when test="${u.status == 'Unverified'}">blue</c:when>
                                                  <c:when test="${u.status == 'Verified'}">green</c:when>
                                                  <c:otherwise>gray</c:otherwise>
                                              </c:choose>" style="display: inline-block; width: 80px; text-align: center;">
                                            ${u.status}
                                        </span>
                                    </td>
                                    <td>
                                        <div style="display: flex; gap: 6px; align-items: center;">
                                            <select name="status" class="form-control-sm" style="width: 300px;">
                                                <c:forEach var="s" items="${statusArray}">
                                                    <option value="${s}" ${u.status == s ? "selected" : ""}>${s}</option>
                                                </c:forEach>
                                            </select>
                                            <button type="submit" class="btn btn-sm btn-success">Save</button>
                                        </div>
                                    </td>
                                    </tr>
                                </form>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div id="logModal" style="display:none; position:fixed; top:10%; left:10%; width:80%; height:80%; background-color:white; border:1px solid #ccc; overflow:auto; z-index:9999; padding:20px;">
                <h2>Activity</h2>
                <button onclick="document.getElementById('logModal').style.display = 'none'">Close</button>
                <br/><br/>

                <table border="1" cellpadding="8" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                            <th>Log ID</th>
                            <th>User ID</th>
                            <th>Action</th>
                            <th>Create Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="log" items="${logs}">
                            <tr>
                                <td>${log.logId}</td>
                                <td>${log.userId}</td>
                                <td>${log.action}</td>
                                <td>${log.createDate}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

        </main>
        <div class="ttr-overlay"></div>

        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/vendors/bootstrap/js/popper.min.js"></script>
        <script src="assets/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/vendors/bootstrap-select/bootstrap-select.min.js"></script>
        <script src="assets/vendors/bootstrap-touchspin/jquery.bootstrap-touchspin.js"></script>
        <script src="assets/vendors/magnific-popup/magnific-popup.js"></script>
        <script src="assets/vendors/counter/waypoints-min.js"></script>
        <script src="assets/vendors/counter/counterup.min.js"></script>
        <script src="assets/vendors/imagesloaded/imagesloaded.js"></script>
        <script src="assets/vendors/masonry/masonry.js"></script>
        <script src="assets/vendors/masonry/filter.js"></script>
        <script src="assets/vendors/owl-carousel/owl.carousel.js"></script>
        <script src='assets/vendors/scroll/scrollbar.min.js'></script>
        <script src="assets/js/functions.js"></script>
        <script src="assets/vendors/chart/chart.min.js"></script>
        <script src="assets/js/admin.js"></script>
        <script src='assets/vendors/calendar/moment.min.js'></script>
        <script src='assets/vendors/calendar/fullcalendar.js'></script>
        <script>
                    const USER_ID = 1;
        </script>
        <script>
            function triggerUpload() {
                document.getElementById('fileInput').click();
            }

            function uploadFile() {
                const form = document.getElementById('uploadForm');
                const formData = new FormData(form);
                const file = document.getElementById('fileInput').files[0];

                if (!file)
                    return;

                formData.set("userId", USER_ID);

                fetch('uploadformtemplate', {
                    method: 'POST',
                    body: formData
                })
                        .then(res => {
                            console.log("Server response:", res);
                            return res.text();
                        })
                        .then(data => {
                            console.log("Server response data:", data);
                            document.getElementById('uploadMessage').innerHTML = data;
                        })
                        .catch(err => {
                            console.error("Upload error:", err);
                            document.getElementById('uploadMessage').innerHTML = "<span style='color:red'>Upload Error!</span>";
                        });

            }
        </script>
        <script>
            function changeStatus(userId, newStatus) {
                console.log("Thay đổi status: userId =", userId, "| newStatus =", newStatus);
                if (!userId || userId.trim() === "") {
                    alert("userId không hợp lệ!");
                    return;
                }

                const formData = new FormData();
                formData.append("userid", userId);
                formData.append("status", newStatus);

                fetch("togglestatus", {
                    method: "POST",
                    body: formData
                })
                        .then(res => {
                            if (res.ok) {
                                location.reload(); // hoặc thông báo success
                            } else {
                                alert("Cập nhật trạng thái thất bại!");
                            }
                        })
                        .catch(err => console.error(err));
            }
        </script>
    </body>
</html>

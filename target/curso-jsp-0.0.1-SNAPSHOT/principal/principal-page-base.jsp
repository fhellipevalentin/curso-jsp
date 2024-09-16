<%--
  Created by IntelliJ IDEA.
  User: fhell
  Date: 12/03/2024
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

<body>
<!-- Pre-loader start -->
<jsp:include page="theme-loader.jsp"></jsp:include>
<!-- Pre-loader end -->
<div id="pcoded" class="pcoded">
  <div class="pcoded-overlay-box"></div>
  <div class="pcoded-container navbar-wrapper">
    <jsp:include page="navbar.jsp"></jsp:include>
    <jsp:include page="navbar-main_menu.jsp"></jsp:include>


    <div class="pcoded-main-container">
      <div class="pcoded-wrapper">
        <div class="pcoded-content">
          <!-- Page-header start -->
          <jsp:include page="page-header.jsp"></jsp:include>
          <!-- Page-header end -->
          <div class="pcoded-inner-content">
            <!-- Main-body start -->
            <div class="main-body">
              <div class="page-wrapper">
                <!-- Page-body start -->
                <div class="page-body">
                  <h1>conteúdo base das páginas do sistema</h1>
                </div>
                <!-- Page-body end -->
              </div>
              <div id="styleSelector"> </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
<jsp:include page="js-include.jsp"></jsp:include>
</html>

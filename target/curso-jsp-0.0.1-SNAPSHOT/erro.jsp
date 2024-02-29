<%--
  Created by IntelliJ IDEA.
  User: fhell
  Date: 29/02/2024
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Erro</title>
</head>
<body>
    <h1>Mensagem de Erro</h1>
    <h4>Entre em contato com a equipe de suporte do sistema</h4>
    <%
      out.print(request.getAttribute("msg"));
    %>
</body>
</html>

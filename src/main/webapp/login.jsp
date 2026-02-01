<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - TorneoManager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .login-card { max-width: 400px; margin: 100px auto; padding: 20px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); background: white; }
    </style>
</head>
<body>

<div class="container">
    <div class="login-card">
        <h3 class="text-center mb-4">Accedi</h3>

        <% String errore = (String) request.getAttribute("errore"); %>
        <% if(errore != null) { %>
        <div class="alert alert-danger" role="alert">
            <%= errore %>
        </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" name="email" id="email" required placeholder="nome@esempio.it">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" name="password" id="password" required>
            </div>
            <button type="submit" class="btn btn-primary w-100">Entra</button>
        </form>

        <div class="text-center mt-3">
            <small>Non hai un account? <a href="registrazione.jsp">Registrati</a></small>
        </div>
    </div>
</div>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Registrazione - TorneoManager</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center justify-content-center" style="height: 100vh;">

<div class="card shadow" style="width: 400px;">
  <div class="card-body">
    <h4 class="card-title text-center mb-4">📝 Registrati</h4>

    <form action="RegistrazioneServlet" method="POST">
      <div class="mb-3">
        <label class="form-label">Nome</label>
        <input type="text" name="nome" class="form-control" required>
      </div>

      <div class="mb-3">
        <label class="form-label">Cognome</label>
        <input type="text" name="cognome" class="form-control" required>
      </div>

      <div class="mb-3">
        <label class="form-label">Email</label>
        <input type="email" name="email" class="form-control" required>
      </div>

      <div class="mb-3">
        <label class="form-label">Password</label>
        <input type="password" name="password" class="form-control" required>
      </div>

      <div class="mb-3">
        <label class="form-label">Telefono</label>
        <input type="text" name="telefono" class="form-control">
      </div>

      <div class="mb-3">
        <label class="form-label">Che ruolo vuoi assumere?</label>
        <select name="tipo" class="form-select" required>
          <option value="PRESIDENTE">Presidente di Squadra</option>
          <option value="ARBITRO">Arbitro</option>
          <option value="ORGANIZZATORE">Organizzatore Tornei</option>
        </select>
      </div>

      <button type="submit" class="btn btn-primary w-100 mt-2">Registrati</button>
    </form>

    <div class="text-center mt-3">
      <a href="login.jsp" class="text-decoration-none">Hai già un account? Accedi!</a>
    </div>
  </div>
</div>

</body>
</html>
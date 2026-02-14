<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.tuodominio.torneomanager.model.*" %>

<%
  Utente utente = (Utente) session.getAttribute("utente");
  if(utente == null || !utente.getTipo().equalsIgnoreCase("ORGANIZZATORE")) {
    response.sendRedirect("login.jsp");
    return;
  }

  int idTorneo = Integer.parseInt(request.getParameter("id"));
  TorneoDAO dao = new TorneoDAO();
  Torneo t = dao.doRetrieveByKey(idTorneo);
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Modifica Torneo</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
  <div class="card shadow">
    <div class="card-header bg-warning text-dark">
      <h4>✏️ Modifica: <%= t.getNome() %></h4>
    </div>
    <div class="card-body">
      <form action="ModificaTorneoServlet" method="post">
        <input type="hidden" name="idTorneo" value="<%= t.getIdTorneo() %>">

        <div class="mb-3">
          <label>Nome Torneo</label>
          <input type="text" name="nome" class="form-control" value="<%= t.getNome() %>" required>
        </div>
        <div class="mb-3">
          <label>Luogo</label>
          <input type="text" name="luogo" class="form-control" value="<%= t.getLuogo() %>" required>
        </div>
        <div class="mb-3">
          <label>Descrizione</label>
          <textarea name="descrizione" class="form-control"><%= t.getDescrizione() %></textarea>
        </div>
        <div class="row mb-3">
          <div class="col">
            <label>Inizio</label>
            <input type="date" name="dataInizio" class="form-control" value="<%= t.getDataInizio() %>" required>
          </div>
          <div class="col">
            <label>Fine</label>
            <input type="date" name="dataFine" class="form-control" value="<%= t.getDataFine() %>" required>
          </div>
        </div>

        <button type="submit" class="btn btn-primary">💾 Salva Modifiche</button>
        <a href="dettaglioTorneo.jsp?id=<%= t.getIdTorneo() %>" class="btn btn-secondary">Annulla</a>
      </form>
    </div>
  </div>
</div>
</body>
</html>
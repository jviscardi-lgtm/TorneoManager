<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.tuodominio.torneomanager.model.Utente" %>

<%
    // Security Check: Solo Organizzatori
    Utente utente = (Utente) session.getAttribute("utente");
    if(utente == null || !utente.getTipo().equalsIgnoreCase("ORGANIZZATORE")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nuovo Torneo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-primary mb-4">
    <div class="container">
        <a class="navbar-brand" href="home.jsp">🔙 Annulla e Torna alla Home</a>
        <span class="text-white">Creazione Nuovo Torneo</span>
    </div>
</nav>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow">
                <div class="card-header bg-white">
                    <h4 class="mb-0 text-primary">Dettagli Evento</h4>
                </div>
                <div class="card-body">

                    <form action="CreaTorneoServlet" method="post">

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Nome Torneo</label>
                                <input type="text" name="nome" class="form-control" placeholder="Es. Champions League Estiva" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Luogo / Campo</label>
                                <input type="text" name="luogo" class="form-control" placeholder="Es. Campetto Parrocchiale" required>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Descrizione / Regole</label>
                            <textarea name="descrizione" class="form-control" rows="3" placeholder="Calcio a 5, gironi all'italiana..."></textarea>
                        </div>

                        <div class="row mb-4">
                            <div class="col-md-6">
                                <label class="form-label">Data Inizio</label>
                                <input type="date" name="dataInizio" class="form-control" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Data Fine (Prevista)</label>
                                <input type="date" name="dataFine" class="form-control" required>
                            </div>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary btn-lg">💾 Crea Torneo</button>
                        </div>

                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
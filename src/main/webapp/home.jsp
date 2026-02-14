<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.tuodominio.torneomanager.model.*" %>
<%@ page import="java.util.List" %>

<%
    // 1. SECURITY CHECK
    Utente utente = (Utente) session.getAttribute("utente");
    if(utente == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // 2. RECUPERO DATI
    TorneoDAO torneoDAO = new TorneoDAO();
    List<Torneo> listaTornei = torneoDAO.doRetrieveAll();

    // DAO per controlli extra (iscrizioni, ecc.)
    SquadraDAO sDaoTemp = new SquadraDAO();
    TorneoDAO tDaoTemp = new TorneoDAO(); // Serve per isIscritto
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - TorneoManager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container">
        <a class="navbar-brand" href="#">⚽ TorneoManager</a>
        <div class="d-flex text-white align-items-center">
            <span class="me-3">Ciao, <strong><%= utente.getNome() %></strong> (<%= utente.getTipo() %>)</span>
            <a href="LogoutServlet" class="btn btn-outline-danger btn-sm">Esci</a>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row">

        <div class="col-md-8">
            <h2 class="mb-3">🏆 Bacheca Tornei</h2>

            <div class="row">
                <%
                    if(listaTornei != null && !listaTornei.isEmpty()) {
                        for(Torneo t : listaTornei) {
                %>
                <div class="col-12 mb-3"> <div class="card shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title text-primary"><%= t.getNome() %></h5>
                        <h6 class="card-subtitle mb-2 text-muted">📍 <%= t.getLuogo() %></h6>
                        <p class="card-text"><small>📅 <%= t.getDataInizio() %> ➝ <%= t.getDataFine() %></small></p>

                        <hr>

                        <% if(utente.getTipo().equalsIgnoreCase("PRESIDENTE")) {
                            // Logica per vedere se è già iscritto
                            Squadra sqTemp = sDaoTemp.doRetrieveByPresidente(utente.getIdUtente());
                            boolean giaIscritto = false;
                            if(sqTemp != null) {
                                giaIscritto = tDaoTemp.isIscritto(t.getIdTorneo(), sqTemp.getIdSquadra());
                            }
                        %>
                        <% if(sqTemp == null) { %>
                        <a href="squadra.jsp" class="btn btn-warning btn-sm">Crea Squadra per Iscriverti</a>

                        <% } else if(giaIscritto) { %>
                        <button class="btn btn-success btn-sm" disabled>✅ Già Iscritto</button>
                        <a href="dettaglioTorneo.jsp?id=<%= t.getIdTorneo() %>" class="btn btn-outline-primary btn-sm">Vedi Classifica</a>

                        <% } else { %>
                        <a href="IscrizioneServlet?idTorneo=<%= t.getIdTorneo() %>"
                           class="btn btn-primary btn-sm"
                           onclick="return confirm('Vuoi iscriverti?')">
                            Iscrivi Squadra
                        </a>
                        <a href="dettaglioTorneo.jsp?id=<%= t.getIdTorneo() %>" class="btn btn-outline-secondary btn-sm">Dettagli</a>
                        <% } %>

                        <% } else { %>
                        <a href="dettaglioTorneo.jsp?id=<%= t.getIdTorneo() %>" class="btn btn-outline-primary w-100">Vedi Classifica & Dettagli</a>
                        <% } %>

                    </div>
                </div>
                </div>
                <%
                    }
                } else {
                %>
                <div class="alert alert-info">Non ci sono tornei attivi al momento.</div>
                <% } %>
            </div>
        </div>

        <div class="col-md-4">

            <% if(utente.getTipo().equalsIgnoreCase("ORGANIZZATORE")) { %>
            <div class="card text-white bg-primary mb-3 shadow">
                <div class="card-header">Organizzazione</div>
                <div class="card-body">
                    <h5 class="card-title">Gestisci Tornei</h5>
                    <p class="card-text">Crea nuovi tornei e genera i calendari.</p>
                    <a href="creaTorneo.jsp" class="btn btn-light w-100 fw-bold text-primary">Crea Nuovo Torneo</a>
                </div>
            </div>
            <% } %>

            <% if(utente.getTipo().equalsIgnoreCase("PRESIDENTE")) { %>
            <div class="card text-white bg-success mb-3 shadow">
                <div class="card-header">Il Tuo Club</div>
                <div class="card-body">
                    <h5 class="card-title">La Mia Squadra</h5>
                    <p class="card-text">Gestisci la rosa, visualizza i giocatori.</p>
                    <a href="squadra.jsp" class="btn btn-light w-100 fw-bold text-success">Gestisci Squadra</a>
                </div>
            </div>
            <% } %>

            <% if(utente.getTipo().equalsIgnoreCase("ARBITRO")) { %>
            <div class="card text-dark bg-warning mb-3 shadow">
                <div class="card-header fw-bold">👮‍♂️ Area Arbitro</div>
                <div class="card-body">
                    <h5 class="card-title">Le tue Partite</h5>
                    <p class="card-text">Inserisci i risultati delle gare.</p>
                    <a href="arbitro.jsp" class="btn btn-dark w-100">Vedi Designazioni</a>
                </div>
            </div>
            <% } %>

        </div> </div> </div> </body>
</html>
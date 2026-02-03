<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.tuodominio.torneomanager.model.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%
    // 1. SECURITY CHECK
    Utente utente = (Utente) session.getAttribute("utente");
    if(utente == null || !utente.getTipo().equalsIgnoreCase("ARBITRO")) {
        response.sendRedirect("login.jsp");
        return;
    }

    // 2. RECUPERO DATI
    PartitaDAO pDao = new PartitaDAO();
    TorneoDAO tDao = new TorneoDAO();
    SquadraDAO sDao = new SquadraDAO();

    List<Torneo> tornei = tDao.doRetrieveAll();
    List<Partita> miePartite = new ArrayList<>();

    // Cicliamo su tutti i tornei per trovare le partite di QUESTO arbitro
    for(Torneo t : tornei) {
        List<Partita> partiteTorneo = pDao.doRetrieveByTorneo(t.getIdTorneo());
        for(Partita p : partiteTorneo) {
            // NOTA: Ho tolto il controllo !p.isGiocata() così vedi anche quelle finite
            // Controlla se getIdUtente() è corretto o se devi usare getId()
            if(p.getIdArbitro() == utente.getIdUtente()) {
                miePartite.add(p);
            }
        }
    }

    List<Squadra> tutteSquadre = sDao.doRetrieveAll();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Area Arbitro</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-warning mb-4">
    <div class="container">
        <span class="navbar-brand text-dark">👮‍♂️ Area Arbitro</span>
        <div class="d-flex align-items-center">
            <span class="me-3 text-dark">Ciao, <%= utente.getCognome() %></span>
            <a href="LogoutServlet" class="btn btn-dark btn-sm">Esci</a>
        </div>
    </div>
</nav>

<div class="container">
    <h2 class="mb-4">Le tue Designazioni</h2>

    <% if(miePartite.isEmpty()) { %>
    <div class="alert alert-info">Non hai partite assegnate al momento.</div>
    <% } else { %>

    <div class="row">
        <% for(Partita p : miePartite) {
            String nomeCasa = "Squadra " + p.getIdSquadraCasa();
            String nomeOspite = "Squadra " + p.getIdSquadraOspite();

            for(Squadra s : tutteSquadre) {
                if(s.getIdSquadra() == p.getIdSquadraCasa()) nomeCasa = s.getNomeSquadra();
                if(s.getIdSquadra() == p.getIdSquadraOspite()) nomeOspite = s.getNomeSquadra();
            }
        %>
        <div class="col-md-6 mb-4">
            <div class="card shadow-sm border-warning">
                <div class="card-header bg-warning text-dark">
                    Partita #<%= p.getIdPartita() %>
                </div>
                <div class="card-body">
                    <h5 class="card-title text-center mb-3">
                        <%= nomeCasa %> <span class="text-muted">vs</span> <%= nomeOspite %>
                    </h5>
                    <p class="card-text text-center"><small><%= p.getDataOra() %></small></p>

                    <hr>

                    <% if(!p.isGiocata()) { %>
                    <form action="InserisciRisultatoServlet" method="post" class="row g-3 align-items-center justify-content-center">
                        <input type="hidden" name="idPartita" value="<%= p.getIdPartita() %>">

                        <div class="col-auto">
                            <label class="visually-hidden">Gol Casa</label>
                            <input type="number" name="golCasa" class="form-control text-center fw-bold" placeholder="0" style="width: 70px;" min="0" required>
                        </div>
                        <div class="col-auto">
                            <span>-</span>
                        </div>
                        <div class="col-auto">
                            <label class="visually-hidden">Gol Ospite</label>
                            <input type="number" name="golOspite" class="form-control text-center fw-bold" placeholder="0" style="width: 70px;" min="0" required>
                        </div>
                        <div class="col-12 mt-3">
                            <button type="submit" class="btn btn-success w-100">⚽ Fischio Finale</button>
                        </div>
                    </form>
                    <% } else { %>
                    <div class="text-center">
                        <h3 class="fw-bold"><%= p.getGolCasa() %> - <%= p.getGolOspite() %></h3>
                        <span class="badge bg-secondary">Partita Terminata</span>
                    </div>
                    <% } %>

                </div>
            </div>
        </div>
        <% } %>
    </div>
    <% } %>
</div>

</body>
</html>
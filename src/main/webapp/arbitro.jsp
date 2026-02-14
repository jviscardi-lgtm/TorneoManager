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

    PartitaDAO pDaoArbitro = new PartitaDAO();
    List<Partita> proposteInSospeso = pDaoArbitro.doRetrieveProposteArbitro(utente.getIdUtente());
    List<Squadra> tutteSquadre = sDao.doRetrieveAll();
    List<Partita> candidatureInAttesa = pDaoArbitro.doRetrieveCandidatureInAttesa(utente.getIdUtente());
    List<Partita> miePartite = pDaoArbitro.doRetrievePartiteConfermate(utente.getIdUtente());

%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Area Arbitro</title>
    <div class="container mt-4">
        <% if(proposteInSospeso != null && !proposteInSospeso.isEmpty()) { %>
        <div class="card shadow-sm mb-4 border-warning">
            <div class="card-header bg-warning text-dark fw-bold">
                🔔 Hai <%= proposteInSospeso.size() %> nuove richieste di arbitraggio!
            </div>
            <ul class="list-group list-group-flush">
                <% for(Partita pReq : proposteInSospeso) { %>
                <li class="list-group-item d-flex justify-content-between align-items-center bg-light">
                    <div>
                        <strong>Partita ID: #<%= pReq.getIdPartita() %></strong><br>
                        <small class="text-muted">Data: <%= pReq.getDataOra() %> | Luogo: <%= pReq.getLuogo() %></small>
                    </div>
                    <div>
                        <a href="RispostaArbitroServlet?idPartita=<%= pReq.getIdPartita() %>&azione=accetta"
                           class="btn btn-success btn-sm me-2">✅ Accetta</a>

                        <a href="RispostaArbitroServlet?idPartita=<%= pReq.getIdPartita() %>&azione=rifiuta"
                           class="btn btn-outline-danger btn-sm"
                           onclick="return confirm('Sicuro di voler rifiutare questo incarico?');">❌ Rifiuta</a>
                    </div>
                </li>
                <% } %>
            </ul>
        </div>
        <% } %>
    </div>
    <%
        List<Partita> partiteLibere = pDaoArbitro.doRetrievePartiteLibere();
    %>

    <div class="card shadow-sm mb-4 border-info">
        <div class="card-header bg-info text-white fw-bold">
            🙋‍♂️ Partite in cerca di Arbitro
        </div>
        <ul class="list-group list-group-flush">
            <% if(partiteLibere.isEmpty()) { %>
            <li class="list-group-item text-muted">Non ci sono partite libere al momento.</li>
            <% } else { %>
            <% for(Partita pLibera : partiteLibere) { %>
            <li class="list-group-item d-flex justify-content-between align-items-center">
                <div>
                    <strong>Partita ID: #<%= pLibera.getIdPartita() %> (Torneo #<%= pLibera.getIdTorneo() %>)</strong><br>
                    <small class="text-muted">Data: <%= pLibera.getDataOra() %> | Luogo: <%= pLibera.getLuogo() %></small>
                </div>
                <a href="CandidatiServlet?idPartita=<%= pLibera.getIdPartita() %>" class="btn btn-outline-primary btn-sm">✋ Candidati</a>
            </li>
            <% } %>
            <% } %>
        </ul>
    </div>
    <% if(candidatureInAttesa != null && !candidatureInAttesa.isEmpty()) { %>
    <div class="card shadow-sm mb-4 border-secondary">
        <div class="card-header bg-secondary text-white fw-bold">
            ⏳ Le tue candidature in attesa di conferma
        </div>
        <ul class="list-group list-group-flush">
            <% for(Partita pCand : candidatureInAttesa) { %>
            <li class="list-group-item d-flex justify-content-between align-items-center bg-light">
                <div>
                    <strong>Partita ID: #<%= pCand.getIdPartita() %></strong><br>
                    <small class="text-muted">L'organizzatore sta valutando la tua richiesta.</small>
                </div>
                <span class="badge bg-warning text-dark">In Valutazione</span>
            </li>
            <% } %>
        </ul>
    </div>
    <% } %>
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
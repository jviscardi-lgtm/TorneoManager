<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.tuodominio.torneomanager.model.*" %>
<%@ page import="java.util.List" %>

<%
    // 1. CONTROLLI DI SICUREZZA
    Utente utente = (Utente) session.getAttribute("utente");
    if(utente == null || !utente.getTipo().equalsIgnoreCase("PRESIDENTE")) {
        response.sendRedirect("login.jsp");
        return;
    }

    // 2. RECUPERO LA SQUADRA DEL PRESIDENTE
    SquadraDAO squadraDAO = new SquadraDAO();
    // Nota: utente.getId() deve esistere nel Bean Utente.
    // Se nel Bean Utente hai chiamato il campo 'idUtente', usa getIdUtente()!
    Squadra squadra = squadraDAO.doRetrieveByPresidente(utente.getIdUtente());

    List<Giocatore> rosa = null;
    if(squadra != null) {
        GiocatoreDAO giocatoreDAO = new GiocatoreDAO();
        rosa = giocatoreDAO.doRetrieveBySquadra(squadra.getIdSquadra());
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>La Mia Squadra</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container">
        <a class="navbar-brand" href="home.jsp">🔙 Torna alla Home</a>
        <span class="text-white">Gestione Rosa</span>
    </div>
</nav>

<div class="container">

    <% if(squadra == null) { %>
    <div class="alert alert-warning text-center">
        <h4>Non hai ancora registrato la tua squadra!</h4>
        <p>Per partecipare ai tornei devi prima creare il tuo club.</p>
        <a href="creaSquadra.jsp" class="btn btn-primary">Crea Squadra Ora</a>
    </div>
    <% } else { %>

    <div class="row">
        <div class="col-md-4">
            <div class="card mb-4 shadow-sm">
                <div class="card-body text-center">
                    <img src="img/logo_default.png" class="img-fluid mb-3" style="max-height: 100px;">
                    <h3><%= squadra.getNomeSquadra() %></h3>
                    <p class="text-muted">Presidente: <%= utente.getNome() %> <%= utente.getCognome() %></p>
                </div>
            </div>

            <div class="card shadow-sm">
                <div class="card-header bg-success text-white">
                    Aggiungi Giocatore
                </div>
                <div class="card-body">
                    <form action="GiocatoreServlet" method="post">
                        <input type="hidden" name="idSquadra" value="<%= squadra.getIdSquadra() %>">

                        <div class="mb-2">
                            <label>Nome</label>
                            <input type="text" name="nome" class="form-control" required>
                        </div>
                        <div class="mb-2">
                            <label>Cognome</label>
                            <input type="text" name="cognome" class="form-control" required>
                        </div>
                        <div class="mb-2">
                            <label>Numero Maglia</label>
                            <input type="number" name="numero" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label>Ruolo</label>
                            <select name="ruolo" class="form-select">
                                <option value="Portiere">Portiere</option>
                                <option value="Difensore">Difensore</option>
                                <option value="Centrocampista">Centrocampista</option>
                                <option value="Attaccante">Attaccante</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-success w-100">Aggiungi</button>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-md-8">
            <div class="card shadow-sm">
                <div class="card-header">
                    Rosa Attuale (<%= rosa != null ? rosa.size() : 0 %> giocatori)
                </div>
                <div class="card-body">
                    <table class="table table-striped table-hover">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Nome</th>
                            <th>Cognome</th>
                            <th>Ruolo</th>
                            <th>Azioni</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% if(rosa != null) {
                            for(Giocatore g : rosa) { %>
                        <tr>
                            <td><b><%= g.getNumeroMaglia() %></b></td>
                            <td><%= g.getNome() %></td>
                            <td><%= g.getCognome() %></td>
                            <td><span class="badge bg-secondary"><%= g.getRuolo() %></span></td>
                            <td>
                                <a href="#" class="btn btn-danger btn-sm" onclick="alert('Funzione Elimina da implementare!')">X</a>
                            </td>
                        </tr>
                        <%  }
                        } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <% } %>

</div>

</body>
</html>
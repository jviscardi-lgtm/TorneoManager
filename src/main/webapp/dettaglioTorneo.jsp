<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.tuodominio.torneomanager.model.*" %>
<%@ page import="java.util.List" %>
<%@ page import="it.tuodominio.torneomanager.model.CalcolatoreClassifica" %>
<%@ page import="it.tuodominio.torneomanager.model.RigaClassifica" %>
<%
  // 1. Recupero ID Torneo dalla request
  String idStr = request.getParameter("id");
  if(idStr == null) { response.sendRedirect("home.jsp"); return; }
  int idTorneo = Integer.parseInt(idStr);

  // 2. Recupero Dati Utente e Torneo
  Utente utente = (Utente) session.getAttribute("utente");

  // Per pulizia dovremmo usare un metodo doRetrieveByKey nel TorneoDAO,
  // qui per brevità recuperiamo la lista e filtriamo (o se hai fatto il metodo doRetrieveById usalo!)
  TorneoDAO tDao = new TorneoDAO();
  List<Torneo> tutti = tDao.doRetrieveAll();
  Torneo torneo = null;
  for(Torneo t : tutti) { if(t.getIdTorneo() == idTorneo) torneo = t; }

  // 3. Recupero Squadre Iscritte
  SquadraDAO sDao = new SquadraDAO();
  List<Squadra> squadreIscritte = sDao.doRetrieveByTorneo(idTorneo);
  List<RigaClassifica> classifica = CalcolatoreClassifica.calcola(idTorneo);
  // 4. Recupero Partite (Se il calendario è già stato generato)
  PartitaDAO pDao = new PartitaDAO();
  List<Partita> partite = pDao.doRetrieveByTorneo(idTorneo);
  UtenteDAO uDao = new UtenteDAO();
  List<Utente> listaArbitri = uDao.doRetrieveAllArbitri();
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Dettaglio Torneo</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark mb-4">
  <div class="container">
    <a class="navbar-brand" href="home.jsp">🔙 Torna alla Home</a>
    <span class="text-white"><%= torneo.getNome() %></span>
  </div>
</nav>

<div class="container">

  <div class="card mb-4">
    <div class="card-body">
      <h2 class="card-title"><%= torneo.getNome() %></h2>
      <p class="text-muted"><%= torneo.getLuogo() %> | <%= torneo.getDataInizio() %> - <%= torneo.getDataFine() %></p>
      <p><%= torneo.getDescrizione() %></p>
    </div>
  </div>
  <% if(utente != null && utente.getTipo().equalsIgnoreCase("ORGANIZZATORE")) { %>
  <div class="card mb-4 border-warning">
    <div class="card-body bg-light d-flex justify-content-between align-items-center">
      <h5 class="m-0 text-dark">⚙️ Gestione Torneo</h5>
      <div>
        <a href="modificaTorneo.jsp?id=<%= torneo.getIdTorneo() %>" class="btn btn-outline-dark btn-sm me-2">✏️ Modifica Dati</a>

        <form action="CambiaStatoTorneoServlet" method="post" style="display:inline;">
          <input type="hidden" name="idTorneo" value="<%= torneo.getIdTorneo() %>">

          <% if(torneo.isChiuso()) { %>
          <input type="hidden" name="azione" value="apri">
          <button type="submit" class="btn btn-success btn-sm">🔓 Riapri Torneo</button>
          <% } else { %>
          <input type="hidden" name="azione" value="chiudi">
          <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Chiudendo il torneo non sarà possibile iscrivere squadre. Confermi?')">🔒 Chiudi Torneo</button>
          <% } %>
        </form>
      </div>
    </div>
  </div>
  <% } %>

  <% if(torneo.isChiuso()) { %>
  <div class="alert alert-danger text-center fw-bold">
    ⛔ QUESTO TORNEO È CHIUSO E CONCLUSO.
  </div>
  <% } %>

  <div class="row">
    <div class="col-md-4">
      <div class="card shadow-sm mb-3">
        <div class="card-header bg-primary text-white">
          🏆 Classifica
        </div>
        <div class="card-body p-0"> <table class="table table-striped table-sm mb-0">
          <thead>
          <tr>
            <th class="ps-3">Squadra</th>
            <th class="text-center">G</th>
            <th class="text-center fw-bold">PT</th>
          </tr>
          </thead>
          <tbody>
          <%
            int posizione = 1;
            for(RigaClassifica r : classifica) {
              // Colore diverso per i primi 3 (Podio)
              String badgeClass = "bg-secondary";
              if(posizione == 1) badgeClass = "bg-warning text-dark"; // Oro
              if(posizione == 2) badgeClass = "bg-secondary";         // Argento (visivo)
              if(posizione == 3) badgeClass = "bg-danger";            // Bronzo (simulato)
          %>
          <tr>
            <td class="ps-3">
              <span class="badge <%= badgeClass %> rounded-pill me-1"><%= posizione %></span>
              <%= r.getNomeSquadra() %>
            </td>
            <td class="text-center"><%= r.getGiocate() %></td>
            <td class="text-center fw-bold"><%= r.getPunti() %></td>
          </tr>
          <%
              posizione++;
            }
          %>
          <% if(classifica.isEmpty()) { %>
          <tr><td colspan="3" class="text-center p-3">Nessuna partita giocata.</td></tr>
          <% } %>
          </tbody>
        </table>
        </div>
      </div>

    </div>
    <div class="col-md-4">
      <div class="card shadow-sm">
        <div class="card-header bg-primary text-white">
          Squadre Iscritte (<%= squadreIscritte.size() %>)
        </div>
        <ul class="list-group list-group-flush">
          <% for(Squadra s : squadreIscritte) { %>
          <li class="list-group-item d-flex justify-content-between align-items-center">
            <%= s.getNomeSquadra() %>
            <span class="badge bg-secondary rounded-pill">⚽</span>
          </li>
          <% } %>
          <% if(squadreIscritte.isEmpty()) { %>
          <li class="list-group-item text-muted">Nessuna squadra iscritta.</li>
          <% } %>
        </ul>
      </div>

      <% if(utente != null && utente.getTipo().equalsIgnoreCase("ORGANIZZATORE")) { %>
      <div class="d-grid gap-2 mt-3">
        <% if(partite.isEmpty() && squadreIscritte.size() >= 2) { %>
        <form action="GeneraCalendarioServlet" method="post">
          <input type="hidden" name="idTorneo" value="<%= torneo.getIdTorneo() %>">
          <button type="submit" class="btn btn-warning btn-lg w-100">
            ⚡ Genera Calendario
          </button>
        </form>
        <% } else if(!partite.isEmpty()) { %>
        <button class="btn btn-success disabled w-100">✅ Calendario Pronto</button>
        <% } else { %>
        <button class="btn btn-secondary disabled w-100">Servono almeno 2 squadre</button>
        <% } %>
      </div>
      <% } %>
    </div>

    <div class="col-md-8">
      <div class="card shadow-sm">
        <div class="card-header bg-dark text-white">
          Calendario Partite
        </div>
        <div class="card-body">
          <% if(partite.isEmpty()) { %>
          <p class="text-center text-muted mt-3">Il calendario non è ancora stato generato.</p>
          <% } else { %>
          <table class="table table-hover">
            <thead>
            <tr>
              <th>Data</th>
              <th>Casa</th>
              <th>Risultato</th>
              <th>Ospite</th>
              <th>Arbitro</th>
            </tr>
            </thead>
            <tbody>
            <% for(Partita p : partite) {

              String nomeCasa = "Squadra " + p.getIdSquadraCasa();
              String nomeOspite = "Squadra " + p.getIdSquadraOspite();
              for(Squadra s : squadreIscritte) {
                if(s.getIdSquadra() == p.getIdSquadraCasa()) nomeCasa = s.getNomeSquadra();
                if(s.getIdSquadra() == p.getIdSquadraOspite()) nomeOspite = s.getNomeSquadra();
              }
            %>
            <tr>
              <td><small><%= p.getDataOra() %></small></td>
              <td class="fw-bold text-end"><%= nomeCasa %></td>
              <td class="text-center bg-light border">
                <% if(p.isGiocata()) { %>
                <%= p.getGolCasa() %> - <%= p.getGolOspite() %>
                <% } else { %>
                -
                <% } %>
              </td>
              <td class="fw-bold"><%= nomeOspite %></td>

            <td>
              <% if(utente.getTipo().equalsIgnoreCase("ORGANIZZATORE")) { %>
              <form action="AssegnaArbitroServlet" method="post" class="d-flex">
                <input type="hidden" name="idPartita" value="<%= p.getIdPartita() %>">
                <input type="hidden" name="idTorneo" value="<%= torneo.getIdTorneo() %>">

                <select name="idArbitro" class="form-select form-select-sm" style="width: 150px;">
                  <option value="0">-- Seleziona --</option>
                  <% for(Utente a : listaArbitri) {
                    // Se l'arbitro è già assegnato, lo selezioniamo di default
                    String selected = (p.getIdArbitro() == a.getIdUtente()) ? "selected" : "";
                  %>
                  <option value="<%= a.getIdUtente() %>" <%= selected %>>
                    <%= a.getCognome() %>
                  </option>
                  <% } %>
                </select>
                <button type="submit" class="btn btn-sm btn-outline-dark ms-1">💾</button>
              </form>
              <% } else { %>
              <%
                String nomeArbitro = "Non assegnato";
                for(Utente a : listaArbitri) {
                  if(a.getIdUtente() == p.getIdArbitro()) nomeArbitro = a.getCognome();
                }
              %>
              <span class="badge bg-info text-dark"><%= nomeArbitro %></span>
              <% } %>
            </td>
            </tr>
            <% } %>
            </tbody>
          </table>
          <% } %>
        </div>
      </div>
    </div>

  </div>
</div>

</body>
</html>
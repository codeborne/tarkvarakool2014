<@html>

<#if loggedInUsername??>
  <p>Kasutaja: ${loggedInUsername}</p>
  <form method="post" class="form-horizontal">
    <button class="btn btn-default btn-sm" name="action" value="logout">Valju</button>
  </form>
<#else>
  <form method="post" class="form-horizontal">

    <h3>Sisselogimine</h3>
    <label>
      Kasutajanimi:
      <input type="text" name="username" class="form-control">
    </label><br>
    <label>Parool: <input type="password" class="form-control" name="password"></label><br>
    <button class="btn btn-default btn-sm" name="action" value="login">Sisene</button>
  </form>

  <#if error??>
    <div class="alert alert-danger">${error}</div>
  </#if>
</#if>

</@html>
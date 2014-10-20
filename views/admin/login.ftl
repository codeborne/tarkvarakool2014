<@html>

<#if loggedInUsername??>
  <p>Olete sisse loginud kasutajanimega ${loggedInUsername}</p>
  <button class="btn btn-default btn-sm" onclick="location='/admin/logout'">Valju</button>
<#else>
  <form method="post" class="form-horizontal">

    <h3>Sisselogimine</h3>
    <label>
      Kasutajanimi:
      <input type="text" name="username" class="form-control">
    </label><br>
    <label>Parool: <input type="password" class="form-control" name="password"></label><br>
    <button id="login-button" class="btn btn-default btn-sm">Sisene</button>
  </form>

  <#if error??>
    <div class="alert alert-danger">${error}</div>
  </#if>
</#if>

</@html>

<@html>
  <#if loggedInUsername??>
  <p><@m'loggedIn'/> ${loggedInUsername}</p>
  <button class="btn btn-default btn-sm" onclick="location='/admin/logout'"><@m'exit'/> </button>
  <#else>
  <div class="panel panel-login">
  <div class="panel-body">
  <form method="post" class="form-horizontal">

      <div class="login-center">
      <h3 id="login-h3"><@m'login'/></h3>
      <label>
        <@m'username'/>
        <input type="text" name="username" class="form-control">
      </label><br>
      <label> <@m'password'/><input type="password" class="form-control" name="password"></label><br>
      <button id="submit" class="btn btn-default btn-sm"><span class="enter"> <@m'enter'/></span><span class= "glyphicon glyphicon-arrow-right"</span></button>
    </div>
  </form>
    <#if error??>
    <div class="alert alert-danger">${error}</div>
    </#if>
  </#if>
</div>
</div>
</@html>

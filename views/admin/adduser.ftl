<@html>

<div class="panel-login">
  <div class="login-center">
    <form method="post" class="form-horizontal">
      <h3 ><@m'changePassword'/></h3>
      <label><@m'username'/><input name="username" class="form-control"></label><br>
      <label><@m'password'/><input type="password" class="form-control" name="passwordFirst"></label><br>
      <label><@m'repeatPassword'/><input type="password" class="form-control" name="passwordSecond"></label><br>
      <button type="submit" class="btn btn-default btn-sm" id="saveNewUser"><span class="enter"><@m'add'/></span></button>
    </form>
  </div>

  <#list userNames as username>
    <div>${username}</div>
  </#list>
</div>

</@html>

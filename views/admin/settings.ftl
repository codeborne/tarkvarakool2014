<@html>
  <form method="post" class="form-horizontal">
    <div class="panel-login">
      <div class="login-center">
        <h3 ><@m'login'/></h3>
        <label><@m'oldPassword'/><input type="password" name="oldPassword" class="form-control"></label><br>
        <label><@m'newPasswordFirst'/><input type="password" class="form-control" name="newPasswordFirst"></label><br>
        <label><@m'newPasswordSecond'/><input type="password" class="form-control" name="newPasswordSecond"></label><br>
        <button type="submit" class="btn btn-default btn-sm"><span class="enter"><@m'enter'/></span></button>
      </div>
      </div>
  </form>
  <#if errorsList?has_content>
    <#list errorsList as error>
    <div class="alert alert-danger">${error} </div>
    </#list>
  </#if>

</@html>

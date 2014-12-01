<@html>

    <div class="panel-login">
      <div class="login-center">
        <form method="post" class="form-horizontal">
        <h3 ><@m'changePassword'/></h3>
        <label><@m'oldPassword'/><input type="password" name="oldPassword" class="form-control"></label><br>
        <label><@m'newPasswordFirst'/><input type="password" class="form-control" name="newPasswordFirst"></label><br>
        <label><@m'newPasswordSecond'/><input type="password" class="form-control" name="newPasswordSecond"></label><br>
        <button type="submit" class="btn btn-default btn-sm" id="saveNewPassword"><span class="enter"><@m'save'/></span></button>
        </form>
      </div>
      <#if errorsList?has_content>
        <#list errorsList as error>
          <div class="alert alert-danger">${error} </div>
        </#list>
      </#if>
      </div>



</@html>

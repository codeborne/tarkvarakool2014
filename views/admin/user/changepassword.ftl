<@html>

    <div class="panel panel-password">
      <div class="panel-body">
      <div class="change-password-center">
        <form method="post" class="form-horizontal" autocomplete="off">
        <h3 ><@m'changePassword'/></h3>
        <label><@m'oldPassword'/><input type="password" name="oldPassword" class="form-control"></label><br>
        <label><@m'newPasswordFirst'/><input type="password" class="form-control" name="newPasswordFirst"></label><br>
        <label><@m'newPasswordSecond'/><input type="password" class="form-control" name="newPasswordSecond"></label><br>
        <button type="submit" class="btn btn-default btn-sm" id="saveNewPassword"><span class="enter"><@m'save'/></span></button>
         <input type="hidden" name="csrfToken" value="${session.getAttribute("csrfToken")}">
        </form>
      </div>
      <#if errorsList?has_content>
        <#list errorsList as error>
          <div class="alert alert-danger">${error} </div>
        </#list>
      </#if>
      </div>
    </div>



</@html>

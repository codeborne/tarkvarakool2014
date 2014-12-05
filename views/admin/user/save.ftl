<@html>

<div class="panel panel-save-users">
  <div class="panel-body">
  <div class="save-center">
    <form method="post" class="form-horizontal" autocomplete="off">
      <h3 ><@m'addUser'/></h3>
      <input style="display:none" type="text" name="fakeusernameremembered"/>
      <input style="display:none" type="password" name="fakepasswordremembered"/>
      <input type="hidden" name="csrfToken" value="${session.getAttribute("csrfToken")}">
      <label><@m'username'/><input name="username" class="form-control" ></label><br>
      <label><@m'password'/><input type="password" class="form-control" name="passwordFirst"></label><br>
      <label><@m'repeatPassword'/><input type="password" class="form-control" name="passwordSecond"></label><br>
      <button type="submit" class="btn btn-default btn-sm" id="saveNewUser"><span class="enter"><@m'add'/></span></button>
    </form>
  </div>
  <#if errorsList?has_content>
    <#list errorsList as error>
      <div class="alert alert-danger alert-danger-save-users">${error} </div>
    </#list>
  </#if>
  </div>
</div>
</@html>

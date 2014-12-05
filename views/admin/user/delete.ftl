<@html>

<div class="panel">
  <div class="login-center">
    <table class="table user-table">
      <thead>
      <tr>
        <th class="settings-delete-users"><@m'user'/></th>
        <th  class="settings-delete-users"><@m'delete'/></th>
      </tr>
      </thead>
      <tbody>
        <#list userNames as username>
        <tr>
          <td>${username}</td>
          <td>
            <#if admin?? && username!=admin>
            <form action="delete" method="post" onsubmit="return confirm('<@m'errorDeletingConfirmation'/>')"
                  class="action-button">
              <input type="hidden" name="csrfToken" value="${session.getAttribute("csrfToken")}">
              <input type="hidden" name="username" value="${username}"/>
              <button class="deleteButton" title="<@m'delete'/>" type="submit" class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-trash"></span>
              </button>
            </form>
            </#if>
          </td>
        </tr>
        </#list>
      </tbody>
    </table>
  </div>
</div>

</@html>
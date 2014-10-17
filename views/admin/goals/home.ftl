<@html>
  <#if goals?has_content>
  <h3>Eesmärgid:</h3>

  <table class="table table-hover">
    <tr>
      <th>Eesmärk</th>
      <th>Eelarve</th>
      <th>Muuda</th>
      <th>Kustuta</th>
    </tr>
    <#list goals as goal>
      <tr>
        <td>${goal.name}</td>
        <td>${goal.budget?c}</td>
        <td>
          <form action="modify">
            <input type="hidden" value="${goal.id}" name="id">
            <button type="submit" class="btn btn-default btn-sm">
              <span class="glyphicon glyphicon-pencil"></span>
            </button>
          </form>
        </td>
        <td>
          <form action="delete" method="post">
            <input type="hidden" name="id" value="${goal.id}"/>
            <button type="submit" class="btn btn-default btn-sm">
              <span class="glyphicon glyphicon-trash"></span>
            </button>
          </form>
        </td>
      </tr>
    </#list>
  </table>
  <#else>
  <h3>Andmebaasis eesmärke ei ole.</h3>
  </#if>
<form action="add">
  <input type="submit" class="btn btn-default" value="Lisa">
</form>

</@html>
<@html>
<form action="add">
    <input type="submit" class="btn btn-default" value="add">
</form>

<#if goals?has_content>
  <h3>Current list of goals:</h3>

  <table class="table table-hover">
      <tr>
          <th>Goal</th>
          <th>Budget</th>
          <th>Modify</th>
          <th>Delete</th>
      </tr>
    <#list goals as goal>
        <tr>
            <td>${goal.name}</td>
            <td>${goal.budget?c}</td>
            <td>
              <form action="modify">
                <input type="hidden" value="${goal.id}" name="id">
                <button type="submit" class="btn btn-default btn-sm">
                  <span class="glyphicon glyphicon-pencil"></span> Muuda
                </button>
              </form>
            </td>
           <td></td> 
        </tr>
    </#list>
  </table>
<#else>
  <h3>There are no goals in the database yet</h3>
</#if>

</@html>
<@html>

<#if goals?has_content>
  <h3>Current list of goals:</h3>
  <table class="table table-hover">
      <tr>
          <th>Goal</th>
          <th>Budget</th>
      </tr>
    <#list goals as goal>
        <tr>
            <td>${goal.name}</td>
            <td>${goal.budget}</td>
        </tr>
    </#list>
  </table>
<#else>
  <h3>There are no goals in the database yet</h3>
</#if>

</@html>
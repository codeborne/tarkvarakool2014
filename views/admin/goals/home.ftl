<@html>

  <#if goals?has_content>
  <h3>Eesmärgid</h3>


  <table class="table table-hover">
    <tr>
      <th>Eesmärk</th>
      <th>Eelarve</th>
      <th>Mõõdikud</th>
      <th>Muuda</th>
      <th>Kustuta</th>
    </tr>
    <#list goals as goal>
      <tr class="goal">
        <td class="nameInTable">${goal.name}</td>
        <td class="budgetInTable">${goal.budget?c}</td>

        <td><form action="/admin/metrics/metrics">
          <input type="hidden" value="${goal.id}" name="goalId">
          <button type="submit" class="metricsButton btn btn-default btn-sm">
            <span class="glyphicon glyphicon-list-alt"></span>
          </button>
        </form></td>

        <td>
          <form action="modify">
            <input type="hidden" value="${goal.id}" name="id">
            <button class="modifyButton" type="submit" class="btn btn-default btn-sm">
              <span class="glyphicon glyphicon-pencil"></span>
            </button>
          </form>
        </td>
        <td>
          <#if !goal.metrics?has_content>
          <form action="delete" method="post" onsubmit="return confirm('Kas oled kustutamises kindel?')">
            <input type="hidden" name="id" value="${goal.id}"/>
            <button class="deleteButton" type="submit" class="btn btn-default btn-sm">
              <span class="glyphicon glyphicon-trash"></span>
            </button>
          </form>
          </#if>
        </td>
      </tr>
    </#list>
  </table>

  <#else>
  <h3 id="noGoals"> Andmebaasis eesmärke ei ole.</h3>
  </#if>
<form action="add">
  <input type="submit" class="btn btn-default" value="Lisa">
</form>
  <#if goals?has_content>
  <button type="submit" id="addMetricsValue" class="btn btn-default btn-sm" onclick="location='/admin/values/value'">
    Nupp
  </button>
  </#if>





</@html>


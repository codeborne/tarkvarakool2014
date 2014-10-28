<@html>

<div class="btn-group">
  <button type="button" class="btn btn-default active" onclick="location='/admin/goals/home'">Eesmärgid</button>
  <button type="button" id="addMetricsValue" class="btn btn-default" onclick="location='/admin/values/value'">
    Väärtused
  </button>
</div>
<br><br>
  <#if goals?has_content>
  <h3>Eesmärgid</h3>

  <table class="table table-hover">
    <tr>
      <th>Eesmärk</th>
      <th>Järjekorranumber</th>
      <th>Kommentaar</th>
      <th>Eelarve</th>
      <th>Mõõdikud</th>
      <th>Muuda</th>
      <th>Kustuta</th>
    </tr>
    <#list goals as goal>
      <tr class="goal">
        <td class="nameInTable">${goal.name}</td>
        <td class="sequenceNumberInTable">
          <form method="post">
            <input type="hidden" value="${goal.id?c}" name="id">
            <select onchange="this.form.submit()" name="sequenceNumber">
              <option>${goal.sequenceNumber}</option>
              <#list 1..goals.size() as number>
              <#if number!=goal.sequenceNumber>
                <option value="${number}">${number}</option>
              </#if>
              </#list>
            </select>
          </form>
        </td>
        <td class="commentInTable">${goal.comment!""}</td>
        <td class="budgetInTable">${goal.budget?c}</td>

        <td>
          <form action="/admin/metrics/metrics">
            <input type="hidden" value="${goal.id?c}" name="goalId">
            <button type="submit" class="metricsButton btn btn-default btn-sm">
              <span class="glyphicon glyphicon-list-alt"></span>
            </button>
          </form>
        </td>

        <td>
          <form action="modify">
            <input type="hidden" value="${goal.id?c}" name="id">
            <button class="modifyButton" type="submit" class="btn btn-default btn-sm">
              <span class="glyphicon glyphicon-pencil"></span>
            </button>
          </form>
        </td>
        <td>
          <#if !goal.metrics?has_content>
            <form action="delete" method="post" onsubmit="return confirm('Kas oled kustutamises kindel?')">
              <input type="hidden" name="id" value="${goal.id?c}"/>
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
</@html>


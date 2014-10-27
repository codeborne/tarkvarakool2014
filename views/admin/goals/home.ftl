<@html>

<div class="btn-group">
  <button type="button" class="btn btn-default active" onclick="location='/admin/goals/home'">Eesmärgid</button>
  <button type="button" id="addMetricsValue" class="btn btn-default" onclick="location='/admin/values/value'">
    Väärtused
  </button>
</div>
<br><br>

<h3>Eesmärgid</h3>

<table class="table table-hover">
  <tr>
    <th>Eesmärk</th>
    <th>Kommentaar</th>
    <th>Eelarve</th>
    <th>Mõõdikud</th>
    <th>Muuda</th>
    <th>Kustuta</th>
  </tr>
  <#if goals?has_content>
    <#list goals as goal>
      <tr class="goal">
        <td class="nameInTable">${goal.name}</td>
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
  </#if>
  <form method="post" class="form-horizontal">
  <tr>
    <td><textarea name="name" class="form-control" rows="1" maxlength="255">${name!""}</textarea></td>
    <td><textarea name="comment" class="form-control" rows="1" maxlength="255">${comment!""}</textarea></td>
    <td><input type="number" class="form-control" min="1" max="2147483647" name="budget"
               <#if budget?? && (budget>0)>value=${budget?c}</#if>></td>
    <button id="goalButton" class="btn btn-default btn-sm">${buttonTitle}</button>
  </tr>
    </form>
</table>
<#--<#else>-->
<#--<h3 id="noGoals"> Andmebaasis eesmärke ei ole.</h3>-->
<#--</#if>-->
<form action="add">
  <input type="submit" class="btn btn-default" value="Lisa">
</form>
</@html>


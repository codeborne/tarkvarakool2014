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
  <form method="post" action="add" id="addGoalForm">
    <tr>
      <td>
        <input name="name" class="form-control" rows="1" placeholder="Sisesta eesmärk"
               maxlength="255" value="${name!""}"></td>
      <td><input name="comment" class="form-control" rows="1" placeholder="Sisesta kommentaar"
                 maxlength="255"  value="${comment!""}"></td>
      <td><input type="number" class="form-control" placeholder="Sisesta eelarve" min="1" max="2147483647" name="budget"
                 <#if budget?? && (budget>0)>value=${budget?c}</#if>></td>
      <td>
        <button id="goalAddOrModifyButton" type="button" class="btn btn-default btn-sm">Lisa</button>
      </td>
    </tr>
    <tr>
      <td colspan="3"
      <span id="errors"></span></td></tr>
  </form>
</table>


<script>

  $(function () {

    var responseHandler = function (response) {
      if (response.trim() == "") {
        window.location = window.location;
      }
      $("#errors").html(response);
    };

    var clickHandler = function () {
      var form = $('#addGoalForm');
      $.post(form.attr("action"), form.serialize(), responseHandler);
    };

    $('#goalAddOrModifyButton').click(clickHandler);

  });



</script>

</@html>


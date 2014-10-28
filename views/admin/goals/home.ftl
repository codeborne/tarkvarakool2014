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
    <th>Jrk nr</th>
    <th>Eesmärk</th>
    <th>Kommentaar</th>
    <th>Eelarve</th>
    <th>Muuda</th>
    <th>Mõõdikud</th>
    <th>Kustuta</th>
  </tr>
  <#if goals?has_content>
    <#list goals as goal>
      <tr class="goal">

        <td class="sequenceNumberInTable">
          <form method="post">
            <input type="hidden" value="${goal.id?c}" name="id">
            <select onchange="this.form.submit()" name="sequenceNumber">
              <option>${goal.sequenceNumber}</option>
              <#list 1..goals.size() as number>
                <option value="${number}">${number}</option>
              </#list>
            </select>
          </form>
        </td>

        <td class="nameInTable">
          <span class="value">${goal.name}</span>
          <input type="text" class="value form-control" name="name" value="${goal.name}" style="display: none;">
        </td>
        <td class="commentInTable">
          <span class="value">${goal.comment!""}</span>
          <input type="text" class="value form-control" name="comment" <#if goal.comment??>value="${goal.comment}"</#if> style="display: none;">
        </td>
        <td class="budgetInTable">
          <span class="value">${goal.budget?c}</span>
          <input type="number" class="value form-control" name="budget" value="${goal.budget?c}" style="display: none;">
        </td>
        <td>
          <input type="hidden" class="value" value="${goal.id?c}" name="id">
          <input type="button" class="saveGoalButton value btn btn-default btn-sm" value="Salvesta"
                 style="display: none" data-action="modify">
          <input type="button" class="cancelGoalButton value btn btn-default btn-sm"
                 onclick="location='/admin/goals/home'; return false;" value="Tühista" style="display:none"
                 data-action="modify">
          <span class="value">
            <button class="modifyButton" type="button" class="btn btn-default btn-sm">
              <span class="glyphicon glyphicon-pencil"></span>
            </button>
          </span>
        </td>

        <td>
          <form action="/admin/metrics/metrics">
            <input type="hidden" value="${goal.id?c}" name="goalId">
            <button type="submit" class="metricsButton btn btn-default btn-sm">
              <span class="glyphicon glyphicon-list-alt"></span>
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
  <tr>
    <td></td>
    <td>
      <input name="name" class="value form-control" rows="1" placeholder="Sisesta eesmärk"
             maxlength="255" value="${name!""}"></td>
    <td><input name="comment" class="value form-control" rows="1" placeholder="Kommentaar"
               maxlength="255" value="${comment!""}"></td>
    <td><input type="number" class="value form-control" placeholder="Eelarve"name="budget"
               <#if budget?? && (budget>0)>value=${budget?c}</#if>></td>
    <td>
      <input type="button" class="saveGoalButton value btn btn-default btn-sm" value="Lisa" data-action="add">
    </td>
  </tr>
  <tr>
    <td></td>
    <td colspan="3"
    <span id="errors"></span></td></tr>
</table>


<script>

  $(function () {

    var responseHandler = function (response) {
      if (response.trim() == "") {
        window.location = window.location;
      }
      $("#errors").html(response);
    };

    var saveClickHandler = function (event) {
      var button = $(event.target);
      var values = button.closest('tr').find('input.value');
      $.post(button.data("action"), values.serialize(), responseHandler);
    };

    $('.saveGoalButton').click(saveClickHandler);

    var modifyClickHandler = function (event) {
      $("input.value").hide();
      $("span.value").show();
      var row = $(event.target).closest('tr');
      row.find("span.value").hide();
      row.find("input.value").show();
    };

    $('.modifyButton').click(modifyClickHandler);
  });
</script>

</@html>


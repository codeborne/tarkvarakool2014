<@html>

<script>
  function sendData(goalId) {
    inputValue = thisObject.children('input').val();
    $.post("/admin/goals/home", {goalId: goalId},
      function(text) {
        var data = JSON.parse(text.replace(/&quot;/g,'"'));
        if(data.errorsList.length>0) {
          alert(data.errorsList.join("\n"));
        } else {
          thisObject.parent().children('span.value').text(data.value);
          thisObject.hide();
          thisObject.parent().children('span.glyphicon').show();
          thisObject.parent().children('span.value').show();
        }
      }
    );
  }

  function sendBudgetData(goalId,thisObject) {
    inputValue = thisObject.children('input').val();
    $.post("/admin/goals/home", {goalId: goalId},
      function(text) {
        var errorsList = JSON.parse(text.replace(/&quot;/g,'"'));
        if(errorsList.length>0) {
          alert(errorsList.join("\n"));
        } else {
          thisObject.parent().children('span.value').text(inputValue);
          thisObject.hide();
          thisObject.parent().children('span.glyphicon').show();
          thisObject.parent().children('span.value').show();
        }
      }
    );
  }

  function showInputHideIconAndValue(thisObject) {
    if ($('.goal-form :visible').size() > 0) {
      $('.goal-form :visible').submit();
    };
    thisObject.hide();
    thisObject.parent().children('span.value').hide();
    thisObject.parent().children('form').children('input').val(thisObject.parent().children('span.value').text());
    thisObject.parent().children('form').show();
    thisObject.parent().children('form').children('input').focus();
  }

</script>

  <div class="btn-group">
    <button type="button" class="btn btn-default active" onclick="location='/admin/goals/home'">Eesmärgid</button>
    <button type="button" id="addMetricsValue" class="btn btn-default" onclick="location='/admin/values/value'">Väärtused</button>
  </div>
  <br><br>
  <#if goals?has_content>
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
    <#list goals as goal>
      <td><span class="goal">${goal.name}</span> <span class="glyphicon glyphicon-pencil hand-pointer" onclick="showInputHideIconAndValue($(this));"></span>
        <form class="goal-form" style="display: none;" onsubmit="sendData(${goal.name}, $(this)); return false;"><input type="text" step="any" class="modify-value"></form></td>

      <tr class="goal">
        <td class="nameInTable">${goal.name}</td>
        <td class="commentInTable">${goal.comment!""}</td>
        <td class="budgetInTable">${goal.budget?c}</td>

        <td><form action="/admin/metrics/metrics">
          <input type="hidden" value="${goal.id?c}" name="goalId">
          <button type="submit" class="metricsButton btn btn-default btn-sm">
            <span class="glyphicon glyphicon-list-alt"></span>
          </button>
        </form></td>

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


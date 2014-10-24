<@html>

  <script>
    function sendData(goalId, metricId, year, isForecast, thisObject) {
      inputValue = thisObject.children('input').val();
      $.post("/admin/values/modify", {goalId: goalId, metricId: metricId, year: year, value: inputValue, isForecast: isForecast},
        function(data) {
          if(data!="")
            alert(data);

          thisObject.hide();
          thisObject.parent().children('span.glyphicon').show();
          thisObject.parent().children('span.value').text(inputValue);
          thisObject.parent().children('span.value').show();
        }
      );
    }

    function sendBudgetData(goalId, year, thisObject) {
      inputValue = thisObject.children('input').val();
      $.post("/admin/budgets/modify", {goalId: goalId, year: year, yearlyBudget: inputValue},
        function(data) {
          if(data!="")
            alert(data);

          thisObject.hide();
          thisObject.parent().children('span.glyphicon').show();
          thisObject.parent().children('span.value').text(inputValue);
          thisObject.parent().children('span.value').show();
        }
      );
    }

    function showInputHideIconAndValue(thisObject) {
      thisObject.hide();
      thisObject.parent().children('span.value').hide();
      thisObject.parent().children('form').children('input').val(thisObject.parent().children('span.value').text());
      thisObject.parent().children('form').show();
      thisObject.parent().children('form').children('input').focus();
    }
  </script>

  <div class="btn-group">
    <button type="button" class="btn btn-default" onclick="location='/admin/goals/home'">Eesmärgid</button>
    <button type="button" id="addMetricsValue" class="btn btn-default active" onclick="location='/admin/values/value'">Väärtused</button>
  </div>
  <br><br>
    <span class="forecast-indicator">P - prognoositav väärtus</span>
  <br><br>
  <#list goals as goal>
  <div class="goal">
    <h4 class="name"> Eesmärk: ${goal.name}</h4>


    <table class="table">
      <div class="tableHead">
        <tr>
          <th>Mõõdik</th>
          <#list minimumYear..maximumYear as year>
            <th>${year?c}</th>
          </#list>
        </tr>
      </div>

        <#list goal.metrics as metric>
          <tr class="metric">
            <td class="name">${metric.name} <#if metric.unit?has_content>(${metric.unit})</#if></td>
            <#list minimumYear..maximumYear as year>
              <td>
                <div class="measured">
                  <span class="value">${((metric.values.get(year))?c)!""}</span> <span class="glyphicon glyphicon-pencil hand-pointer" onclick="showInputHideIconAndValue($(this));"></span>
                  <form style="display: none;" onsubmit="sendData(${goal.id?c}, ${metric.id?c}, ${year?c}, false, $(this)); return false;">
                    <input type="number" step="any" class="modify-value">
                  </form>
                </div>
                <div class="forecasted">
                  <span class="value">${((metric.values.get(year))?c)!""}</span><sup class="forecast-indicator">P</sup> <span class="glyphicon glyphicon-pencil hand-pointer" onclick="showInputHideIconAndValue($(this));"></span>
                  <form style="display: none;" onsubmit="sendData(${goal.id?c}, ${metric.id?c}, ${year?c}, true, $(this)); return false;">
                    <input type="number" step="any" class="modify-value">
                  </form>
                </div>
              </td>
            </#list>
          </tr>
        </#list>
      <tr class="yearlyBudget">
        <td>Kulutatud raha eurodes</td>
        <#list minimumYear..maximumYear as year>
          <td><span class="value">${((goal.yearlyBudgets.get(year))?c)!""}</span> <span class="glyphicon glyphicon-pencil hand-pointer" onclick="showInputHideIconAndValue($(this));"></span>
            <form style="display: none;" onsubmit="sendBudgetData(${goal.id}, ${year?c}, $(this)); return false;"><input type="number" step="any" class="modify-value"></form></td>
        </#list>
      </tr>
    </table>
    <br>
    <br>

  </#list>
</@html>

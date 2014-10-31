<@html values_active=true>

<script>
  function sendData(goalId, metricId, year, isForecast, thisObject) {
    inputValue = thisObject.children('input').val();
    $.post("/admin/values/modify", {goalId: goalId, metricId: metricId, year: year, value: inputValue, isForecast: isForecast},
      function (text) {
        var data = JSON.parse(text.replace(/&quot;/g, '"'));
        if (data.errorsList.length > 0) {
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

  function sendBudgetData(goalId, year, thisObject) {
    inputValue = thisObject.children('input').val();
    $.post("/admin/budgets/modify", {goalId: goalId, year: year, yearlyBudget: inputValue},
      function (text) {
        var errorsList = JSON.parse(text.replace(/&quot;/g, '"'));
        if (errorsList.length > 0) {
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
    if ($('.metric-value-form :visible').size() > 0) {
      $('.metric-value-form :visible').submit();
    }
    ;
    thisObject.hide();
    thisObject.parent().children('span.value').hide();
    thisObject.parent().children('form').children('input').val(thisObject.parent().children('span.value').text());
    thisObject.parent().children('form').show();
    thisObject.parent().children('form').children('input').focus();
  }

</script>

<br><br>
<#list goals as goal>
<div class="panel panel-default">
  <div class="goal">
<div class="panel-heading">
<span class="forecast-indicator">P - prognoositav väärtus</span>
<br><br>

    <h4 class="name"> ${goal.name}</h4>

</div>
  <div class="panel-body">
    <table class="table">
      <thead>
        <tr>
          <th>Mõõdik</th>
          <#list minimumYear..maximumYear as year>
            <th> ${year?c}</th>
          </#list>
        </tr>
        </thead>
      <tbody>

      <#list goal.metrics as metric>
        <tr class="metric">
          <td class="metricName">${metric.name} <#if metric.unit?has_content>(${metric.unit})</#if></td>
          <td class="startLevel">${metric.startLevel?c}</td>
          <#list (minimumYear+1)..maximumYear as year>
            <td>
              <div class="measured">
                <span class="value">${((metric.values.get(year))?c)!""}</span> <span
                class="glyphicon glyphicon-pencil hand-pointer" onclick="showInputHideIconAndValue($(this));"></span>

                <form class="metric-value-form" style="display: none;"
                      onsubmit="sendData(${goal.id?c}, ${metric.id?c}, ${year?c}, false, $(this)); return false;">
                  <input type="text" step="any" class="modify-value">
                </form>
              </div>
              <div class="forecasted">
                <span class="value">${((metric.values.get(year))?c)!""}</span><sup class="forecast-indicator">P</sup>
                <span class="glyphicon glyphicon-pencil hand-pointer"
                      onclick="showInputHideIconAndValue($(this));"></span>

                <form class="metric-value-form" style="display: none;"
                      onsubmit="sendData(${goal.id?c}, ${metric.id?c}, ${year?c}, true, $(this)); return false;">
                  <input type="text" step="any" class="modify-value">
                </form>
              </div>
            </td>
          </#list>
        </tr>
      </#list>

      <tr class="yearlyBudget">
        <td>Kulutatud raha eurodes</td>
        <#list minimumYear..maximumYear as year>
          <td><span class="value">${((goal.yearlyBudgets.get(year))?c)!""}</span> <span
            class="glyphicon glyphicon-pencil hand-pointer" onclick="showInputHideIconAndValue($(this));"></span>

            <form class="metric-value-form" style="display: none;"
                  onsubmit="sendBudgetData(${goal.id}, ${year?c}, $(this)); return false;">
              <input type="number" step="any" class="modify-value">
            </form>
          </td>
        </#list>
        </tr>
        </tbody>


    </table>
</div>
</div>
    <br>
  </div>
    </#list>
</@html>

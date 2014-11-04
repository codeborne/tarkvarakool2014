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

          var changedValue = parseFloat(data.value);
          if(data.comparableValue!=""){
          var comparableValue2 = parseFloat(data.comparableValue);
          if (isForecast && comparableValue2 >= changedValue){
            thisObject.parent().parent().children('div.measured').children('span.value').css('color', 'green');
          }
          else if (isForecast && comparableValue2 < changedValue){
            thisObject.parent().parent().children('div.measured').children('span.value').css('color', 'red');
          }
          else if (!isForecast && changedValue >= comparableValue2) {
            thisObject.parent().children('span.value').css('color', 'green');
          }
          else if (!isForecast && changedValue < comparableValue2 ){
            thisObject.parent().children('span.value').css('color', 'red');
          }
          }
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
          <th>Algtase</th>
          <#list minimumYear..maximumYear as year>
            <th> ${year?c}</th>
          </#list>
          <th>Sihttase</th>
        </tr>
        </thead>
      <tbody>

      <#list goal.metrics as metric>
        <tr class="metric">
          <td class="metricName">${metric.name} <#if metric.unit?has_content>(${metric.unit})</#if></td>
          <td class="startLevel">${metric.startLevel?c}</td>
          <#list (minimumYear)..maximumYear as year>
            <td>
              <div class="measured">
                <span class="value" <#if (metric.values.get(year)?has_content && metric.forecasts.get(year)?has_content && metric.values.get(year)>=metric.forecasts.get(year))> style="color: green"
                <#elseif (metric.values.get(year)?has_content && metric.forecasts.get(year)?has_content && metric.values.get(year)<metric.forecasts.get(year))> style="color: red"</#if>  <#--<#if (metric.values.get(year)?has_content && metric.forecasts.get(year)?has_content &&-->
                >${((metric.values.get(year))?c)!""}</span> <span
                class="glyphicon glyphicon-pencil hand-pointer" onclick="showInputHideIconAndValue($(this));"></span>

                <form class="metric-value-form" style="display: none;"
                      onsubmit="sendData(${goal.id?c}, ${metric.id?c}, ${year?c}, false, $(this)); return false;">
                  <input type="text" step="any" class="modify-value">
                </form>
              </div>
              <div class="forecasted">
                <span class="value">${((metric.forecasts.get(year))?c)!""}</span><sup class="forecast-indicator">P</sup>
                <span class="glyphicon glyphicon-pencil hand-pointer"
                      onclick="showInputHideIconAndValue($(this));"></span>

                <form class="metric-value-form" style="display: none;"
                      onsubmit="sendData(${goal.id?c}, ${metric.id?c}, ${year?c}, true, $(this)); return false;">
                  <input type="text" step="any" class="modify-value">
                </form>
              </div>
            </td>
          </#list>
          <td class="targetLevel">${metric.targetLevel?c}</td>
        </tr>
      </#list>

      <tr class="yearlyBudget">
        <td>Kulutatud raha eurodes</td>
        <td></td>
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

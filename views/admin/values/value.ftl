<@html values_active=true>

<script>
  function sendData(goalId, metricId, year, isForecast, thisObject, csrfToken) {
    inputValue = thisObject.children('input').val();
    $.post("/admin/values/modify", {goalId: goalId, metricId: metricId, year: year, value: inputValue, isForecast: isForecast, csrfToken: csrfToken},
      function (text) {
        var data = JSON.parse(text.replace(/&quot;/g, '"'));
        if (data.errorsList.length > 0) {
          alert(data.errorsList.join("\n"));
        }
        else {
          thisObject.hide();
          if(data.value.trim()!="") {
            thisObject.parent().children('span.value').removeClass("glyphicon");
            thisObject.parent().children('span.value').removeClass("glyphicon-pencil");
            thisObject.parent().children('span.value').text(data.value);

            var changedValue = parseFloat(data.value);
            if (data.comparableValue.trim() != "") {
              var comparableValue2 = parseFloat(data.comparableValue);
              if (isForecast && comparableValue2 >= changedValue) {
                thisObject.parent().parent().children('div.measured').children('span.value').removeClass("redValue");
                thisObject.parent().parent().children('div.measured').children('span.value').addClass("greenValue");
              }
              else if (isForecast && comparableValue2 < changedValue) {
                thisObject.parent().parent().children('div.measured').children('span.value').removeClass("greenValue");
                thisObject.parent().parent().children('div.measured').children('span.value').addClass("redValue");
              }
              else if (!isForecast && changedValue >= comparableValue2) {
                thisObject.parent().children('span.value').removeClass("redValue");
                thisObject.parent().children('span.value').addClass("greenValue");
              }
              else if (!isForecast && changedValue < comparableValue2) {
                thisObject.parent().children('span.value').removeClass("greenValue");
                thisObject.parent().children('span.value').addClass("redValue");
              }
            }

          }
          else{
            thisObject.parent().parent().children('div.measured').children('span.value').removeClass("greenValue");
            thisObject.parent().parent().children('div.measured').children('span.value').removeClass("redValue");
            thisObject.parent().children('span.value').addClass("glyphicon");
            thisObject.parent().children('span.value').addClass("glyphicon-pencil");
            thisObject.parent().children('span.value').text("");
          }

          thisObject.parent().children('span.value').show();
          thisObject.parent().children('sup.forecast-indicator').show();

        }
      }
    );
  }

  function sendBudgetData(goalId, year, thisObject, csrfToken) {
    inputValue = thisObject.children('input').val();
    $.post("/admin/budgets/modify", {goalId: goalId, year: year, yearlyBudget: inputValue, csrfToken: csrfToken},
      function (text) {
        var errorsList = JSON.parse(text.replace(/&quot;/g, '"'));
        if (errorsList.length > 0) {
          alert(errorsList.join("\n"));
        } else {
          thisObject.hide();
          if(inputValue == ""){
            thisObject.parent().children('span.value').addClass("glyphicon");
            thisObject.parent().children('span.value').addClass("glyphicon-pencil");
            thisObject.parent().children('span.value').text("");
          }
          else{
            thisObject.parent().children('span.value').removeClass("glyphicon");
            thisObject.parent().children('span.value').removeClass("glyphicon-pencil");
            thisObject.parent().children('span.value').text(inputValue);
          }
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
    thisObject.parent().children('sup.forecast-indicator').hide();
    thisObject.parent().children('form').children('input').val(thisObject.parent().children('span.value').text());
    thisObject.parent().children('form').show();
    thisObject.parent().children('form').children('input').focus();
  }

</script>
  <#if goals?has_content>
<#list goals as goal>
<div class="panel panel-default">
  <div class="goal">
<div class="panel-heading">
  <form action="/admin/charts">
    <input type="hidden" value="${goal.id?c}" name="goalId">
    <button class="chart-button" type="submit" class="btn btn-default btn-sm">
      <span class="glyphicon glyphicon-stats"></span><span><br>Graafik</span>
    </button>
  </form>
  <#if goal_index ==0>
   </#if>
    <h4 class="name"> ${goal.name}</h4>
</div>
  <div class="panel-body">
    <table class="table valueTable table-hover">
      <thead>
        <tr>
          <th><@m'metric'/></th>
          <th><@m'startLevel'/></th>
          <#list minimumYear..maximumYear as year>
            <th> ${year?c}</th>
          </#list>
          <th><@m'targetLevel'/></th>
        </tr>
        </thead>
      <tbody>
      <#list goal.metrics as metric>
        <tr class="metric">
          <td class="metricName">${metric.name} <#if metric.unit?has_content>(${metric.unit})</#if></td>
          <td class="startLevel"><#if metric.startLevel??>${metric.startLevel}<#else>N/A</#if></td>
          <#list (minimumYear)..maximumYear as year>
            <td class="values">
              <div class="measured">
                <span title="<@m'modify'/>" onclick="showInputHideIconAndValue($(this));"
                      <#if metric.values.get(year)?has_content>
                        <#if (metric.forecasts.get(year)?has_content && metric.values.get(year)>=metric.forecasts.get(year))>
                    class="value hand-pointer greenValue"
                <#elseif (metric.forecasts.get(year)?has_content && metric.values.get(year)<metric.forecasts.get(year))>
                    class="value hand-pointer redValue"
                    <#else>class="value hand-pointer" </#if>
                      <#else>
                      class="value glyphicon glyphicon-pencil hand-pointer"
                      </#if>>${((metric.values.get(year))?c)!""}</span>
                <form class="metric-value-form" style="display: none;"
                      onsubmit="sendData(${goal.id?c}, ${metric.id?c}, ${year?c}, false, $(this), '${session.getAttribute("csrfToken")}'); return false;">
                  <input type="text" step="any" class="modify-value" title="Vajuta enter">
                </form>
              </div>
              <br>
              <div class="forecasted">
                <span <#if metric.forecasts.get(year)??>class="value hand-pointer"<#else> class="value glyphicon glyphicon-pencil hand-pointer"</#if> title="<@m'modify'/>" onclick="showInputHideIconAndValue($(this));">${((metric.forecasts.get(year))?c)!""}</span><sup class="forecast-indicator"><@m'valueSymbol'/></sup>
                <form class="metric-value-form" style="display: none;"
                      onsubmit="sendData(${goal.id?c}, ${metric.id?c}, ${year?c}, true, $(this), '${session.getAttribute("csrfToken")}'); return false;">
                  <input type="text" step="any" class="modify-value" title="Vajuta enter">
                </form>
              </div>
            </td>
          </#list>
          <td class="targetLevel"><#if metric.targetLevel??>${metric.targetLevel}<#else>N/A</#if></td>
        </tr>
      </#list>
      <tr class="yearlyBudget">
        <td><em><@m'moneySpent'/></em></td>
        <td></td>
        <#list minimumYear..maximumYear as year>
          <td class="values">

            <span <#if goal.yearlyBudgets.get(year)??>class="value hand-pointer"<#else>class="value glyphicon glyphicon-pencil hand-pointer"</#if> title="<@m'modify'/>" onclick="showInputHideIconAndValue($(this));">${((goal.yearlyBudgets.get(year))?c)!""}</span>
            <form class="metric-value-form" style="display: none;"
                  onsubmit="sendBudgetData(${goal.id}, ${year?c}, $(this), '${session.getAttribute("csrfToken")}'); return false;">
              <input type="text" step="any" class="modify-value">
            </form>
          </td>
        </#list>
        <td></td>
        </tr>
      <tr></tr>
        </tbody>
    </table>
    <br>
    <span class="forecast-indicator"><@m'estimatedValue'/></span></td>
    <br><br>
</div>
</div>
  </div>
    </#list>
  <#else>
  <div class="panel">
    <div class="missingGoals">
      <h3 id="login-h3"><@m'noValues'/></h3>
    </div>
  </div>
  </#if>
</@html>



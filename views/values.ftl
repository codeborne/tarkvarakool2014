<@html values_active=true>
  <#if goals?has_content>
    <#list goals as goal>
    <div class="panel panel-default">
      <a id="${goal.id}" class="anchor"></a>
      <div class="goal">
        <div class="panel-heading">
          <form action="/charts">
            <input type="hidden" value="${goal.id?c}" name="goalId">
            <button type="submit" class="chart-button btn btn-default btn-sm">
              <span class="glyphicon glyphicon-stats" title="<@m'viewChart'/>">
            </button>
          </form>
          <a href="/home#${goal.id}">
            <button class="values-view-button btn btn-default btn-sm">
              <span class="glyphicon glyphicon-home" title="<@m'goToGoal'/>"></span>
            </button>
          </a>
          <h4 class="name line-break"><#if language == 'et'>${goal.name}<#elseif language == 'en'><#if goal.engName??>${goal.engName}<#else><i>${goal.name}</i></#if></#if></h4>
          <div class="line-break"><#if language == 'et'>${goal.comment!""}<#elseif language == 'en'><#if goal.engComment??>${goal.engComment}<#else><i>${goal.comment!""}</i></#if></#if></div>
          <h4 class="budget"><@m'budget'/> ${goal.budget} €</h4>
        </div>
        <div class="panel-body">
          <table class="table valueTable valueStripes">
            <thead>
            <tr>
              <th class="valuesMetric"><@m'metric'/></th>
              <th><@m'startLevel'/></th>
              <#list minimumYear..maximumYear as year>
                <th>${year?c}</th>
              </#list>
              <th><@m'targetLevel'/></th>
            </tr>
            </thead>
            <tbody>
              <#list goal.metrics as metric>
                <#if metric.isPublic == true>
                <tr class="metric">
                  <td class="name line-break"><#if language == 'et'>${metric.name}&nbsp;<#if metric.unit?has_content>(${metric.unit})</#if><#elseif language == 'en'><#if metric.engName??>${metric.engName}&nbsp;<#else><i>${metric.name}</i>&nbsp;</#if><#if metric.engUnit?has_content>(${metric.engUnit})<#else><i>(${metric.unit!""})</i></#if></#if></td>
                  <td class="startLevel"><#if metric.startLevel??>${metric.startLevel}<#else>N/A</#if></td>
                  <#list minimumYear..maximumYear as year>
                    <td class="values">
              <span <#if (metric.forecasts.get(year)?has_content && metric.values.get(year)?has_content)>
                                      <#if  (metric.values.get(year)>=metric.forecasts.get(year))>
                                  class="value greenValue"
                              <#elseif (metric.values.get(year)<metric.forecasts.get(year))>
                                  class="value redValue"</#if> <#else> class="value"</#if>>
                <#if (metric.values.get(year)?c)?has_content>${((metric.values.get(year)))}
                <#elseif (currentYear>year)>N/A
                <#else></#if>

              </span>
                    </td>
                  </#list>
                  <td class="targetLevel"><#if metric.targetLevel??>${metric.targetLevel}<#else>N/A</#if></td>
                </tr>
                </#if>
              </#list>
            </tbody>
          </table>

          <table class="table valueTable valueStripes">
            <thead>
            <tr>
              <th><@m'budgetSum'/> ${goal.budget} €</th>
              <th></th>
              <#list minimumYear..maximumYear as year>
                <th>${year?c}</th>
              </#list>
            </tr>
            </thead>
            <tbody>
            <tr class="moneySpentRow">
              <td><@m'moneySpent'/></td>
              <td></td>
              <#list minimumYear..maximumYear as year>
                <td>${goal.yearlyBudgets.get(year)!""}</td>
              </#list>
              <td></td>
            </tr>
            </tbody>
          </table>
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


<@html values_active=true>
  <#if goals?has_content>
  <#list goals as goal>
  <div class="panel panel-default">
  <div class="goal">
    <div class="panel-heading">
      <button class="chart-button" type="button" class="btn btn-default btn-sm" title="<@m'charts'/>" onclick="location='/charts'">
        <span class="glyphicon glyphicon-stats"></span>
      </button>
      <h4 class="name"><#if language == 'et'>${goal.name}<#elseif language == 'en'><#if goal.engName??>${goal.engName}<#else><i>${goal.name}</i></#if></#if></h4>
      <div style="white-space: pre;"><#if language == 'et'>${goal.comment!""}<#elseif language == 'en'><#if goal.engComment??>${goal.engComment}<#else><i>${goal.comment!""}</i></#if></#if></div>
      <h4 class="budget"><@m'budget'/> ${goal.budget?c} €</h4>
    </div>
    <div class="panel-body">
      <table class="table valueTable">
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
          <td class="name"><#if language == 'et'>${metric.name}
            <#if metric.unit?has_content>(${metric.unit})</#if>
          <#elseif language == 'en'>
            <#if metric.engName??>${metric.engName}<#else><i>${metric.name}</i></#if>
            <#if metric.engUnit?has_content>(${metric.engUnit})<#else><i>(${metric.unit!""})</i></#if>
            </#if>
          </td>
          <td class="startLevel"><#if metric.startLevel??>${metric.startLevel?c}<#else>N/A</#if></td>
          <#list minimumYear..maximumYear as year>
            <td class="values">
              <span class="value">
                <#if (metric.values.get(year)?c)?has_content>${((metric.values.get(year))?c)}
                <#elseif (currentYear>year)>N/A
                <#else></#if>
              </span>
            </td>
          </#list>
          <td class="targetLevel"><#if metric.targetLevel??>${metric.targetLevel?c}<#else>N/A</#if></td>
        </tr>
          </#if>
        </#list>
      </tbody>
      </table>
    </div>
  </div>
  </div>
  </#list>
  <#else>
  <div class="panel-login">
    <div class="missingGoals">
      <h3 id="login-h3"><@m'noValues'/></h3>
    </div>
  </div>
  </#if>

</@html>


<@html values_active=true>
<br><br>
  <#if goals?has_content>
  <#list goals as goal>
  <div class="panel panel-default">
  <div class="goal">
    <div class="panel-heading">
      <h4 class="name"> <#if language == 'et'>${goal.name}<#elseif language == 'en'>${goal.engName}</#if></h4>
      <div style="white-space: pre;"><#if language == 'et'>${goal.comment!""}<#elseif language == 'en'>${goal.engComment!""}</#if></div>
      <h4 class="budget"><@m'budget'/>${goal.budget?c} €</h4>
    </div>
    <div class="panel-body">
      <table class="table">
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
          <#if metric.isPublic == true>
        <tr class="metric">
          <td class="name"><#if language == 'et'>${metric.name} <#if metric.unit?has_content>(${metric.unit})</#if>
          <#elseif language == 'en'>${metric.engName} <#if metric.engUnit?has_content>(${metric.engUnit})</#if></#if></td>
          <td class="startLevel">${metric.startLevel?c}</td>
          <#list minimumYear..maximumYear as year>
            <td>
              <span class="value">
                <#if (metric.values.get(year)?c)?has_content>${((metric.values.get(year))?c)}
                <#elseif (currentYear>year)>N/A
                <#else></#if>
              </span>
            </td>
          </#list>
          <td class="targetLevel">${metric.targetLevel?c}</td>
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


<@html>
  <#if goals?has_content>
    <#list goals as goal>
    <div class="panel panel-default goal">
      <div class="panel-heading">
        <form action="/charts">
          <input type="hidden" value="${goal.id?c}" name="goalId">
          <button class="chart-button" type="submit" class="btn btn-default btn-sm" title="<@m'charts'/>">
            <span class="glyphicon glyphicon-stats"></span>
          </button>
        </form>
        <h4 class="name"><#if language == 'et'>${goal.name}<#elseif language == 'en'><#if goal.engName??>${goal.engName}<#else><i>${goal.name}</i></#if></#if></h4>
        <div style="white-space: pre;"><#if language == 'et'>${goal.comment!""}<#elseif language == 'en'><#if goal.engComment??>${goal.engComment}<#else><i>${goal.comment!""}</i></#if></#if></div>
        <h4 class="budget"><@m'budget'/> ${goal.budget?c} €</h4>
      </div>
      <div class="panel-body">
        <table class="table userHomeTable">
          <thead>
          <tr>
            <th><@m'metric'/></th>
            <th><@m'publicDescription'/></th>
            <th><@m'startLevel'/></th>
            <th><@m'targetLevel'/></th>
            <th><@m'infoSource'/></th>
          </tr>
          </thead>
          <tbody>
            <#list goal.metrics as metric>
              <#if metric.isPublic == true>
              <tr class="metric">
                <td class="name">
                  <#if language == 'et'>${metric.name}
                  <#elseif language == 'en'>
                    <#if metric.engName??>${metric.engName}<#else><i>${metric.name}</i></#if>

                    </#if>
                </td>
                <td class="userViewPublicDescription"><#if language == 'et'>${metric.publicDescription!""}<#elseif language == 'en'><#if metric.engPublicDescription??>${metric.engPublicDescription}<#else><i>${metric.publicDescription!""}</i></#if></#if></td>
                <td class="startLevel">
                  <#if metric.startLevel??>${metric.startLevel?c}
                    <#if language == 'et'>
                      <#if metric.unit?has_content>${metric.unit}</#if>
                    <#elseif language == 'en'>
                        <#if metric.engUnit?has_content>${metric.engUnit}<#else><i>${metric.unit!""}</i></#if>
                    </#if><br>
                    <#if metric.commentOnStartLevel?has_content> (${metric.commentOnStartLevel})</#if>
                  <#elseif metric.commentOnStartLevel?has_content>${metric.commentOnStartLevel}
                  <#else>N/A</#if>
                </td>
                <td class="targetLevel">
                  <#if metric.targetLevel??>${metric.targetLevel?c}
                    <#if language == 'et'>
                      <#if metric.unit?has_content>${metric.unit}</#if>
                    <#elseif language == 'en'>
                        <#if metric.engUnit?has_content>${metric.engUnit}<#else><i>${metric.unit!""}</i></#if>
                        </#if><br>
                    <#if metric.commentOnTargetLevel?has_content> (${metric.commentOnTargetLevel})</#if>
                  <#elseif metric.commentOnTargetLevel?has_content> ${metric.commentOnTargetLevel}
                  <#else>N/A</#if>
                </td>
                <td class="infoSource">
                  <#if metric.infoSource?has_content && (metric.infoSource?contains("http://") || metric.infoSource?contains("https://")) >
                   <a href="${metric.infoSource}" target="_blank"><span class="glyphicon glyphicon-info-sign"></span></a>
                  <#else> ${metric.infoSource!""}
                  </#if>
                </td>
              </tr>
              </#if>
            </#list>
          </tbody>
        </table>
      </div>
    </div>
    </#list>
  <#else>
  <div class="panel-login">
    <div class="missingGoals">
      <h3 id="login-h3"><@m'noGoals'/></h3>
    </div>
  </div>
  </#if>
</@html>


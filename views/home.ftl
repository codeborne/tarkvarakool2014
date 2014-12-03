  <@html>
  <#if goals?has_content>
    <#list goals as goal>
      <#list infoSourceContentList as metricInfosource>
        <#if goal_index == metricInfosource_index>
    <div class="panel panel-default goal panel-user">
      <div class="panel-heading">
        <form action="/charts">
          <input type="hidden" value="${goal.id?c}" name="goalId">
          <button class="chart-button" type="submit" class="btn btn-default btn-sm">
            <span class="glyphicon glyphicon-stats"></span><span><br>Graafik</span>
          </button>
        </form>
        <h4
          class="name"><#if language == 'et'>${goal.name}<#elseif language == 'en'><#if goal.engName??>${goal.engName}<#else>
        <i>${goal.name}</i></#if></#if></h4>

        <div><#if language == 'et'>${goal.comment!""}<#elseif language == 'en'><#if goal.engComment??>${goal.engComment}<#else>
        <i>${goal.comment!""}</i></#if></#if></div>
        <h4 class="budget"><@m'budget'/> ${goal.budget} €</h4>
      </div>
      <div class="panel-body">
        <table class="table userHomeTable">
          <thead>
          <tr>
            <th><@m'metric'/></th>
            <th><@m'publicDescription'/></th>
            <th><@m'startLevel'/></th>
            <th><@m'targetLevel'/><br>(2020)</th>
            <th><@m'infoSource'/></th>
          </tr>
          </thead>
          <tbody>
            <#list goal.metrics as metric>
              <#list metricInfosource as infosource>
                <#if metric_index == infosource_index>
                  <#if metric.isPublic == true>
                  <tr class="metric">
                    <td class="name">
                      <#if language == 'et'>${metric.name}
                      <#elseif language == 'en'>
                          <#if metric.engName??>${metric.engName}<#else><i>${metric.name}</i></#if>
                      </#if>
                    </td>
                    <td
                      class="userViewPublicDescription"><#if language == 'et'>${metric.publicDescription!""}<#elseif language == 'en'><#if metric.engPublicDescription??>${metric.engPublicDescription}<#else>
                    <i>${metric.publicDescription!""}</i></#if></#if></td>
                    <td class="startLevel">
                      <#if metric.startLevel??>${metric.startLevel}
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
                      <#if metric.targetLevel??>${metric.targetLevel}
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
                      <#list infosource as infoItem>
                        <#if (infoItem?contains("http://") || infoItem?contains("https://")) >
                          <span style="white-space: pre-wrap;"> <a href="${infoItem}" target="_blank"><span class="glyphicon glyphicon-link"></span></a></span>
                          <#else><span style="white-space: pre-wrap;">${infoItem}</span>
                        </#if>
                      </#list>
                    </td>
                  </tr>
                  </#if>
                </#if>
              </#list>
            </#list>
          </tbody>
        </table>
      </div>
    </div>
        </#if>
      </#list>
    </#list>
  <#else>
  <div class="panel">
    <div class="missingGoals">
      <h3 id="login-h3"><@m'noGoals'/></h3>
    </div>
  </div>
  </#if>
</@html>


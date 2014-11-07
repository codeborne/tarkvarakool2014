<@html>
<br><br>
  <#if goals?has_content>
    <#list goals as goal>
    <div class="panel panel-default goal">
      <div class="panel-heading">
        <h4 class="name"><#if language == 'et'>${goal.name}<#elseif language == 'en'>${goal.engName!""}</#if></h4>

        <div style="white-space: pre;"><#if language == 'et'>${goal.comment!""}<#elseif language == 'en'>${goal.engComment!""}</#if></div>
        <h4 class="budget"><@m'budget'/>${goal.budget?c} €</h4>
      </div>
      <div class="panel-body">
        <table class="table">
          <thead>
          <tr>
            <th><@m'metric'/></th>
            <th><@m'publicDescription'/></th>
            <th><@m'startLevel'/></th>
            <th><@m'startLevelComment'/></th>
            <th><@m'targetLevel'/></th>
            <th><@m'targetLevelComment'/></th>
            <th><@m'infoSource'/></th>
          </tr>
          </thead>
          <tbody>
            <#list goal.metrics as metric>
              <#if metric.isPublic == true>
              <tr class="metric">
                <td class="name">
                  <#if language == 'et'>${metric.name} <#if metric.unit?has_content>(${metric.unit})</#if>
                  <#elseif language == 'en'>${metric.engName!""} <#if metric.engUnit?has_content>(${metric.engUnit})</#if></#if>
                </td>

                <td class="publicDescription"><#if language == 'et'>${metric.publicDescription}<#elseif language == 'en'>${metric.engPublicDescription!""}</#if></td>
                <td class="startLevel">${metric.startLevel?c}</td>
                <td class="commantOnStartLevel">${metric.commentOnStartLevel}</td>
                <td class="targetLevel">${metric.targetLevel?c}</td>
                <td class="commentOnTargetLevel">${metric.commentOnTargetLevel}</td>
                <td class="infoSource"><#if metric.infoSource?has_content><a href="${metric.infoSource}" target="_blank">Link</a></#if></td>
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


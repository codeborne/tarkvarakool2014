<@html>
<br><br>
  <#if goals?has_content>
    <#list goals as goal>
    <div class="panel panel-default goal">
      <div class="panel-heading">
        <h4 class="name">${goal.name}</h4>

        <div style="white-space: pre;">${goal.comment!""}</div>
        <h4 class="budget"><@m'budget'/>${goal.budget?c} €</h4>
      </div>
      <div class="panel-body">
        <table class="table">
          <thead>
          <tr>
            <th><@m'metric'/></th>
            <th><@m'publicDescription'/></th>
            <th><@m'startLevel'/></th>
            <th><@m'startLevelComment'/>r</th>
            <th><@m'targetLevel'/></th>
            <th><@m'targetLevelComment'/></th>
            <th><@m'infoSource'/></th>
          </tr>
          </thead>
          <tbody>
            <#list goal.metrics as metric>
              <#if metric.isPublic == true>
              <tr class="metric">
                <td class="name">${metric.name} <#if metric.unit?has_content>(${metric.unit})</#if></td>
                <td class="publicDescription">${metric.publicDescription}</td>
                <td class="startLevel">${metric.startLevel?c}</td>
                <td class="commantOnStartLevel">${metric.commentOnStartLevel}</td>
                <td class="targetLevel">${metric.targetLevel?c}</td>
                <td class="commentOnTargetLevel">${metric.commentOnTargetLevel}</td>
                <td class="infoSource">${metric.infoSource}</td>
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


<#--<div class="languageButtons btn-group button-menu-inner">-->


  <#--<a href="/language?locale=en" class="language-button-eng">ENG</a>-->


  <#--<a href="/language?locale=et" class="language-button-est">EST</a>-->
<#--</div>-->
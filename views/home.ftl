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
              class="name line-break"><#if language == 'et'>${goal.name}<#elseif language == 'en'><#if goal.engName??>${goal.engName}<#else>
              <i>${goal.name}</i></#if></#if></h4>

            <div
              class="line-break"><#if language == 'et'>${goal.comment!""}<#elseif language == 'en'><#if goal.engComment??>${goal.engComment}<#else>
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
                <th><@m'targetLevel'/>&nbsp;(2020)</th>
                <th><@m'infoSource'/></th>
                <th><@m'results'/></th>
              </tr>
              </thead>
              <tbody class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                <#list goal.metrics as metric>
                  <#list metricInfosource as infosource>
                    <#if metric_index == infosource_index>
                      <#if metric.isPublic == true>
                      <tr class="metric" role="tab" id="heading_${goal_index}_${metric_index}">
                        <td
                          class="name line-break"><#if language == 'et'>${metric.name}<#elseif language == 'en'><#if metric.engName??>${metric.engName}<#else>
                          <i>${metric.name}</i></#if></#if></td>
                        <td
                          class="userViewPublicDescription line-break"><#if language == 'et'>${metric.publicDescription!""}<#elseif language == 'en'><#if metric.engPublicDescription??>${metric.engPublicDescription}<#else>
                          <i>${metric.publicDescription!""}</i></#if></#if></td>
                        <td class="startLevel">
                          <#if metric.startLevel??>${metric.startLevel}
                            <#if language == 'et'>
                              <#if metric.unit?has_content>${metric.unit}</#if>
                              <br>
                              <#if metric.commentOnStartLevel?has_content> (${metric.commentOnStartLevel})</#if>
                            <#elseif language == 'en'>
                              <#if metric.engUnit?has_content>${metric.engUnit}<#else><i>${metric.unit!""}</i></#if>
                              <br>
                              <#if metric.engStartLevelComment?has_content> (${metric.engStartLevelComment})
                              <#elseif metric.commentOnStartLevel?has_content><i>(${metric.commentOnStartLevel}
                                )</i></#if>
                            </#if>
                          <#elseif language == 'et' && metric.commentOnStartLevel?has_content>${metric.commentOnStartLevel}
                          <#elseif language == 'en'>
                            <#if metric.engStartLevelComment?has_content>${metric.engStartLevelComment}
                            <#else><i>(${metric.commentOnStartLevel})</i>
                            </#if>
                          <#else>N/A</#if>
                        </td>
                        <td class="targetLevel">
                          <#if metric.targetLevel??>${metric.targetLevel}
                            <#if language == 'et'>
                              <#if metric.unit?has_content>${metric.unit}</#if>
                              <br>
                              <#if metric.commentOnTargetLevel?has_content> (${metric.commentOnTargetLevel})</#if>
                            <#elseif language == 'en'>
                              <#if metric.engUnit?has_content>${metric.engUnit}<#else><i>${metric.unit!""}</i></#if>
                              <br>
                              <#if metric.engTargetLevelComment?has_content> (${metric.engTargetLevelComment})
                              <#elseif metric.commentOnTargetLevel?has_content><i>(${metric.commentOnTargetLevel}
                                )</i></#if>
                            </#if>
                          <#elseif language == 'et' && metric.commentOnTargetLevel?has_content>${metric.commentOnTargetLevel}
                          <#elseif language == 'en'>
                            <#if metric.engTargetLevelComment?has_content>${metric.engTargetLevelComment}
                            <#else><i>(${metric.commentOnTargetLevel})</i>
                            </#if>
                          <#else>N/A</#if>
                        </td>
                        <td class="infoSource">
                          <#list infosource as infoItem>
                            <#if (infoItem?contains("http://") || infoItem?contains("https://")) >
                              <span class="line-break"> <a href="${infoItem}" target="_blank"><span
                                class="glyphicon glyphicon-new-window"></span></a>&nbsp;</span>
                            <#else><span class="line-break">${infoItem}&nbsp;</span>
                            </#if>
                          </#list>
                        </td>
                        <td>

                          <a data-toggle="collapse" class="collapsed" data-parent="#accordion"
                             href="#collapse_${goal_index}_${metric_index}" aria-expanded="false"
                             aria-controls="collapse_${goal_index}_${metric_index}"><#if !isMetricPerformancePositive(metric)??>

                          <#elseif isMetricPerformancePositive(metric)><span
                            class="glyphicon glyphicon-thumbs-up greenValue"></span>
                          <#else><span class="glyphicon glyphicon-thumbs-down redValue"></span>
                          </#if></a>

                          <div class="action-button">
                            <form action="/metrics/charts">
                              <input type="hidden" value="${metric.id?c}" name="metricId">
                              <button class="small-chart-button" type="submit" class="btn btn-default btn-sm" title="<@m'chart'/>">
                                <span class="glyphicon glyphicon-stats"></span>
                              </button>
                            </form>
                          </div>

                        </td>
                      </tr>

                      <tr id="collapse_${goal_index}_${metric_index}" class="panel-collapse collapse" role="tabpanel"
                          aria-labelledby="heading_${goal_index}_${metric_index}">
                        <td colspan="6">
                          <table class="">
                            <tablehead>
                              <#list minimumYear..maximumYear as year>
                                <th>${year?c}</th>
                              </#list>
                            </tablehead>
                            <tbody>
                            <tr>
                              <#list minimumYear..maximumYear as year>
                                <td class="values">
                                  <span>
                                    <#if (metric.values.get(year)?c)?has_content>${((metric.values.get(year)))}
                                    <#elseif (currentYear>year)>N/A
                                    <#else></#if>
                                  </span>
                                </td>
                              </#list>
                            </tr>
                            </tbody>
                          </table>

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


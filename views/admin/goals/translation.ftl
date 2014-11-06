<@html>

<br>
<br>

<div class="panel panel-default goal">
  <form method="post">
  <div class="panel-heading">
    <h4 class="name">${goal.name!""}</h4>
    <textarea name="engName" rows="5" placeholder="<@m'translateGoal'/>">${goal.engName!""}</textarea>
    <div style="white-space: pre;">${goal.comment!""}</div>
    <textarea name="engComment" rows="5" placeholder="<@m'translateComment'/>">${goal.engComment!""}</textarea>
  </div>

<table class="table table-hover">
  <tr>
    <th><@m'metric'/></th>
    <th><@m'translateMetric'/></th>
    <th><@m'unit'/></th>
    <th><@m'translateUnit'/></th>
    <th><@m'publicDescription'/></th>
    <th><@m'translatePublicDescription'/></th>
  </tr>
  <tbody>
    <#list goal.metrics as metric>
      <#--<#if metric.isPublic == true>-->
      <tr class="metric">

        <td class="name">${metric.name} <input type="hidden" value="${metric.id}"></td>
        <td><textarea name="engMetricName" rows="2" placeholder="<@m'translateMetric'/>">${metric.engName!""}</textarea></td>
        <td class="unit">${metric.unit!""}</td>
        <td><textarea name="engUnit" rows="2" placeholder="<@m'translateUnit'/>">${metric.engUnit!""}</textarea></td>
        <td class="publicDescription">${metric.publicDescription!""}</td>
        <td><textarea name="engPublicDescription" rows="2" placeholder="<@m'translatePublicDescription'/>">${metric.engPublicDescription!""}</textarea></td>
      </tr>
      <#--</#if>-->
    </#list>
  <tr>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td> <input type="submit" value="<@m'save'/>" class="saveGoalButton value btn btn-default btn-sm"></td>
  </tr>
  </tbody>
</table>
  </form>
  <span id="errors"></span>
</div>
</@html>
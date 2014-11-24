<@html>
<div class="panel panel-default goal">
  <div class="panel-heading">
    <h4 class="translationHeading"><@m'translationHeading'/></h4>
  </div>
  <div class="panel-body">
  <form method="post">
    <table class="table" id="translationTable">
      <thead>
      <tr>
        <th></th>
        <th><@m'estonian'/></th>
        <th><@m'english'/></th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td><@m'goal'/></td>
        <td class="name">${goal.name!""}</td>
        <td><textarea name="engName" rows="2" maxlength="255" placeholder="<@m'translateGoal'/>">${goal.engName!""}</textarea></td>
      </tr>
      <tr>
        <td><@m'comment'/></td>
        <td class="comment">${goal.comment!""}</td>
        <td><textarea name="engComment" rows="2" maxlength="255" placeholder="<@m'translateComment'/>">${goal.engComment!""}</textarea></td>
      </tr>
      </tbody>
      <#list goal.metrics as metric>
        <#if metric.isPublic == true>
        <tbody>
        <tr>
          <td><@m'metric'/></td>
          <td class="metric-name">${metric.name}</td>
          <td> <textarea name="engMetricName" maxlength="255" rows="2" placeholder="<@m'translateMetric'/>">${metric.engName!""}</textarea></td>
        </tr>
        <tr>
          <td><@m'unit'/></td>
          <td class="unit">${metric.unit!""}</td>
          <td><textarea name="engUnit" maxlength="255" rows="2" placeholder="<@m'translateUnit'/>">${metric.engUnit!""}</textarea></td>
        </tr>
        <tr>
          <td><@m'publicDescription'/></td>
          <td class="publicDescription">${metric.publicDescription!""}</td>
          <td><textarea name="engPublicDescription" maxlength="255" rows="2" placeholder="<@m'translatePublicDescription'/>">${metric.engPublicDescription!""}</textarea></td>
        </tr>
        <tr>
          <td><@m'infoSource'/></td>
          <td class="infoSource">${metric.infoSource!""}</td>
          <td><textarea name="engInfoSource" maxlength="255" rows="2" placeholder="<@m'translateInfoSource'/>">${metric.engInfoSource!""}</textarea></td>
        </tr>
        </tbody>
        </#if>
      </#list>
  </table>
    <input type="submit" value="<@m'save'/>" class="saveGoalButton btn btn-default btn-sm" id="saveTranslationButton" onclick="/">
  </form>
  <span id="errors"></span>
</div>
</div>
</@html>
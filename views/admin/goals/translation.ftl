<@html>

<br>
<br>

<div class="panel panel-default goal">

  <div class="panel-heading">
    <h4 class="translationHeading"><@m'translationHeading'/></h4>
  </div>
  <div class="panel-body"
  <form method="post">
    <table class="table table-hover">
      <thead>
      <tr>
        <th><@m'goal'/></th>
        <th><@m'translateGoal'/></th>
        <th><@m'comment'/></th>
        <th><@m'translateComment'/></th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td class="name">${goal.name!""}</td>
        <td><textarea name="engName" rows="3" placeholder="<@m'translateGoal'/>">${goal.engName!""}</textarea></td>
        <td>${goal.comment!""}</td>
        <td><textarea name="engComment" rows="3" placeholder="<@m'translateComment'/>">${goal.engComment!""}</textarea>
        </td>
      </tr>
      </tbody>
    </table>


    <table class="table table-hover">
      <thead>
      <tr>
        <th><@m'metric'/></th>
        <th><@m'translateMetric'/></th>
        <th><@m'unit'/></th>
        <th><@m'translateUnit'/></th>
        <th><@m'publicDescription'/></th>
        <th><@m'translatePublicDescription'/></th>
      </tr>
      </thead>
      <tbody>
        <#list goal.metrics as metric>
          <#if metric.isPublic == true>
          <tr class="metric">

            <td class="name">${metric.name} <input type="hidden" value="${metric.id}"></td>
            <td><textarea name="engMetricName" rows="2"
                          placeholder="<@m'translateMetric'/>">${metric.engName!""}</textarea></td>
            <td class="unit">${metric.unit!""}</td>
            <td><textarea name="engUnit" rows="2" placeholder="<@m'translateUnit'/>">${metric.engUnit!""}</textarea>
            </td>
            <td class="publicDescription">${metric.publicDescription!""}</td>
            <td><textarea name="engPublicDescription" rows="2"
                          placeholder="<@m'translatePublicDescription'/>">${metric.engPublicDescription!""}</textarea>
            </td>
          </tr>
          </#if>
        </#list>
      <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td>    </td>
        <td><input type="submit" value="<@m'save'/>" class="blueButton saveGoalButton value btn btn-default btn-sm"></td>
      </tr>
      </tbody>
    </table>
  </form>
  <span id="errors"></span>
  <button type="submit" class="blueButton goBackButton btn btn-default btn-sm" onclick="location='/admin/goals'"><span
    class="enter"><@m'goToMainPage'/></span></button>

</div>
</div>
</@html>
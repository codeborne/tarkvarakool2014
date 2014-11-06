<@html>
<script>

  function sendData(goalId) {
    var hasErrors=false;
    $('textarea').each( function( index, element ){
      inputValue = $(this).val();
      metricId = $(this).parent().parent().children('td.name').children('input').val();

      if($(this).attr('name') === 'engMetricName'){
        $.post("/admin/goals/modify", {metricId: metricId, value: inputValue, field: "engMetricName"},
          function errorCheck(text) {
            var errorsList = JSON.parse(text.replace(/&quot;/g, '"'));
            if (errorsList.length > 0) {
              hasErrors = true;
            }
          }
        );
      }
      else if($(this).attr('name') == 'engUnit'){
        $.post("/admin/goals/modify", {metricId: metricId, value: inputValue, field: "engUnit"},
          function errorCheck(text) {
            var errorsList = JSON.parse(text.replace(/&quot;/g, '"'));
            if (errorsList.length > 0) {
              hasErrors = true;
            }
          }
        );
      }
      else if($(this).attr('name') == 'engPublicDescription'){
        $.post("/admin/goals/modify", {metricId: metricId, field: "engPublicDescription", value: inputValue},
          function errorCheck(text) {
            var errorsList = JSON.parse(text.replace(/&quot;/g, '"'));
            if (errorsList.length > 0) {
              hasErrors = true;
            }
          }
        );
      }
      else if($(this).attr('name') == 'engName'){
        $.post("/admin/goals/modify", {goalId: goalId, field:"engName", value: inputValue},
          function errorCheck(text) {
            var errorsList = JSON.parse(text.replace(/&quot;/g, '"'));
            if (errorsList.length > 0) {
              hasErrors = true;
            }
          }
      );
      }
      else if($(this).attr('name') == 'engComment'){
        $.post("/admin/goals/modify", {goalId: goalId, field:"engComment", value:inputValue},
          function errorCheck(text) {
          var errorsList = JSON.parse(text.replace(/&quot;/g, '"'));
          if (errorsList.length > 0) {
            hasErrors = true;
          }
        });
      }
      }
       );
      if(hasErrors){
        alert(errorsList.join("\n"));
      }
    window.location = window.location;

  }



</script>
<br>
<br>

<div class="panel panel-default goal">
  <form method="post">
  <div class="panel-heading">
    <h4 class="name">${goal.name}</h4>
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
      <#if metric.isPublic == true>
      <tr class="metric">

        <td class="name">${metric.name} <input type="hidden" value="${metric.id}"></td>
        <#--<td>${metric.engName!""}</td>-->
        <td><textarea name="engMetricName" rows="2" placeholder="<@m'translateMetric'/>">${metric.engName!""}</textarea></td>
        <td class="unit">${metric.unit!""}</td>
        <td><textarea name="engUnit" rows="2" placeholder="<@m'translateUnit'/>">${metric.engUnit!""}</textarea></td>
        <td class="publicDescription">${metric.publicDescription!""}</td>
        <td><textarea name="engPublicDescription" rows="2" placeholder="<@m'translatePublicDescription'/>">${metric.engPublicDescription!""}</textarea></td>
      </tr>
      </#if>
    </#list>
  <tr>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td>

        <input type="button" value="<@m'save'/>" class="saveGoalButton value btn btn-default btn-sm" onclick="sendData(${goal.id?c}); return false;">

    </td>
  </tr>
  </tbody>
</table>
  </form>
</div>
</@html>
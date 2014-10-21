<@html>

<form method="get">
<input type="hidden" value="${goal.id}" name="goalID">
</form>
<h3>${goal.name}</h3>
<table class="table table-hover">
  <tr>
    <th>Mõõdik</th>
    <th>Avalik kirjeldus</th>
    <th>Mitteavalik kirjeldus</th>
    <th>Algtase</th>
    <th>Algtaseme kommentaar</th>
    <th>Sihttase</th>
    <th>Sihttaseme kommentaar</th>
    <th>Infoallikas</th>
    <th>Asutus, kuhu raporteerida</th>
  </tr>

  <#list goal.metrics as metric>
    <tr>
      <td id="metricName">${metric.name}</td>
      <td id="metricPublicDescription">${metric.publicDescription}</td>
      <td id="metricPrivateDescription">${metric.privateDescription}</td>
      <td id="metricStartLevel">${metric.startLevel}</td>
      <td id="metricCommantOnStartLevel">${metric.commentOnStartLevel}</td>
      <td id="metricTargetLevel">${metric.targetLevel}</td>
      <td id="metricCommentOnTargetLevel">${metric.commentOnTargetLevel}</td>
      <td id="metrivInfoSource">${metric.infoSource}</td>
      <td id="metricInstitutionToReport">${metric.institutionToReport}</td>
      </tr>
  </#list>
  </table>

<button type="submit" id="goBackButton" class="btn btn-default btn-sm" onclick="location='/admin/goals'">
  Pealehele
</button>
<button type="submit" id="addMetricButton" class="btn btn-default btn-sm" onclick="location='/admin/metrics/add?goalId=${goal.id}'">
  Lisa mõõdik
</button>

</@html>
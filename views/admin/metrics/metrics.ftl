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
    <th>Muuda</th>
    <th>Kustuta</th>
  </tr>

  <#list goal.metrics as metric>
    <tr class="metric">
      <td class="name">${metric.name}</td>
      <td class="publicDescription">${metric.publicDescription}</td>
      <td class="privateDescription">${metric.privateDescription}</td>
      <td class="startLevel">${metric.startLevel?c}</td>
      <td class="commentOnStartLevel">${metric.commentOnStartLevel}</td>
      <td class="targetLevel">${metric.targetLevel?c}</td>
      <td class="commentOnTargetLevel">${metric.commentOnTargetLevel}</td>
      <td class="infoSource">${metric.infoSource}</td>
      <td class="institutionToReport">${metric.institutionToReport}</td>
      <td>
        <form action="modify">
          <input type="hidden" value="${goal.id}" name="goalId">
          <input type="hidden" name="metricId" value="${metric.id}"/>
          <button class="modifyButton" type="submit" class="btn btn-default btn-sm">
            <span class="glyphicon glyphicon-pencil"></span>
          </button>
        </form>
      </td>
      <td>
        <form action="delete" method="post" onsubmit="return confirm('Kas oled kustutamises kindel?')">
          <input type="hidden" value="${goal.id}" name="goalId">
          <input type="hidden" name="id" value="${metric.id}"/>
          <button class="deleteButton" type="submit" class="btn btn-default btn-sm">
            <span class="glyphicon glyphicon-trash"></span></button>
        </form>
      </td>
    </tr>
  </#list>
</table>

<button type="submit" id="goBackButton" class="btn btn-default btn-sm" onclick="location='/admin/goals'">
  Pealehele
</button>
<form action="add">
  <input type="hidden" value="${goal.id}" name="goalId">
<button type="submit" id="addMetricButton" class="btn btn-default btn-sm">
        <#--onclick="location='/admin/metrics/add?goalId=${goal.id}'">-->
  Lisa mõõdik
</button>
</form>

</@html>
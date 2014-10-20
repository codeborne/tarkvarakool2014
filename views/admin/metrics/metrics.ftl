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
      <td>${metric.name}</td>
      <td>${metric.publicDescription}</td>
      <td>${metric.privateDescription}</td>
      <td>${metric.startLevel}</td>
      <td>${metric.commentOnStartLevel}</td>
      <td>${metric.targetLevel}</td>
      <td>${metric.commentOnTargetLevel}</td>
      <td>${metric.infoSource}</td>
      <td>${metric.institutionToReport}</td>
      </tr>
  </#list>
  </table>

<form action="/admin/goals">
  <button type="submit" class="btn btn-default btn-sm">
    Pealehele
  </button>
</form>

</@html>
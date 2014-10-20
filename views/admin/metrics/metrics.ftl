<@html>
<#--<input type="hidden" value="${goal.id}" name="goalId">-->
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

  <#list metrics as metric>
    <tr>
      <td>${metric.name}</td>
      <td>${metric.publicDescription}</td>
      <td>${metric.privateDescription}</td>
      <td>${metric.startLevel}</td>
      <td>${metric.startLevelComment}</td>
      <td>${metric.targetLevel}</td>
      <td>${metric.targetLevelComment}</td>
      <td>${metric.infoSource}</td>
      <td>${metric.institutionToReport}</td>
      </tr>
  </#list>
  </table>

</@html>
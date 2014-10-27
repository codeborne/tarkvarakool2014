<@html>

<form method="get">
  <input type="hidden" value="${goal.id?c}" name="goalID">
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
    <th>Järjekorra number</th>
    <th>Muuda</th>
    <th>Kustuta</th>
  </tr>

  <#list goal.metrics as metric>
    <tr class="metric">
      <td class="name">${metric.name} <#if metric.unit?has_content>(${metric.unit})</#if></td>
      <td class="publicDescription">${metric.publicDescription}</td>
      <td class="privateDescription">${metric.privateDescription}</td>
      <td class="startLevel">${metric.startLevel?c}</td>
      <td class="commentOnStartLevel">${metric.commentOnStartLevel}</td>
      <td class="targetLevel">${metric.targetLevel?c}</td>
      <td class="commentOnTargetLevel">${metric.commentOnTargetLevel}</td>
      <td class="infoSource">${metric.infoSource}</td>
      <td class="institutionToReport">${metric.institutionToReport}</td>
      <td class="orderNumber">
        <form method="post" action="/admin/metrics/modify?goalId=${goal.id}&metricId=${metric.id}">
          <input name="orderNumber" value="${metric.orderNumber}" maxlength="7" class="order-number">
          <input type="hidden" name="name" value="${metric.name}">
          <input type="hidden" name="publicDescription" value="${metric.publicDescription}">
          <input type="hidden" name="privateDescription" value="${metric.privateDescription}">
          <input type="hidden" name="startLevel" value="${metric.startLevel}">
          <input type="hidden" name="commentOnStartLevel" value="${metric.commentOnStartLevel}">
          <input type="hidden" name="targetLevel" value="${metric.targetLevel}">
          <input type="hidden" name="commentOnTargetLevel" value="${metric.commentOnTargetLevel}">
          <input type="hidden" name="infoSource" value="${metric.infoSource}">
          <input type="hidden" name="institutionToReport" value="${metric.institutionToReport}">
          <input type="hidden" name="unit" value="${metric.unit}">
        </form>
      </td>
      <td>
        <form action="modify">
          <input type="hidden" value="${goal.id?c}" name="goalId">
          <input type="hidden" name="metricId" value="${metric.id?c}"/>
          <button class="modifyButton" type="submit" class="btn btn-default btn-sm">
            <span class="glyphicon glyphicon-pencil"></span>
          </button>
        </form>
      </td>
      <td>
        <form action="delete" method="post" onsubmit="return confirm('Kas oled kustutamises kindel?')">
          <input type="hidden" value="${goal.id?c}" name="goalId">
          <input type="hidden" name="id" value="${metric.id?c}"/>
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
  <input type="hidden" value="${goal.id?c}" name="goalId">
<button type="submit" id="addMetricButton" class="btn btn-default btn-sm">
  Lisa mõõdik
</button>
</form>

</@html>
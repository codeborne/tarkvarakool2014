<@html>

<form method="get">
  <input type="hidden" value="${goal.id?c}" name="goalID">
</form>
<h3>${goal.name}</h3>
<table class="table table-hover">
  <thead>
    <tr>
      <th>Sorteeri</th>
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
  </thead>
  <tbody id="sortable">
    <#list goal.metrics as metric>
      <tr class="metric">
        <td class="sort">
          <span class="glyphicon glyphicon-sort hand-pointer"></span>
          <form class="orderNumberForm" goalId="post" action="/admin/metrics/modify">
            <input type="hidden" name="goalId" value="${goal.id?c}">
            <input type="hidden" name="metricId" value="${metric.id?c}">
            <input type="hidden" name="orderNumber" value="${metric.orderNumber?c}">
            <input type="hidden" name="name" value="${metric.name}">
            <input type="hidden" name="publicDescription" value="${metric.publicDescription}">
            <input type="hidden" name="privateDescription" value="${metric.privateDescription}">
            <input type="hidden" name="startLevel" value="${metric.startLevel?c}">
            <input type="hidden" name="commentOnStartLevel" value="${metric.commentOnStartLevel}">
            <input type="hidden" name="targetLevel" value="${metric.targetLevel?c}">
            <input type="hidden" name="commentOnTargetLevel" value="${metric.commentOnTargetLevel}">
            <input type="hidden" name="infoSource" value="${metric.infoSource}">
            <input type="hidden" name="institutionToReport" value="${metric.institutionToReport}">
            <input type="hidden" name="unit" value="${metric.unit}">
          </form>
        </td>
        <td class="name">${metric.name} <#if metric.unit?has_content>(${metric.unit})</#if></td>
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
  </tbody>
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

<script>
  $(function() {
    $( "#sortable" ).sortable({
      placeholder: "ui-state-highlight",
      handle:'.glyphicon-sort',
      cursor: "move",
      axis: "y",
      update: function( event, ui ) {
        var orderBeforeInput = ui.item.prev().find("[name=orderNumber]");
        var orderAfterInput = ui.item.next().find("[name=orderNumber]");

        if (orderBeforeInput.length == 0)
          var orderBefore = parseFloat(orderAfterInput.val()) - 2;
        else
          var orderBefore = parseFloat(orderBeforeInput.val());

        if (orderAfterInput.length == 0)
          var orderAfter = parseFloat(orderBeforeInput.val()) + 2;
        else
          var orderAfter = parseFloat(orderAfterInput.val());

        ui.item.find("[name=orderNumber]").val(orderBefore + (orderAfter - orderBefore) / 2);

        $.ajax({
          type: "POST",
          url: "/admin/metrics/modify",
          data: ui.item.find(".orderNumberForm").serialize(),
          error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("Tekkis viga");
          }
        });
      }
    });
  });
</script>

</@html>
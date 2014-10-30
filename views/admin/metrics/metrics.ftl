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
      <th>Ühik</th>
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
            <input type="hidden" name="unit" value="${metric.unit}">
            <input type="hidden" name="publicDescription" value="${metric.publicDescription}">
            <input type="hidden" name="privateDescription" value="${metric.privateDescription}">
            <input type="hidden" name="startLevel" value="${metric.startLevel?c}">
            <input type="hidden" name="commentOnStartLevel" value="${metric.commentOnStartLevel}">
            <input type="hidden" name="targetLevel" value="${metric.targetLevel?c}">
            <input type="hidden" name="commentOnTargetLevel" value="${metric.commentOnTargetLevel}">
            <input type="hidden" name="infoSource" value="${metric.infoSource}">
            <input type="hidden" name="institutionToReport" value="${metric.institutionToReport}">
          </form>
        </td>


        <td class="name">
          <span class="value">${metric.name}</span>
          <input class="value form-control" name="name" value="${metric.name}" style="display: none;">
        </td>
        <td class="unit">
          <span class="value">${metric.unit}</span>
          <input class="value form-control" name="unit" value="${metric.unit}" style="display: none;">
        </td>
        <td class="publicDescription">
          <span class="value">${metric.publicDescription}</span>
          <input class="value form-control" name="publicDescription" value="${metric.publicDescription}" style="display: none;">
        </td>
        <td class="privateDescription">
          <span class="value">${metric.privateDescription}</span>
          <input class="value form-control" name="privateDescription" value="${metric.privateDescription}" style="display: none;">
        </td>
        <td class="startLevel">
          <span class="value">${metric.startLevel}</span>
          <input class="value form-control" name="startLevel" value="${metric.startLevel}" style="display: none;">
        </td>
        <td class="commentOnStartLevel">
          <span class="value">${metric.commentOnStartLevel}</span>
          <input class="value form-control" name="commentOnStartLevel" value="${metric.commentOnStartLevel}" style="display: none;">
        </td>
        <td class="targetLevel">
          <span class="value">${metric.targetLevel}</span>
          <input class="value form-control" name="targetLevel" value="${metric.targetLevel}" style="display: none;">
        </td>
        <td class="commentOnTargetLevel">
          <span class="value">${metric.commentOnTargetLevel}</span>
          <input class="value form-control" name="commentOnTargetLevel" value="${metric.commentOnTargetLevel}" style="display: none;">
        </td>
        <td class="infoSource">
          <span class="value">${metric.infoSource}</span>
          <input class="value form-control" name="infoSource" value="${metric.infoSource}" style="display: none;">
        </td>
        <td class="institutionToReport">
          <span class="value">${metric.institutionToReport}</span>
          <input class="value form-control" name="institutionToReport" value="${metric.institutionToReport}" style="display: none;">
        </td>

        <td>

          <input type="hidden" class="value" value="${metric.orderNumber?c}" name="orderNumber">
          <input type="hidden" class="value" value="${goal.id?c}" name="goalId">
          <input type="hidden" class="value" name="metricId" value="${metric.id?c}"/>
          <input type="button" class="saveGoalButton value btn btn-default btn-sm" value="Salvesta"
                 style="display: none" data-action="modify">
          <input type="button" class="cancelGoalButton value btn btn-default btn-sm"
                 onclick="location='metrics?goalId=${goal.id?c}'; return false;" value="Tühista" style="display:none"
                 data-action="modify">
          <span class="value">
            <button class="modifyButton" type="button" class="btn btn-default btn-sm">
              <span class="glyphicon glyphicon-pencil"></span>
            </button>
          </span>



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

  <tr class="addMetric">
    <td></td>
    <td><input name="name" class="value form-control" placeholder="Mõõdik" value="${name!""}"></td>
    <td><input name="unit" class="value form-control" placeholder="Ühik" value="${unit!""}"></td>
    <td><input name="publicDescription" class="value form-control" placeholder="Avalik kirjeldus" value="${publicDescription!""}"></td>
    <td><input name="privateDescription" class="value form-control" placeholder="Mitteavalik kirjeldus" value="${privateDescription!""}"></td>
    <td><input name="startLevel" type="number" class="value form-control" placeholder="Algtase" value="${(startLevel?c)!0}"></td>
    <td><input name="commentOnStartLevel" class="value form-control" placeholder="Algtaseme kommentaar" value="${commentOnStartLevel!""}"></td>
    <td><input name="targetLevel" type="number" class="value form-control" placeholder="Sihttase" value="${(targetLevel?c)!0}"></td>
    <td><input name="commentOnTargetLevel" class="value form-control" placeholder="Sihttaseme kommentaar" value="${commentOnTargetLevel!""}"></td>
    <td><input name="infoSource" class="value form-control" placeholder="Infoallikas" value="${infoSource!""}"></td>
    <td><input name="institutionToReport" class="value form-control" placeholder="Asutus, kuhu raporteerida" value="${institutionToReport!""}"></td>
    <td>
      <input type="button" class="saveGoalButton value btn btn-default btn-sm" value="Lisa" data-action="add">
      <input type="hidden" class="value" value="${goal.id?c}" name="goalId">
    </td>
  </tr>
  </tbody>
</table>

<span id="errors"></span>

<button type="submit" id="goBackButton" class="btn btn-default btn-sm" onclick="location='/admin/goals'">Pealehele</button>


<script>
  $(function () {

    var responseHandler = function (response) {
      if (response.trim() == "") {
        window.location = window.location;
      }
      $("#errors").html(response);
    };

    var saveClickHandler = function (event) {
      var button = $(event.target);
      var values = button.closest('tr').find('input.value');
      $.post(button.data("action"), values.serialize(), responseHandler);
    };

    $('.saveGoalButton').click(saveClickHandler);

    var modifyClickHandler = function (event) {
      $("input.value").hide();
      $("span.value").show();
      var row = $(event.target).closest('tr');
      row.find("span.value").hide();
      row.find("input.value").show();
    };

    $('.modifyButton').click(modifyClickHandler);
  });
</script>

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
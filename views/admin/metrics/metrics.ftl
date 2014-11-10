<@html>
<br><br>
<form method="get">
  <input type="hidden" value="${goal.id?c}" name="goalID">
</form>
<div class="panel panel-default">
  <div class="panel-heading">
    <h4 class="headingName">${goal.name}</h4>
  </div>
  <div class="panel-body">
    <table class="table table-hover">
      <thead>
      <tr>
        <th><@m'sort'/></th>
        <th><@m'metric'/></th>
        <th><@m'unit'/></th>
        <th><@m'publicDescription'/></th>
        <th><@m'privateDescription'/></th>
        <th><@m'startLevel'/></th>
        <th><@m'startLevelComment'/></th>
        <th><@m'targetLevel'/></th>
        <th><@m'targetLevelComment'/></th>
        <th><@m'infoSource'/></th>
        <th><@m'institutionReport'/></th>
        <th><@m'public'/></th>
        <th><@m'actions'/></th>
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
          <input type="hidden" name="startLevel" <#if metric.startLevel??>value="${(metric.startLevel?c)}"</#if>>
          <input type="hidden" name="commentOnStartLevel" value="${metric.commentOnStartLevel}">
          <input type="hidden" name="targetLevel" <#if metric.targetLevel??>value="${(metric.targetLevel?c)}"</#if>>
          <input type="hidden" name="commentOnTargetLevel" value="${metric.commentOnTargetLevel}">
          <input type="hidden" name="infoSource" value="${metric.infoSource}">
          <input type="hidden" name="institutionToReport" value="${metric.institutionToReport}">
          <input type="hidden" name="isPublic" value="${metric.isPublic?c}">
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
        <input class="value form-control" name="publicDescription" value="${metric.publicDescription}"
               style="display: none;">
      </td>
      <td class="privateDescription">
        <span class="value">${metric.privateDescription}</span>
        <input class="value form-control" name="privateDescription" value="${metric.privateDescription}"
               style="display: none;">
      </td>
      <td class="startLevel">
        <span class="value"><#if metric.startLevel??>${metric.startLevel?c}<#else>N/A</#if></span>
        <input class="value form-control" name="startLevel" <#if metric.startLevel??>value="${(metric.startLevel?c)}"</#if> style="display: none;">
      </td>
      <td class="commentOnStartLevel">
        <span class="value">${metric.commentOnStartLevel}</span>
        <input class="value form-control" name="commentOnStartLevel" value="${metric.commentOnStartLevel}"
               style="display: none;">
      </td>
      <td class="targetLevel">
        <span class="value"><#if metric.targetLevel??>${metric.targetLevel?c}<#else>N/A</#if></span>
        <input class="value form-control" name="targetLevel" <#if metric.targetLevel??>value="${(metric.targetLevel?c)}"</#if> style="display: none;">
      </td>
      <td class="commentOnTargetLevel">
        <span class="value">${metric.commentOnTargetLevel}</span>
        <input class="value form-control" name="commentOnTargetLevel" value="${metric.commentOnTargetLevel}"
               style="display: none;">
      </td>
      <td class="infoSource">
        <span class="value"><#if metric.infoSource?has_content><a href="${metric.infoSource}" target="_blank"><span class="glyphicon glyphicon-info-sign"></span></a></#if></span>
        <input class="value form-control" name="infoSource" value="${metric.infoSource}" style="display: none;">
      </td>
      <td class="institutionToReport">
        <span class="value">${metric.institutionToReport}</span>
        <input class="value form-control" name="institutionToReport" value="${metric.institutionToReport}"
               style="display: none;">
      </td>
      <td class="isPublic">
        <span class="value"> <#if metric.isPublic?? && metric.isPublic == true><@m 'public'/><#else><@m'private'/></#if></span>
        <input class="value" type="checkbox" name="isPublic" value=true <#if metric.isPublic?? && metric.isPublic == true> checked </#if> style="display: none;">
      </td>
      <td class="actions">
        <input type="hidden" class="value" value="${metric.orderNumber?c}" name="orderNumber">
        <input type="hidden" class="value" value="${goal.id?c}" name="goalId">
        <input type="hidden" class="value" name="metricId" value="${metric.id?c}"/>

        <div class="action-button">
          <input type="hidden" class="value" value="${metric.id?c}" name="id">
        <input type="button" class="saveGoalButton value btn btn-default btn-sm" value=""
               style="display: none" data-action="save">
        <input type="button" class="cancelGoalButton value btn btn-default btn-sm"
               onclick="location='metrics?goalId=${goal.id?c}'; return false;" value="" style="display:none"
               data-action="save"></div>
          <div class="action-button">
          <span class="value">
            <button class="modifyButton" type="button" class="btn btn-default btn-sm">
              <span class="glyphicon glyphicon-pencil"></span>
            </button>
          </span>
            </div>
        <form action="delete" method="post" onsubmit="return confirm('<@m'errorDeletingConfirmation'/>')">
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
    <td><input name="name" class="value form-control" placeholder="<@m'metric'/>" value="${name!""}"></td>
    <td><input name="unit" class="value form-control" placeholder="<@m'unit'/>" value="${unit!""}"></td>
    <td><input name="publicDescription" class="value form-control" placeholder="<@m'publicDescription'/>"
               value="${publicDescription!""}"></td>
    <td><input name="privateDescription" class="value form-control" placeholder="<@m'privateDescription'/>"
               value="${privateDescription!""}"></td>
    <td><input name="startLevel" type="number" class="value form-control" placeholder="<@m'startLevel'/>"
               <#if startLevel??>value="${(startLevel?c)}"</#if></td>
    <td><input name="commentOnStartLevel" class="value form-control" placeholder="<@m'startLevelComment'/>"
               value="${commentOnStartLevel!""}"></td>
    <td><input name="targetLevel" type="number" class="value form-control" placeholder="<@m'targetLevel'/>"
               <#if targetLevel??>value="${(targetLevel?c)}"</#if></td>
    <td><input name="commentOnTargetLevel" class="value form-control" placeholder="<@m'targetLevelComment'/>"
               value="${commentOnTargetLevel!""}"></td>
    <td><input name="infoSource" class="value form-control" placeholder="<@m'infoSource'/>" value="${infoSource!""}"></td>
    <td><input name="institutionToReport" class="value form-control" placeholder="<@m'institutionReport'/>"
               value="${institutionToReport!""}"></td>

    <td> <input class="value" type="checkbox" name="isPublic" value="true" ></td>
    <td colspan="3">
      <input type="button" id="add" class="saveGoalButton value btn btn-default btn-sm" value="<@m'add'/>" data-action="save">
      <input type="hidden" class="value" value="${goal.id?c}" name="goalId">
    </td>
  </tr>
  </tbody>
  </table>
</div>
</div>
<span id="errors"></span>
<button type="submit" class="blueButton goBackButton btn btn-default btn-sm" onclick="location='/admin/goals'"><span
  class="enter"><@m'goToMainPage'/></span></button>


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
  $(function () {
    $("#sortable").sortable({
      placeholder: "ui-state-highlight",
      handle: '.glyphicon-sort',
      cursor: "move",
      axis: "y",
      update: function (event, ui) {
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
          url: "/admin/metrics/save",
          data: ui.item.find(".orderNumberForm").serialize(),
          error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("<@m'error'/>");
          }
        });
      }
    });
  });
</script>
</@html>
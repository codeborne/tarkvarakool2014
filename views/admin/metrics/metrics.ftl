<@html>
<form method="get">
  <input type="hidden" value="${goal.id?c}" name="goalID">
</form>

<div class="panel panel-default">
  <div class="panel-heading">
    <h4 class="headingName">${goal.name}</h4>
  </div>
  <div class="panel-body">
    <table id="metrics-table" class="table">
      <thead>
      <tr>
        <th><@m'sort'/></th>
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
      <#list goal.metrics as metric>
        <tbody class="metric sortable">
        <tr>
          <td class="sort" rowspan="2">
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
          <td class="name" colspan="8">
            <span class="value">${metric.name}</span>
            <input class="nameValue form-control" name="name" value="${metric.name}" style="display: none;">
          </td>
          <td class="unit">
            <span class="value">${metric.unit}</span>
            <input class="unitValue form-control" name="unit" value="${metric.unit}" style="display: none;">
          </td>
          <td class="actions" rowspan="2">
            <input type="hidden" class="orderNumberValue" value="${metric.orderNumber?c}" name="orderNumber">
            <input type="hidden" class="goalIdValue" value="${goal.id?c}" name="goalId">
            <input type="hidden" class="metricIdValue" name="metricId" value="${metric.id?c}"/>

            <div class="action-button">
              <span class="buttonValue">
              <input type="hidden" class="value" value="${metric.id?c}" name="id">
              <input type="button" class="saveGoalButton value btn btn-default btn-sm" value=""
                     style="display: none" data-action="save">
              <input type="button" class="cancelGoalButton value btn btn-default btn-sm"
                     onclick="location='metrics?goalId=${goal.id?c}'; return false;" value="" style="display:none"
                     data-action="save"></span> </div>

            <div class="action-button">
            <span class="value">
              <button class="modifyButton" type="button" class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-pencil">
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
        <tr>
          <td class="publicDescription">
            <span class="value">${metric.publicDescription}</span>
            <textarea class="publicDescriptionValue form-control" name="publicDescription" style="display: none;">
              <#if metric.publicDescription??>${metric.publicDescription}</#if></textarea>
          </td>

          <td class="privateDescription">
            <span class="value">${metric.privateDescription}</span>
            <textarea class="privateDescriptionValue form-control" name="privateDescription" style="display: none;">
              <#if metric.privateDescription??>${metric.privateDescription}</#if></textarea>
          </td>
          <td class="startLevel">
            <span class="value"><#if metric.startLevel??>${metric.startLevel?c}<#else>N/A</#if></span>
            <input class="startLevelValue form-control" name="startLevel"
                   <#if metric.startLevel??>value="${(metric.startLevel?c)}"</#if> style="display: none;">
          </td>
          <td class="commentOnStartLevel">
            <span class="value">${metric.commentOnStartLevel}</span>
            <input class="commentOnStartLevelValue form-control" name="commentOnStartLevel"
                   value="${metric.commentOnStartLevel}"
                   style="display: none;">
          </td>
          <td class="targetLevel">
            <span class="value"><#if metric.targetLevel??>${metric.targetLevel?c}<#else>N/A</#if></span>
            <input class="targetLevelValue form-control" name="targetLevel"
                   <#if metric.targetLevel??>value="${(metric.targetLevel?c)}"</#if> style="display: none;">
          </td>
          <td class="commentOnTargetLevel">
            <span class="value">${metric.commentOnTargetLevel}</span>
            <input class="commentOnTargetLevelValue form-control" name="commentOnTargetLevel"
                   value="${metric.commentOnTargetLevel}"
                   style="display: none;">
          </td>
          <td class="infoSource">
            <span class="value"><#if metric.infoSource?has_content><a href="${metric.infoSource}" target="_blank"><span
              class="glyphicon glyphicon-info-sign"></span></a></#if></span>
            <input class="infoSourceValue form-control" name="infoSource" value="${metric.infoSource}"
                   style="display: none;">
          </td>
          <td class="institutionToReport">
            <span class="value">${metric.institutionToReport}</span>
            <textarea class="institutionToReportValue form-control" name="institutionToReport" style="display: none;">
              <#if metric.institutionToReport??>${metric.institutionToReport}</#if></textarea>
          </td>
          <td class="isPublic">
            <span
              class="value"> <#if metric.isPublic?? && metric.isPublic == true><@m 'public'/><#else><@m'private'/></#if></span>
            <input class="isPublicValue" type="checkbox" name="isPublic"
                   value=true <#if metric.isPublic?? && metric.isPublic == true> checked </#if> style="display: none;">
          </td>
        </tr>
        </tbody>
      </#list>
      <tbody class="addMetric">
      <tr>
        <td rowspan="2"></td>
        <td colspan="8">
          <input name="name" class="nameValue form-control" placeholder="<@m'metric'/>" value="${name!""}">
        </td>
        <td>
          <input name="unit" class="unitValue form-control" placeholder="<@m'unit'/>" value="${unit!""}">
        </td>
        <td rowspan="2">
          <input type="button" id="add" class="saveGoalButton value btn btn-default btn-sm" value="<@m'add'/>"
                 data-action="save">
          <input type="hidden" class="goalIdValue" value="${goal.id?c}" name="goalId">
        </td>
      </tr>
      <tr>
        <td>
          <textarea name="publicDescription" class="publicDescriptionValue form-control"
                    placeholder="<@m'publicDescription'/>">${publicDescription!""}</textarea>
        </td>
        <td>
          <textarea name="privateDescription" class="privateDescriptionValue form-control"
                    placeholder="<@m'privateDescription'/>">${privateDescription!""}</textarea>
        </td>
        <td>
        <input name="startLevel" type="number" class="startLevelValue form-control" placeholder="<@m'startLevel'/>"
               <#if startLevel??>value="${(startLevel?c)}"</#if>
        </td>
        <td>
          <input name="commentOnStartLevel" class="commentOnStartLevelValue form-control"
                 placeholder="<@m'startLevelComment'/>"
                 value="${commentOnStartLevel!""}">
        </td>
        <td>
        <input name="targetLevel" type="number" class="targetLevelValue form-control" placeholder="<@m'targetLevel'/>"
               <#if targetLevel??>value="${(targetLevel?c)}"</#if>
        </td>
        <td>
          <input name="commentOnTargetLevel" class="commentOnTargetLevelValue form-control"
                 placeholder="<@m'targetLevelComment'/>"
                 value="${commentOnTargetLevel!""}">
        </td>
        <td>
          <input name="infoSource" class="infoSourceValue form-control" placeholder="<@m'infoSource'/>"
                 value="${infoSource!""}">
        </td>
        <td>
          <textarea name="institutionToReport" class="institutionToReportValue form-control"
                    placeholder="<@m'institutionReport'/>">${institutionToReport!""}</textarea>
        </td>
        <td>
          <input class="isPublicValue" type="checkbox" name="isPublic" value="true">
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</div>
<span id="errors"></span>
<button type="submit" class="blueButton goBackButton btn btn-default btn-sm" onclick="location='/admin/goals'"><span><@m'goToMainPage'/></span></button>


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
      var goalIdValue = button.closest('tbody').find('input.goalIdValue').val();
      var metricIdValue = button.closest('tbody').find('input.metricIdValue').val();
      var orderNumberValue = button.closest('tbody').find('input.orderNumberValue').val();
      var nameValue = button.closest('tbody').find('input.nameValue').val();
      var unitValue = button.closest('tbody').find('input.unitValue').val();
      var startLevelValue = button.closest('tbody').find('input.startLevelValue').val();
      var commentOnStartLevelValue = button.closest('tbody').find('input.commentOnStartLevelValue').val();
      var targetLevelValue = button.closest('tbody').find('input.targetLevelValue').val();
      var commentOnTargetLevelValue = button.closest('tbody').find('input.commentOnTargetLevelValue').val();
      var infoSourceValue = button.closest('tbody').find('input.infoSourceValue').val();
      var isPublicValue = button.closest('tbody').find('input.isPublicValue').val();
      var publicDescriptionValue = button.closest('tbody').find('textarea.publicDescriptionValue').val();
      var privateDescriptionValue = button.closest('tbody').find('textarea.privateDescriptionValue').val();
      var institutionToReportValue = button.closest('tbody').find('textarea.institutionToReportValue').val();

      $.post(button.data("action"), {name: nameValue, unit: unitValue, publicDescription: publicDescriptionValue, privateDescription: privateDescriptionValue,
        startLevel: startLevelValue, commentOnStartLevel: commentOnStartLevelValue, targetLevel: targetLevelValue, commentOnTargetLevel: commentOnTargetLevelValue,
        infoSource: infoSourceValue, institutionToReport: institutionToReportValue, goalId: goalIdValue, metricId: metricIdValue, orderNumber: orderNumberValue}, responseHandler);
    };

    $('.saveGoalButton').click(saveClickHandler);

    var modifyClickHandler = function (event) {
      $("input.value").hide();
      $("textarea.publicDescriptionValue").hide();
      $("textarea.privateDescriptionValue").hide();
      $("textarea.institutionToReportValue").hide();
      $("input.startLevelValue").hide();
      $("input.commentOnStartLevelValue").hide();
      $("input.targetLevelValue").hide();
      $("input.commentOnTargetLevelValue").hide();
      $("input.infoSourceValue").hide();
      $("input.unitValue").hide();
      $("input.nameValue").hide();
      $("input.isPublicValue").hide();
      $("span.value").show();
      var row = $(event.target).closest('tbody');
      row.find("span.value").hide();
      row.find("input.value").hide()
      row.find("textarea.publicDescriptionValue").show();
      row.find("textarea.privateDescriptionValue").show();
      row.find("textarea.institutionToReportValue").show();
      row.find("input.unitValue").show();
      row.find("input.nameValue").show();
      row.find("input.commentOnStartLevelValue").show();
      row.find("input.commentOnTargetLevelValue").show();
      row.find("input.targetLevelValue").show();
      row.find("input.startLevelValue").show();
      row.find("input.isPublicValue").show();

      row.find("input.infoSourceValue").show();
      row.find("input.value").show();

    };

    $('.modifyButton').click(modifyClickHandler);
  });
</script>

<script>
  $(function () {
    $(".sortable").sortable({
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
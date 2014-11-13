<@html>
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

    <td class="metricContent">
      <ul>
        <li>
          <span class="labelOnlyShownWhenModifying" style="display: none;"><@m'metric'/>: </span>
          <h4 class="metricHeading"><span class="value name">${metric.name}</span></h4>
          <input class="value form-control" name="name" maxlength="255" value="${metric.name}" style="display: none;">
        </li>

        <li>
          <span class="labels labelsStyle"><@m'publicDescription'/>: </span>
          <span class="value publicDescription">${metric.publicDescription}</span>
          <input class="value form-control" name="publicDescription" maxlength="255" value="${metric.publicDescription}"
                 style="display: none;">
        </li>

        <li>
          <span class="labels labelsStyle"><@m'privateDescription'/>: </span>
          <span class="value privateDescription">${metric.privateDescription}</span>
          <input class="value form-control" name="privateDescription" maxlength="255"
                 value="${metric.privateDescription}"
                 style="display: none;">
        </li>

        <li>
          <div>
            <span class="labels labelsStyle"><@m'startLevel'/>: </span>
            <#if metric.startLevel??>
              <span class="value startLevel">${metric.startLevel?c}</span>
              <span class="value unit">${metric.unit}</span>
              <span class="value commentOnStartLevel">
                <#if metric.commentOnStartLevel?length!=0>(${metric.commentOnStartLevel})</#if>
              </span>
            <#else>
              <span class="value startLevel">N/A</span>
            </#if>
            <input class="value form-control" name="startLevel" type="number"
                   <#if metric.startLevel??>value="${(metric.startLevel?c)}"</#if> style="display: none;">
          </div>
          <div>
            <span class="labelOnlyShownWhenModifying" style="display: none;"><@m'startLevelComment'/>: </span>
            <input class="value form-control" name="commentOnStartLevel" maxlength="255"
                   value="${metric.commentOnStartLevel}"
                   style="display: none;">
          </div>
          <div>
            <span class="labels labelsStyle"> &nbsp; &nbsp; <@m'targetLevel'/>: </span>
            <#if metric.targetLevel??>
              <span class="value targetLevel">${metric.targetLevel?c}</span>
              <span class="value unit">${metric.unit}</span>
              <span class="value commentOnTargetLevel">
                <#if metric.commentOnTargetLevel?length!=0>(${metric.commentOnTargetLevel})</#if>
              </span>
            <#else>
                   <span class="value targetLevel">N/A</span>
            </#if>
            <input class="value form-control" name="targetLevel" type="number"
                   <#if metric.targetLevel??>value="${(metric.targetLevel?c)}"</#if> style="display: none;">
          </div>
          <div>
            <span class="labelOnlyShownWhenModifying" style="display: none;"><@m'targetLevelComment'/>: </span>
            <input class="value form-control" name="commentOnTargetLevel" maxlength="255"
                   value="${metric.commentOnTargetLevel}"
                   style="display: none;">
          </div>
          <div>
            <span class="labelOnlyShownWhenModifying" style="display: none;"><@m'unit'/>: </span>
            <input class="value form-control" name="unit" maxlength="255" value="${metric.unit}" style="display: none;">
          </div>
        </li>

        <li>
          <div>
            <span class="labels labelsStyle"><@m'infoSource'/>: </span>
                  <span class="value infoSource"><#if metric.infoSource?has_content><a href="${metric.infoSource}"
                                                                                       target="_blank">${metric.infoSource}</a></#if>
                  </span>
            <input class="value form-control" name="infoSource" maxlength="255" value="${metric.infoSource}"
                   style="display: none;">
          </div>
          <div>
            <span class="labels labelsStyle"> &nbsp; &nbsp;<@m'institutionReport'/>: </span>
            <span class="value institutionToReport">${metric.institutionToReport}</span>
            <input class="value form-control" name="institutionToReport" maxlength="255"
                   value="${metric.institutionToReport}"
                   style="display: none;">
          </div>
        </li>

        <li>
          <span class="labelOnlyShownWhenModifying" style="display: none;"><@m'public'/>: </span>
                <span
                  class="value isPublic"> <#if metric.isPublic?? && metric.isPublic == true><@m 'public'/><#else><@m'private'/></#if></span>
          <input class="value" type="checkbox" name="isPublic"
                 value=true <#if metric.isPublic?? && metric.isPublic == true> checked </#if>
                 style="display: none;">
        </li>
      </ul>
    </td>

    <td class="actions">
      <input type="hidden" class="value" value="${metric.orderNumber?c}" name="orderNumber">
      <input type="hidden" class="value" value="${goal.id?c}" name="goalId">
      <input type="hidden" class="value" name="metricId" value="${metric.id?c}"/>

      <div class="action-button">
        <input type="hidden" class="value" value="${metric.id?c}" name="id">
        <input type="button" title="<@m'save'/>" class="saveGoalButton value btn btn-default btn-sm" value=""
               style="display: none" data-action="save">
        <input type="button" title="<@m'cancel'/>" class="cancelGoalButton value btn btn-default btn-sm"
               onclick="location='metrics?goalId=${goal.id?c}'; return false;" value="" style="display:none"
               data-action="save">
      </div>

      <div class="action-button">
             <span class="value">
              <button class="modifyButton" title="<@m'modify'/>" type="button" class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-pencil"></span>
              </button>
             </span>
      </div>

      <div class="action-button">
        <form action="delete" method="post" onsubmit="return confirm('<@m'errorDeletingConfirmation'/>')">
          <input type="hidden" value="${goal.id?c}" name="goalId">
          <input type="hidden" name="id" value="${metric.id?c}"/>
              <span class="value">
              <button class="deleteButton " title="<@m'delete'/>" type="submit" class="btn btn-default btn-sm">
                <span class="glyphicon glyphicon-trash"></span>
              </button>
              </span>
        </form>
      </div>
    </td>
  </tr>
  </#list>

<tr class="addMetric">
  <td></td>
  <td>

    <ul>
      <li>
        <span class="addLabel"><@m'metric'/>: </span>
        <input name="name" class="value form-control" maxlength="255" placeholder="<@m'metric'/>" value="${name!""}">
      </li>

      <li>
        <span class="addLabel"><@m'publicDescription'/>: </span>
        <input name="publicDescription" class="value form-control" maxlength="255"
               placeholder="<@m'publicDescription'/>"
               value="${publicDescription!""}">
      </li>

      <li>
        <span class="addLabel"><@m'privateDescription'/>: </span>
        <input name="privateDescription" class="value form-control" maxlength="255"
               placeholder="<@m'privateDescription'/>"
               value="${privateDescription!""}">
      </li>

      <li>
        <div>
          <span class="addLabel"><@m'startLevel'/>: </span>
          <input name="startLevel" type="number" class="value form-control" placeholder="<@m'startLevel'/>"
                 <#if startLevel??>value="${(startLevel?c)}"</#if>>
        </div>
        <div>
          <span class="addLabel"><@m'startLevelComment'/>: </span>
          <input name="commentOnStartLevel" class="value form-control" maxlength="255"
                 placeholder="<@m'startLevelComment'/>"
                 value="${commentOnStartLevel!""}">
        </div>
        <div>
          <span class="addLabel"><@m'targetLevel'/>: </span>
          <input name="targetLevel" type="number" class="value form-control" placeholder="<@m'targetLevel'/>"
                 <#if targetLevel??>value="${(targetLevel?c)}"</#if>>
        </div>
        <div>
          <span class="addLabel"><@m'targetLevelComment'/>: </span>
          <input name="commentOnTargetLevel" class="value form-control" maxlength="255"
                 placeholder="<@m'targetLevelComment'/>"
                 value="${commentOnTargetLevel!""}">
        </div>
        <div>
          <span class="addLabel"><@m'unit'/>: </span>
          <input name="unit" class="value form-control" maxlength="255" placeholder="<@m'unit'/>" value="${unit!""}">
        </div>
      </li>

      <li>
        <div>
          <span class="addLabel"><@m'infoSource'/>: </span>
          <input name="infoSource" class="value form-control" maxlength="255" placeholder="<@m'infoSource'/>"
                 value="${infoSource!""}">
        </div>
        <div>
          <span class="addLabel"><@m'institutionReport'/>: </span>
          <input name="institutionToReport" class="value form-control" maxlength="255"
                 placeholder="<@m'institutionReport'/>"
                 value="${institutionToReport!""}">
        </div>
      </li>

      <li>
        <span class="addLabel"><@m'public'/>: </span>
        <input class="value" type="checkbox" name="isPublic" value="true">
      </li>
    </ul>
  </td>
  <td id="goalSaveButtonTd">
    <input type="button" id="metricSaveButton" class="saveGoalButton value btn btn-default btn-sm" value="<@m'add'/>"
           data-action="save">
    <input type="hidden" class="value" value="${goal.id?c}" name="goalId">
  </td>
</tr>
</tbody>
</table>
</div>
</div>
<span id="errors"></span>


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
      row.find("span.labels").removeClass("labelsStyle");
      $("span.addLabel").hide();
      row.find("span.value").hide();
      row.find("span.labelOnlyShownWhenModifying").show();
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
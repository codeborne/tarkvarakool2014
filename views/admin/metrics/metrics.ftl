<@html>
<form method="get">
  <input type="hidden" value="${goal.id?c}" name="goalID">
</form>
<div class="panel panel-default">
<div class="panel-heading">
  <h4 class="headingName">${goal.name}</h4>
</div>
<div class="panel-body">
<table class="table table-hover" id="adminMetricTable">
<thead>
<tr>
  <th><@m'sort'/></th>
  <th><@m'metric'/></th>
  <th><@m'actions'/></th>
</tr>
</thead>
<tbody id="sortable">
  <#list goal.metrics as metric>
    <#list infoSourceContentList as infosource>
      <#if metric_index == infosource_index>
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
            <input type="hidden" name="csrfToken" value="${session.getAttribute("csrfToken")}">
          </form>
        </td>
        <td class="metricContent">
          <span id="errors_${metric.id?c}"></span>
          <ul>
            <li>
              <h4 class="metricHeading"><span class="value name">${metric.name}</span></h4>
              <span class="labelOnlyShownWhenModifying" style="display: none;"><@m'metric'/>: </span>
              <textarea class="value form-control" name="name" maxlength="255" placeholder="<@m'metric'/>"
                        style="display: none;">${metric.name}</textarea>
            </li>
            <li>
              <span class="labels labelsStyle"><@m'publicDescription'/>: </span>
              <span class="value publicDescription">${metric.publicDescription}</span>
              <textarea class="value form-control" name="publicDescription" maxlength="255"
                        placeholder="<@m'publicDescription'/>"
                        style="display: none;"><#if metric.publicDescription??>${metric.publicDescription}</#if></textarea>
            </li>
            <li>
              <span class="labels labelsStyle"><@m'privateDescription'/>: </span>
              <span class="value privateDescription">${metric.privateDescription}</span>
              <textarea class="value form-control" name="privateDescription" maxlength="255"
                        placeholder="<@m'privateDescription'/>"
                        style="display: none;"><#if metric.privateDescription??>${metric.privateDescription}</#if></textarea>
            </li>
            <li>
              <div>
                <span class="labelOnlyShownWhenModifying" style="display: none;"><@m'unit'/>: </span>
                <input class="value form-control smallInputFields" name="unit" maxlength="255" placeholder="<@m'unit'/>"
                       value="${metric.unit}" style="display: none;">
              </div>
              <br class="value">
              <div>
                <span class="labels labelsStyle"><@m'startLevel'/>: </span>
                <#if metric.startLevel??>
                  <span class="value startLevel">${metric.startLevel}</span>
                  <span class="value unit">${metric.unit}</span>
                  <span class="value commentOnStartLevel">&nbsp;<#if metric.commentOnStartLevel?length!=0>
                    (${metric.commentOnStartLevel})</#if></span>
                <#elseif metric.commentOnStartLevel?length!=0>
                  <span class="value commentOnStartLevel">(${metric.commentOnStartLevel})</span>
                <#else>
                  <span class="value startLevel">N/A</span>
                </#if>
                <input class="value form-control smallInputFields" name="startLevel" placeholder="<@m'startLevel'/>"
                       <#if metric.startLevel??>value="${(metric.startLevel?c)}"</#if> style="display: none;">
              </div>
              <div>
                <span class="labelOnlyShownWhenModifying" style="display: none;"><@m'startLevelComment'/>: </span>
                <input class="value form-control smallInputFields" name="commentOnStartLevel" maxlength="255"
                       placeholder="<@m'startLevelComment'/>"
                       value="${metric.commentOnStartLevel}"
                       style="display: none;">
              </div>
              <div>
                <span class="labels labelsStyle"> &nbsp; &nbsp;<@m'targetLevel'/>: </span>
                <#if metric.targetLevel??>
                  <span class="value targetLevel">${metric.targetLevel}</span>
                  <span class="value unit">${metric.unit}</span>
                  <span class="value commentOnTargetLevel">&nbsp;<#if metric.commentOnTargetLevel?length!=0>
                    (${metric.commentOnTargetLevel})</#if></span>
                <#elseif metric.commentOnTargetLevel?length!=0>
                  <span class="value commentOnTargetLevel">(${metric.commentOnTargetLevel})</span>
                <#else>
                  <span class="value targetLevel">N/A</span>
                </#if>
                <input class="value form-control smallInputFields" name="targetLevel" placeholder="<@m'targetLevel'/>"
                       <#if metric.targetLevel??>value="${(metric.targetLevel?c)}"</#if> style="display: none;">
              </div>
              <div>
                <span class="labelOnlyShownWhenModifying" style="display: none;"><@m'targetLevelComment'/>: </span>
                <input class="value form-control smallInputFields" name="commentOnTargetLevel" maxlength="255"
                       placeholder="<@m'targetLevelComment'/>"
                       value="${metric.commentOnTargetLevel}"
                       style="display: none;">
              </div>
            </li>
            <li>
              <div>
                <span class="labels labelsStyle"><@m'institutionReport'/>: </span>
                <span class="value institutionToReport">${metric.institutionToReport}</span>
                <textarea rows="1" cols="80" class="value form-control mediumInputFields" name="institutionToReport"
                          maxlength="255"
                          placeholder="<@m'institutionReport'/>"
                          style="display: none;">${metric.institutionToReport}</textarea>
              </div>
            </li>
            <li>
              <div>
                <span class="labels labelsStyle"><@m'infoSource'/>: </span>
            <span class="value infoSource">
              <#list infosource as infoItem>
                <#if (infoItem?contains("http://") || infoItem?contains("https://")) >
                  <span style="white-space: pre-wrap;"><a href="${infoItem}" target="_blank" >${infoItem}</a></span><#else> <span style="white-space: pre-wrap;">${infoItem}</span></#if>
              </#list>
            </span>
                <textarea rows="2" cols="200" class="value form-control" name="infoSource" placeholder="<@m'infoSource'/>" maxlength="1000" style="display: none;">${metric.infoSource}</textarea>
              </div>
            </li>
          </ul>
        </td>
        <td class="actions">
          <input type="hidden" class="value" value="${metric.orderNumber?c}" name="orderNumber">
          <input type="hidden" class="value" value="${goal.id?c}" name="goalId">
          <input type="hidden" class="value" name="metricId" value="${metric.id?c}"/>
          <input type="hidden" class="value" name="csrfToken" value="${session.getAttribute("csrfToken")}">
          <div class="action-button">
            <input type="hidden" class="value" value="${metric.id?c}" name="id">
            <input type="button" title="<@m'save'/>" class="saveGoalButton value btn btn-default btn-sm" value=""
                   style="display: none" data-action="save">
            <input type="button" title="<@m'cancel'/>" class="cancelGoalButton value btn btn-default btn-sm"
                   onclick="location='metrics?goalId=${goal.id?c}'; return false;" value="" style="display:none"
                   data-action="save">
          </div>
          <div class="action-button">
        <span class="value"><button class="modifyButton" title="<@m'modify'/>" type="button"
                                    class="btn btn-default btn-sm"><span class="glyphicon glyphicon-pencil"></span>
        </button></span>
          </div>
          <div class="action-button">
            <form action="delete" method="post" onsubmit="return confirm('<@m'errorDeletingConfirmation'/>')">
              <input type="hidden" value="${goal.id?c}" name="goalId">
              <input type="hidden" name="id" value="${metric.id?c}"/>
              <input type="hidden" class="value" name="csrfToken" value="${session.getAttribute("csrfToken")}">
          <span class="value"><button class="deleteButton " title="<@m'delete'/>" type="submit"
                                      class="btn btn-default btn-sm"><span class="glyphicon glyphicon-trash"></span>
          </button></span>
            </form>
          </div>
          <div class="action-button">
            <input class="isPublicValue" type="hidden" value="${goal.id?c}" name="goalId"/>
            <input class="isPublicValue" type="hidden" name="metricId" value="${metric.id?c}"/>
            <input class="isPublicValue" type="hidden" name="isStatusUpdateOnly" value="true"/>
            <input type="hidden" class="isPublicValue" name="csrfToken" value="${session.getAttribute("csrfToken")}">
            <input class="isPublicValue" type="hidden" name="isPublic"
                   <#if metric.isPublic==false>value="true"<#else>value="false"</#if>/>
        <span class="value"><input type="button" title="<@m'public'/>/<@m'private'/>"
                                   class="isPublicValue btn btn-default btn-sm publicButton" value=""
                                   data-action="save"<#if metric.isPublic==true> style="color:green"
                                   <#else>style="color:red"</#if> /></span>
          </div>
          <#if metric.startLevel?? && metric.targetLevel??>
          <div class="action-button">
            <form action="/admin/metrics/charts">
              <input type="hidden" value="${metric.id?c}" name="metricId">
              <button class="small-chart-button" type="submit" class="btn btn-default btn-sm" title="<@m'chart'/>">
                <span class="glyphicon glyphicon-stats"></span>
              </button>
            </form>
          </div>
          </#if>
        </td>
      </tr>
      </#if>
    </#list>
  </#list>
<tr class="addMetric">
  <td></td>
  <td>
    <span id="adderrors"></span>
    <ul>
      <li>
        <span class="addLabel"><@m'metric'/>: </span>
        <textarea name="name" class="value form-control" maxlength="255"
                  placeholder="<@m'metric'/>">${name!""}</textarea>
      </li>
      <li>
        <span class="addLabel"><@m'publicDescription'/>: </span>
        <textarea name="publicDescription" class="value form-control" maxlength="255"
                  placeholder="<@m'publicDescription'/>">${publicDescription!""}</textarea>
      </li>
      <li>
        <span class="addLabel"><@m'privateDescription'/>: </span>
        <textarea name="privateDescription" class="value form-control" maxlength="255"
                  placeholder="<@m'privateDescription'/>">${privateDescription!""}</textarea>
      </li>
      <li>
        <div>
          <span class="addLabel"><@m'unit'/>: </span>
          <input name="unit" class="value form-control smallInputFields" maxlength="255" placeholder="<@m'unit'/>"
                 value="${unit!""}">
        </div>
        <div>
          <span class="addLabel"><@m'startLevel'/>: </span>
          <input name="startLevel" class="value form-control smallInputFields" placeholder="<@m'startLevel'/>"
                 <#if startLevel??>value="${(startLevel?c)}"</#if>>
        </div>
        <div>
          <span class="addLabel"><@m'startLevelComment'/>: </span>
          <input name="commentOnStartLevel" class="value form-control smallInputFields" maxlength="255"
                 placeholder="<@m'startLevelComment'/>"
                 value="${commentOnStartLevel!""}">
        </div>
        <div>
          <span class="addLabel"><@m'targetLevel'/>: </span>
          <input name="targetLevel" class="value form-control smallInputFields" placeholder="<@m'targetLevel'/>"
                 <#if targetLevel??>value="${(targetLevel?c)}"</#if>>
        </div>
        <div>
          <span class="addLabel"><@m'targetLevelComment'/>: </span>
          <input name="commentOnTargetLevel" class="value form-control smallInputFields" maxlength="255"
                 placeholder="<@m'targetLevelComment'/>"
                 value="${commentOnTargetLevel!""}">
        </div>
      </li>
      <li>
        <div>
          <span class="addLabel"><@m'institutionReport'/>: </span>
          <textarea rows="1" cols="80" name="institutionToReport" class="value form-control mediumInputFields"
                    maxlength="255"
                    placeholder="<@m'institutionReport'/>">${institutionToReport!""}</textarea>
        </div>
      </li>
      <li>
        <div>
          <span class="addLabel"><@m'infoSource'/>: </span>
          <textarea rows="2" cols="200" name="infoSource" class="value form-control" maxlength="1000"
                    placeholder="<@m'infoSource'/>">${infoSource!""}</textarea>
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
    <input type="hidden" class="value" name="csrfToken" value="${session.getAttribute("csrfToken")}">
  </td>
</tr>
</tbody>
</table>
</div>
</div>
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
      var values = button.closest('tr').find('.value');
      $.post(button.data("action"), values.serialize(), function (response) {
        if (response.trim() == "") {
          window.location = window.location;
        }
        if (button.closest("td").children("input[name=metricId]").length == 0)
          $("#adderrors").html(response);
        else
          $("#errors_" + button.closest("td").children("input[name=metricId]").first().val()).html(response);
      });
    };

    var saveIsPublicClickHandler = function (event) {
      var button = $(event.target);
      var values = button.closest('tr').find('.isPublicValue');
      $.post(button.data("action"), values.serialize(), responseHandler);
    };

    $('.saveGoalButton').click(saveClickHandler);
    $('.publicButton').click(saveIsPublicClickHandler);

    var modifyClickHandler = function (event) {
      $(".value").hide();
      $("span.value").show();
      var row = $(event.target).closest('tr');
      row.find("span.labels").removeClass("labelsStyle");
      $("span.addLabel").hide();
      row.find("span.value").hide();
      row.find("span.labelOnlyShownWhenModifying").show();
      row.find("input.value").show();
      row.find("textarea.value").show();
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
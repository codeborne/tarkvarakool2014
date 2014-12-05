<@html>

<div class="panel panel-default panel-home">
  <div class="panel-heading">
    <#if message??>
      <div class="alert alert-success"><@m message/></div>
    </#if>
    <h4 class="name"><@m'goals'/></h4>
  </div>
  <div class="panel-body">
    <table class="table table-hover">
      <thead>
      <tr>
        <th class="sort"><@m'sort'/></th>
        <th><@m'goal'/></th>
        <th><@m'comment'/></th>
        <th><@m'budget'/></th>
        <th><#if goals?has_content><@m'metrics'/></#if></th>
        <th class="actions"><#if goals?has_content><@m'actions'/></#if></th>
      </tr>
      </thead>
      <tbody id="sortableGoals">
        <#if goals?has_content>
          <#list goals as goal>
          <tr class="goal">
            <td class="sort">
              <span class="glyphicon glyphicon-sort hand-pointer" title="<@m 'sortingInstruction'/>"></span>

              <form class="orderNumberForm">
                <input type="hidden" value="${goal.id?c}" name="id">
                <input type="hidden" value="${goal.sequenceNumber}" name="sequenceNumber">
                <input type="hidden"  name="csrfToken" value="${session.getAttribute("csrfToken")}">
              </form>
            </td>
            <td class="nameInTable">
              <span class="value line-break">${goal.name}</span>
              <textarea class="value form-control" name="name" maxlength="255" placeholder="<@m'goal'/>" style="display: none">${goal.name}</textarea>
            </td>
            <td class="commentInTable">
              <span class="value line-break">${goal.comment!""}</span>
              <textarea class="value form-control" name="comment" maxlength="255" placeholder="<@m'comment'/>" style="display: none"><#if goal.comment??>${goal.comment}</#if></textarea>
            </td>
            <td class="budgetInTable">
              <span class="value">${goal.budget}</span>
              <input type="number" class="value form-control" name="budget" placeholder="<@m'budget'/>" value="${goal.budget?c}"
                     style="display: none;">
            </td>
            <td>
              <form action="/admin/metrics/metrics">
                <input type="hidden" value="${goal.id?c}" name="goalId">
                <button type="submit" class="metricsButton btn btn-default btn-sm" title="<@m'viewMetrics'/>">
                  <span class="glyphicon glyphicon-list-alt"></span>
                </button>
              </form>
            </td>
            <td class="actions">
              <div class="action-button">
                <input type="hidden" class="value" value="${goal.id?c}" name="id">
                <input type="hidden" class="value" name="csrfToken" value="${session.getAttribute("csrfToken")}">
                <input type="button" title="<@m'save'/>" class="saveGoalButton value btn btn-default btn-sm" value=""
                       style="display: none" data-action="save">
                <input type="button" title="<@m'cancel'/>" class="cancelGoalButton value btn btn-default btn-sm"
                       onclick="location='/admin/goals/home'; return false;" value="" style="display:none"
                       data-action="save">
              </div>
              <div class="action-button">
              <span class="value">
                <button type="button" title="<@m'modify'/>" class="modifyButton btn btn-default btn-sm">
                  <span class="glyphicon glyphicon-pencil"></span>
                </button>
              </span>
              </div>
              <form action="/admin/goals/translation" class="action-button">
                <input type="hidden" value="${goal.id?c}" name="goalId">
                <span class="value">
                <button type="submit" class="translationButton"
                  <#if isEverythingTranslated[goal_index]==false>title="<@m'needsTranslation'/>"
                  <#else> title="<@m'translated'/>"</#if>>
                  <span <#if isEverythingTranslated[goal_index]==false> class="glyphicon glyphicon-globe redValue"
                  <#else> class="glyphicon glyphicon-globe greenValue"></#if></span>
                </button>
                  </span>
              </form>
              <#if !goal.metrics?has_content>
                <form action="delete" method="post" onsubmit="return confirm('<@m'errorDeletingConfirmation'/>')"
                      class="action-button">
                  <input type="hidden" name="id" value="${goal.id?c}"/>
                  <input type="hidden" class="value" name="csrfToken" value="${session.getAttribute("csrfToken")}">
                  <span class="value">
                  <button class="deleteButton" title="<@m'delete'/>" type="submit" class="btn btn-default btn-sm">
                    <span class="glyphicon glyphicon-trash"></span>
                  </button>
                  </span>
                </form>
              </#if>
            </td>
          </tr>
          </#list>
        </#if>
      <tr>
        <td><input type="hidden" class="value" name="csrfToken" value="${session.getAttribute("csrfToken")}"></td>
        <td>
          <textarea class="value form-control" name="name" maxlength="255" placeholder="<@m'goal'/>"><#if name??> ${name}</#if></textarea>
          </td>
        <td>
          <textarea class="value form-control" name="comment" maxlength="255" placeholder="<@m'comment'/>"><#if comment??> ${comment}</#if></textarea>
          </td>
        <td><input type="number" class="value form-control" placeholder="<@m'budget'/>" name="budget"
                   <#if budget?? && (budget>=0)>value=${budget?c}</#if>></td>
        <td colspan=3>
          <input type="button" value="<@m'add'/>" class="saveGoalButton value btn btn-default btn-sm" id="goalSaveButton"
                 data-action="save">
        </td>
      </tr>
      </tbody>
    </table>
    <span id="errors"></span>

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
      $.post(button.data("action"), values.serialize(), responseHandler);
    };

    $('.saveGoalButton').click(saveClickHandler);

    var modifyClickHandler = function (event) {
      $(".value").hide();
      $("span.value").show();
      var row = $(event.target).closest('tr');
      row.find("span.value").hide();
      row.find("input.value").show();
      row.find("textarea.value").show();
    };

    $('.modifyButton').click(modifyClickHandler);
  });


</script>

<script>
  $(function () {
    $("#sortableGoals").sortable({
      placeholder: "ui-state-highlight",
      handle: '.glyphicon-sort',
      cursor: "move",
      axis: "y",
      items: "tr:not(:last-child)",
      update: function (event, ui) {
        var id = ui.item.find("[name=id]").val();
        var csrfToken = ui.item.find("[name=csrfToken]").val();
        var sequenceNumberBeforeInput = ui.item.prev().find("[name=sequenceNumber]");

        if (sequenceNumberBeforeInput.length == 0)
          var order = 1;

        else
          var order = parseInt(sequenceNumberBeforeInput.val()) + 1;

        $.ajax({
          type: "POST",
          url: "/admin/goals/home",
          data: { sequenceNumber: order, id: id, csrfToken: csrfToken },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("<@m'error'/>");
          }
        });
      }
    });
  });
</script>

</@html>


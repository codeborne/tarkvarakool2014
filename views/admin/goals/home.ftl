<@html>
<br><br>
<div class="panel panel-default">
  <div class="panel-heading">
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
        <th><@m'metrics'/></th>
        <th class="actions"><@m'actions'/></th>
      </tr>
      </thead>
      <tbody id="sortableGoals">
        <#if goals?has_content>
          <#list goals as goal>
          <tr class="goal">
            <td class="sort">
              <span class="glyphicon glyphicon-sort hand-pointer"></span>
              <form class="orderNumberForm">
                <input type="hidden" value="${goal.id?c}" name="id">
                <input type="hidden" value="${goal.sequenceNumber}" name="sequenceNumber">
              </form>
            </td>
            <td class="nameInTable">
              <span class="value">${goal.name}</span>
              <input type="text" class="value form-control" name="name" value="${goal.name}"
                     style="display: none;">
            </td>
            <td class="commentInTable">
              <span class="value">${goal.comment!""}</span>
              <input type="text" class="value form-control" name="comment"
                     <#if goal.comment??>value="${goal.comment}"</#if>
                     style="display: none;">
            </td>
            <td class="budgetInTable">
              <span class="value">${goal.budget?c}</span>
              <input type="number" class="value form-control" name="budget" value="${goal.budget?c}"
                    style="display: none;">
            </td>
            <td>
              <form action="/admin/metrics/metrics">
                <input type="hidden" value="${goal.id?c}" name="goalId">
                <button type="submit" class="metricsButton btn btn-default btn-sm">
                  <span class="glyphicon glyphicon-list-alt"></span>
                </button>
              </form>
            </td>
            <td class="actions">
              <div class="action-button">
                <input type="hidden" class="value" value="${goal.id?c}" name="id">
                <input type="button " class="saveGoalButton value btn btn-default btn-sm" value=""
                       style="display: none" data-action="save">
                <input type="button" class="cancelGoalButton value btn btn-default btn-sm"
                       onclick="location='/admin/goals/home'; return false;" value="" style="display:none"
                       data-action="save">
              </div>
              <div class="action-button">
              <span class="value">
                <button class="modifyButton" type="button" class="btn btn-default btn-sm">
                  <span class="glyphicon glyphicon-pencil"></span>
                </button>
              </span>
              </div>
              <form action="/admin/goals/translation" class="action-button">
                <input type="hidden" value="${goal.id?c}" name="goalId">
                <button type="submit" class="translationButton btn btn-default btn-sm">
                  <span class="glyphicon glyphicon-globe"></span>
                </button>
              </form>
              <#if !goal.metrics?has_content>

                <form action="delete" method="post" onsubmit="return confirm('<@m'errorDeletingConfirmation'/>')"
                      class="action-button">
                  <input type="hidden" name="id" value="${goal.id?c}"/>
                  <button class="deleteButton" type="submit" class="btn btn-default btn-sm">
                    <span class="glyphicon glyphicon-trash"></span>
                  </button>
                </form>
              </#if>
            </td>
          </tr>
          </#list>
        </#if>
      <tr>
        <td></td>
        <td>
          <input name="name" class="value form-control" rows="1" placeholder="<@m'insertGoal'/>"
                 maxlength="255" value="${name!""}"></td>
        <td><input name="comment" class="value form-control" rows="1" placeholder="<@m'insertComment'/>"
                   maxlength="255" value="${comment!""}"></td>
        <td><input type="number" class="value form-control" placeholder="<@m'insertBudget'/>" name="budget"
                   <#if budget?? && (budget>=0)>value=${budget?c}</#if>></td>
        <td colspan=3>
          <input type="button" id="add" value="<@m'add'/>" class="saveGoalButton value btn btn-default btn-sm"
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
    $("#sortableGoals").sortable({
      placeholder: "ui-state-highlight",
      handle: '.glyphicon-sort',
      cursor: "move",
      axis: "y",
      items: "tr:not(:last-child)",
      update: function (event, ui) {
        var id = ui.item.find("[name=id]").val();
        var sequenceNumberBeforeInput = ui.item.prev().find("[name=sequenceNumber]");

        if (sequenceNumberBeforeInput.length == 0)
          var order = 1;

        else
          var order = parseInt(sequenceNumberBeforeInput.val()) + 1;

        $.ajax({
          type: "POST",
          url: "/admin/goals/home",
          data: { sequenceNumber: order, id: id },
          error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("<@m'error'/>");
          }
        });
      }
    });
  });
</script>

</@html>


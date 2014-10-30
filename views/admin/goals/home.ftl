<@html>

<div class="panel panel-default">
<div class="panel-heading">

  <h4 class="name">Eesmärgid</h4>
  </div>
<div class="panel-body">
<table class="table table-hover">
  <thead>
  <tr>
    <th>Jrk nr</th>
    <th>Eesmärk</th>
    <th>Kommentaar</th>
    <th>Eelarve</th>
    <th>Muuda</th>
    <th>Mõõdikud</th>
    <th>Kustuta</th>
  </tr>
  </thead>
  <tbody id="sortableGoals">
  <#if goals?has_content>
    <#list goals as goal>
      <tr class="goal">
        <td class="sort">
          <span class="glyphicon glyphicon-sort hand-pointer"></span>
          <form class="orderNumberForm" >
            <input type="hidden" value="${goal.id?c}" name="id">
            <input type="hidden" value="${goal.sequenceNumber}" name="sequenceNumber">
          </form>
        </td>

        <td class="nameInTable">
          <span class="value">${goal.name}</span>
          <input type="text" class="value form-control" name="name" value="${goal.name}" style="display: none;">
        </td>
        <td class="commentInTable">
          <span class="value">${goal.comment!""}</span>
          <input type="text" class="value form-control" name="comment" <#if goal.comment??>value="${goal.comment}"</#if>
                 style="display: none;">
        </td>
        <td class="budgetInTable">
          <span class="value">${goal.budget?c}</span>
          <input type="number" class="value form-control" name="budget" value="${goal.budget?c}" style="display: none;">
        </td>
        <td>
          <input type="hidden" class="value" value="${goal.id?c}" name="id">
          <input type="button" class="saveGoalButton value btn btn-default btn-sm" value="Salvesta"
                 style="display: none" data-action="modify">
          <input type="button" class="cancelGoalButton value btn btn-default btn-sm"
                 onclick="location='/admin/goals/home'; return false;" value="Tühista" style="display:none"
                 data-action="modify">
          <span class="value">
            <button class="modifyButton" type="button" class="btn btn-default btn-sm">
              <span class="glyphicon glyphicon-pencil"></span>
            </button>
          </span>
        </td>

        <td>
          <form action="/admin/metrics/metrics">
            <input type="hidden" value="${goal.id?c}" name="goalId">
            <button type="submit" class="metricsButton btn btn-default btn-sm">
              <span class="glyphicon glyphicon-list-alt"></span>
            </button>
          </form>
        </td>

        <td>
          <#if !goal.metrics?has_content>
            <form action="delete" method="post" onsubmit="return confirm('Kas oled kustutamises kindel?')">
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
      <input name="name" class="value form-control" rows="1" placeholder="Sisesta eesmärk"
             maxlength="255" value="${name!""}"></td>
    <td><input name="comment" class="value form-control" rows="1" placeholder="Kommentaar"
               maxlength="255" value="${comment!""}"></td>
    <td><input type="number" class="value form-control" placeholder="Eelarve" name="budget"
               <#if budget?? && (budget>0)>value=${budget?c}</#if>></td>
    <td>
      <input type="button" class="saveGoalButton value btn btn-default btn-sm" value="Lisa" data-action="add">
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
      row.find("span.value").hide();
      row.find("input.value").show();
    };

    $('.modifyButton').click(modifyClickHandler);
  });
</script>

<script>
  $(function() {
    $( "#sortableGoals" ).sortable({
      placeholder: "ui-state-highlight",
      handle:'.glyphicon-sort',
      cursor: "move",
      axis: "y",
      items: "tr:not(:last-child)",
      update: function( event, ui ) {
        var id = ui.item.find("[name=id]").val();
        var sequenceNumberBeforeInput = ui.item.prev().find("[name=sequenceNumber]");

        if (sequenceNumberBeforeInput.length == 0)
          var order = 1;

        else
          var order = parseInt(sequenceNumberBeforeInput.val())+1;

        $.ajax({
          type: "POST",
          url: "/admin/goals/home",
          data: { sequenceNumber : order, id : id },
          error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("Tekkis viga");
          }
        });
      }
    });
  });
</script>

</@html>


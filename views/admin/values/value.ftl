<@html>

<script>
  function sendData(goalId, metricId, year, thisObject) {
    inputValue = thisObject.children('input').val();
    $.post("/admin/values/modify", {goalId: goalId, metricId: metricId, year: year, value: inputValue},
      function(data) {
        if(data!="")
          alert("Error occurred: " + data);

        thisObject.hide();
        thisObject.parent().children('span.glyphicon').show();
        thisObject.parent().children('span.value').text(inputValue);
        thisObject.parent().children('span.value').show();
      }
    );
  }

  function showInputHideIconAndValue(thisObject) {
    thisObject.hide();
    thisObject.parent().children('span.value').hide();
    thisObject.parent().children('form').children('input').val(thisObject.parent().children('span.value').text());
    thisObject.parent().children('form').show();
  }
</script>
  <#list goals as goal>
  <div class="goal">
    <h4 class="name"> Eesmärk:${goal.name}</h4>


    <table class="table">
      <div class="tableHead">
        <tr>
          <th>Mõõdik</th>
          <#list minimumYear..maximumYear as year>
            <th>${year?c}</th>
          </#list>
        </tr>
        <#list goal.metrics as metric>
          <tr class="metric">
            <td class="name">${metric.name}</td>
            <#list minimumYear..maximumYear as year>
              <td><span class="value">${((metric.values.get(year))?c)!""}</span> <span class="glyphicon glyphicon-pencil hand-pointer" onclick="showInputHideIconAndValue($(this));"></span>
              <form style="display: none;" onsubmit="sendData(${goal.id}, ${metric.id}, ${year?c}, $(this)); return false;"><input></form></td>
            </#list>
          </tr>
        </#list>
    </table>
    <br>
    <br>
  </div>
  </#list>
<button class="btn btn-default btn-sm" onclick="location='/admin/goals/home'; return false;">
  Tagasi
</button>
</@html>

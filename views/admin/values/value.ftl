<@html>

<script>
  function sendData(goalId, metricId, year, value) {
    $.post("/admin/values/modify", {goalId: goalId, metricId: metricId, year: year, value: value},
      function(data) {
        if(data!="")
          alert("Error occurred: " + data);

        $("#newValue").parent().parent().val("")
      }
    );
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
              <td><span class="value">${(metric.values.get(year))!""}</span> <span class="glyphicon glyphicon-pencil" onclick="$(this).hide(); $(this).parent().children('form').show()"></span>
              <form style="display: none;" onsubmit="sendData(${goal.id}, ${metric.id}, ${year?c}, $(this).children('input').val()); $(this).hide(); $(this).parent().children('span.glyphicon').show(); $(this).parent().children('span.value').text($(this).children('input').val()); return false;"><input></form></td>
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

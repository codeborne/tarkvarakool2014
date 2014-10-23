<@html>

<script>
  function sendData() {
    $.post("/admin/values/modify", {goalId: 1, metricId: 1, year: 2010, value: 1},
      function(data) {
        alert(data);
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
          <div class="tableBody">
            <tr class="metric">
              <td class="name" onclick="sendData();">${metric.name}</td>
              <#--<#list minimumYear..maximumYear as year>-->
                <#--<td><#if metric.values[year]??>${metric.values[year]}</#if></td>-->
              <#--</#list>-->
            </tr>
          </div>
        </#list>
    </table>
    <br>
    <br>
  </div>
  </#list>
<button class="btn btn-default btn-sm"
        onclick="location='/admin/goals/home'; return false;">
  Tagasi
</button>
</@html>

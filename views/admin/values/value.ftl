<@html>
  <#list goals as goal>
  <div class="goal">
    <h4 class="name"> Eesmärk:${goal.name}</h4>


    <table class="table">
      <div class="tableHead">
        <tr>
          <th>Mõõdik</th>
          <th>2014</th>
          <th>2015</th>
          <th>2016</th>
          <th>2017</th>
          <th>2018</th>
          <th>2019</th>
          <th>2020</th>
        </tr>
      </div>

      <#list goal.metrics as metric>
        <div class="tableBody">
          <tr class="metric">
            <td class="name">${metric.name}</td>
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

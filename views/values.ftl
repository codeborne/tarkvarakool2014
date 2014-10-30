<@html values_active=true>
  <#list goals as goal>
  <div class="panel panel-default goal">
  <div class="goal">
    <div class="panel-heading">
      <h4 class="name"> ${goal.name}</h4>
      <div style="white-space: pre;">${goal.comment!""}</div>
      <h4 class="budget">Eelarve: €${goal.budget?c} </h4>
    </div>
    <div class="panel-body">
      <table class="table">
      <thead>
      <tr>
        <th>Mõõdik</th>
        <#list minimumYear..maximumYear as year>
          <th> ${year?c}</th>
        </#list>
      </tr>
      </thead>
      <tbody>
        <#list goal.metrics as metric>
        <tr class="metric">
          <td class="name">${metric.name} <#if metric.unit?has_content>(${metric.unit})</#if></td>
          <#list minimumYear..maximumYear as year>
            <td><span class="value">${((metric.values.get(year))?c)!""}</span></td>
          </#list>
        </tr>
        </#list>
      </tbody>
      </table>
    </div>
  </div>
  </div>
  </#list>
</@html>


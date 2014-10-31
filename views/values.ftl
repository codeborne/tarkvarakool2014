<@html values_active=true>
  <#list goals as goal>
  <div class="panel panel-default">
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
        <th>Algtase</th>
        <#list minimumYear..maximumYear as year>
          <th> ${year?c}</th>
        </#list>
        <th>Sihttase</th>
      </tr>
      </thead>
      <tbody>
        <#list goal.metrics as metric>
        <tr class="metric">
          <td class="name">${metric.name} <#if metric.unit?has_content>(${metric.unit})</#if></td>
          <td class="startLevel">${metric.startLevel?c}</td>
          <#list minimumYear..maximumYear as year>
            <td><span class="value">${((metric.values.get(year))?c)!""}</span></td>
          </#list>
          <td class="targetLevel">${metric.targetLevel?c}</td>
        </tr>
        </#list>
      </tbody>
      </table>
    </div>
  </div>
  </div>
  </#list>

</@html>


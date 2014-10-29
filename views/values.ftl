<@html values_active=true>
  <#list goals as goal>
  <div class="goal">
    <h4 class="name"> Eesmärk: ${goal.name}</h4>


    <table class="table">
      <div class="tableHead">
        <tr>
          <th>Mõõdik</th>
          <#list minimumYear..maximumYear as year>
            <th> ${year?c}</th>
          </#list>
        </tr>
        <#list goal.metrics as metric>
          <tr class="metric">
            <td class="name">${metric.name} <#if metric.unit?has_content>(${metric.unit})</#if></td>
            <#list minimumYear..maximumYear as year>
              <td><span class="value">${((metric.values.get(year))?c)!""}</span></td>
            </#list>
          </tr>
        </#list>
    </table>
    <br>
    <br>
  </div>
  </#list>
</@html>

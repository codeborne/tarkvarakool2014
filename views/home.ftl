<@html>
  <#list goals as goal>
  <div class="goal">
    <h4 class="name"> Eesmärk: ${goal.name}</h4>
    <h4 class="budget">Eelarve: €${goal.budget?c} </h4>

    <table class="table">
      <div class="tableHead">
        <tr>
          <th>Mõõdik</th>
          <th>Kirjeldus</th>
          <th>Algtase</th>
          <th>Kommentaar</th>
          <th>Sihttase</th>
          <th>Kommentaar</th>
          <th>Infoallikas</th>
        </tr>
      </div>

      <#list goal.metrics as metric>
        <div class="tableBody">
          <tr class="metric">
            <td class="name">${metric.name}</td>
            <td class="publicDescription">${metric.publicDescription}</td>
            <td class="startLevel">${metric.startLevel?c}</td>
            <td class="commantOnStartLevel">${metric.commentOnStartLevel}</td>
            <td class="targetLevel">${metric.targetLevel?c}</td>
            <td class="commentOnTargetLevel">${metric.commentOnTargetLevel}</td>
            <td class="infoSource">${metric.infoSource}</td>
          </tr>
        </div>
      </#list>
    </table>
    <br>
    <br>
  </div>
  </#list>
</@html>

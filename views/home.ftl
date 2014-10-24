<@html>
  <div class="btn-group">
    <button type="button" class="btn btn-default active" onclick="location='/home'">Eesmärgid</button>
    <button type="button" id="MetricsValue" class="btn btn-default" onclick="location='/values'">Väärtused</button>
  </div>
  <br><br>
  <#list goals as goal>
  <div class="goal">
    <h4 class="name"> Eesmärk: ${goal.name}</h4>
    <div style="white-space: pre;">${goal.comment!""}</div>
    <h4 class="budget">Eelarve: €${goal.budget?c} </h4>

    <table class="table">
      <div class="tableHead">
        <tr>
          <th>Mõõdik</th>
          <th>Kirjeldus</th>
          <th>Algtase</th>
          <th>Algtaseme kommentaar</th>
          <th>Sihttase</th>
          <th>Sihttaseme kommentaar</th>
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

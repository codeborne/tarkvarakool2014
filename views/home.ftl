<@html>
      <td>
        <div class="btn-group button-menu-inner">
          <button type="button" class="switch-button active" onclick="location='/home'">Eesmärgid</button>
          <button type="button" id="MetricsValue" class="switch-button" onclick="location='/values'">Väärtused</button>
        </div>
      </td>
    </tr>
  </table>
  <#list goals as goal>
    <div class="panel panel-default goal">
      <div class="panel-heading">
        <h4 class="name">${goal.name}</h4>
        <div style="white-space: pre;">${goal.comment!""}</div>
        <h4 class="budget">Eelarve: €${goal.budget?c} </h4>
      </div>
      <div class="panel-body">
        <table class="table">
          <thead>
            <tr>
              <th>Mõõdik</th>
              <th>Kirjeldus</th>
              <th>Algtase</th>
              <th>Algtaseme kommentaar</th>
              <th>Sihttase</th>
              <th>Sihttaseme kommentaar</th>
              <th>Infoallikas</th>
            </tr>
          </thead>
          <tbody>
          <#list goal.metrics as metric>
              <tr class="metric">
                <td class="name">${metric.name} <#if metric.unit?has_content>(${metric.unit})</#if></td>
                <td class="publicDescription">${metric.publicDescription}</td>
                <td class="startLevel">${metric.startLevel?c}</td>
                <td class="commantOnStartLevel">${metric.commentOnStartLevel}</td>
                <td class="targetLevel">${metric.targetLevel?c}</td>
                <td class="commentOnTargetLevel">${metric.commentOnTargetLevel}</td>
                <td class="infoSource">${metric.infoSource}</td>
              </tr>
          </#list>
          </tbody>
        </table>
      </div>
    </div>
  </#list>
</@html>


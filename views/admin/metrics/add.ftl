<@html>
<form method="post" class="form-horizontal">
  <h3>Lisage mõõdik</h3>
  <label>
    Mõõdik:
    <textarea name="name" class="form-control" rows="5" maxlength="255">
      <#if name??>${name}</#if>
    </textarea><br>
    Avalik kirjeldus:
    <textarea name="publicDescription" class="form-control" rows="5">
      <#if publicDescription??>${publicDescription}</#if>
    </textarea><br>
    Mitteavalik kirjeldus:
    <textarea name="privateDescription" class="form-control" rows="5">
      <#if privateDescription??>${privateDescription}</#if>
    </textarea><br>
    Algtase:
    <input type="number" class="form-control" min="0" max="2147483647" name="startLevel"
           <#if startLevel?? && (startLevel>=0)>value=${startLevel?c}</#if>><br>
    Algtaseme kommentaar:
    <textarea name="commentOnStartLevel" class="form-control" rows="5">
      <#if commentOnStartLevel??>${commentOnStartLevel}</#if>
    </textarea><br>
    Sihttase:
    <input type="number" class="form-control" min="0" max="2147483647" name="targetLevel"
           <#if targetLevel?? && (targetLevel>=0)>value=${targetLevel?c}</#if>><br>
    Sihttaseme kommentaar:
    <textarea name="commentOnTargetLevel" class="form-control" rows="5">
      <#if commentOnTargetLevel??>${commentOnTargetLevel}</#if>
    </textarea><br>
    Info allikas:
    <input type="text" name="infoSource" class="form-control" rows="5"
      <#if infoSource??>${infoSource}</#if>><br>
    Asutus, kuhu raporteerida:
    <input type="text" name="reportInstitution" class="form-control" rows="5"
      <#if reportInstitution??>${reportInstitution}</#if>><br>
  </label><br>

  <button class="btn btn-default btn-sm">Lisa</button>
</form>

  <#if errorsList?has_content>
    <#list errorsList as error>
    <div class="alert alert-danger">${error}</div>
    </#list>
  </#if>
</@html>
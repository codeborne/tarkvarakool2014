<@html>

<#if errorsList?has_content>
  <#list errorsList as error>
  <div class="alert alert-danger">${error}</div>
  </#list>
</#if>

<form method="post" class="form-horizontal">

  <input type="hidden" value="${goal.id?c}" name="goalId">
  <input type="hidden" name="orderNumber" class="form-control" value="${orderNumber}">

  <h3>${title}: ${goal.name}</h3>

  <label>
    Mõõdik:
    <textarea name="name" class="form-control" rows="5" maxlength="255"><#if name??>${name}</#if></textarea>
  </label>
  <br>
  <label>
    Ühik:
    <textarea name="unit" class="form-control" rows="1"><#if unit??>${unit}</#if></textarea>
  </label>
  <br>
  <label>
    Avalik kirjeldus:
    <textarea name="publicDescription" class="form-control" rows="5"><#if publicDescription??>${publicDescription}</#if></textarea><br>
  </label>
  <br>
  <label>
    Mitteavalik kirjeldus:
    <textarea name="privateDescription" class="form-control" rows="5"><#if privateDescription??>${privateDescription}</#if></textarea>
  </label>
  <br>
  <label>
    Algtase:
    <input type="number" class="form-control" min="0" max="2147483647" name="startLevel"<#if startLevel?? && (startLevel>=0)>value=${startLevel?c}</#if>>
  </label>
  <br>
  <label>
    Algtaseme kommentaar:
    <textarea name="commentOnStartLevel" class="form-control" rows="5"><#if commentOnStartLevel??>${commentOnStartLevel}</#if></textarea>
  </label>
  <br>
  <label>
    Sihttase:
    <input type="number" class="form-control" min="0" max="2147483647" name="targetLevel"<#if targetLevel?? && (targetLevel>=0)>value=${targetLevel?c}</#if>>
  </label>
  <br>
  <label>
    Sihttaseme kommentaar:
    <textarea name="commentOnTargetLevel" class="form-control" rows="5"><#if commentOnTargetLevel??>${commentOnTargetLevel}</#if></textarea>
  </label>
  <br>
  <label>
    Infoallikas:
    <input type="text" name="infoSource" class="form-control" value="<#if infoSource??>${infoSource}</#if>">
  </label>
  <br>
  <label>
    Asutus, kuhu raporteerida:
    <input type="text" name="institutionToReport" class="form-control" value="<#if institutionToReport??>${institutionToReport}</#if>"><br>
  </label>
  <br>

  <button class="btn btn-default btn-sm submitButton" type="submit" class="btn btn-default btn-sm">
  ${buttonTitle}
  </button>
  <button class="btn btn-default btn-sm" onclick="location='/admin/metrics/metrics?goalId=${goal.id?c}'; return false;">
    Tagasi
  </button>
  <br><br>

</form>

</@html>
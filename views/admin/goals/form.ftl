<@html>
<form method="post" class="form-horizontal">
    <h3>${title}</h3>
    <label>
        Eesmärk:
        <textarea name="name" class="form-control" rows="5" maxlength="255">${name!""}</textarea>
    </label><br>
    <label>
      Kommentaar:
      <textarea name="comment" class="form-control" rows="5" maxlength="255">${comment!""}</textarea>
    </label><br>
    <label>Eelarve: <input type="number" class="form-control" min="1" max="2147483647" name="budget" <#if budget?? && (budget>0)>value=${budget?c}</#if>></label><br>
  <button id="goalAddOrModifyButton" class="btn btn-default btn-sm">${buttonTitle}</button>
  <button id="goBack" class="btn btn-default btn-sm" onclick="location='/admin/goals/home'; return false;">Tagasi</button>
</form>

<#if errorsList?has_content>
  <#list errorsList as error>
    <div class="alert alert-danger">${error}</div>
  </#list>
</#if>
</@html>
<@html>
<form method="post" class="form-horizontal">
    <h3>${title}</h3>
    <label>
      Eesmärk:
        <textarea name="name" class="form-control" rows="5" maxlength="255"><#if name??>${name}</#if></textarea>
    </label><br>
    <label>Eelarve: <input type="number" class="form-control" min="1" max="2147483647" name="budget" <#if budget?? && (budget>0)>value=${budget?c}</#if>></label><br>
    <button class="btn btn-default btn-sm">${buttonTitle}</button>
</form>

<#if errorsList?has_content>
  <#list errorsList as error>
    <div class="alert alert-danger">${error}</div>
  </#list>
</#if>

</@html>
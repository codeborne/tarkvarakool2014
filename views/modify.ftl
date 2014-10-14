<@html>
<form method="post">
    <h3>Muuda eesmärk</h3>
    <label>
        Eesmärk:
        <textarea name="name" class="form-control" rows="5" maxlength="255"><#if name??>${name}</#if></textarea>
    </label><br>
    <label>Eelarve: <input type="number" class="form-control" name="budget" <#if budget??>value=${budget?c}</#if>></label><br>
    <button class="form-control">Muuda</button>
</form>

<#if errorsList?has_content>
  <#list errorsList as error>
    <div class="alert alert-danger">${error}</div>
  </#list>
</#if>

</@html>
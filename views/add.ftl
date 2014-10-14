<@html>
<form method="post">
    <h3>Create a new goal</h3>
    <label>
        Goal:
        <textarea name="name" class="form-control" rows="5" maxlength="255"><#if name??>${name}</#if></textarea>
    </label><br>
    <label>Budget: <input type="number" class="form-control" name="budget" <#if budget??>value=${budget}</#if>></label><br>
    <button class="form-control">Submit</button>
</form>

<#if errorsList?has_content>
  <#list errorsList as error>
    <div class="alert alert-danger">${error}</div>
  </#list>
</#if>

</@html>
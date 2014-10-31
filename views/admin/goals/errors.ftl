<#if errorsList?has_content>
  <#list errorsList as error>
    <div class="alert alert-danger">${error}</div>
  </#list>
</#if>
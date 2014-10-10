<@html>

Tere, mis sinu nimi on?
<form method="post">
  <input type="text" name="firstName">
  <input type="text" name="lastName">
  <button>submit</button>
</form>

<#if fullName??>
  <h1>Tere ${fullName}</h1>
</#if>

</@html>

<@html>

Tere, mis sinu nimi on?
<form method="post">
  <input type="text" name="name">
  <button>submit</button>
</form>

<#if name??>
  <h1>Tere ${name}</h1>
</#if>

</@html>

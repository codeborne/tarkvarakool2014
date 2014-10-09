<@html>

Tere, mis sinu nimi on?
<form method="post">
  <input type="text" name="name">
  <button>submit</button>
</form>

<#if megaName??>
  <h1>Tere ${megaName}</h1>
</#if>

</@html>

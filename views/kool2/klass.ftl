<html>
<head>
    <title>Veebileht</title>
</head>

<body>

<form method="post">

    <h1>Tere kasutaja</h1>
    <label>Mis su nimi on? <input name="name"></label><br>
    <label>Mis su perekonna nimi on? <input name="familyName"></label><br>
    <button>Send</button>

    <#if name??>
        Hi, ${name} ${familyName}
    </#if>
</form>

</body>

</html>

<html>
<head>
    <title>Calculator</title>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/kool2.css"/>
</head>
<body>
<form method="post" class="form-inline" role="form">
    <input value="${currentValue}" readonly class="form-control">
    <select name="operator" class="form-control">
        <option value="+"<#if operator="+"> selected</#if>>+</option>
        <option value="-"<#if operator="-"> selected</#if>>-</option>
        <option value="*"<#if operator="*"> selected</#if>>*</option>
        <option value="/"<#if operator="/"> selected</#if>>/</option>
        <option value="^"<#if operator="^"> selected</#if>>^</option>
    </select>
    <input name="operand" class="form-control">
    <button name="submit" value="true" class="form-control">=</button>
    <button class="form-control" onclick="location='reset'; return false;">Reset</button>

<#if error??>
    <div class="alert alert-danger alert-error fade in" data-dismiss="alert">
        <div class="close">&times;</div>
    ${error}
    </div>
</#if>

</form>
</body>
</html>


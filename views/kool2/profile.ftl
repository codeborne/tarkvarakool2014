<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>Tarkvara kool</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>
<body>

<#if accessAllowed>

<div style="margin: 50px;">
    Hi, ${username}<br>
    Your gender is: ${sex}<br>
    ${likeJava}<br>
    Your favourite color is ${colors}
</div>

</#if>

</body>
</html>


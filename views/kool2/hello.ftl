<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>Tarkvara kool</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>
<body>

<#if accessAllowed>

<form class="form-horizontal" role="form" method="post" action="profile">
    <div class="row">
        <div class="col-sm-offset-2 col-md-10">
            <h4>Hi, <strong style="color: green;">${username}</strong>. Please tell us about yourself.</h4>
        </div>
    </div>
    <div class="row">
        <label for="male" class="col-sm-offset-1 col-md-1 control-label">Male:</label>

        <div class="col-md-1"><input type="radio" value="male" name="sex" id="male" class="form-control" checked></div>
    </div>
    <div class="row">
        <label for="female" class="col-sm-offset-1 col-md-1 control-label">Female:</label>

        <div class="col-md-1"><input type="radio" value="female" name="sex" id="female" class="form-control"></div>
    </div>
    <div class="row">
        <label for="java" class="col-sm-offset-1 col-md-1 control-label">I love Java</label>

        <div class="col-md-1"><input type="checkbox" name="java" id="java" value="true" class="form-control" checked></div>
    </div>
    <div class="row">
        <label for="java" class="col-sm-offset-1 col-md-1 control-label">Choose your favourite color</label>

        <div class="col-md-2">
            <select name="colors" class="form-control">
                <option value="blue">Blue</option>
                <option value="red">Red</option>
                <option value="yellow">Yellow</option>
            </select>
        </div>
    </div>
    <div class="col-sm-offset-2 col-md-12" style="margin-top: 15px;">
        <button type="submit" class="btn btn-default" name="send" value="true">Send</button>
    </div>
    <input type="hidden" name="username" value="${username}">
    <input type="hidden" name="password" value="${password}">
</form>

<#else>

<form class="form-horizontal" role="form" method="post">
    <div class="row">
        <div class="col-sm-offset-2 col-md-10">
            <h4>Please login</h4>
        </div>
    </div>
    <div class="row">
        <label for="username" class="col-md-2 control-label">Username:</label>

        <div class="col-md-4"><input type="text" name="username" id="username" class="form-control"
                                     placeholder="Your name">
        </div>
    </div>
    <div class="row">
        <label for="password" class="col-md-2 control-label">Password:</label>

        <div class="col-md-4"><input type="password" name="password" id="password" class="form-control"
                                     placeholder="Your password">
        </div>
    </div>
    <div class="col-sm-offset-2 col-md-12" style="margin-top: 15px;">
        <button type="submit" class="btn btn-default" name="login" value="true">Login</button>
    </div>
    <#if login>
        <div class="row">
            <div class="col-sm-offset-2 col-md-4" style="margin-top: 15px;">
                <div class="alert alert-danger" role="alert">Wrong username or password</div>
            </div>
        </div>
    </#if>

</form>

</#if>

</body>
</html>


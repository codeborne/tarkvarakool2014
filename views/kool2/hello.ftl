<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>Tarkvara kool</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>
<body>

<div class="row">
    <div class="col-sm-offset-2 col-md-10">
        <p>Please tell us about yourself.</p>
    </div>
</div>
<form class="form-horizontal" role="form" method="post">
    <div class="row">
        <label for="name" class="col-md-2 control-label">Name:</label>

        <div class="col-md-4"><input type="text" name="name" id="name" class="form-control" placeholder="Your name">
        </div>
    </div>
    <div class="row">
        <label for="male" class="col-sm-offset-1 col-md-1 control-label">Male:</label>
        <div class="col-md-1"><input type="radio" value="male" name="sex" id="male" class="form-control"></div>
    </div>
    <div class="row">
        <label for="female" class="col-sm-offset-1 col-md-1 control-label">Female:</label>
        <div class="col-md-1"><input type="radio" value="female" name="sex" id="female" class="form-control"></div>
    </div>
        </div>
    <div class="row">
        <label for="java" class="col-sm-offset-1 col-md-1 control-label">I love Java</label>
        <div class="col-md-1"><input type="checkbox" name="java" id="java" value="like" class="form-control"></div>
    </div>
    <div class="row">
        <label for="java" class="col-md-2 control-label">Choose your favourite color</label>
        <div class="col-md-2">
            <select name="colors" class="form-control">
                <option value="blue">Blue</option>
                <option value="red">Red</option>
                <option value="yellow">Yellow</option>
            </select>
           </div>
    </div>
    <div class="col-sm-offset-2 col-md-12" style="margin-top: 15px;">
        <button type="submit" class="btn btn-default" name="submit">Send</button>
    </div>

</form>

<#if submit="true">
<p>Tere, ${name}<br>
    Your gender is: ${gender}<br>
    ${likeJava}<br>
    Your favourite color is ${colors}
</p>
</#if>
</div>
</div>

<form class="form-horizontal" role="form">
    <div class="form-group">
    <#--<label for="inputEmail3" class="col-sm-2 control-label">Email</label>-->
        <#--<div class="col-sm-10">-->
            <#--<input type="email" class="form-control" id="inputEmail3" placeholder="Email">-->
        <#--</div>-->
    </div>
    <div class="form-group">
    <#--<label for="inputPassword3" class="col-sm-2 control-label">Password</label>-->
        <#--<div class="col-sm-10">-->
            <#--<input type="password" class="form-control" id="inputPassword3" placeholder="Password">-->
        <#--</div>-->
    </div>
    <div class="form-group">
    <#--<div class="col-sm-offset-2 col-sm-10">-->
            <#--<div class="checkbox">-->
                <#--<label>-->
                    <#--<input type="checkbox"> Remember me-->
                <#--</label>-->
            <#--</div>-->
        <#--</div>-->
    </div>
    <div class="form-group">
    <#--<div class="col-sm-offset-2 col-sm-10">-->

        <#--</div>-->
    </div>
</form>

</body>
</html>


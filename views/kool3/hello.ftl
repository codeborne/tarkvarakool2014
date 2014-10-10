
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

<@html>
<body id="back">

<div id="question">
Hello,what is your name?

<form method="post">
    <input type="text" name="name"> <br>
Please enter your e-mail. <br>
    <input type="email" name="email"> <br>
    <button>submit</button>

</form>
    <#if name??>Hello, ${name}</#if> <br>
    <#if email??> Your e-mail is ${email}</#if>

    <div class="row">
        <div class="col-lg-6">
            <div class="input-group">
      <span class="input-group-addon">
        <input type="checkbox">
      </span>
                Remember me!
            </div><!-- /input-group -->
        </div><!-- /.col-lg-6 -->
        <div class="col-lg-6">
    </div>
        <a href="https://www.google.com">
        <button type="button" class="btn btn-default btn-lg" >
            <span class="glyphicon glyphicon-star"></span> Star
        </button>
            </a>

        <a href="http://www.villathai.ee">
        <button type="button" class="btn btn-default btn-lg">
            <span class="glyphicon glyphicon-cutlery"></span> Dinner
        </button>
            </a>
        <a href="http://www.aliexpress.com">
        <button type="button" class="btn btn-default btn-lg">
            <span class="glyphicon glyphicon-shopping-cart"></span> Purchase
        </button>
            </a>

        <a href="http://www.jetairways.com">
        <button type="button" class="btn btn-default btn-lg">
            <span class="glyphicon glyphicon-plane"></span> Vacation
        </button>
            </a>

        <iframe width="560" height="315" src="//www.youtube.com/embed/x2snKVh6XZI" frameborder="0" allowfullscreen></iframe> <br>

        <button type="button" class="btn btn-default navbar-btn">Sign in</button>

</div>
</body>
</@html>


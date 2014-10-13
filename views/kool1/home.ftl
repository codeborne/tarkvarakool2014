<@html>
<head><!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
</head>
<body>


<div id="main">

    <h1>Hello world!</h1>

    <p>Here you can use Google</p>

    <div>
        <a href="http://www.google.com"><img src="http://i.forbesimg.com/media/lists/companies/google_416x416.jpg"/></a>
    </div>

    <a href="/kool1/nextpage">Teisele lehele</a>

</div>

<form class="form-horizontal" role="form" method="post">
    <div class="form-group">
        <label for="inputText3" class="col-sm-2 control-label">Kasutajanimi</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="inputText3" placeholder="Kasutajanimi" name="username">
        </div>
    </div>
    <div class="form-group">
        <label for="inputPassword3" class="col-sm-2 control-label">Salasõna</label>
        <div class="col-sm-10">
            <input type="password" class="form-control" id="inputPassword3" placeholder="Salasõna" name="password">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <div class="checkbox">
                <label>
                    <input type="checkbox"> Jäta meelde
                </label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default">Logi sisse</button>
        </div>
    </div>
</form>

  <p class="warning"><#if warning??>${warning}</#if></p>
</body>
</@html>
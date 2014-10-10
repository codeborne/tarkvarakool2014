<@html>
<head><!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
</head>
<body>
<div id="submit">
<form method="post">
    Name:
    <input type="text" name="name"><br>
    Surname:
    <input type="text" name="surname"><br>
    <button>Submit</button>
</form>

    <p id="tere"><#if name??>Tere, ${name} ${surname}</#if></p>
</div>

<div id="main">

    <h1>Hello world!</h1>

    <p>Here you can use Google</p>

    <div>
        <a href="http://www.google.com"><img src="http://i.forbesimg.com/media/lists/companies/google_416x416.jpg"/></a>
    </div>

    <a href="http://localhost:8080/kool1/nextpage">Teisele lehele</a>

</div>

<form class="form-horizontal" role="form" action="http://localhost:8080/kool1/nextpage" onsubmit="">
    <div class="form-group">
        <label for="inputEmail3" class="col-sm-2 control-label">E-mail</label>
        <div class="col-sm-10">
            <input type="email" class="form-control" id="inputEmail3" placeholder="Email">
        </div>
    </div>
    <div class="form-group">
        <label for="inputPassword3" class="col-sm-2 control-label">Salasõna</label>
        <div class="col-sm-10">
            <input type="password" class="form-control" id="inputPassword3" placeholder="Salasõna">
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
</body>
</@html>
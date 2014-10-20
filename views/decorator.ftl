<#macro html>
<!DOCTYPE html>
<html>
<head>
  <title>Tarkvarakool</title>
  <link rel="shortcut icon" href="/favicon.png" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="/styles.css"/>
</head>
<body>
  <div class="container theme-showcase" role="main">
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Eesmärgid</a>
        </div>
        <div class="navbar-form navbar-collapse collapse navbar-right">
          <#if loggedInUsername??>
            Tere, ${loggedInUsername}<br>
            <button type="submit" class="btn-sm btn-success" onclick="location='/admin/logout'">Logi valja</button>
          <#else>
            <button type="submit" class="btn-sm btn-success" onclick="location='/admin/login'">Logi sisse</button>
          </#if>
        </div>
      </div>
    </div>

    <#nested>
  </div>
</body>
</html>
</#macro>

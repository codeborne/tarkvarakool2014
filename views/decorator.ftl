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
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <div class="navbar-brand">Eesmärgid</div>
        </div>
        <div class="navbar-collapse collapse navbar-right">
          <#if loggedInUsername??>
            <span class="greetings">Tere, ${loggedInUsername}</span>
            <form class="navbar-form navbar-left" action="/admin/logout">
              <button id="logout-button" type="submit" class="btn-sm btn-success">Logi valja</button>
            </form>
          <#else>
            <form class="navbar-form navbar-left" action="/admin/login">
              <button type="submit" class="btn-sm btn-success">Logi sisse</button>
            </form>
          </#if>
        </div>
      </div>
    </div>

    <#nested>
  </div>
</body>
</html>
</#macro>

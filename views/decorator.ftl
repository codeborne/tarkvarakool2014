<#macro html>
<!DOCTYPE html>
<html>
<head>
  <title>Infoühiskonna arendamise mõõdikud</title>
  <link rel="shortcut icon" href="/favicon.png" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="/styles.css"/>
  <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
</head>
<body>
  <div class="navbar navbar-default" role="navigation">
    <div class="container">
      <div class="navbar-left title">Infoühiskonna arendamise mõõdikud</div>
      <div class="navbar-collapse collapse navbar-right">
        <#if loggedInUsername??>
          <form class="navbar-form navbar-left" action="/admin/logout">
            <span class="greetings">Tere, ${loggedInUsername}</span>
            <button id="logout-button" type="submit" class="btn-sm btn-danger logout-button">Logi välja</button>
          </form>
        <#else>
          <form class="navbar-form navbar-left" action="/admin/login">
            <button id="login-button" type="submit" class="btn-sm btn-success">Logi sisse</button>
          </form>
        </#if>
      </div>
    </div>
  </div>

  <div class="container theme-showcase" role="main">
    <#nested>
  </div>
</body>
</html>
</#macro>

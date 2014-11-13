<#macro html values_active=false>
<!DOCTYPE html>
<html>
<head>
  <title><@m'title'/></title>
  <link rel="shortcut icon" href="/favicon.png"/>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="/styles.css"/>
  <link href='http://fonts.googleapis.com/css?family=Roboto+Condensed:400,300,700,400italic,700italic,300italic'
        rel='stylesheet' type='text/css'>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
  <script type="text/javascript" src="https://www.google.com/jsapi"></script>
  <script type="text/javascript"></script>

</head>
<body>
<div class="navbar navbar-default" role="navigation">
  <div class="container container-top">

    <div class="navbar-collapse collapse navbar-right">

      <div class="languageButtons btn-group button-menu-inner pull-left">
        <a href="/language?locale=en" class="language-button-eng<#if language == 'en'> active</#if>">ENG</a>
        <a href="/language?locale=et" class="language-button-est<#if language == 'et'> active</#if>">EST</a>
      </div>

      <#if loggedInUsername??>
        <form class="navbar-form pull-left" action="/admin/logout">
          <span class="greetings"><@m'hello'/> <strong>${loggedInUsername}</strong></span>
          <button id="logout-button" type="submit" class="authentication-button">
            <span class="glyphicon glyphicon-lock"></span> <@m'exit'/>
          </button>
        </form>
      <#else>
        <form class="navbar-form pull-left" action="/admin/login">
          <button id="login-button" type="submit" class="authentication-button">
            <span class="glyphicon glyphicon-lock"></span><@m'login'/>
          </button>
        </form>
      </#if>
    </div>
  </div>
</div>

<div class="container main-content" role="main">
  <table class="toprow">
    <tr>
      <td><img src="/images/rm-logo.png"></td>
      <td>
        <div class="symbol"></div>
      </td>
      <td class="title"><@m'title'/></td>
      <td>
        <div class="btn-group button-menu-inner">
          <button type="button" class="switch-button<#if !values_active> active</#if>" onclick="location='${homeUrl}'">
            <@m'goals'/>
          </button>
          <button type="button" id="MetricsValue" class="switch-button<#if values_active> active</#if>"
                  onclick="location='${valuesUrl}'"><@m'values'/>
          </button>
        </div>
      </td>
    </tr>
  </table>
  <#nested>
</div>

</body>
<div class="footer"></div>
</html>
</#macro>

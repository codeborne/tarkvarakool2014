<#setting locale="et">
<#setting number_format=",##0.##">
<#macro html values_active=false>
<!DOCTYPE html>
<html lang="${language}">
<head>
  <title><@m'title'/></title>
  <link rel="shortcut icon" href="/favicon.png"/>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="/styles.css"/>
  <link rel="stylesheet" type="text/css" href="/print.css" media="print">
  <link href='https://fonts.googleapis.com/css?family=Roboto+Condensed:400,300,700,400italic,700italic,300italic'
        rel='stylesheet' type='text/css'>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
  <script type="text/javascript" src="https://www.google.com/jsapi"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="wrapper">
  <div class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container container-top">
      <div class="navbar-right">
        <#if loggedInUsername??>
          <div class="languageButtons btn-group button-menu-inner pull-left">
            <button id="userViewButton" type="submit"
                    class="switch-view-button pull-left <#if !homeUrl?contains("admin")>active</#if>"
                    onclick="location='/home'"><@m'userView'/></button>
            <button id="adminViewButton" type="submit"
                    class="switch-view-button pull-left <#if homeUrl?contains("admin")>active</#if>"
                    onclick="location='/language?locale=changeLanguage'"><@m'adminView'/></button>
          </div>
        </#if>
        <#if !homeUrl?contains("admin")>
          <div class="languageButtons btn-group button-menu-inner pull-left">
            <a href="/language?locale=en" class="language-button-eng<#if language == 'en'> active</#if>">ENG</a>
            <a href="/language?locale=et" class="language-button-est<#if language == 'et'> active</#if>">EST</a>
          </div>
        </#if>
        <a href="/all-data" class="pull-left glyphicon glyphicon-download" title="<@m'downloadAllData'/>"><span class="sr-only">"<@m'downloadAllData'/>"</span></a>
        <#if loggedInUsername??>
          <a <#if admin?? && loggedInUsername == admin> href="/admin/settings" <#else>
                                                        href="/admin/user/changepassword"</#if>
                                                        class="pull-left glyphicon glyphicon-wrench"
                                                        title="<@m'settings'/>"></a>



          <form class="pull-left" action="/admin/logout">
            <span class="greetings"><@m'hello'/>&nbsp; <strong>${loggedInUsername}</strong></span>
            <button id="logout-button" type="submit" class="authentication-button">
              <span class="glyphicon glyphicon-lock"></span> <@m'exit'/>
            </button>
          </form>
        <#else>
          <form class="pull-left" action="/admin/login">
            <button id="login-button" type="submit" class="authentication-button">
              <span class="glyphicon glyphicon-lock"></span><@m'login'/>
            </button>
          </form>
        </#if>
      </div>
    </div>
  </div>
  <div class="header-wrapper">
  <div class="main-header" data-spy="affix" data-offset-top="40">
    <div class="container">
    <table class="toprow">
          <tr>
            <td><a href='${homeUrl}'><#if language == 'et'><img src="/images/logo.png" alt="Majandus- ja kommunikatsiooniministeerium" class="logo"><#elseif language == 'en'><img src="/images/logo_eng.png" alt="Republic of Estonia Ministry of Economic Affairs and Communications" class="logo_eng"></#if></a></td>
            <td class="title"><@m'title'/></td>
            <td>
              <div class="btn-group button-menu-inner">
                <button type="button" class="switch-button<#if !values_active> active</#if>"
                        onclick="location='${homeUrl}'">
                  <@m'goals'/>
                </button>
                <button type="button" id="MetricsValue" class="switch-button<#if values_active> active</#if>"
                        onclick="location='${valuesUrl}'"><@m'results'/>
                </button>
              </div>
            </td>
          </tr>
        </table>
    </div>
  </div>
  </div>
  <div class="container main-content" role="main">
    <#nested>
  </div>
</div>
<div class="footer">
 <div class="container">
 </div>
</div>
</body>
</html>
</#macro>


<@html>
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
    <div class="navbar-collapse collapse">
      <form class="navbar-form navbar-right" role="form">
        <div class="form-group">
          <input type="text" placeholder="Kasutajanimi" class="form-control">
        </div>
        <div class="form-group">
          <input type="password" placeholder="Salasõna" class="form-control">
        </div>
        <button type="submit" class="btn btn-success">Logi sisse</button>
      </form>
    </div>
  </div>
</div>
  <#if goals?has_content>
  <h3>Eesmärgid:</h3>


  <table class="table table-hover">
    <tr>
      <th>Eesmärk</th>
      <th>Eelarve</th>
      <th>Lisa mõõdik</th>
      <th>Muuda</th>
      <th>Kustuta</th>
    </tr>
    <#list goals as goal>
      <tr>
        <td>${goal.name}</td>
        <td>${goal.budget?c}</td>
        <td><form action="/admin/metrics/add">
          <input type="hidden" value="${goal.id}" name="goalId">
          <button type="submit" class="btn btn-default btn-sm">
            <span class="glyphicon glyphicon-list-alt"></span>
          </button>
        </form></td>
        <td>
          <form action="modify">
            <input type="hidden" value="${goal.id}" name="id">
            <button type="submit" class="btn btn-default btn-sm">
              <span class="glyphicon glyphicon-pencil"></span>
            </button>
          </form>
        </td>
        <td>
          <form action="delete" method="post" onsubmit="return confirm('Kas oled kustutamises kindel?')">
            <input type="hidden" name="id" value="${goal.id}"/>
            <button type="submit" class="btn btn-default btn-sm">
              <span class="glyphicon glyphicon-trash"></span>
            </button>
          </form>
        </td>
      </tr>
    </#list>
  </table>
  <#else>
  <h3>Andmebaasis eesmärke ei ole.</h3>
  </#if>
<form action="add">
  <input type="submit" class="btn btn-default" value="Lisa">
</form>

</@html>


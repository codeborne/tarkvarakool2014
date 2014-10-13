<html>
<head>
    <title>Add goal</title>
    <style>
        .error {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
<form method="post">
    <h3>Create a new goal</h3>
    <label>Goal: <input type="text" name="name"></label><br>
    <label>Budget: <input type="text" name="budget"></label><br>
    <button>Submit</button>
</form>

<#if goals?has_content>
  <h3>Current list of goals:</h3>
  <table>
      <tr>
          <th>Goal</th>
          <th>Budget</th>
      </tr>
    <#list goals as goal>
        <tr>
            <td>${goal.name}</td>
            <td>${goal.budget}</td>
        </tr>
    </#list>
  </table>
<#else>
  <h3>There are no goals in the database yet</h3>
</#if>


<#if errorsList?has_content>
  <#list errorsList as error>
    <div class="error">${error}</div>
  </#list>
</#if>

</body>
</html>
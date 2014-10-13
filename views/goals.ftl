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

<#if errors??>
<div class="error">errors</div>
</#if>

</body>
</html>
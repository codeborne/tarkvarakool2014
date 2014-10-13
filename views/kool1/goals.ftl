<@html>
<ul>
  <#list allGoals as goal>
    <li>
      <span>${goal.goalText}</span>
      <span>${goal.budget}</span>
    </li>
  </#list>
</ul>

<form method="post">
  <p>Sisesta eesmärk</p>
  <textarea name="insertedGoal" maxlength="250"></textarea>
  <p>Sisesta eelarve</p>
  <input type="number" name="insertedBudget"><br>
  <input type="submit" value="Salvesta">
</form>

</@html>
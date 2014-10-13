<@html>
<ul>
  <#if allGoals??>
    <#list allGoals as goal>
      <li>
        <span>${goal.goalText}</span>
        <span>${goal.budget}</span>
      </li>
    </#list>
  </#if>
</ul>

<form method="post">
  <p>Sisesta eesmärk</p>
  <textarea name="insertedGoal" maxlength="250"><#if insertedGoal??>${insertedGoal}</#if></textarea>

  <p>Sisesta eelarve</p>
  <input type="number" name="insertedBudget" <#if insertedBudget??>value=${insertedBudget}</#if>><br>
  <input type="submit" value="Salvesta">
</form>
  <#if message??>${message}</#if>
</@html>


<@html>

  <#if allGoals??>
    <table class="table table-hover">
      <tr>
        <th>Eesmärk</th>
        <th>Eelarve</th>
      </tr>
    <#list allGoals as goal>
      <tr>
        <td>${goal.goalText}</td>
        <td>${goal.budget}</td>
      </tr>
    </#list>
  </table>
  </#if>


<form method="post">
  <p>Sisesta eesmärk</p>
  <textarea name="insertedGoal" class="form-control" rows="5" maxlength="255"><#if insertedGoal??>${insertedGoal}</#if></textarea>

  <p>Sisesta eelarve</p>
  <input type="number" name="insertedBudget" <#if insertedBudget??>value=${insertedBudget}</#if>><br>
  <input type="submit" value="Salvesta">
</form>

<#if message??><p class="alert alert-danger">${message}</p></#if>
</@html>


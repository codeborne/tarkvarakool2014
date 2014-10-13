<@html>


<form method="post">
  <h2>Sisestage eesmärk</h2><br>
  <label> Eesmärk<input type="text" name="goal"></label><br>

  <h2>Sisestage eelarve summa</h2><br>
  <label> Eelarve<input type="text" name="sum"> </label><br>
  <button>Sisesta</button>

</form> <br>

Sisestatud eesmärgid:


  <#if goals??>

  <table>
    <tr>
      <th>Eesmärk</th>
      <th>Summa</th>
    </tr>

    <#list goals as goal>

      <tr>
        <td>${goal.goal}</td>
        <td>${goal.sum}</td>
      </tr>
    </#list>
  </table>
  </#if>






</@html>
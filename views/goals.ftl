<@html>


<form method="post" xmlns="http://www.w3.org/1999/html">

  <label> Eesmärk<textarea name="goal" placeholder="Sisestage eesmärk"></textarea></label><br>
  <label> Eelarve<input type="text" name="sum" placeholder="Sisestage eelarve summa"></label><br>
  <button>Sisesta</button><br>
  <#if warning??>${warning}</#if><br
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
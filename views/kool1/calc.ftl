<@html>
  <#if calculator.result??>${calculator.result}</#if><br>
  <#if note??>${note}</#if><br>

<form method="post">

  <div class="radioButton"><input type="radio" name="operator" value="+">+</div>
  <div class="radioButton"><input type="radio" name="operator" value="-">-</div>
  <div class="radioButton"><input type="radio" name="operator" value="*">*</div>
  <div class="radioButton"><input type="radio" name="operator" value="/">/</div>
  <div class="radioButton"><input type="radio" name="operator" value="^">^</div>
  <br>

  <input type="number" name="operand" placeholder="Sisesta number"> <br>
  <input type="submit" value="Arvuta!">


</form>



</@html>
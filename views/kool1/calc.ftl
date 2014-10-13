<@html>
  <#if calculator.result??>${calculator.result}</#if><br>
  <p class="alert"><#if note??>${note}</#if></p><br>

<form method="post">

  <div class="radioButton"><input type="radio" name="operator" value="+">+</div>
  <div class="radioButton"><input type="radio" name="operator" value="-">-</div>
  <div class="radioButton"><input type="radio" name="operator" value="*">*</div>
  <div class="radioButton"><input type="radio" name="operator" value="/">/</div>
  <div class="radioButton"><input type="radio" name="operator" value="^">^</div>
  <br>

  <input type="number" name="operand" placeholder="Sisesta number"> <br>
  <br>
  <p class="alert"><#if message??>${message}</#if></p>

  <input type="submit" value="Arvuta!"></form>
  <div id="reset"><a href="reset" id="resetlink">Reset</a></div>






</@html>
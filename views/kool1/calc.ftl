<@html>
  <p id="result"><#if calculator.result??>${calculator.result}</#if></p><br>
<p class="alert"><#if message??>${message}</#if></p>

<form method="post">

  <div class="radioButton"><input type="radio" name="operator" value="+">+</div>
  <div class="radioButton"><input type="radio" name="operator" value="-">-</div>
  <div class="radioButton"><input type="radio" name="operator" value="*">*</div>
  <div class="radioButton"><input type="radio" name="operator" value="/">/</div>
  <div class="radioButton"><input type="radio" name="operator" value="^">^</div>
  <br>

  <input type="number" name="operand" placeholder="Sisesta number"> <br>



  <input type="submit" value="Arvuta!"></form>
<div id="reset"><a href="reset" id="resetLink">Reset</a></div>

</@html>
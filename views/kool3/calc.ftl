<@html>
<h1>
  Calculator
</h1>
<h2>Let's start!</h2>

  <#if calculator.currentValue??> ${calculator.currentValue}</#if>



<form method="post">
  <#--<input type="radio" name="gender" value="male"> Male<br>-->

  <input type="radio" name="operator" value="+">+
  <input type="radio" name="operator" value="-">-
  <input type="radio" name="operator" value="*">*
  <input type="radio" name="operator" value="/">/
  <br>

<input type="number" name="operand" > <br>
<#if divisorIsZero>Cannot divide by zero, please try again, number you see is previous result!</#if><br>
<#if warning??>${warning}</#if><br>
   <input type="submit" value="Submit"><br>


</form>

<form method="get" action="reset">
  <button>Reset</button>
</form>

<button onclick="javascript: location='reset';">test</button>

</@html>
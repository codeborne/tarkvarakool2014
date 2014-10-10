<@html>

<form method="post">
    <input value="${currentValue}" readonly>
    <select name="operator">
        <option value="+">+</option>
        <option value="-">-</option>
        <option value="*">*</option>
        <option value="/">/</option>
        <option value="^">^</option>
    </select>
    <input name="operand">
    <button name="submit" value="true">=</button>
    <button name="reset" value="true">Reset</button>

    <#if error??><br><div style="color: red;">${error}</div></#if>
</form>
</@html>
<@html>
<div id="submit">
  <form method="post">
    Name:
    <input type="text" name="name"><br>
    Surname:
    <input type="text" name="surname"><br>
    <button>Submit</button>
  </form>

  <p id="hello"><#if name?? && surname??>Tere, ${name} ${surname}</#if></p>
</div>

<ul>
    <li>Nr 1</li>
    <li>Nr 2</li>
</ul>

<a href="http://www.postimees.ee"><div class="button">Postimees</div></a>
<a href="http://www.delfi.ee"><div class="button">Delfi</div></a>
<a href="http://www.facebook.com"><div class="button">Facebook</div></a>
<a href="http://www.ilm.ee"><div class="button" id="weirdButton">Ilm.ee</div></a>
<a href="http://www.google.com"><div class="button">Google</div></a>


</@html>
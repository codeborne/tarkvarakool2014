<@html>

<div id="submit">
<form method="post">
    Name:
    <input type="text" name="name"><br>
    Surname:
    <input type="text" name="surname"><br>
    <button>Submit</button>
</form></div>

    <#if name??>Tere, ${name} ${surname}</#if>


<div id="main">

    <h1>Hello world!</h1>

    <p>Here you can use Google</p>

    <div>
        <a href="http://www.google.com"><img src="http://i.forbesimg.com/media/lists/companies/google_416x416.jpg"/></a>
    </div>

    <a href="http://localhost:8080/kool1/nextpage">Teisele lehele</a>

</div>
</@html>
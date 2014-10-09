<!DOCTYPE html>
<html>
<head>
    <title>Tarkavarakool</title>
    <style>
        body {
            background-color: cadetblue;
        }

        #apple {
            color: green;
        }
        #orange {
            font-family: Arial;
        }
        .fancy {
            font-weight: bold;
        }

        #container {
            text-align: center;
        }

        #boxes {
            display: inline-block;
        }

        #boxes div {
            border: 1px solid black;
            -webkit-border-radius: 10px;
            -moz-border-radius: 10px;
            border-radius: 10px;
            height: 100px;
            width: 100px;
        }

        .first, .second, .third {
            float: left;
        }

        .first {
            background-color: blue;

        }
        .second{
            background-color: red ;

        }
        .third {
            background-color: greenyellow;
        }

        #boxes div:nth-child(4), #boxes div:nth-child(7) {
            clear: left;
        }



    </style>
</head>
<body>

<h1>Hello world!</h1>
<ol>
    <li id="apple">Apple</li>
    <li class="fancy">Banana</li>
    <li id="orange">Orange</li>
</ol>

<ul>
    <li class="fancy">abc</li>
</ul>

<a href="http://www.google.com">
    <img src="https://www.google.ee/images/srpr/logo11w.png"/>
</a>

<div id ="container">
<div id="boxes">
    <div class="first"></div>
    <div class="first"></div>
    <div class="first"></div>
    <div class="second"></div>
    <div class="second"></div>
    <div class="second"></div>
    <div class="third"></div>
    <div class="third"></div>
    <div class="third"></div>
</div>
</div>

</body>
</html>




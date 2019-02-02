<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

    <!-- Compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

    <style>

    </style>
</head>
<body>
<div class="row">
    <div class="col s1">1</div>
    <div class="col s1">2</div>
    <div class="col s1">3</div>
    <div class="col s1">4</div>
    <div class="col s1">5</div>
    <div class="col s1">6</div>
    <div class="col s1">7</div>
    <div class="col s1">8</div>
    <div class="col s1">9</div>
    <div class="col s1">10</div>
    <div class="col s1">11</div>
    <div class="col s1">12</div>
</div>

<div class="row">
    <div class="col m3"></div>
    <div class="col m9">

           <#list courses as course>

               <div class="col s6 m3">
                   <div class="card">
                       <div class="card-image">
                           <img src="https://materializecss.com/images/sample-1.jpg">
                           <span class="card-title">${course.name}</span>
                       </div>
                       <div class="card-content">
                           <p>${course.description}</p>
                       </div>
                       <div class="card-action">
                           <a href="/course/${course.id}">Join(Start)</a>
                       </div>
                   </div>
               </div>

           </#list>

    </div>
</div>


</body>
</html>
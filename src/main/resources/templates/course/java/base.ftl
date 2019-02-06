<#macro base_layout>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>IT Label 1</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:700" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link rel="stylesheet" href="/css/style.css">

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body class="grey lighten-4">
<header class="row row-margin0 white">
    <div class="col s8">
        <a href="#" data-target="mobile-demo" class="sidenav-trigger">
            <i class="material-icons">menu</i>
        </a>
    </div>
    <div class="user-block s4"></div>
</header>
<main class="row row-margin0">
    <div class="sidebar_menu col s1 grey lighten-2">
        <ul class="menu-list hide-on-med-and-down ">
            <li><a href="" class="menu-item">1</a></li>
            <li><a href="" class="menu-item">2</a></li>
            <li><a href="" class="menu-item">3</a></li>
            <li><a href="" class="menu-item">4</a></li>
            <li><a href="" class="menu-item">5</a></li>
        </ul>

        <ul class="sidenav" id="mobile-demo">
            <li><a href="#">1</a></li>
            <li><a href="" class="menu-item">1</a></li>
            <li><a href="" class="menu-item">2</a></li>
            <li><a href="" class="menu-item">3</a></li>
            <li><a href="" class="menu-item">4</a></li>
            <li><a href="" class="menu-item">5</a></li>
        </ul>
    </div>


    <div class="main-content col s11 grey lighten-4">
        <div class="container content-container white">
            <div class="row row-margin0">
                <div class="col s12 breadcrumbs">

                    <ul class="pagination">
                        <li class="col s1"><a href="#!"><i class="material-icons">arrow_back</i></a></li>
                        <li class="grey-text text-lighten-1"><a  href="#!" class="grey-text text-lighten-1">Java-разработка — Облачное хранилище <span class="progress-percent">0%</span></a></li>
                        <li class="disabled grey-text text-lighten-1 grey lighten-4"><a href="#!" class="grey-text text-lighten-1">Теория</a></li>

                    </ul>

                </div>
            </div>
            <div class="row">

                <#nested>

                <div class="col s3">
                    <div class="course-list-of-lessons">
                        <h4 class="list-title">Шаги</h4>
                        <ul class="list-of-lessons">
                            <#list steps as stepInfo>
                                <li class="list-of-lessons-item <#if step.id == stepInfo.id>active grey lighten-3 </#if> ">
                                    <a href="#" class="
                                        <#if step.id == stepInfo.id>active teal-text <#else> grey-text </#if> ">
                                        ${stepInfo.title}</a>
                                </li>
                            </#list>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<script>document.addEventListener('DOMContentLoaded', function() {
    var elems = document.querySelectorAll('.sidenav');
    var instances = M.Sidenav.init(elems);
});</script>
</body>
</html>
</#macro>
<%--
  Created by IntelliJ IDEA.
  User: PaulRyan
  Date: 2/8/2016
  Time: 2:02 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Docudile - Home</title>

    <link rel="stylesheet" href="${"/resources/css/bootstrap.min.css"}">
    <link rel="stylesheet" href="${"/resources/bootflat/css/bootflat.css"}  ">
    <link rel="stylesheet" href="${"/resources/css/index.css"}">
    <link rel="stylesheet" href="${"/resources/css/setup.css"}">

    <link rel="icon"
          type="image/png"
          href="${"/resources/img/logo.png"}">
</head>
<body style="background: #55acef;">

<header class="dd-frontpage dd-onepage">
    <div class="dd-border">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <nav class="navbar">
                        <div class="navbar-inner">
                            <div class="container-fluid">
                                <!-- Brand and toggle get grouped for better mobile display -->
                                <div class="navbar-header">
                                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                                            data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                                        <span class="sr-only">Toggle navigation</span>
                                        <span class="icon-bar"></span>
                                        <span class="icon-bar"></span>
                                        <span class="icon-bar"></span>
                                    </button>
                                    <a href="#" class="dd-navbar-logo pull-left"><img src="${"/resources/img/logo-inverted.png"}"></a>
                                    <a class="navbar-brand dd-brand" href="/"><strong>docudile</strong></a>
                                </div>

                                <!-- Collect the nav links, forms, and other content for toggling -->
                                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                                    <ul class="nav navbar-nav navbar-right dd-nav-links">
                                        <li><a href="login">LOGIN</a></li>
                                    </ul>
                                </div>
                                <!-- /.navbar-collapse -->
                            </div>
                            <!-- /.container-fluid -->
                        </div>
                    </nav>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="row dd-register">
            <div class="col-sm-4 col-sm-offset-4">
                <h3>Create an account</h3>
                <form action="register" method="post">
                    <div class="form-group">
                        <input type="text" class="form-control" id="inputFirstname" name="firstname" placeholder="First name">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" id="inputLastname" name="lastname" placeholder="Last name">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" id="inputEmail" name="username" placeholder="Username">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" id="inputPassword" name="password" placeholder="Password">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" id="inputOffice" name="office" placeholder="Office">
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="row">

                        <div class="col-sm-6 col-sm-offset-6">
                            <button type="submit" class="btn btn-primary btn-block dd-setup-btn">Create an account</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</header>

<main>

</main>

<footer class="footer">
    <div class="container dd-footer">
        <div class="row">
            <div class="col-sm-5">
                <h6>ABOUT</h6>
                <p>
                    Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae at
                    <a href="!#">info@docudi.le</a>.
                </p>
            </div>
            <div class="col-sm-2 col-sm-offset-1">
                <h6>PRODUCT</h6>
                <ul>
                    <li>Features</li>
                    <li>Examples</li>
                    <li>Tour</li>
                    <li>Gallery</li>
                </ul>
            </div>
            <div class="col-sm-2">
                <h6>Technologies</h6>
                <ul>
                    <li>Dropbox API</li>
                    <li>Abbyy Finereader 12</li>
                    <li>Spring MVC</li>
                    <li>Hibernate</li>
                </ul>
            </div>
            <div class="col-sm-2">
                <h6>LEGAL</h6>
                <ul>
                    <li>Terms</li>
                    <li>Legal</li>
                    <li>Privacy</li>
                    <li>License</li>
                </ul>
            </div>
        </div>
    </div>
</footer>


<script rel="script" src="${"/resources/js/jquery-2.1.3.min.js"}"></script>
<script rel="script" src="${"/resources/js/bootstrap.min.js"}"></script>
</body>
</html>

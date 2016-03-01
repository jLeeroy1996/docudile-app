<%--
  Created by IntelliJ IDEA.
  User: PaulRyan
  Date: 3/2/2016
  Time: 4:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Docudile - Home</title>

    <link rel="stylesheet" href="${"/resources/css/bootstrap.min.css"}">
    <link rel="stylesheet" href="${"/resources/bootflat/css/bootflat.css"}">
    <link rel="stylesheet" href="${"/resources/fonts/font-awesome/css/font-awesome.min.css"}">
    <link rel="stylesheet" href="${"/resources/css/index.css"}">
    <link rel="stylesheet" href="${"/resources/css/setup.css"}">
    <link rel="stylesheet" href="${"/resources/css/bootstrap-tagsinput.css"}">
    <link rel="stylesheet" href="${"/resources/select2/css/select2.min.css"}">


    <link rel="icon"
          type="image/png"
          href="${"/resources/img/logo.png"}">
</head>
<body style="background: #55acef">
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
                                    <a href="/" class="dd-navbar-logo pull-left"><img
                                            src="${"/resources/img/logo-inverted.png"}"></a>
                                    <a class="navbar-brand dd-brand" href="/"><strong>docudile</strong></a>
                                </div>

                                <!-- Collect the nav links, forms, and other content for toggling -->
                                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                                    <ul class="nav navbar-nav navbar-right dd-nav-links">
                                        <p class="dd-setup-hello">Hello <strong>Paul Ryan!</strong></p>
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
        <div class="row dd-pretrain">
            <div class="col-sm-6 col-sm-offset-3">
                <form>
                    <div class="form-group">
                        <select id="dd-select-train" class="form-control">
                            <option value="memo">Memo</option>
                            <option value="letter">Letter</option>
                        </select>
                    </div>
                </form>
            </div>
        </div>
    </div>
</header>

<main>

</main>

<script rel="script" src="${"/resources/js/jquery-2.1.3.min.js"}"></script>
<script rel="script" src="${"/resources/js/bootstrap.min.js"}"></script>
<script rel="script" src="${"/resources/js/bootstrap-tagsinput.js"}"></script>
<script rel="script" src="${"/resources/select2/js/select2.min.js"}"></script>
<script rel="script" src="${"/resources/js/setup.js"}"></script>
<script>
    $("#dd-select-train").select2();
</script>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: PaulRyan
  Date: 3/2/2016
  Time: 1:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Docudile - Home</title>

    <link rel="stylesheet" href="${"/resources/css/bootstrap.min.css"}">
    <link rel="stylesheet" href="${"/resources/bootflat/css/bootflat.css"}">
    <link rel="stylesheet" href="${"/resources/fonts/font-awesome/css/font-awesome.min.css"}">
    <link rel="stylesheet" href="${"/resources/css/setup.css"}">
    <link rel="stylesheet" href="${"/resources/css/bootstrap-tagsinput.css"}">
    <link rel="stylesheet" href="${"/resources/css/site.css"}">

    <link rel="icon"
          type="image/png"
          href="${"/resources/img/logo.png"}">
</head>
<body style="background: #55acef">
<header>
    <nav class="navbar dd-home-navbar">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-2" aria-expanded="false"><span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
                </button>
                <a href="/home" class="dd-home-navbar-logo pull-left"><img src="${"/resources/img/logo-inverted.png"}"></a>
                <a class="navbar-brand dd-brand" href="/home">Docudile</a></div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-2">
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-right dd-nav-links">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <li role="presentation" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-user"></i><small> Paul Ryan</small></a>
                            <ul class="dropdown-menu">
                                <li class="dropdown-header">Menu</li>
                                <li>
                                    <a href="/setup/content"><i class="fa fa-file-text"></i> Content</a>
                                </li>
                                <li>
                                    <a href="/setup/classifier"><i class="fa fa-align-left"></i> Structure</a>
                                </li>
                                <li>
                                    <a href="/setup/data"><i class="fa fa-tags"></i> Tags</a>
                                </li>
                                <li class="divider" role="separator"></li>
                                <li>
                                    <a href="/${spring_security_logout}"><i class="fa fa-sign-out"></i> Logout</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
</header>
<div class="container dd-setup">
    <div class="row dd-register">
        <div class="col-sm-4 col-sm-offset-2">
            <h4>Categories</h4>
            <div class="dd-cat-table-box">
                <table class="table" id="categoryTable">
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
        <div class="col-sm-4">
            <h4>Add your tags</h4>
            <c:if test="${not empty error}">
                <p>${error}</p>
            </c:if>
            <div class="form-group dd-select">
                <select class="form-control" id="tagType">
                    <option value="Office" selected>Office or College</option>
                    <option value="Address">Address</option>
                    <option value="Person">Person</option>
                    <option value="Position">Position</option>
                </select>
            </div>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Display Name" id="displayName">
            </div>
            <div class="form-group">
                <select class="form-control" id="inputTags" multiple></select>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="row">
                <div class="col-sm-5 col-sm-offset-1">

                </div>
                <div class="col-sm-4 col-sm-offset-2">
                    <button class="btn btn-primary btn-block dd-setup-btn" id="tagAdd">Add</button>
                </div>
            </div>

        </div>
    </div>
    <div class="row">
        <div class="col-sm-2 col-sm-offset-8">
            <a class="btn btn-primary btn-block dd-setup-btn" id="continueBtn" href="/setup/">Continue</a>
        </div>
    </div>
</div>
<script rel="script" src="${"/resources/js/jquery-2.1.3.min.js"}"></script>
<script rel="script" src="${"/resources/js/dropzone.js"}"></script>
<script rel="script" src="${"/resources/js/bootstrap.min.js"}"></script>
<script rel="script" src="${"/resources/js/bootstrap-tagsinput.js"}"></script>
<script rel="script" src="${"/resources/bootflat/js/jquery.fs.selecter.min.js"}"></script>
<script rel="script" src="${"/resources/js/setup.js"}"></script>
<script>
    $('#inputTags').tagsinput({
        confirmKeys: [13]
    })
</script>
</body>
</html>

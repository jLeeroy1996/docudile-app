<%--
  Created by IntelliJ IDEA.
  User: PaulRyan
  Date: 3/2/2016
  Time: 4:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Docudile</title>

    <link rel="stylesheet" href="${"/resources/css/bootstrap.min.css"}">
    <link rel="stylesheet" href="${"/resources/bootflat/css/bootflat.css"}">
    <link rel="stylesheet" href="${"/resources/treeview/bootstrap-treeview.min.css"}">
    <link rel="stylesheet" href="${"/resources/css/bootstrap-treenav.css"}">
    <link rel="stylesheet" href="${"/resources/fonts/font-awesome/css/font-awesome.min.css"}">
    <link rel="stylesheet" href="${"/resources/select2/css/select2.min.css"}">
    <link rel="stylesheet" href="${"/resources/css/dropzone.css"}">
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
<main class="dd-retrain-box">
    <div class="container">
        <div class="row dd-pretrain">
            <div class="col-sm-6 col-sm-offset-3">
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a href="#retrain" aria-controls="home" role="tab" data-toggle="tab">Retrain</a></li>
                    <li role="presentation"><a href="#new" aria-controls="new" role="tab" data-toggle="tab">New</a></li>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="retrain">
                        <div class="form-group">
                            <select id="dd-select-train" class="form-control">
                                <option value="memo">Memo</option>
                                <option value="letter">Letter</option>
                            </select>
                        </div>
                        <div class="form-group dd-retrain-file-list">
                            <table class="table">
                                <tbody>
                                <tr>
                                    <td>excuse.docx</td>
                                    <td><a href="#!"><i class="fa fa-times"></i></a></td>
                                </tr>
                                <tr>
                                    <td>excuse.docx</td>
                                    <td><a href="#!"><i class="fa fa-times"></i></a></td>
                                </tr>
                                <tr>
                                    <td>excuse.docx</td>
                                    <td><a href="#!"><i class="fa fa-times"></i></a></td>
                                </tr>
                                <tr>
                                    <td>excuse.docx</td>
                                    <td><a href="#!"><i class="fa fa-times"></i></a></td>
                                </tr>
                                <tr>
                                    <td>excuse.docx</td>
                                    <td><a href="#!"><i class="fa fa-times"></i></a></td>
                                </tr>
                                <tr>
                                    <td>excuse.docx</td>
                                    <td><a href="#!"><i class="fa fa-times"></i></a></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="form-group">
                            <form action="/training/category/new?_csrf=${_csrf.token}" id="category_upload_retrain" class="dropzone">
                                <div class="fallback">
                                    <input name="file" type="file" multiple>
                                </div>
                            </form>
                        </div>
                        <div class="form-group">
                            <button id="category_upload_retrain_btn" class="btn btn-primary">Create</button>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="new">
                        <div class="form-group">
                            <select id="dd-select-new" class="form-control">
                                <option value="memo">Memo</option>
                                <option value="letter">Letter</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Category name..." id="category_name"/>
                        </div>
                        <div class="form-group">
                            <form action="/training/category/new?_csrf=${_csrf.token}" id="category_upload_new" class="dropzone">
                                <div class="fallback">
                                    <input name="file" type="file" multiple>
                                </div>
                            </form>
                        </div>
                        <div class="form-group">
                            <button id="category_upload_new_btn" class="btn btn-primary">Create</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<script rel="script" src="${"/resources/js/jquery-2.1.3.min.js"}"></script>
<script rel="script" src="${"/resources/js/dropzone.js"}"></script>
<script rel="script" src="${"/resources/js/bootstrap.min.js"}"></script>
<script rel="script" src="${"/resources/treeview/bootstrap-treeview.min.js"}"></script>
<script rel="script" src="${"/resources/js/bootstrap-treenav.js"}"></script>
<script rel="script" src="${"/resources/bootstrap-fileinput/js/plugins/canvas-to-blob.min.js"}"></script>
<script rel="script" src="${"/resources/bootstrap-fileinput/js/fileinput.min.js"}"></script>
<script rel="script" src="${"/resources/bootflat/js/jquery.fs.selecter.min.js"}"></script>
<script rel="script" src="${"/resources/js/setup.js"}"></script>
<script>
    $("#dd-select-train").selecter();
    $("#dd-select-new").selecter();
</script>
</body>
</html>

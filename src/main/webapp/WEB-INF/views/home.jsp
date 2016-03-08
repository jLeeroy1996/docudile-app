<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <link rel="stylesheet" href="${"/resources/css/dropzone.css"}">
    <link rel="stylesheet" href="${"/resources/css/site.css"}">

    <link rel="icon"
          type="image/png"
          href="${"/resources/img/logo.png"}">
</head>
<body>

<c:set var="folder-empty" value="${'/resources/img/folder-empty.png'}" />
<c:set var="folder-filled" value="${'/resources/img/folder-filled.png'}" />

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
                <form class="navbar-form navbar-left dd-search" role="search" id="searchForm">
                    <div class="form-search search-only">
                        <i class="search-icon glyphicon glyphicon-search"></i>
                        <input type="text" class="form-control search-query" id="query-string">
                    </div>
                </form>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-right dd-nav-links">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <li><button class="btn navbar-btn" data-toggle="modal" data-target="#uploadModal"><i class="fa fa-upload"></i> Upload</button></li>
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
<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="uploadModalTitle">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="uploadModalTitle">Upload Document(s)</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <form action="/home/upload-documents?_csrf=${_csrf.token}" id="upload_doc" class="dropzone dropzone-blue">
                        <div class="fallback">
                            <input name="document">
                        </div>
                    </form>
                </div>
                <div class="form-group">
                    <button id="upload_doc_btn" class="btn btn-primary">Create</button>
                </div>
            </div>
            <div class="modal-footer"></div>
        </div>
    </div>
</div>
<main>
    <div class="container-fluid dd-breadcrumbs">
        <div class="row">
            <div class="col-sm-2">
            </div>
            <div class="col-sm-7">
                <p></p>
            </div>
            <div class="col-sm-3">
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-2 dd-navtree">
                <div id="treeview"></div>
            </div>
            <div class="col-sm-7 dd-filebox" id="filebox">
                <table class="table table-hover" id="dd-filebox-table">
                    <thead>
                    <tr>
                        <th class="col-sm-7">Name</th>
                        <th class="col-sm-5">Last modified</th>
                    </tr>
                    </thead>
                    <tbody id="dd-filebox-id">
                    </tbody>
                </table>
            </div>
            <div id="fileInfo" class="col-sm-3">
            </div>
        </div>
    </div>
</main>
<script rel="script" src="${"/resources/js/jquery-2.1.3.min.js"}"></script>
<script rel="script" src="${"/resources/js/dropzone.js"}"></script>
<script rel="script" src="${"/resources/js/bootstrap.min.js"}"></script>
<script rel="script" src="${"/resources/treeview/bootstrap-treeview.min.js"}"></script>
<script rel="script" src="${"/resources/js/bootstrap-treenav.js"}"></script>
<script rel="script" src="${"/resources/js/custom.js"}"></script>

<script>
    $('#upload_doc').dropzone({
        paramName: 'document',
        clickable: true,
        autoProcessQueue: false,
        init: function() {
            var dropzone = this;
            $('#upload_doc_btn').click(function() {
                dropzone.processQueue();
            });
        }
    });
</script>
</body>
</html>
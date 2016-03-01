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
    <link rel="stylesheet" href="${"/resources/css/site.css"}">
    <link rel="stylesheet" href="${"/resources/bootstrap-fileinput/css/fileinput.min.css"}">
    <link rel="stylesheet" href="${"/resources/select2/css/select2.min.css"}">

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
                <a href="#" class="dd-home-navbar-logo pull-left"><img src="${"/resources/img/logo-inverted.png"}"></a>
                <a class="navbar-brand dd-brand" href="#">docu dile</a></div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-2">
                <form class="navbar-form navbar-left dd-search" role="search">
                    <div class="form-search search-only">
                        <i class="search-icon glyphicon glyphicon-search"></i>
                        <input type="text" class="form-control search-query">
                    </div>
                </form>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-right dd-nav-links">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <li><button class="btn navbar-btn" data-toggle="modal" data-target="#uploadModal"><i class="fa fa-upload"></i> Upload</button></li>
                        <li><a href="#" data-toggle="modal" data-target="#createTypeModal"><i class="fa fa-archive"></i><small> Training</small></a></li>
                        <li><a href="#"><i class="fa fa-user"></i><small> Paul Ryan</small></a></li>
                        <li><a href="#"><i class="fa fa-cog"></i></a></li>
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
                <label class="control-label">Select File(s)</label>
                <input id="uploadDoc" name="document" type="file" multiple class="file-loading">
            </div>
            <div class="modal-footer"></div>
        </div>
    </div>
</div>
<div class="modal fade" id="createTypeModal" tabindex="-1" role="dialog" aria-labelledby="createTypeModalTitle">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="createTypeModalTitle">Training</h4>
            </div>
            <div class="modal-body">
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active">
                        <a href="#structureTraining" aria-controls="structureTraining" role="tab" data-toggle="tab">Structure-based</a>
                    </li>
                    <li role="presentation">
                        <a href="#contentTraining" aria-controls="contentTraining" role="tab" data-toggle="tab">Content-based</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="structureTraining">
                        <ul class="nav nav-pills nav-justified" role="tablist">
                            <li role="presentation" class="active">
                                <a href="#newStructure" aria-controls="newStructure" role="tab" data-toggle="tab">New</a>
                            </li>
                            <li role="presentation">
                                <a href="#retainStructure" aria-controls="retrainStructure" role="tab" data-toggle="tab">Retrain</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div role="tabpanel" class="tab-pane active" id="newStructure">
                                <div class="form-group">
                                    <input type="text" class="form-control" id="structureName" name="typename" placeholder="Structure Type Name">
                                </div>
                                <label class="control-label">Select File(s)</label>
                                <input id="uploadSampleStructNew" name="documents" type="file" multiple class="file-loading">
                            </div>
                            <div role="tabpanel" class="tab-pane" id="retainStructure">
                                <div class="form-group">
                                    <select id="train-structure" style="width: 100%;"></select>
                                </div>
                                <label>Files</label>
                                <table class="table">
                                    <tbody>
                                    <tr>
                                        <td><i class="fa fa-file-image-o"></i> 021416 Moa.docx</td>
                                        <td><a href="#"><i class="fa fa-trash-o"></i></a></td>
                                    </tr>
                                    <tr>
                                        <td><i class="fa fa-file-image-o"></i> 021416 Moa.docx</td>
                                        <td><a href="#"><i class="fa fa-trash-o"></i></a></td>
                                    </tr>
                                    <tr>
                                        <td><i class="fa fa-file-image-o"></i> 021416 Moa.docx</td>
                                        <td><a href="#"><i class="fa fa-trash-o"></i></a></td>
                                    </tr>
                                    </tbody>
                                </table>
                                <label class="control-label">Select File(s)</label>
                                <input id="uploadSampleStructRe" name="documents" type="file" multiple class="file-loading">
                            </div>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="contentTraining">
                        <ul class="nav nav-pills nav-justified" role="tablist">
                            <li role="presentation" class="active">
                                <a href="#newContent" aria-controls="newContent" role="tab" data-toggle="tab">New</a>
                            </li>
                            <li role="presentation">
                                <a href="#retrainContent" aria-controls="retrainContent" role="tab" data-toggle="tab">Retrain</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div role="tabpanel" class="tab-pane active" id="newContent">
                                <div class="form-group">
                                    <input type="text" class="form-control" id="contentName" name="typename" placeholder="Content Type Name">
                                </div>
                                <label class="control-label">Select File(s)</label>
                                <input id="uploadSampleContNew" name="documents" type="file" multiple class="file-loading">
                            </div>
                            <div role="tabpanel" class="tab-pane" id="retrainContent">
                                <div class="form-group">
                                    <select id="train-content" style="width: 100%;"></select>
                                </div>
                                <label>Files</label>
                                <table class="table">
                                    <tbody>
                                        <tr>
                                            <td><i class="fa fa-file-image-o"></i> 021416 Moa.docx</td>
                                            <td><a href="#"><i class="fa fa-trash-o"></i></a></td>
                                        </tr>
                                        <tr>
                                            <td><i class="fa fa-file-image-o"></i> 021416 Moa.docx</td>
                                            <td><a href="#"><i class="fa fa-trash-o"></i></a></td>
                                        </tr>
                                        <tr>
                                            <td><i class="fa fa-file-image-o"></i> 021416 Moa.docx</td>
                                            <td><a href="#"><i class="fa fa-trash-o"></i></a></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <label class="control-label">Select File(s)</label>
                                <input id="uploadSampleContRe" name="documents" type="file" multiple class="file-loading">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
            </div>
        </div>
    </div>
</div>
<main>
    <div class="container-fluid dd-breadcrumbs">
        <div class="row">
            <div class="col-sm-2">

            </div>
            <div class="col-sm-7">
                <ol class="breadcrumb">
                    <li><a href="#">Docudile</a></li>
                    <li><a href="#">Memo</a></li>
                    <li class="active"><a href="#">2016</a></li>
                    <li class="active">MOA</li>
                </ol>
            </div>
            <div class="col-sm-3">

            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-2 dd-navtree">
                <%--<ul class="nav nav-pills nav-stacked nav-tree" id="myTree" data-toggle="nav-tree" data-nav-tree-expanded="fa fa-folder-open-o" data-nav-tree-collapsed="fa fa-folder-o">
                    <li>
                        <a href="/home" data-name="" data-folder="">Docudile</a>
                        <ul class="nav nav-pills nav-stacked nav-tree">
                            <c:forEach items="${nodes}" var="node" >
                                <c:set var="node" value="${node}" scope="request"/>
                                <c:set var="link" value="" scope="request"/>
                                <jsp:include page="get-files.jsp" />
                            </c:forEach>
                        </ul>
                    </li>
                </ul>--%>
                <div id="treeview"></div>
            </div>
            <div class="col-sm-7 dd-filebox">
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
<script rel="script" src="${"/resources/js/bootstrap.min.js"}"></script>
<script rel="script" src="${"/resources/treeview/bootstrap-treeview.min.js"}"></script>
<script rel="script" src="${"/resources/js/bootstrap-treenav.js"}"></script>
<script rel="script" src="${"/resources/bootstrap-fileinput/js/plugins/canvas-to-blob.min.js"}"></script>
<script rel="script" src="${"/resources/bootstrap-fileinput/js/fileinput.min.js"}"></script>
<script rel="script" src="${"/resources/select2/js/select2.min.js"}"></script>
<script rel="script" src="${"/resources/js/custom.js"}"></script>
<script rel="script" src="${"/resources/js/history.js"}"></script>
</body>
</html>
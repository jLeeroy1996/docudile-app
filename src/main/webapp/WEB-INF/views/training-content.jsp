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
    <link rel="stylesheet" href="${"/resources/css/site.css"}">
    <link rel="stylesheet" href="${"/resources/bootstrap-fileinput/css/fileinput.min.css"}">
    <link rel="stylesheet" href="${"/resources/select2/css/select2.min.css"}">

    <link rel="icon"
          type="image/png"
          href="${"/resources/img/logo.png"}">
</head>
<body style="background: #2b83c6">

<header>
    <nav class="navbar dd-home-navbar">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-2" aria-expanded="false"><span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
                </button>
                <a href="#" class="dd-home-navbar-logo pull-left"><img src="${"/resources/img/logo-inverted.png"}"></a>
                <a class="navbar-brand dd-brand" href="#">Docudile</a></div>
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
                            <input id="dd-training-files-content-retrain" name="content-retrain[]" type="file" multiple class="file-loading">
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="new">
                        <div class="form-group">
                            <select id="dd-select-content-new" class="form-control" id="structureName">
                                <option value="memo">Memo</option>
                                <option value="letter">Letter</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <input type="text" name="trainNewCategory" class="form-control" placeholder="Category name..." id="categoryName"/>
                        </div>
                        <div class="form-group">
                            <input id="dd-training-files-content-new" name="content-new[]" type="file" multiple class="file-loading">
                        </div>
                    </div>
                </div>
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
<script>
    $(document).on('ready', function () {
        var token = $("input[name='_csrf']").val();
        var doc_url = "./trainCategory?_csrf=" + token;
        var type_url = "./new-type?_csrf=" + token;
        $("#dd-select-content-retrain").select2();
        $("#dd-select-content-new").select2();

        $("#dd-training-files-content-retrain").fileinput({
            uploadUrl: "http://localhost/file-upload-single/1", // server upload action
            uploadAsync: true,
            maxFileCount: 5
        });
        $("#dd-training-files-content-new").fileinput({
            uploadUrl: doc_url, // server upload action
            uploadAsync: false,
            uploadExtraData: {
                name: $("input:text #structureName").val(),
                categoryName: $("input:text #categoryName").val()
            },
            maxFileCount: 10
        });
    })

</script>
</body>
</html>

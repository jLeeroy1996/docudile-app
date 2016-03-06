/**
 * Created by franc on 2/9/2016.
 */
window.treeData = null
window.tree = null;
$(document).on('ready', function () {
    $.fn.modal.Constructor.prototype.enforceFocus = $.noop;
    var token = $("input[name='_csrf']").val();
    var doc_url = "./home/upload-documents?_csrf=" + token;
    var type_url = "./new-type?_csrf=" + token;
    window.treeData = $.retrieveTreeData();
    window.tree = createTreeView();
    $(document).keyup(function (e) {
        if (e.keyCode == 27) {
            clearDetails();
        }
    });
});

$.extend({
    retrieveTreeData: function () {
        var response = null;
        $.ajax({
            url: "/home/folder", async: false, type: 'get', success: function (data) {
                response = convertToTreeData(data);
            }
        });
        return response;
    }
});

function createTreeView() {
    return $("#treeview").treeview({
        collapseIcon: "glyphicon glyphicon-folder-open",
        expandIcon: "glyphicon glyphicon-folder-close",
        emptyIcon: "glyphicon glyphicon-folder-close",
        data: window.treeData,
        levels: 1,
        onNodeSelected: function (event, node) {
            updateFilebox(node.id);
            updateDetailsBoxFromTreeview(node.id);
        }
    });
}

function revealNode(nodeName, parentNodeName) {
    var result = findNode(nodeName, parentNodeName);
    window.tree.treeview('revealNode', [result, {silent: true}]);
    window.tree.treeview('selectNode', [result, {silent: true}]);
    window.tree.treeview('clearSearch');
}

function findNode(nodeName, nodeId) {
    var results = window.tree.treeview('search', [nodeName, {
        ignoreCase: false,
        exactMatch: true,
        revealResults: false,
        silent: true
    }]);
    for (var i in results) {
        if (results[i].id === nodeId) {
            return results[i];
        }
    }
}

function convertToTreeData(data) {
    var obj = [];
    for (var i in data) {
        if (data[i] != null && typeof(data[i]) == "object") {
            var temp = {
                id: data[i].id,
                text: data[i].name
            }
            if (data[i].childFolders != null && data[i].childFolders.length > 0) {
                temp.nodes = convertToTreeData(data[i].childFolders);
            }
            obj.push(temp);
        }
    }
    return obj;
}

function viewDetailsFile(data) {
    clearDetails();
    var template = '<h3><small><i class="glyphicon glyphicon-list-alt"></i> Details</small></h3>' +
        '<ul class="list-group">' +
        '<li class="list-group-item"><i class="glyphicon glyphicon-user"></i> Owner: ' + data.user.firstname + ' ' + data.user.lastname + '</li>' +
        '<li class="list-group-item"><i class="glyphicon glyphicon-calendar"></i> Date Uploaded: ' + data.dateUploaded + '</li>' +
        '<li class="list-group-item"><i class="glyphicon glyphicon-folder-close"></i> Path: ' + data.path + data.filename + '</li>' +
        '</ul>' +
        '<h3><small><i class="glyphicon glyphicon-cog"></i> Manage</small></h3>' +
        '<p><i class="glyphicon glyphicon-info-sign"></i> Info: Deleting the file will result to the permanent loss of that file.</p>' +
        '<div class="row">' +
        '<div class="col-sm-6">' +
        '<button class="btn btn-success btn-block"><i class="glyphicon glyphicon-floppy-disk"></i> Download</button>' +
        '</div>' +
        '<div class="col-sm-6">' +
        '<button class="btn btn-danger btn-block"><i class="glyphicon glyphicon-remove"></i> Delete</button>' +
        '</div>' +
        '</div>';
    $('#fileInfo').append(template);
}

function viewDetailsFolder(data) {
    clearDetails();
    var template = '<h3><small><i class="glyphicon glyphicon-list-alt"></i> Details</small></h3>' +
        '<ul class="list-group">' +
        '<li class="list-group-item"><i class="glyphicon glyphicon-user"></i> Owner: ' + data.user.firstname + ' ' + data.user.lastname + '</li>' +
        '<li class="list-group-item"><i class="glyphicon glyphicon-calendar"></i> Date Modified: ' + data.dateModified + '</li>' +
        '<li class="list-group-item"><i class="glyphicon glyphicon-folder-close"></i> Path: ' + data.path + '</li>' +
        '</ul>' +
        '<h3><small><i class="glyphicon glyphicon-cog"></i> Manage</small></h3>' +
        '<p><i class="glyphicon glyphicon-info-sign"></i> Info: Deleting the file will result to the permanent loss of that file.</p>';
    $('#fileInfo').append(template);
}

function clearDetails() {
    $('#fileInfo').empty();
}

function updateDetailsBoxFromTreeview(id) {
    $.ajax({
        dataType: "json",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        url: '/home/folder/' + id,
        success: function (response) {
            viewDetailsFolder(response);
        }
    });
}

function updateFilebox(id) {
    $.ajax({
        dataType: "json",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        url: '/home/folder/' + id,
        success: function (response) {
            $('#dd-filebox-id').empty();
            $.each(response.childFolders, function (key, inside) {
                var icon = '/resources/img/folder-filled.png';
                if (inside.childFolders.length == 0 && inside.files.length == 0) {
                    icon = '/resources/img/folder-empty.png';
                }
                var tablerow = $('<tr class="dd-file-row clickable-row">' +
                    '<td ><img src="' + icon + '" class="dd-row-icon" /> ' + inside.name + "</td>" +
                    '<td class="dd-row-details">' + inside.dateModified + '</td>');
                $('#dd-filebox-id').append(tablerow);
                tablerow.click(function () {
                    $(this).addClass('active').siblings().removeClass('active');
                    viewDetailsFolder(inside);
                });
                tablerow.dblclick(function () {
                    updateFilebox(inside.id);
                    revealNode(inside.name, inside.id);
                });
            });
            $.each(response.files, function (key, inside) {
                var tablerow = $('<tr id="file-' + inside.id + '" class="dd-file-row clickable-row">' +
                    '<td><img src="/resources/img/file-icon.png" class="dd-row-icon" /> ' + inside.filename + "</td>" +
                    '<td class="dd-row-details">' + inside.dateUploaded + '</td>');
                $('#dd-filebox-id').append(tablerow);
                tablerow.click(function () {
                    $(this).addClass('active').siblings().removeClass('active');
                    viewDetailsFile(inside);
                });
            });
        }
    });
}
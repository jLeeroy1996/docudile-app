/**
 * Created by franc on 2/9/2016.
 */
window.treeData = null
window.tree = null;
$(document).on('ready', function () {
    $.fn.modal.Constructor.prototype.enforceFocus = $.noop;
    var token = $("input[name='_csrf']").val();
    window.treeData = $.retrieveTreeData();
    window.tree = createTreeView();
    $(document).keyup(function (e) {
        if (e.keyCode == 27) {
            clearDetails();
        }
    });
    $("#searchForm").on("submit", function (e) {
        var queryString = $("#query-string").val();
        if (!queryString.trim()) {
            e.preventDefault();
            return;
        }
        var filebox = $("#filebox");
        readyFileboxBeforeSearch(queryString);
        $.ajax({
            type: 'GET',
            url: '/home/search/' + queryString,
            success: function (response) {
                if(response.length === 0) {
                    $("#filebox").append('<h3>No result found</h3>');
                }
                else {
                    appendTableForSearch();
                    $.each(response, function (key, inside) {
                        var image = '<img src="/resources/img/file-icon.png" class="dd-search-icon" />';
                        var filename = '<p class="searchFilename">' + inside.filename + '</p>';
                        var path = '<p class="searchPath">' + inside.path + '</p>';
                        var tr = '<tr><td class="col-sm-1">' + image + ' </td><td>' + filename + path + '</td></tr>';
                        var tablerow = $(tr);
                        console.log(tr);
                        $('#searchResult').append(tr);
                        tablerow.click(function () {
                            $(this).addClass('active').siblings().removeClass('active');
                            viewDetailsFile(inside);
                        });
                    });
                }
                console.log(response);
            }
        });
        e.preventDefault();
    });
});

function readyFileboxBeforeSearch(queryString) {
    var filebox = $("#filebox");
    filebox.empty();
    var header = '<h4 class="search-header">Search result for "' + queryString + '"</h4>'
    filebox.append(header);
}

function appendTableForSearch() {
    var filebox = $("#filebox");
    var table = '<table class="table table-hover">' +
            '<tbody id="searchResult">' +
            '</tbody>' +
            '</table>';
    filebox.append(table);
}

function readyFileboxAfterSearch() {
    var filebox = $("#filebox");
    filebox.empty();
    var line = '<table class="table table-hover" id="dd-filebox-table">' +
        '<thead>' +
        '<tr>' +
        '<th class="col-sm-7">Name</th>' +
        '<th class="col-sm-5">Last modified</th>' +
        '</tr>' +
        '</thead>' +
        '<tbody id="dd-filebox-id">' +
        '</tbody>' +
        '</table>';
    filebox.append(line);
}

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
            readyFileboxAfterSearch();
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
        '<a class="btn btn-success btn-block" href="/file/download/' + data.id + '"><i class="glyphicon glyphicon-floppy-disk"></i> Download</a>' +
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
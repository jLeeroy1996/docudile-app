/**
 * Created by PaulRyan on 2/15/2016.
 */
$(document).ready(function() {
   $("#dd-filebox-table").children("tbody").children("tr").on('click', function () {
       $(this).toggleClass("active");
       console.log("asdfsdf");
   });
});

function myDblClick(id) {
    var data = '/get-files?folderId=' + id,
        url = "/home" + data,
        folder = 'folderId=' + id;
    history.pushState(data, null, url);
    updateFilebox(folder);
}

function updateFilebox(getFilesUrl) {
    $.ajax({
        dataType: "json",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        url: '/home/get-files',
        data: getFilesUrl,
        success: function (response) {
            $('#dd-filebox-id').empty();
            $.each(response.childFolders, function (key, inside) {
                var icon = '/resources/img/folder-filled.png';
                if (inside.childFolders.length == 0 && inside.files.length == 0) {
                    icon = '/resources/img/folder-empty.png';
                }
                var tablerow = '<tr class="dd-file-row" data-name="/get-files?folderId=' + inside.id + '" data-folder="folderId=' + inside.id + '" ondblclick="myDblClick(' + inside.id + ')">' +
                    '<td ><img src="' + icon + '" class="dd-row-icon" /> ' + inside.name + "</td>" +
                    '<td class="dd-row-details">me</td>' +
                    '<td class="dd-row-details">Feb 14, 2015</td>' +
                    '<td class="dd-row-details"><a href="#"><i class="fa fa-trash-o"></i></a></td>';
                $('#dd-filebox-id').append(tablerow);
            });
            $.each(response.files, function (key, inside) {
                var tablerow = '<tr class="dd-file-row">' +
                    '<td><img src="/resources/img/file-icon.png" class="dd-row-icon" /> ' + inside.filename + "</td>" +
                    '<td class="dd-row-details">me</td>' +
                    '<td class="dd-row-details">Feb 14, 2015</td>' +
                    '<td class="dd-row-details"><a href="#"><i class="fa fa-trash-o"></i></a></td>';
                $('#dd-filebox-id').append(tablerow);
            });
            console.log(response);
        },
        error: function () {

        }
    });
}
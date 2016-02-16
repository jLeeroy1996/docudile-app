/**
 * Created by PaulRyan on 2/15/2016.
 */
var container = document.querySelector('.dd-navtree');

container.addEventListener('click', function (e) {
    if (e.target != e.currentTarget) {
        e.preventDefault();
        // e.target is the image inside the link we just clicked.
        var data = e.target.getAttribute('data-name'),
            url = "/home" + data,
            folder = e.target.getAttribute('data-folder');
        history.pushState(data, null, url);
        updateFilebox(folder);
    }
    e.stopPropagation();
}, false);

$(document).ready(function() {
   $("#dd-filebox-id").children("tr").children("td").click(function () {
       $(this.parentNode).toggleClass("active");
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
                var tablerow = '<tr data-name="/get-files?folderId=' + inside.id + '" data-folder="folderId=' + inside.id + '" ondblclick="myDblClick(' + inside.id + ')">' +
                    '<td ><img src="' + icon + '" class="dd-row-icon" /> ' + inside.name + "</td>" +
                    '<td class="dd-row-details">me</td>' +
                    '<td class="dd-row-details">Feb 14, 2015</td>' +
                    '<td class="dd-row-details"><a href="#"><i class="fa fa-trash-o"></i></a></td>';
                $('#dd-filebox-id').append(tablerow);
            });
            $.each(response.files, function (key, inside) {
                var tablerow = "<tr>" +
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
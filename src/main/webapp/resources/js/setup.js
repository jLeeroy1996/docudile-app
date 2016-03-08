/**
 * Created by PaulRyan on 3/2/2016.
 */

$(document).ready(function () {
    Dropzone.autoDiscover = false;
    var token = $("input[name='_csrf']").val();
    var tagType = $('#tagType :selected').val();
    $("#tagType").on("change", function () {
       tagType =  $('#tagType :selected').val();
    });
    $("#tagAdd").click(function () {
        var data = {};
        var url = "/training/tagger?_csrf=" + token;
        var displayName = $('#displayName').val();
        var tags = $('#inputTags').tagsinput("items");
        var tr = "<tr><td>" + displayName + "</td><td><a href='#!'><i class='fa fa-times'></i></a></td></tr>";
        $('#categoryTable').append(tr);
        data.tagType = tagType;
        data.displayName = displayName;
        data.data = tags;
        post(data, url);
        $('#tagType').val("");
        $('#inputTags').tagsinput("removeAll");
    });
    $('#classifier_upload').dropzone({
        paramName: 'file',
        clickable: true,
        autoProcessQueue: false,
        init: function() {
            var dropzone = this;
            $('#classifier_upload_btn').click(function() {
                dropzone.processQueue();
            });
            dropzone.on('sending', function(file, xhr, formData) {
                formData.append('type_name', $('#classifier_types').val());
            });
        }
    });
    $('#category_upload_retrain').dropzone({
        paramName: 'file',
        clickable: true,
        autoProcessQueue: false,
        uploadMultiple: true,
        parallelUploads: 40000,
        init: function() {
            var dropzone = this;
            $('#category_upload_retrain_btn').click(function() {
                dropzone.processQueue();
            });
        }
    });
    $('#category_upload_new').dropzone({
        paramName: 'file',
        clickable: true,
        autoProcessQueue: false,
        uploadMultiple: true,
        parallelUploads: 40000,
        init: function() {
            var dropzone = this;
            $('#category_upload_new_btn').click(function() {
                dropzone.processQueue();
            });
            dropzone.on('sendingmultiple', function(file, xhr, formData) {
                formData.append('category_name', $('#category_name').val());
            });
        }
    });
    $('#tagType').selecter({
        cover: true
    });
});

function post(data, url) {
    var jsonData = JSON.stringify(data);
    console.log(jsonData);
    $.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        data: jsonData,
        contentType: "application/json",
        success: function(e) {
            console.log("success_save");
        }
    });
}
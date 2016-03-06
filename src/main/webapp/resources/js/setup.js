/**
 * Created by PaulRyan on 3/2/2016.
 */

$(document).ready(function () {
    Dropzone.autoDiscover = false;
    var token = $("input[name='_csrf']").val();
    $("#tagAdd").click(function () {
        var data = {};
        var url = "/training/tagger?_csrf=" + token;
        var categoryName = $('#inputCategory').val();
        var tags = $('#inputTags').tagsinput("items");
        var tr = "<tr><td>" + categoryName + "</td><td><a href='#!'><i class='fa fa-times'></i></a></td></tr>";
        $('#categoryTable').append(tr);
        data.name = categoryName;
        data.data = tags;
        post(data, url);
        $('#inputCategory').val("");
        $('#inputTags').tagsinput("removeAll");
    });
    $("#inputStartYear").on("change", function () {
        var value = $(":selected", this).val();
        value = parseFloat(value) + 1;
        var select = document.getElementById("inputEndYear");
        $("#inputEndYear").empty();
        for (var i = value + 50; i >= value; --i) {
            var option = document.createElement('option');
            option.text = option.value = i;
            select.add(option, 0);
        }
    });
    $('#classifier_upload').dropzone({
        paramName: 'file',
        clickable: true,
        autoProcessQueue: false,
        init: function() {
            var dropzone = this;
<<<<<<< HEAD
            $('#classifier_upload_btn').click(function() {
=======
            $('#category_upload_new_btn').click(function() {
>>>>>>> origin/master
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
        init: function() {
            var dropzone = this;
            $('#category_upload_new_btn').click(function() {
                dropzone.processQueue();
            });
            dropzone.on('sending', function(file, xhr, formData) {
                formData.append('category_name', $('#category_name').val());
            });
        }
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
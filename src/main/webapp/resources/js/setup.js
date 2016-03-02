/**
 * Created by PaulRyan on 3/2/2016.
 */
var data = [];
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

$(document).ready(function () {
    $("#categoryAdd").click(function () {
        var categoryName = $('#inputCategory').val();
        var tags = $('#inputTags').tagsinput("items");
        var tr = "<tr><td>" + categoryName + "</td><td><a href='#!'><i class='fa fa-times'></i></a></td></tr>";
        $('#categoryTable').append(tr);
        data["name"] = categoryName;
        data["data"] = tags;
        post(data);
        $('#inputCategory').val("");
        $('#inputTags').tagsinput("removeAll");
    });
});

function post(data) {
    var jsonData = JSON.stringify(data);
    $.ajax({
        type: 'POST',
        url: "http://localhost:8080/trainTag",
        dataType: 'json',
        data: jsonData,
        contentType: "application/json",
        success: function(e) {
            console.log("success_save");
        }
    });
}
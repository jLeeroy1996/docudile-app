/**
 * Created by PaulRyan on 3/2/2016.
 */
var categories = [];
$("#inputStartYear").ready(function () {
    var select = document.getElementById("inputStartYear");
    for (var i = 1900; i <= 2016; ++i) {
        var option = document.createElement('option');
        option.text = option.value = i;
        select.add(option, 0);
    }
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

$(document).ready(function () {
    $("#categoryAdd").click(function () {
        var categoryName = $('#inputCategory').val();
        var tags = $('#inputTags').val();
        var tr = "<tr><td>" + categoryName + "</td><td><a href='#!'><i class='fa fa-times'></i></a></td></tr>";
        $('#categoryTable').append(tr);
        categories[categoryName] = tags;
        console.log(categories);
        $('#inputCategory').val("");
        $('#inputTags').tagsinput("removeAll");
    });
});

function post(path, params, method) {
    method = method || "post"; // Set method to post by default if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
        }
    }

    document.body.appendChild(form);
    form.submit();
}

$("#continueBtn").click(function () {
    post("/url-here", categories);
});
/**
 * Created by franc on 2/9/2016.
 */
$(document).on('ready', function() {
    var token = $("input[name='_csrf']").val();
    var url = "./upload-documents?_csrf=" + token;
    $("#uploadDoc").fileinput({showCaption: false, uploadUrl: url});
});
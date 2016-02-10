/**
 * Created by franc on 2/9/2016.
 */
$(document).on('ready', function() {
    var token = $("input[name='_csrf']").val();
    var doc_url = "./home/upload-documents?_csrf=" + token;
    var type_url = "./home/new-type?_csrf=" + token;
    $("#uploadDoc").fileinput({showCaption: false, uploadUrl: doc_url});
    $("#uploadSample").fileinput({
        showCaption: false,
        uploadExtraData: {
            typeName: $("input:text #inputTypeName").val()
        },
        uploadUrl: doc_url,
        uploadAsync: false
    });
});
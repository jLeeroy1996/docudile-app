/**
 * Created by franc on 2/9/2016.
 */
$(document).on('ready', function() {
    var token = $("input[name='_csrf']").val();
    var doc_url = "./upload-documents?_csrf=" + token;
    var type_url = "./new-type?_csrf=" + token;
    $("#uploadDoc").fileinput({showCaption: false, uploadUrl: doc_url});
    $("#uploadSampleStruct").fileinput({
        showCaption: false,
        uploadExtraData: {
            name: $("input:text #structureName").val()
        },
        uploadUrl: doc_url,
        uploadAsync: false
    });
    $("#uploadSampleCont").fileinput({
        showCaption: false,
        uploadExtraData: {
            name: $("input:text #contentName").val()
        },
        uploadUrl: doc_url,
        uploadAsync: false
    });
});
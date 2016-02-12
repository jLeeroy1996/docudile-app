/**
 * Created by franc on 2/9/2016.
 */
$(document).on('ready', function() {
    $.fn.modal.Constructor.prototype.enforceFocus = $.noop;
    var token = $("input[name='_csrf']").val();
    var doc_url = "./upload-documents?_csrf=" + token;
    var type_url = "./new-type?_csrf=" + token;
    $("#uploadDoc").fileinput({showCaption: false, uploadUrl: doc_url});
    $("#uploadSampleStructNew").fileinput({
        showCaption: false,
        uploadExtraData: {
            name: $("input:text #structureName").val()
        },
        uploadUrl: doc_url,
        uploadAsync: false
    });
    $("#uploadSampleStructRe").fileinput({
        showCaption: false,
        uploadExtraData: {
            name: $("input:text #structureName").val()
        },
        uploadUrl: doc_url,
        uploadAsync: false
    });
    $("#uploadSampleContNew").fileinput({
        showCaption: false,
        uploadExtraData: {
            name: $("input:text #contentName").val()
        },
        uploadUrl: doc_url,
        uploadAsync: false
    });
    $("#uploadSampleContRe").fileinput({
        showCaption: false,
        uploadExtraData: {
            name: $("input:text #contentName").val()
        },
        uploadUrl: doc_url,
        uploadAsync: false
    });
    $("#train-content").select2({
        data: [{id: 0, text: "OBE"}, {id: 1, text: "LOL"}, {id: 2, text: "DEAR"}, {id: 3, text: "DEAR"}, {id: 4, text: "DEAR"}, {id: 5, text: "DEAR"}, {id: 6, text: "DEAR"}, {id: 7, text: "DEAR"}]
    });
    $("#train-structure").select2({
        data: [{id: 0, text: "OBE"}, {id: 1, text: "LOL"}, {id: 2, text: "DEAR"}, {id: 3, text: "DEAR"}, {id: 4, text: "DEAR"}, {id: 5, text: "DEAR"}, {id: 6, text: "DEAR"}, {id: 7, text: "DEAR"}]
    });
});
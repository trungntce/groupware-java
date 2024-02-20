<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<div id="content" class="content">
    <h3 class="page-title"><spring:message code='gallery.bigtitle'/> <small><spring:message code='gallery.smalltitlefree'/></small></h3>
    <!-- html -->
    <div class="row">
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse" style="">
                <div class="panel-heading ui-sortable-handle">
                    <h4 class="panel-title"><spring:message code='emp.list.information'/></h4>
                </div>
                <div class="panel-body">
                    <form id="myform" class="form-inline" action="">
                        <table class="table mb-0">
                            <tbody>
                            <tr>
                                <td style="background: #f0f4f6;width: 15%"><spring:message code='gallery.topic'/></td>
                                <td style="width: 35%">${namedmt}</td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;width: 15%"><spring:message code='gallery.title'/></td>
                                <td style="width: 35%"><input style="width: 50%;" id="title" name="title"
                                                              class="form-control form-control-sm mr-1"
                                                              type="text"
                                                              value=""></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;width: 15%"><spring:message code='gallery.content'/></td>
                                <td style="width: 100%"><textarea id="noidung" name="noidung"
                                                                  class="form-control"
                                                                  style="width:100%"
                                                                  rows="2"
                                                                  cols="80"></textarea></td>
                            </tr>
                            <tr>
                                <td style="width: 15%">
                                    <button type="button" class="btn btn-success" onclick="addG()"><spring:message code='topic.bigtitle'/></button>
                                    <button type="button" class="btn btn-primary" onclick="back()"><spring:message code='dateboard.notice.company.back'/></button>
                                </td>
                                <td style="width: 35%"></td>
                                <td style="width: 15%"></td>
                                <td style="width: 35%"></td>
                            </tr>

                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
<script src="${_ctx}/resources/css/maincss/ckeditor/ckeditor.js"></script>
<script src="${_ctx}/resources/css/maincss/ckfinder/ckfinder.js"></script>
<script>
    $(document).ready(function () {

        var editor = CKEDITOR.replace("noidung");
        CKFinder.setupCKEditor(editor, '/resources/css/maincss/ckfinder/');

        $('#myform').validate({
            rules: {
                title: {
                    required: true,
                    minlength: 10,

                },
                noidung: {
                    required: true,
                    minlength: 10,

                },

            },

            messages: {
                title: {
                    required: "Please input title ",
                    minlength: "Min Length is 10 character",

                },
                noidung: {
                    required: "Please input Content ",
                    minlength: "Min Length is 10 character",

                },
            },

        });


    });

    function addG() {
        var isValid = $('#myform').valid();
        if (isValid == true) {
            if (CKEDITOR.instances['noidung'].getData().trim() != "") {
                var settings = {
                    "url": "/board/addFreeStyle",
                    "method": "POST",
                    "headers": {
                        "Content-Type": "application/json"
                    },
                    "data": JSON.stringify({
                        "idDmt": "${idDmt}",
                        "title": $("#title").val(),
                        "imgCover": "/resources/Upload/images/Notice-1.jpg",
                        "content": CKEDITOR.instances['noidung'].getData(),
                    }),
                };
                $.ajax(settings).done(function (response) {
                    alert("Success");
                    location.reload();
                });
            } else {
                toastr['error'](' Please input contents !');
            }
        }
    }
</script>

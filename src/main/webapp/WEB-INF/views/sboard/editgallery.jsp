<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<div id="content" class="content">
    <h3 class="page-title"><spring:message code='gallery.bigtitleedit'/> <small><spring:message code='gallery.smalltitlegallery'/></small></h3>
    <!-- html -->
    <div class="row">
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse" style="">
                <div class="panel-heading ui-sortable-handle">
                    <h4 class="panel-title"><spring:message code='emp.list.information'/></h4>
                </div>
                <div class="panel-body">
                        <form id="formUploadFile" class="form-inline" action="/updategallery" method="post" enctype="multipart/form-data" >
                            <input type="hidden" value="${lstDetail.id}" id="idTest" name="idTest"/>
                            <table class="table mb-0">
                            <tbody>
                            <tr>
                                <td style="background: #f0f4f6;width: 15%"><spring:message code='gallery.topic'/></td>
                                <td style="width: 35%">${lstDetail.nameDmt}</td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;width: 15%"><spring:message code='gallery.title'/></td>
                                <td style="width: 35%"><input style="width: 50%;" id="title" name="title"
                                                              class="form-control form-control-sm mr-1"
                                                              type="text"
                                                              value="${lstDetail.title}"></td>
                                <td></td>
                                <td></td>
                            </tr>

                            <tr>
                                <td style="background: #f0f4f6;width: 15%"><spring:message code='gallery.content'/></td>
                                <td style="width: 100%"><textarea id="noidung" name="noidung"
                                                                  class="form-control"
                                                                  style="width:100%"
                                                                  rows="2"
                                                                  cols="80">${lstDetail.content}</textarea></td>

                            </tr>

                            <tr>
                                <td style="background: #f0f4f6;width: 15%">Link 1</td>
                                <td style="width: 35%"><input style="width: 50%;" id="link1" name="link1"
                                                              class="form-control form-control-sm mr-1"
                                                              type="text"
                                                              value="${lstDetail.link1}"></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;width: 15%">Link 2</td>
                                <td style="width: 35%"><input style="width: 50%;" id="link2" name="link2"
                                                              class="form-control form-control-sm mr-1"
                                                              type="text"
                                                              value="${lstDetail.link2}"></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;width: 15%">Files</td>
                                <td style="width: 35%"><input type="file" class="form-control-file" id="multipartFile"
                                                              name="multipartFile"></td>
                                <td></td>
                                <td></td>
                            </tr>

                            <tr>
                                <td style="width: 15%">
                                    <button type="submit" class="btn btn-success"> <spring:message code='author.list.update'/></button>

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

        if ('${title}' != '') {
            toastr['${title}']('${message}');
        }

        var editor = CKEDITOR.replace("noidung");
        CKFinder.setupCKEditor(editor, '/resources/css/maincss/ckfinder/');

        $('#formUploadFile').validate({
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


</script>

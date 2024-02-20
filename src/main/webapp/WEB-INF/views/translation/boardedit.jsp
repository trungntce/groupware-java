<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<%
    String start_date = request.getParameter("start_date");
    if (start_date == null || start_date == "null") {
        start_date = "";
    }
    String end_date = request.getParameter("end_date");
    if (end_date == null || end_date == "null") {
        end_date = "";
    }
    String levelDeptGet = request.getParameter("levelDept");
    if (levelDeptGet == null || levelDeptGet == "null") {
        levelDeptGet = "1";
    }
    String optionSearchGet = request.getParameter("optionSearch");
    if (optionSearchGet == null || optionSearchGet == "null") {
        optionSearchGet = "";
    }
    String inputSearchGet = request.getParameter("inputSearch");
    if (inputSearchGet == null || inputSearchGet == "null") {
        inputSearchGet = "";
    }
    String displayStartPra = request.getParameter("displayStartPra");
    if (displayStartPra == null || displayStartPra == "null") {
        displayStartPra = "0";
    }
    String translationStatusGet = request.getParameter("translationStatus");
    if (translationStatusGet == null || translationStatusGet == "null") {
        translationStatusGet = "";
    }
    String backSearch="start_date="+start_date+"&end_date="+end_date+"&levelDept="+levelDeptGet+"&optionSearch="+optionSearchGet+"&inputSearch="+inputSearchGet+"&displayStartPra="+displayStartPra+"&translationStatus="+translationStatusGet;
%>
<div id="content" class="content">
    <script type="text/javascript" src="${_ctx}/resources/js/ckeditor/ckeditor.js"></script>
    <h3 class="page-title">
        <spring:message code='work.add.edit' />
        <br>
        <small><spring:message code='detail.edit' /></small>
    </h3>

    <!-- html -->
    <div class="row">
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse" style="">
                <div class="panel-heading ui-sortable-handle">
                    <h4 class="panel-title">
                        <spring:message code='work.add.information' />
                    </h4>
                </div>

                <div class="panel-body">
                    <form id="myform" class="form-inline" action="">
                        <table class="table mb-0">
                            <tbody>
                                <tr>
                                    <td style="background: #f0f4f6;width: 70px"><spring:message code='work.add.title' /></td>
                                    <td><input id="title" name="title" maxlength="200" class="form-control form-control-sm mr-1 w-100" ${transDTO.type} value="${transDTO.title}"></td>
                                </tr>
                                <tr>
                                    <td style="background: #f0f4f6;"><spring:message code='work.add.contents' /></td>
                                    <td><textarea id="contents" name="contents" class="ckeditor form-control" rows="2" cols="80">${transDTO.contents}</textarea></td>
                                </tr>

                                <tr>
                                    <td colspan="2">
                                        <button type="button" class="btn btn-success" onclick="EditW()"><spring:message code='work.add.edit' /></button>
                                        <button type="button" class="btn btn-primary" onclick="back()"><spring:message code='work.add.back' /></button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
<!-- script -->
<script>
    $(document).ready(function () {
        if ('${title}' != '') {
            if ('${title}' == 'success') {
                //toastr['${title}']('${message}');
                toastr.success('', "<spring:message code='notification.index.success' />", { timeOut: 1000 });
            } else {
                toastr.error('', "<spring:message code='notification.index.error' />", { timeOut: 1000 });
            }
        }
        $('#myform').validate({
            rules: {
                title: {
                    required: true,
                    minlength: 3,
                    maxlength: 200,
                },
                contents: {
                    required: true,
                },
            },

            messages: {
                title: {
                    required: "Please input title ",
                    minlength: "Min Length is 3 character",
                    maxlength: "Max Length is 200 character",
                },
                contents: {
                    required: "Please input contents ",
                },
            },
        });
    });

    function EditW() {
        on();
        var isValid = $('#myform').valid();
        var important = "N";
        var workStatus = "N";
        if (isValid == true) {
            if ($('#important').is(":checked")) {
                important = 'Y';
            }
            if ($('#workStatus').is(":checked")) {
                workStatus = 'Y';
            }
            var settings = {
                "url": "/work/edittrans",
                "method": "POST",
                "headers": {
                    "Content-Type": "application/json"
                },
                "data": JSON.stringify({
                    "translationId": '${transDTO.translationId}',
                    "title": $("#title").val(),
                    "contents": CKEDITOR.instances['contents'].getData(),
                }),
            };
            $.ajax(settings).done(function (response) {
                location.href = "/board/notice/${loai}/view.hs?boardId=${boardId}&title=success&message=Edit sucess"+"&<%=backSearch%>";
                off();
            });
        }else{
            off();
        }
    }
    function back() {
        location.href = "/board/notice/${loai}/view.hs?boardId=${boardId}"+"&<%=backSearch%>";
    }

</script>
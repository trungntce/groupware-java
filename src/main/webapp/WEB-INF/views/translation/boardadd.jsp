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
        <spring:message code='work.addtran.addnew'/>
        <br>
        <small><spring:message code='detail.add'/></small>
    </h3>
    <!-- html -->
    <div class="row">
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse">
                <div class="panel panel-inverse">
                    <div class="panel-heading ui-sortable-handle">
                        <h4 class="panel-title"><spring:message code='work.addtran.contents'/></h4>
                    </div>
                    <div class="panel-body">
                        <form class="form-inline" method="post">
                            <table class="table mb-0">
                                <tbody>
                                    <tr>
                                        <td style="background: #f0f4f6; width: 90px;"><spring:message code='work.addtran.title'/></td>
                                        <td>${detailDTO.title}</td>
                                    </tr>
                                    <tr>
                                        <td style="background: #f0f4f6;"><spring:message code='work.addtran.contents'/></td>
                                        <td>${detailDTO.contents}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </div>
            </div>

            <div class="panel panel-inverse mt-1">
                <div class="panel-heading ui-sortable-handle">
                    <h4 class="panel-title"><spring:message code='work.addtran.information'/></h4>
                </div>
                <div class="panel-body">
                    <form id="myform" class="form-inline" action="">
                        <table class="table mb-0">
                            <tbody>
                                <tr>
                                    <td style="background: #f0f4f6; width: 90px;"><spring:message code='work.addtran.lang'/></td>
                                    <td>
                                        <select id="language" name="language" class="custom-select custom-select-sm form-control form-control-sm mr-1 w-100">
                                            <c:forEach items="${lstLang}" var="lstL">
                                                <option value="${lstL.langCd}">${lstL.langName}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="background: #f0f4f6;"><spring:message code='work.addtran.title'/></td>
                                    <td><input id="title" name="title" maxlength="200" class="form-control form-control-sm mr-1 w-100" type="text"></td>
                                </tr>
                                <tr>
                                    <td style="background: #f0f4f6;"><spring:message code='work.addtran.contents'/></td>
                                    <td><textarea id="contents" name="contents" class="ckeditor form-control w-100" rows="2" cols="80"></textarea></td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <button type="button" class="btn btn-success" onclick="add()"><spring:message code='work.list.save'/></button>
                                        <button type="button" class="btn btn-primary" onclick="back()"><spring:message code='work.addtran.back'/></button>
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
                toastr.success('', "<spring:message code='notification.index.success'/>", { timeOut: 1000 });
            } else {
                toastr.error('', "<spring:message code='notification.index.error'/>", { timeOut: 1000 });
            }
        }

        $('#myform').validate({
            event: 'blur',
            rules: {
                title: {
                    required: true,
                    minlength: 3,
                    maxlength: 200,
                },
                contents: {
                    required: function () {
                        CKEDITOR.instances.contents.updateElement();
                    },
                }

            },
            messages: {
                title: {
                    required: "Please input title ",
                    minlength: "Min Length is 3 character",
                    maxlength: "Max Length is 200 character",
                },
                contents: {
                    required: "Please input contents ",

                }
            },
        });
    });

    function back() {
        location.href = "/board/notice/${type}/view.hs?boardId=" + '${id}'+"&<%=backSearch%>";
    }

    function add() {
        on();
        console.log(CKEDITOR.instances['contents'].getData())

        var isValid = $('#myform').valid();
        if (isValid == true) {
            var settings = {
                "url": "/translational/them.hs",
                "method": "POST",
                "headers": {
                    "Content-Type": "application/json"
                },
                "data": JSON.stringify({
                    "title": $("#title").val(),
                    "langCd": $("#language").val(),
                    "contents": CKEDITOR.instances['contents'].getData(),
                    "translationId": '${id}',
                    "type": '${loai}'

                }),
            };
            $.ajax(settings).done(function (response) {
                if (response == "g") {
                    location.href = "/board/notice/group/view.hs?boardId=${id}&title=success&message=Add new sucess"+"&<%=backSearch%>";
                    off();
                }
                if (response == "d") {
                    location.href = "/board/notice/department/view.hs?boardId=${id}&title=success&message=Add new sucess"+"&<%=backSearch%>";
                    off();
                }
                if (response == "f") {
                    location.href = "/board/notice/free/view.hs?boardId=${id}&title=success&message=Add new sucess"+"&<%=backSearch%>";
                    off();
                }
                if (response == "c") {
                    location.href = "/board/notice/company/view.hs?boardId=${id}&title=success&message=Add new sucess"+"&<%=backSearch%>";
                    off();
                }
            });
        }else{
            off();
        }
    }

</script>
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
    String translationStatusGet = request.getParameter("translationStatus");
    if (translationStatusGet == null || translationStatusGet == "null") {
        translationStatusGet = "";
    }
    String workStatusGet = request.getParameter("workStatus");
    if (workStatusGet == null || workStatusGet == "null") {
        workStatusGet = "";
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
    String backSearch = "start_date=" + start_date + "&end_date=" + end_date + "&levelDept=" + levelDeptGet + "&translationStatus=" + translationStatusGet + "&workStatus=" + workStatusGet + "&optionSearch=" + optionSearchGet + "&inputSearch=" + inputSearchGet + "&displayStartPra=" + displayStartPra;
%>
<div id="content" class="content">
    <script type="text/javascript" src="${_ctx}/resources/js/ckeditor/ckeditor.js"></script>
    <h3 class="page-title">
        <spring:message code='main.index.work'/> - <spring:message code='work.add.edit' />
        <br>
        <small>
            <spring:message code='detail.edit' />
        </small>
    </h3>
    <div class="text-right">
        <button type="button" class="btn btn-success" onclick="goEvent()">
            <spring:message code='work.add.edit'/>
        </button>
        <button type="button" class="btn btn-primary" onclick="back()">
            <spring:message code='work.add.back'/>
        </button>
    </div>
    <br/>
    <!-- html -->
    <div class="row">
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse">

                <div class="panel-heading ui-sortable-handle">
                    <h4 class="panel-title"><spring:message code='work.add.information'/></h4>
                </div>

                <div class="panel-body">
                    <form id="myform" class="form-inline" action="">
                        <table class="table mb-0">
                            <tbody>
                            <tr>
                                <td style="background: #f0f4f6; width: 90px;"><spring:message code='work.add.title'/></td>
                                <td><input id="title" name="title" class="form-control form-control-sm mr-1 w-100" type="text" value="${workDTO.title}"></td>
                            </tr>

                            <tr>
                                <td style="background: #f0f4f6"><spring:message code='work.add.contents'/></td>
                                <td>
                                    <table class="w-100">
                                        <thead>
                                            <tr>
                                                <th><spring:message code="work.detail.contents"/></th>
                                                <th style="width: 60px"><spring:message code='work.list.delete'/></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${workChild}" var="lst">
                                            <c:choose>
                                                <c:when test="${lst.rownum == 1}">
                                                    <tr id="row-${lst.id}">
                                                        <td><input id="${lst.id}" name="contents" class="form-control form-control-sm mr-1 w-100" type="text" value="${lst.contents}"></td>
                                                        <td><button class="btn btn-success" type="button" onclick="addMore()"><i class="fas fa-plus"></i></button></td>
                                                    </tr>
                                                </c:when>
                                                <c:otherwise>
                                                    <tr id="row-${lst.id}">
                                                        <td><input id="${lst.id}" name="contents" class="form-control form-control-sm mr-1 w-100" type="text" value="${lst.contents}"></td>
                                                        <td><button class="btn btn-success" type="button" onclick="removeRowContent('row-${lst.id}','${lst.id}')"><i class="fas fa-minus"></i></button></td>
                                                    </tr>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <div id="test-work"></div>
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

        enterCall();
        var workstart = $("#workstart").datepicker();
        var workend = $("#workend").datepicker();
        if ('${title}' != '') {
            if ('${title}' == 'success') {
                //toastr['${title}']('${message}');
                toastr.success('', "<spring:message code='notification.index.success' />", {timeOut: 1000});
            } else {
                toastr.error('', "<spring:message code='notification.index.error' />", {timeOut: 1000});
            }
        }

        $('#myform').validate({
            rules: {
                title: {
                    required: true,
                    minlength: 3,
                    maxlength: 100,
                },
                contents: {
                    required: true,
                },
            },

            messages: {
                title: {
                    required: "Please input title ",
                    minlength: "Min Length is 3 character",
                    maxlength: "Max Length is 100 character",
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
        if (isValid == true) {
            var settings = {
                "url": "/work/daywork/editW",
                "method": "POST",
                "headers": {
                    "Content-Type": "application/json"
                },
                "data": JSON.stringify({
                    "dayWorkId": '${workDTO.dayWorkId}',
                    "empCd": '${workDTO.empCd}',
                    "title": $("#title").val(),
                    "contents": CKEDITOR.instances['contents'].getData(),
                }),
            };
            $.ajax(settings).done(function (response) {
                location.href = "/work/daywork/detail.hs?id=${workDTO.dayWorkId}&title=success&message=Edit sucess&<%=backSearch%>";
                off();
            });
        } else {
            off();
        }
    }

    function back() {
        location.href = "/work/daywork/detail.hs?id=${workDTO.dayWorkId}&<%=backSearch%>";
    }

    let number = ${workDTO.contents};

    let subNumber = number + 1;

    function addMore() {
        number = number + 1;

        let strDiv = 'test-work'
        let strPlus = '#test-work'
        let preStr = strDiv + number;
        let idContent = 'idcontent' + number;
        let valueStr = "'idTrContent" + number + "'";
        let subStr = '<div id="' + preStr + '"></div>'
        let str = '<table class="w-100"><tbody><tr id="idTrContent' + number + '"><td><input id="' + idContent + '" name="contents" class="form-control form-control-sm mr-1 w-100" type="text"></td><td style="width: 60px"><button class="btn btn-success" onclick="removeRow(' + valueStr + ')"><i class="fas fa-minus"></i></button></td></tr></tbody></table>' + subStr;
        if (number === subNumber) {
            $(strPlus).html(str);
        } else {
            let lastStr = strPlus + (number - 1);
            $(lastStr).html(str);
            //console.log(lastStr)
        }

    }

    let cldt = "";

    function goEvent() {
        on();
        let strSave = "";
        let totalStr = "";
        let arrayInput = [];

        <c:forEach items="${workChild}" var="lst">
            //console.log($("#"+${lst.id}).val());
            if ($("#" +${lst.id}).val() != '' && $("#" +${lst.id}).val() != undefined) {
                arrayInput.push({"${lst.id}": $("#" + ${ lst.id }).val()});
            }

        </c:forEach>


        for (let i = (${ workDTO.contents } +1); i <= number; i++) {
            cldt = '#idcontent' + i;
            console.log(cldt)
            if ($(cldt).val() != '' && $(cldt).val() != undefined) {
                arrayInput.push({"contents": $(cldt).val()});
            }
        }

        arrayInput.push({"title": $("#title").val()});

        console.log(arrayInput)
        var isValid = $('#myform').valid();
        if (isValid == true) {
            var settings = {
                "url": "/work/daywork/editW?dayWorkId=${workDTO.dayWorkId}&type=${type}",
                "method": "POST",
                "headers": {
                    "Content-Type": "application/json"
                },
                "data": JSON.stringify(arrayInput),
            };
            $.ajax(settings).done(function (response) {
                off();
                location.href = "/work/daywork/detail.hs?id=${workDTO.dayWorkId}&title=success&message=Add new sucess&<%=backSearch%>";
            });
        }
    }

    function removeRow(str) {
        let linkStr = '#' + str
        console.log(linkStr)
        $(linkStr).html('');
    }

    function removeRowContent(str, id) {
        $.ajax({
            type: "POST",
            url: "/work/daywork/deleteWorkChild",
            data: {
                'id': id,
            },
            success: function (response) {
                //location.href = "/emp/list.hs?title=success&message=Delete sucess";
                toastr.success('', "<spring:message code='notification.index.success' />", {timeOut: 1000});
            },
            error: function (error) {
                toastr.success('', "<spring:message code='notification.index.error' />", {timeOut: 1000});
            }
        })
        let linkStr = '#' + str
        console.log(linkStr)
        $(linkStr).html('');
    }
</script>
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
    String backSearch="start_date="+start_date+"&end_date="+end_date+"&levelDept="+levelDeptGet+"&translationStatus="+translationStatusGet+"&workStatus="+workStatusGet+"&optionSearch="+optionSearchGet+"&inputSearch="+inputSearchGet+"&displayStartPra="+displayStartPra;
%>
        <div id="content" class="content">
            <script type="text/javascript" src="${_ctx}/resources/js/ckeditor/ckeditor.js"></script>
            <h3 class="page-title">
                <spring:message code='work.add.tieude' />
            </h3>
            <div class="text-right">
                <button type="button" class="btn btn-success" onclick="goEvent()">
                    <spring:message code='work.list.save' />
                </button>
                <button type="button" class="btn btn-primary" onclick="back()">
                    <spring:message code='work.add.back' />
                </button>
            </div>
            <br/>
            <!-- html -->
            <!-- <button style="margin-bottom: 10px;" type="button" class="btn btn-success" onclick="addMore()">
                <spring:message code='button.rowadd' />
            </button> -->
            <div class="row">
                <div class="col-xl-12 ui-sortable">
                    <div class="panel panel-inverse">
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
                                            <td style="background: #f0f4f6;width: 15%">
                                                <spring:message code='work.add.contents' />
                                            </td>
                                            <td style="width: 100%">
                                                <table>
                                                    <tbody>
                                                        <thead>
                                                            <tr>
                                                                <th style="width: 600px;text-align: center;">
                                                                    <spring:message code="work.detail.contents" />
                                                                </th>
                                                                <th style="width: 100px;text-align: center;">
                                                                    <spring:message code='work.list.delete' />
                                                                </th>
                                                            </tr>
                                                        </thead>
                                                        <tr>
                                                            <td>
                                                                <input id="idcontent1" name="title"
                                                                    style=" height: 38px; font-size: 17px;width: 600px;"
                                                                    class="form-control form-control-sm mr-1"
                                                                    type="text" value="">
                                                            </td>

                                                            <td style="text-align: center;vertical-align: inherit;">
                                                                <button class="btn btn-success" type="button"
                                                                    onclick="addMore()">
                                                                    <i class="fas fa-plus"></i>
                                                                </button>

                                                            </td>
                                                        </tr>

                                                        <tr id="idTrContent2">
                                                            <td>
                                                                <input id="idcontent2" name="title"
                                                                    style=" height: 38px; font-size: 17px;width: 600px;"
                                                                    class="form-control form-control-sm mr-1"
                                                                    type="text" value="">
                                                            </td>

                                                            <td style="text-align: center;vertical-align: inherit;">
                                                                <button class="btn btn-success"
                                                                    onclick="removeRow('idTrContent2')">
                                                                    <i class="fas fa-minus"></i>
                                                                </button>

                                                            </td>

                                                        </tr>

                                                        <tr id="idTrContent3">
                                                            <td>
                                                                <input id="idcontent3" name="title"
                                                                    style=" height: 38px; font-size: 17px;width: 600px;"
                                                                    class="form-control form-control-sm mr-1"
                                                                    type="text" value="">
                                                            </td>

                                                            <td style="text-align: center;vertical-align: inherit;">
                                                                <button class="btn btn-success"
                                                                    onclick="removeRow('idTrContent3')">
                                                                    <i class="fas fa-minus"></i>
                                                                </button>

                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                                <div id="test-work">
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>
                <input id="idcontent" name="" type="hidden" value="">

            </div>
        </div>


        <!-- script -->
        <script>
            $(document).ready(function () {
                enterCall();
                if ('${title}' != '') {
                    if ('${title}' == 'success') {
                        //toastr['${title}']('${message}');
                        toastr.success('', "<spring:message code='notification.index.success' />", { timeOut: 1000 });
                    } else {
                        toastr.error('', "<spring:message code='notification.index.error' />", { timeOut: 1000 });
                    }
                }

                $(".open_slide_btn").click(function () {
                    $(this).hide();
                    $(this).next().show();
                    $(this).parent().parent().next().slideDown();
                });

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

            let number = 3;
            function addMore() {
                number = number + 1;
                let strDiv = 'test-work'
                let strPlus = '#test-work'
                let preStr = strDiv + number;
                let idContent = 'idcontent' + number;
                let valueStr = "'idTrContent" + number + "'";
                let subStr = '<div id="' + preStr + '"></div>'
                let str = '<table><tbody><tr id="idTrContent' + number + '"><td><input id="' + idContent + '" name="title" style="width:600px; height: 38px; font-size: 17px;"class="form-control form-control-sm mr-1" type="text" value=""></td><td style="text-align: center;vertical-align: inherit;width: 100px"> <button class="btn btn-success" onclick="removeRow(' + valueStr + ')"><i class="fas fa-minus"></i></button></td></tr></tbody></table>' + subStr;
                if (number === 4) {
                    $(strPlus).html(str);
                }
                else {
                    let lastStr = strPlus + (number - 1);
                    $(lastStr).html(str);
                    //console.log(lastStr)
                }

            }

            let cldt = "";

            function goEvent() {
                on();
                let arrayInput = [];

                for (let i = 1; i <= number; i++) {
                    cldt = '#idcontent' + i;
                    strSave = '';

                    if ($(cldt).val() != '' && $(cldt).val() != undefined) {
                        arrayInput.push({ "contents": $(cldt).val() });
                    }
                }

                arrayInput.push({ "title": '${daywork}' });

                console.log(arrayInput)
                var isValid = $('#myform').valid();
                if (isValid == true) {
                    var settings = {
                        "url": "/work/daywork/addmore",
                        "method": "POST",
                        "headers": {
                            "Content-Type": "application/json"
                        },
                        "data": JSON.stringify(arrayInput),
                    };
                    $.ajax(settings).done(function (response) {
                        location.href = "/work/mydaywork/detail.hs?id=${daywork}&<%=backSearch%>";
                        off();
                    });
                }
            }

            function back() {
                location.href = "/work/mydaywork/detail.hs?id=${daywork}&<%=backSearch%>";
            }
            function removeRow(str) {
                let linkStr = '#' + str
                console.log(linkStr)
                $(linkStr).html('');
            }

        </script>
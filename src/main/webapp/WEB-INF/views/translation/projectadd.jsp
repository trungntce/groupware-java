<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<%
    String start_date1 = request.getParameter("start_date1");
    if (start_date1 == null || start_date1 == "null") {
        start_date1 = "";
    }

    String end_date1 = request.getParameter("end_date1");
    if (end_date1 == null || end_date1 == "null") {
        end_date1 = "";
    }

    String levelDeptGet1 = request.getParameter("levelDept1");
    if (levelDeptGet1 == null || levelDeptGet1 == "null") {
        levelDeptGet1 = "1";
    }
    request.setAttribute("levelDeptGet1", levelDeptGet1);

    String translationStatusGet1 = request.getParameter("translationStatus1");
    if (translationStatusGet1 == null || translationStatusGet1 == "null") {
        translationStatusGet1 = "";
    }
    request.setAttribute("translationStatusGet1", translationStatusGet1);

    String approval1 = request.getParameter("approval1");
    if (approval1 == null || approval1 == "null") {
        approval1 = "";
    }
    request.setAttribute("approval1", approval1);

    String optionSearchGet1 = request.getParameter("optionSearch1");
    if (optionSearchGet1 == null || optionSearchGet1 == "null") {
        optionSearchGet1 = "";
    }
    request.setAttribute("optionSearchGet1", optionSearchGet1);

    String inputSearchGet1 = request.getParameter("inputSearch1");
    if (inputSearchGet1 == null || inputSearchGet1 == "null") {
        inputSearchGet1 = "";
    }

    String displayStartPra1 = request.getParameter("displayStartPra1");
    if (displayStartPra1 == null || displayStartPra1 == "null") {
        displayStartPra1 = "0";
    }


    //##########################################################################################################################

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
    request.setAttribute("levelDeptGet", levelDeptGet);
    String translationStatusGet = request.getParameter("translationStatus");
    if (translationStatusGet == null || translationStatusGet == "null") {
        translationStatusGet = "";
    }
    request.setAttribute("translationStatusGet", translationStatusGet);
    String dayProjectStatus = request.getParameter("dayProjectStatus");
    if (dayProjectStatus == null || dayProjectStatus == "null") {
        dayProjectStatus = "";
    }
    request.setAttribute("dayProjectStatus", dayProjectStatus);
    String optionSearchGet = request.getParameter("optionSearch");
    if (optionSearchGet == null || optionSearchGet == "null") {
        optionSearchGet = "";
    }
    request.setAttribute("optionSearchGet", optionSearchGet);
    String inputSearchGet = request.getParameter("inputSearch");
    if (inputSearchGet == null || inputSearchGet == "null") {
        inputSearchGet = "";
    }
    String displayStartPra = request.getParameter("displayStartPra");
    if (displayStartPra == null || displayStartPra == "null") {
        displayStartPra = "0";
    }
    String backSearch="start_date="+start_date+"&end_date="+end_date+"&levelDept="+levelDeptGet+"&translationStatus="+translationStatusGet+"&dayProjectStatus="+dayProjectStatus+"&optionSearch="+optionSearchGet+"&inputSearch="+inputSearchGet+"&displayStartPra="+displayStartPra+"&start_date1="+start_date1+"&end_date1="+end_date1+"&levelDept1="+levelDeptGet1+"&translationStatus1="+translationStatusGet1+"&approval1="+approval1+"&optionSearch1="+optionSearchGet1+"&inputSearch1="+inputSearchGet1+"&displayStartPra1="+displayStartPra1;

%>
        <div id="content" class="content">
            <script type="text/javascript" src="${_ctx}/resources/js/ckeditor/ckeditor.js"></script>
            <h3 class="page-title">
                <spring:message code='work.addtran.addnew' />
                <br>
                <small>
                    <spring:message code='detail.add' />
                </small>
            </h3>
            <!-- html -->
            <div class="row">
                <div class="col-xl-12 ui-sortable">
                    <div class="panel panel-inverse" style="">


                        <div class="panel panel-inverse" style="">
                            <div class="panel-heading ui-sortable-handle">
                                <h4 class="panel-title">
                                    <spring:message code='work.addtran.contents' />
                                </h4>
                            </div>
                            <div class="panel-body">
                                <form id="" class="form-inline" action="" method="post">
                                    <table class="table mb-0">
                                        <tbody>
                                            <tr>
                                                <td style="background: #f0f4f6;width: 250px;">
                                                    <spring:message code='work.addtran.contents' />
                                                </td>
                                                <td>${DTO.title}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </form>
                            </div>
                        </div>

                        <div class="panel-heading ui-sortable-handle">
                            <h4 class="panel-title">
                                <spring:message code='work.addtran.information' />
                            </h4>
                        </div>
                        <div class="panel-body">
                            <form id="myform" class="form-inline" action="">
                                <table class="table mb-0">
                                    <tbody>
                                        <tr>
                                            <td style="background: #f0f4f6;width: 250px;">
                                                <spring:message code='work.addtran.lang' />
                                            </td>
                                            <td style=" ">
                                                <select style="width: 40%;" id="language" name="language"
                                                    class="custom-select custom-select-sm form-control form-control-sm mr-1">

                                                    <c:forEach items="${lstLang}" var="lstL">
                                                        <option value="${lstL.langCd}">${lstL.langName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;width: 250px;">
                                                <spring:message code='work.addtran.title' />
                                            </td>
                                            <td style=" "><input style="width: 40%;" id="title" name="title" maxlength="200"
                                                    class="form-control form-control-sm mr-1" type="text" value=""></td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;width: 250px;">
                                                <spring:message code='work.addtran.contents' />
                                            </td>
                                            <td style=" "><textarea id="contents" name="contents"
                                                    class="ckeditor form-control" style="width:100%" rows="2"
                                                    cols="80"></textarea></td>
                                        </tr>
                                        <tr>
                                            <td style="width: 250px;">
                                                <button type="button" class="btn btn-success" onclick="add()">
                                                    <spring:message code='work.list.save' />
                                                </button>
                                                <button type="button" class="btn btn-primary" onclick="back()">
                                                    <spring:message code='work.addtran.back' />
                                                </button>
                                            </td>
                                            <td style=" "></td>
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
                if("${type}"=="dpi"){
                    location.href="/project/project/detail.hs?pjId=${pjId}&dpId=${dpId}&<%=backSearch%>";
                }
                if("${type}"=="dp"){
                    location.href="/project/project/dayproject.hs?pjId=${pjId}&<%=backSearch%>";
                }
                if("${type}"=="p"){
                    location.href="/project/project/list.hs?<%=backSearch%>";
                }
            }

            function add() {
                on();
                console.log(CKEDITOR.instances['contents'].getData())

                var isValid = $('#myform').valid();
                if (isValid == true) {
                    var settings = {
                        "url": "/translational/addTranProject",
                        "method": "POST",
                        "headers": {
                            "Content-Type": "application/json"
                        },
                        "data": JSON.stringify({
                            "title": $("#title").val(),
                            "langCd": $("#language").val(),
                            "contents": CKEDITOR.instances['contents'].getData(),
                            "translationId": '${id}',
                            "type": '${type}'

                        }),
                    };
                    $.ajax(settings).done(function (response) {
                        if (response == "dpi") {
                            location.href = "/project/project/detail.hs?pjId=${pjId}&dpId=${dpId}&title=success&message=Add new sucess&<%=backSearch%>";
                            off();
                        }
                        if (response == "dp") {
                            location.href = "/project/project/dayproject.hs?pjId=${pjId}&title=success&message=Add new sucess&<%=backSearch%>";
                            off();
                        }
                        if (response == "p") {
                            location.href = "/project/project/list.hs?title=success&message=Add new sucess&<%=backSearch%>";
                            off();
                        }

                    });
                }else{
                    off();
                }
            }

        </script>
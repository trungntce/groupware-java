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
        <style>
            ::placeholder {
                color: red !important;
            }
            .currency:after {
                content: ' VND';
            }
        </style>
        <div id="content" class="content">
            <h3 class="page-title">
                <spring:message code='dayproject.dayproject.addbudget' />
                <br>
                <small>
                    <spring:message code='detail.add' />
                </small>
            </h3>
            <!-- html -->
            <div class="row">
                <div class="col-xl-12 ui-sortable">
                    <div class="panel panel-inverse" style="">
                        <div class="panel-heading ui-sortable-handle">
                            <h4 class="panel-title">
                                <spring:message code='emp.list.information' />
                            </h4>
                        </div>
                        <div class="panel-body">
                            <form id="myform" class="form-inline" action="">
                                <table class="table mb-0">
                                    <tbody>
                                        <tr>
                                            <td style="background: #f0f4f6;width: 15%">
                                                <spring:message code='dayproject.adddayproject.projectname' />
                                            </td>
                                            <td style="width: 35%">
                                                ${lstInfoProjectByID.title}
                                            </td>
                                            <td style="background: #f0f4f6;width: 15%">
                                                <spring:message code='dayproject.adddayproject.spentstatus' />
                                            </td>
                                            <td style="width: 35%">
                                               ${langCd=='ko' ? '예산추가': (langCd=='en' ? 'deposit':'Nạp tiền')}
                                            </td>

                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;width: 15%">
                                                <spring:message code='emp.list.department' />
                                            </td>
                                            <td style="width: 35%">
                                                ${lstInfoProjectByID.deptName}
                                            </td>
                                            <td style="background: #f0f4f6;width: 15%">
                                                <spring:message code='emp.list.position' />
                                            </td>
                                            <td style="width: 35%">
                                                ${lstInfoProjectByID.positionName}
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;width: 15%">
                                                <spring:message code='emp.list.empname' />
                                            </td>
                                            <td style="width: 35%">
                                                ${lstInfoProjectByID.empName}
                                            </td>
                                            <td style="background: #f0f4f6;width: 15%">
                                                <spring:message code='dayproject.adddayproject.createdate' />
                                            </td>
                                            <td style="width: 35%">
                                                ${lstInfoProjectByID.regDt}
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;width: 15%">
                                                <spring:message code='dayproject.adddayproject.title' />
                                            </td>
                                            <td style="width: 35%"><textarea id="title" name="title" maxlength="1000"
                                                    class="form-control" style="width:100%" rows="2"
                                                    cols="80"></textarea></td>
                                            <td style="background: #f0f4f6;width: 15%">
                                                <spring:message code='dayproject.adddayproject.amount' />
                                            </td>
                                            <td  style="width: 35%">
                                                <input style="width: 40%;" id="project_price" name="project_price" class="form-control form-control-sm mr-1" type="number" value="">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="4">
                                                <button type="button" class="btn btn-success" onclick="addP()">
                                                    <spring:message code='emp.list.add' />
                                                </button>
                                                <button type="button" class="btn btn-primary" onclick="back()">
                                                    <spring:message code='work.addtran.back' />
                                                </button>
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
                $('#myform').validate({
                    rules: {
                        title: {
                            required: true,
                            minlength: 3,
                            maxlength: 1000,
                        },
                        project_price: {
                            required: true,
                        },
                    },

                    messages: {
                        title: {
                            required: "Please input Title ",
                            minlength: "Min Length is 3 character",
                            maxlength: "Max Length is 1000 character",
                        },
                        project_price: {
                            required: "Please input money ",
                        },

                    },

                });
            });

            function back() {
                 location.href = "/project/project/dayproject.hs?pjId=${pjId}&<%=backSearch%>";
            }
            function addP() {

                var isValid = $('#myform').valid();
                var amount = $("#project_price").val();
                
                if (isValid == true  && amount > 0) {
                    on();
                    var settings = {
                        "url": "/API/dayproject/addDeposit",
                        "method": "POST",
                        "headers": {
                            "Content-Type": "application/json"
                        },
                        "data": JSON.stringify({
                            pjId: '${pjId}',
                            title: $("#title").val(),
                            projectPrice: $("#project_price").val()
                        }),
                    };
                    $.ajax(settings).done(function (response) {
                        location.href = "/project/project/dayproject.hs?pjId=${pjId}&title=success&message=Add new sucess&<%=backSearch%>";
                        off();
                    });

                } else if(amount <= 0){
                    alert("<spring:message code='project.value.amount' />");
                    off();
                }
                 else {
                    off();
                }
            }

        </script>
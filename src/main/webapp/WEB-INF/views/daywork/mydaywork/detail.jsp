<%@ page import="java.time.format.DateTimeFormatter" %>
    <%@ page import="java.time.LocalDateTime" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
                <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
                    <link href="${_ctx}/resources/js/plugins/jstree/dist/themes/default/style.min.css"
                        rel="stylesheet" />

                    <link rel="stylesheet"
                        href="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.css">
                    <script src="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.js"></script>

                    <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css"
                        rel="stylesheet">
                    <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
                    <c:set var="test" value="${detailDTO.regDt}" />
                    <style>
                        .comment-table-th{
                            text-align: center;
                        }
                    </style>
                    <% DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd"); LocalDateTime
                        now=LocalDateTime.now(); String currentDate=dtf.format(now).toString(); String workDate=(String)
                        pageContext.getAttribute("test"); workDate=workDate.split(" ")[0];
    String disable = "";
    if (!workDate.equals(currentDate)) {
        disable = " disabled"; } %>
                        <% String start_date=request.getParameter("start_date"); if (start_date==null ||
                            start_date=="null" ) { start_date="" ; } String end_date=request.getParameter("end_date");
                            if (end_date==null || end_date=="null" ) { end_date="" ; } String
                            levelDeptGet=request.getParameter("levelDept"); if (levelDeptGet==null ||
                            levelDeptGet=="null" ) { levelDeptGet="1" ; } String
                            translationStatusGet=request.getParameter("translationStatus"); if
                            (translationStatusGet==null || translationStatusGet=="null" ) { translationStatusGet="" ; }
                            String workStatusGet=request.getParameter("workStatus"); if (workStatusGet==null ||
                            workStatusGet=="null" ) { workStatusGet="" ; } String
                            optionSearchGet=request.getParameter("optionSearch"); if (optionSearchGet==null ||
                            optionSearchGet=="null" ) { optionSearchGet="" ; } String
                            inputSearchGet=request.getParameter("inputSearch"); if (inputSearchGet==null ||
                            inputSearchGet=="null" ) { inputSearchGet="" ; } String
                            displayStartPra=request.getParameter("displayStartPra"); if (displayStartPra==null ||
                            displayStartPra=="null" ) { displayStartPra="0" ; } String backSearch="start_date=" +
                            start_date + "&end_date=" + end_date + "&levelDept=" + levelDeptGet + "&translationStatus="
                            + translationStatusGet + "&workStatus=" + workStatusGet + "&optionSearch=" + optionSearchGet
                            + "&inputSearch=" + inputSearchGet + "&displayStartPra=" + displayStartPra; %>
                            <div id="content" class="content">
                                <h3 class="page-title">
                                    <spring:message code='main.index.work' /> -
                                    <spring:message code='work.detail.tieude' />
                                    <br>
                                    <small>
                                        <spring:message code='detail.message' />
                                    </small>
                                </h3>
                                <div>
                                    <div></div>
                                    <div style="float: right">
                                        <h4 class="panel-title">
                                            <!-- <button style="background: red;" type="button" class="btn btn-purple"
                                            <%=disable%> onclick="saveW()">
                                            <spring:message code='work.list.save' />
                                        </button> -->
                                            <c:choose>
                                                <c:when test="${statusEdit==1}">
                                                    <button type="button" class="btn btn-purple" <%=disable%>
                                                        onclick="editW()">
                                                        <spring:message code='work.detail.edit' />
                                                    </button>
                                                    <button type="button" class="btn btn-primary" <%=disable%>
                                                        onclick="addmore()">
                                                        <spring:message code='work.detail.addmoredaywork' />
                                                    </button>
                                                </c:when>
                                            </c:choose>
                                            <button type="button" class="btn btn-primary" onclick="back()">
                                                <spring:message code='work.detail.back' />
                                            </button>
                                        </h4>
                                    </div>
                                </div>
                                <script>
                                    $(document).ready(function () {

                                        var target = $(".toggle-group");
                                        $(target).click(function (index) {
                                            var number = 0;
                                            if (confirm("<spring:message code='work.detail.contents.change.confirm' />")) {
                                                var target_index = target.index(this);
                                                $(".toggle-group").each(function (index) {
                                                    number = number + 1;
                                                    if (target_index != index) {
                                                        $(this).prop('checked', false).change();
                                                    }
                                                    else {
                                                        var thisStatus;

                                                        var checkboxes = document.getElementsByName('checkboxStatus');
                                                        var id = checkboxes[(number / 2) - 1].value;
                                                        var preCheck = checkboxes[0].checked;

                                                        $.ajax({
                                                            type: "POST",
                                                            url: "/work/daywork/updateInfor",
                                                            data: {
                                                                id: id,
                                                                "dayworkid": '${detailDTO.dayWorkId}',
                                                            },
                                                            success: function (response) {
                                                                if(response == 'A'){
                                                                    toastr['success']("<spring:message code='notification.index.success'/>");
                                                                }else{
                                                                    alert("<spring:message code='dragdrop.alertpermission'/>");
                                                                }
                                                                
                                                            },
                                                            error: function (error) {
                                                                toastr['error']('error !');
                                                            }
                                                        });
                                                    }
                                                });


                                            } else {
                                                event.stopPropagation();
                                            }
                                        });


                                        $('#content-main tbody tr td input[  type="checkbox"]').change(function () {

                                            if (confirm("<spring:message code='work.detail.contents.change.confirm' />") == true) {
                                                var id = this.id.split("_")[0];
                                                var statusvalue = this.id.split("_")[1];

                                                console.log(id);
                                                var classValue = ('.' + id + '_check');
                                                console.log(classValue);
                                                $(classValue).not(this).prop('checked', false);

                                                console.log(classValue);

                                                var settings = {
                                                    "url": "/work/work/statusupdate",
                                                    "method": "POST",
                                                    "headers": {
                                                        "Content-Type": "application/json"
                                                    },
                                                    "data": JSON.stringify({
                                                        "id": id,
                                                        "status": statusvalue,
                                                    }),
                                                };
                                                $.ajax(settings).done(function (response) {
                                                    let result = response;
                                                    document.getElementById("time-" + id).innerHTML = result;
                                                    toastr['success']('update sucess !');
                                                });
                                            } else {
                                                if (this.checked == false) {
                                                    this.checked = true
                                                } else {
                                                    this.checked = false
                                                }
                                            }
                                        })
                                    });
                                </script>

                                <div style="clear: both"></div>
                                <div class="row">
                                    <div class="col-xl-12">
                                        <div class="panel panel-inverse">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">
                                                    <spring:message code='work.detail.information' />
                                                </h4>
                                            </div>
                                            <div class="panel-body">
                                                <table class="table mb-0">
                                                    <tr>
                                                        <td style="background: #f0f4f6;vertical-align: inherit; width: 90px;"><spring:message code='work.detail.empName'/></td>
                                                        <td>${detailDTO.empName}</td>
                                                    </tr>

                                                    <tr>
                                                        <td style="background: #f0f4f6;vertical-align: inherit;"><spring:message code='work.detail.dept' /></td>
                                                        <td>${detailDTO.deptName}</td>
                                                    </tr>
                                                    <tr>
                                                        <td style="background: #f0f4f6;vertical-align: inherit;"><spring:message code='work.detail.position' /></td>
                                                        <td>${detailDTO.positionName}</td>
                                                    </tr>
                                                    <tr>
                                                        <td style="background: #f0f4f6;vertical-align: inherit;"><spring:message code='work.detail.title' /></td>
                                                        <td>${detailDTO.title}</td>
                                                    </tr>
                                                    <tr>
                                                        <td style="background: #f0f4f6; vertical-align: inherit;"><spring:message code='work.detail.contents' /></td>
                                                        <td>
                                                            <table id="content-main" class="table mb-auto">
                                                                <thead>
                                                                    <tr>
                                                                        <th class="all"><spring:message code='work.detail.contents'/></th>
                                                                        <th><spring:message code='work.detail.datecreate'/></th>
                                                                        <th class="min-tablet"><spring:message code='work.detail.modifydate'/></th>
                                                                        <th class="min-tablet"><spring:message code='work.list.pedding'/></th>
                                                                        <th class="min-tablet"><spring:message code='work.list.doing'/></th>
                                                                        <th class="min-tablet"><spring:message code='work.list.finish'/></th>
                                                                    </tr>
                                                                </thead>

                                                                <tbody>

                                                                    <c:forEach items="${workChild}" var="lst">
                                                                        <tr id="row-${lst.id}" style="height: 66px;">
                                                                            <td style="vertical-align: inherit;">${lst.contents}</td>
                                                                            <td style="vertical-align: inherit; text-align: center;">${lst.createDate}</td>
                                                                            <td style="vertical-align: inherit; text-align: center;" id="time-${lst.id}">${lst.modifyDate}</td>

                                                                            <c:choose>
                                                                                <c:when
                                                                                    test="${lst.workStatus=='WORKING'}">
                                                                                    <td style="text-align: center; vertical-align: inherit;">
                                                                                        <input id="${lst.id}_NOTYET"
                                                                                            class="${lst.id}_check"
                                                                                            type="checkbox" <%=disable%>
                                                                                        ${styleDisble}
                                                                                        name="ndaddPeing"
                                                                                        value="NOTYET">
                                                                                    </td>
                                                                                    <td style="text-align: center; vertical-align: inherit;">
                                                                                        <input id="${lst.id}_WORKING"
                                                                                            class="${lst.id}_check"
                                                                                            type="checkbox" <%=disable%>
                                                                                        ${styleDisble}
                                                                                        checked=""
                                                                                        name="addDoing" value="WORKING">
                                                                                    </td>

                                                                                    <td style="text-align: center; vertical-align: inherit;">
                                                                                        <input id="${lst.id}_FINISH"
                                                                                            class="${lst.id}_check"
                                                                                            type="checkbox" <%=disable%>
                                                                                        ${styleDisble}
                                                                                        name="addFinish"
                                                                                        value="FINISH">
                                                                                    </td>
                                                                                </c:when>
                                                                                <c:when
                                                                                    test="${lst.workStatus=='NOTYET'}">
                                                                                    <td style="text-align: center; vertical-align: inherit;">
                                                                                        <input id="${lst.id}_NOTYET"
                                                                                            class="${lst.id}_check"
                                                                                            type="checkbox" <%=disable%>
                                                                                        ${styleDisble}
                                                                                        checked=""
                                                                                        name="addPending"
                                                                                        value="NOTYET">
                                                                                    </td>
                                                                                    <td style="text-align: center; vertical-align: inherit;">
                                                                                        <input id="${lst.id}_WORKING"
                                                                                            class="${lst.id}_check"
                                                                                            type="checkbox" <%=disable%>
                                                                                        ${styleDisble}
                                                                                        name="addDoing"
                                                                                        value="WORKING">
                                                                                    </td>

                                                                                    <td style="text-align: center; vertical-align: inherit;">
                                                                                        <input id="${lst.id}_FINISH"
                                                                                            class="${lst.id}_check"
                                                                                            type="checkbox" <%=disable%>
                                                                                        ${styleDisble}
                                                                                        name="addFinish"
                                                                                        value="FINISH">
                                                                                    </td>
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <td style="text-align: center; vertical-align: inherit;">
                                                                                        <input id="${lst.id}_NOTYET"
                                                                                            class="${lst.id}_check"
                                                                                            type="checkbox" <%=disable%>
                                                                                        ${styleDisble}
                                                                                        name="addPending"
                                                                                        value="NOTYET">
                                                                                    </td>
                                                                                    <td style="text-align: center; vertical-align: inherit;">
                                                                                        <input id="${lst.id}_WORKING"
                                                                                            class="${lst.id}_check"
                                                                                            type="checkbox" <%=disable%>
                                                                                        ${styleDisble}
                                                                                        name="addDoing"
                                                                                        value="WORKING">
                                                                                    </td>

                                                                                    <td style="text-align: center; vertical-align: inherit;">
                                                                                        <input id="${lst.id}_FINISH"
                                                                                            class="${lst.id}_check"
                                                                                            type="checkbox" <%=disable%>
                                                                                        ${styleDisble}
                                                                                        name="addFinish"
                                                                                        checked="" value="FINISH">
                                                                                    </td>
                                                                                </c:otherwise>
                                                                            </c:choose>

                                                                        </tr>
                                                                    </c:forEach>
                                                                </tbody>
                                                            </table>
                                                        </tr>

                                                    </td>
                                                    </tr>
                                                    <c:choose>
                                                        <c:when test="${countCheck == 2}">
                                                            <c:choose>
                                                                <c:when test="${statusEdit==1}">
                                                                    <tr>
                                                                        <td style="background: #f0f4f6; vertical-align: inherit;"><spring:message code='work.detail.workfunction' /></td>
                                                                        <td>
                                                                            <div class="function-childrent w-100 float-left">
                                                                                <div style="float: right;">
                                                                                    <button type="button" class="btn btn-purple" <%=disable%> onclick="editmore()"><spring:message code='work.detail.edit' /></button>
                                                                                </div>
                                                                            </div>
                                                                        </td>
                                                                    </tr>
                                                                </c:when>
                                                            </c:choose>
                                                            <tr>
                                                                <td style="background: #f0f4f6; vertical-align: inherit;"><spring:message code='work.detail.addmorecontents' /></td>
                                                                <td>
                                                                    <table id="content-main" class="table mb-auto">
                                                                        <thead class="text-center">
                                                                            <tr>
                                                                                <th><spring:message code='work.detail.contents' /></th>
                                                                                <th><spring:message code='work.detail.datecreate' /></th>
                                                                                <th><spring:message code='work.detail.modifydate' /></th>
                                                                                <th><spring:message code='work.list.pedding' /></th>
                                                                                <th><spring:message code='work.list.doing' /></th>
                                                                                <th><spring:message code='work.list.finish' /></th>
                                                                            </tr>
                                                                        </thead>

                                                                        <tbody>
                                                                            <c:forEach items="${workMoreChild}" var="lst">
                                                                                <tr id="row-${lst.id}" style=" height: 66px;">
                                                                                    <td style="vertical-align: inherit;"> ${lst.contents}</td>
                                                                                    <td style="vertical-align: inherit; text-align: center;"> ${lst.createDate}</td>
                                                                                    <td id="time-${lst.id}" style="vertical-align: inherit; text-align: center;"> ${lst.modifyDate}</td>

                                                                                    <c:choose>
                                                                                        <c:when test="${lst.workStatus=='WORKING'}">
                                                                                            <td style="text-align: center; vertical-align: inherit;">
                                                                                                <input
                                                                                                    id="${lst.id}_NOTYET"
                                                                                                    class="${lst.id}_check"
                                                                                                    type="checkbox"
                                                                                                    <%=disable%>
                                                                                                ${styleDisble}
                                                                                                name="ndaddPeing"
                                                                                                value="NOTYET">
                                                                                            </td>
                                                                                            <td style="text-align: center; vertical-align: inherit;">
                                                                                                <input
                                                                                                    id="${lst.id}_WORKING"
                                                                                                    class="${lst.id}_check"
                                                                                                    type="checkbox"
                                                                                                    <%=disable%>
                                                                                                ${styleDisble}
                                                                                                checked=""
                                                                                                name="addDoing"
                                                                                                value="WORKING">
                                                                                            </td>

                                                                                            <td style="text-align: center; vertical-align: inherit;">
                                                                                                <input
                                                                                                    id="${lst.id}_FINISH"
                                                                                                    class="${lst.id}_check"
                                                                                                    type="checkbox"
                                                                                                    <%=disable%>
                                                                                                ${styleDisble}
                                                                                                name="addFinish"
                                                                                                value="FINISH">
                                                                                            </td>
                                                                                        </c:when>
                                                                                        <c:when
                                                                                            test="${lst.workStatus=='NOTYET'}">
                                                                                            <td style="text-align: center; vertical-align: inherit;">
                                                                                                <input
                                                                                                    id="${lst.id}_NOTYET"
                                                                                                    class="${lst.id}_check"
                                                                                                    type="checkbox"
                                                                                                    <%=disable%>
                                                                                                ${styleDisble}
                                                                                                checked=""
                                                                                                name="addPending"
                                                                                                value="NOTYET">
                                                                                            </td>
                                                                                            <td style="text-align: center; vertical-align: inherit;">
                                                                                                <input
                                                                                                    id="${lst.id}_WORKING"
                                                                                                    class="${lst.id}_check"
                                                                                                    type="checkbox"
                                                                                                    <%=disable%>
                                                                                                ${styleDisble}
                                                                                                name="addDoing"
                                                                                                value="WORKING">
                                                                                            </td>

                                                                                            <td style="text-align: center; vertical-align: inherit;">
                                                                                                <input
                                                                                                    id="${lst.id}_FINISH"
                                                                                                    class="${lst.id}_check"
                                                                                                    type="checkbox"
                                                                                                    <%=disable%>
                                                                                                ${styleDisble}
                                                                                                name="addFinish"
                                                                                                value="FINISH">
                                                                                            </td>
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <td style="text-align: center; vertical-align: inherit;">
                                                                                                <input
                                                                                                    id="${lst.id}_NOTYET"
                                                                                                    class="${lst.id}_check"
                                                                                                    type="checkbox"
                                                                                                    <%=disable%>
                                                                                                ${styleDisble}
                                                                                                name="addPending"
                                                                                                value="NOTYET">
                                                                                            </td>
                                                                                            <td style="text-align: center; vertical-align: inherit;">
                                                                                                <input
                                                                                                    id="${lst.id}_WORKING"
                                                                                                    class="${lst.id}_check"
                                                                                                    type="checkbox"
                                                                                                    <%=disable%>
                                                                                                ${styleDisble}
                                                                                                name="addDoing"
                                                                                                value="WORKING">
                                                                                            </td>

                                                                                            <td style="text-align: center; vertical-align: inherit;">
                                                                                                <input
                                                                                                    id="${lst.id}_FINISH"
                                                                                                    class="${lst.id}_check"
                                                                                                    type="checkbox"
                                                                                                    <%=disable%>
                                                                                                ${styleDisble}
                                                                                                name="addFinish"
                                                                                                checked=""
                                                                                                value="FINISH">
                                                                                            </td>
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </tr>
                                                                            </c:forEach>
                                                                        </tbody>
                                                                    </table>
                                                                </tr>
                                                        </c:when>
                                                    </c:choose>


                                                    <tr>
                                                        <td style="background: #f0f4f6;vertical-align: inherit;"><spring:message code='work.detail.registerDate' /></td>
                                                        <td style="vertical-align: inherit;">${detailDTO.regDt}</td>
                                                    </tr>
                                                    <tr>
                                                        <td style="background: #f0f4f6; vertical-align: inherit;"><spring:message code='work.detail.workfunction' /></td>
                                                        <td style="vertical-align: inherit;">
                                                            <button type="button" class="btn btn-primary" <%=disable%> onclick="addtran('${detailDTO.dayWorkId}')"><spring:message code='work.detail.addTran' /></button>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <td style="background: #f0f4f6;font-weight: bold; vertical-align: inherit;"><spring:message code='work.detail.translation' /></td>
                                                        <td style="vertical-align: inherit;">
                                                            <div id="aTran">
                                                                <table id="tbTran" class="table mb-auto">
                                                                    <thead>
                                                                        <tr>
                                                                            <th class="all"><spring:message code='work.detail.stt'/></th>
                                                                            <th class="min-tablet"><spring:message code='work.detail.language' /></th>
                                                                            <th><spring:message code='work.detail.empName' /></th>
                                                                            <th class="min-tablet"><spring:message code='work.detail.title' /></th>
                                                                            <th><spring:message code='work.detail.contents' /></th>
                                                                            <th class="min-tablet"><spring:message code='work.detail.registerDate' /></th>
                                                                            <th class="min-tablet"><spring:message code='work.detail.status' /></th>
                                                                            <th class="min-tablet"><spring:message code='work.add.edit' /></th>
                                                                            <th class="min-tablet"><spring:message code='work.detail.workfunction' /></th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <c:forEach items="${detailTran}" var="ltk">
                                                                            <tr>
                                                                                <td style=" vertical-align: inherit;">${ltk.rownum}</td>
                                                                                <td style="text-align: center; vertical-align: inherit;">${ltk.langName}</td>
                                                                                <td style=" vertical-align: inherit;">${ltk.empName}</td>
                                                                                <td style=" vertical-align: inherit;">${ltk.title}</td>
                                                                                <td style=" vertical-align: inherit;">${ltk.contents}</td>
                                                                                <td style=" vertical-align: inherit;">${ltk.regDt}</td>
                                                                                <td style=" vertical-align: inherit;">
                                                                                    <input class="toggle-group" name="checkboxStatus" id="status_${ltk.translationId}" value="${ltk.translationId}" type="checkbox" ${disabled}
                                                                                    ${ltk.translationStatus=="1"
                                                                                    ?"checked":"" }
                                                                                    data-toggle="toggle" data-on=" <spring:message code='work.detail.button.translate.finish' />" data-off=" <spring:message code='work.detail.button.translate.notfinish' />" data-onstyle="success" data-offstyle="danger">
                                                                                </td>
                                                                                <c:if test="${empCd eq ltk.empCd}">
                                                                                    <td style=" vertical-align: inherit;">
                                                                                        <button type="button" class="btn btn-danger" onclick="editTrans('${ltk.translationId}','${detailDTO.dayWorkId}')">
                                                                                            <spring:message code='work.detail.edit' />
                                                                                        </button>
                                                                                    </td>
                                                                                    <td style=" vertical-align: inherit;">
                                                                                        <button type="button" class="btn btn-danger" onclick="delTran('${ltk.translationId}')">
                                                                                            <spring:message code='work.detail.delete' />
                                                                                        </button>
                                                                                    </td>
                                                                                </c:if>
                                                                                <c:if test="${empCd != ltk.empCd}">
                                                                                    <td style=" vertical-align: inherit;">
                                                                                        <button type="button" class="btn btn-danger" disabled onclick="editTrans('${ltk.translationId}','${detailDTO.dayWorkId}')">
                                                                                            <spring:message code='work.detail.edit' />
                                                                                        </button>
                                                                                    </td>
                                                                                    <td style=" vertical-align: inherit;">
                                                                                        <button type="button" class="btn btn-danger" disabled onclick="delTran('${ltk.translationId}')">
                                                                                            <spring:message code='work.detail.delete' />
                                                                                        </button>
                                                                                    </td>
                                                                                </c:if>
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </td>
                                                    </tr>

                                                    <c:choose>
                                                        <c:when test='${positionEmp<8 || empDTO.adminYn == "Y"}'>
                                                            <tr>
                                                                <td style="background: #f0f4f6; vertical-align: inherit;"><spring:message code='work.detail.commentfunction' /></td>
                                                                <td style="vertical-align: inherit;">
                                                                    <button type="button" class="btn btn-primary" onclick="addcomment('${detailDTO.dayWorkId}')"><spring:message code='work.detail.addc' /></button>
                                                                </td>
                                                            </tr>
                                                        </c:when>
                                                    </c:choose>
                                                    <tr>
                                                        <td style="background: #f0f4f6;font-weight: bold;  vertical-align: inherit;"><spring:message code='work.detail.messa' /></td>
                                                        <td>
                                                            <div id="aComment">
                                                                <table id="tbComment" class="table mb-auto">
                                                                    <thead>
                                                                        <tr>
                                                                            <th class="all"><spring:message code='work.detail.stt' /></th>
                                                                            <th class="min-tablet"><spring:message code='work.detail.language' /></th>
                                                                            <th class="comment-table-th"><spring:message code='work.detail.empName' /></th>
                                                                            <th class="comment-table-th"><spring:message code='work.detail.contents' /></th>
                                                                            <th class="min-tablet"><spring:message code='work.detail.registerDate' /></th>
                                                                            <th class="min-tablet"><spring:message code='work.detail.showhide' /></th>
                                                                            <th class="min-tablet"><spring:message code='work.detail.translation' /></th>
                                                                            <th><spring:message code='work.add.edit' /></th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <c:choose>
                                                                            <c:when test="${detailCMT.size() == 0}">
                                                                                <tr>
                                                                                    <td class="cursor-pointer" board-id="${list.boardId}" style="text-align: center; vertical-align: inherit;">
                                                                                        <spring:message code='main.index.datastatus' />
                                                                                    </td>
                                                                                </tr>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <c:forEach items="${detailCMT}" var="ltk">
                                                                                    <tr>
                                                                                        <td style="vertical-align: inherit;">${ltk.rownum}</td>
                                                                                        <td style="vertical-align: inherit;">${ltk.langName}</td>
                                                                                        <td style="vertical-align: inherit;">${ltk.empName}</td>
                                                                                        <td style="vertical-align: inherit;"><div class="content-main-daywork"> ${ltk.contents}</div></td>
                                                                                        <td style="vertical-align: inherit;">${ltk.regDt}</td>
                                                                                        <td>
                                                                                            <button type="button" class="btn btn-primary open_slide_btn"><spring:message code='work.detail.show' /></button>
                                                                                            <button type="button" class="btn btn-primary close_slide_btn"><spring:message code='work.detail.hide' /></button>
                                                                                        </td>
                                                                                        <td>
                                                                                            <button type="button" class="btn btn-danger" onclick="addCmtTrans('${ltk.dayWorkCommentId}','${detailDTO.dayWorkId}')">
                                                                                                <spring:message code='work.detail.addTran' />
                                                                                            </button>
                                                                                        </td>
                                                                                        <c:if test="${empCd == ltk.empCd}">
                                                                                            <td style="text-align: center; vertical-align: inherit;">
                                                                                                <button type="button" class="btn btn-danger" onclick="editComment('${ltk.dayWorkCommentId}','${detailDTO.dayWorkId}')">
                                                                                                    <spring:message code='work.detail.edit' />
                                                                                                </button>
                                                                                            </td>
                                                                                        </c:if>
                                                                                        <c:if test="${empCd != ltk.empCd}">
                                                                                            <td style="text-align: center; vertical-align: inherit;">
                                                                                                <button type="button" class="btn btn-danger" disabled onclick="editComment('${ltk.dayWorkCommentId}','${detailDTO.dayWorkId}')">
                                                                                                    <spring:message code='work.detail.edit' />
                                                                                                </button>
                                                                                            </td>
                                                                                        </c:if>
                                                                                    </tr>
                                                                                    <tr style="display: none; vertical-align: inherit;">
                                                                                        <td colspan="8" style="word-break: break-all;"> ${ltk.cmtTranslation}</td>
                                                                                    </tr>
                                                                                </c:forEach>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- Modal -->


                                <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
                                    aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">Choose person you want to add.</h5>
                                                <input type="text" hidden="" name="" id="giatri" />
                                                <button type="button" class="close" data-dismiss="modal"
                                                    aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>

                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-primary" onclick="addCo()">Add
                                                </button>
                                                <button type="button" class="btn btn-secondary"
                                                    data-dismiss="modal">Close
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <script type="text/javascript">
                                $(document).ready(function () {
                                    var preCheck = '${detailDTO.useYn}';
                                    if(preCheck === 'N'){
                                        $("#content").html("<spring:message code='main.index.datastatus' />");
                                    }
                                    var lang_dt;
                                    if ('${_lang}' == 'ko') {
                                        lang_dt = lang_kor;
                                    } else if ('${_lang}' == 'en') {
                                        lang_dt = lang_en;
                                    } else {
                                        lang_dt = lang_vt;
                                    }

                                    $(".open_slide_btn").click(function () {
                                        $(this).hide();
                                        $(this).next().show();
                                        $(this).parent().parent().next().slideDown();
                                    });

                                    $(".close_slide_btn").click(function () {
                                        $(this).hide();
                                        $(this).prev().show();
                                        $(this).parent().parent().next().slideUp();
                                    });

                                    var myTable = $('#tbCo').dataTable({
                                        "responsive": true,
                                        "lengthChange": false,
                                        "autoWidth": false,
                                        "language": lang_dt
                                    });
                                    var t = $('#tbTran').dataTable({
                                        "responsive": true,
                                        "lengthChange": false,
                                        "autoWidth": false,
                                        "language": lang_dt
                                    });
                                    // var tbComment = $('#tbComment').dataTable({ "responsive": true, "lengthChange": false, "autoWidth": false, "language": lang_dt });

                                    if ('${title}' != '') {
                                        if ('${title}' == 'success') {
                                            //toastr['${title}']('${message}');
                                            toastr.success('', "<spring:message code='notification.index.success' />", { timeOut: 1000 });
                                        } else {
                                            toastr.error('', "<spring:message code='notification.index.error' />", { timeOut: 1000 });
                                        }
                                    }



                                    let test = 'getTransCmt'
                                });

                                function addtran(id) {
                                    location.href = "/work/mydaywork/addTran.hs?id=" + id + "&loai=mydaywork&<%=backSearch%>";
                                }
                                function delTran(id) {
                                    $.ajax({
                                        type: "POST",
                                        url: "/work/work/xoaTran",
                                        data: {
                                            id: id,
                                        },
                                        success: function (response) {
                                            location.href = "/work/mydaywork/detail.hs?id=${detailDTO.dayWorkId}&title=success&message=Delete sucess&<%=backSearch%>";
                                        },
                                        error: function (error) {
                                            toastr['error']('error !');
                                        }
                                    });
                                }

                                function editW() {
                                    location.href = "/work/mydaywork/edit.hs?dayWorkId=${detailDTO.dayWorkId}&type=main&<%=backSearch%>";
                                }

                                function editmore() {
                                    location.href = "/work/mydaywork/editmore.hs?dayWorkId=${detailDTO.dayWorkId}&type=sub&<%=backSearch%>";
                                }

                                function addmore() {
                                    location.href = "/work/mydaywork/addmore.hs?dayWorkId=${detailDTO.dayWorkId}" + "&loai=mydaywork&<%=backSearch%>";
                                }

                                function editComment(id, dw) {
                                    location.href = "/work/mydaywork/editcomment.hs?dayWorkCommentId=" + id + "&dayWorkId=" + dw + "&loai=mydaywork&<%=backSearch%>";
                                }

                                function back() {
                                    location.href = "/work/mydaywork/list.hs?<%=backSearch%>";
                                }

                                function editTrans(id, dw) {
                                    location.href = "/work/mydaywork/edittrans.hs?transId=" + id + "&dayWorkId=" + dw + "&loai=mydaywork&<%=backSearch%>";
                                }

                                function addcomment(id) {
                                    location.href = "/work/mydaywork/addcomment.hs?id=" + id + "&loai=mydaywork&<%=backSearch%>";
                                }

                                function addCmtTrans(id, dw) {
                                    location.href = "/work/mydaywork/addcomttrans.hs?id=" + id + "&loai=mydaywork&dayworkid=" + dw + "&<%=backSearch%>";
                                }

                                function getTransCmt(id) {
                                    console.log(id);
                                    let str = '<td colspan="8" style="word-break: break-all;">' + id + '</td>'
                                    let str_ = 'content-comment' + id
                                    document.getElementById(str_).innerHTML = str;
                                }


                            </script>
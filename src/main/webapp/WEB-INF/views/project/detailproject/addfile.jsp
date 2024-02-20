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
<script>
    var i =0;
    <c:choose>
    <c:when test="${lstFile.size() >0}">
    i=${lstFile.size()};
    </c:when>
    </c:choose>
    var langCd='${_lang}';
    var urls="/API/dayProject/";
    var dpId='${dpId}';
    var msg='<spring:message code='dayproject.file.over' />';
</script>
<div id="content" class="content">
    <h3 class="page-title">
        <spring:message code='file.attach' />
        <br>
        <small>
            <spring:message code='detail.add' />
        </small>
    </h3>
    <!-- html -->
    <div>
        <h2 class="page-title" style="float: left" id="titlefile">
            <small><spring:message code='dayproject.file.total' /> : <span id="titlefileSmall"></span> MB (<spring:message code='dayproject.file.maximum' />: 100MB)</small>
        </h2>
        <button style="margin-bottom: 5px;float: right;margin-right: 5px" class="btn btn-primary" onclick="addNewRow()">
            <spring:message code='button.rowadd' />
        </button>
    </div>
    <div style="clear: both"></div>
    <div class="panel panel-inverse">
        <div style="height:350px;overflow: auto;">
            <div class="m-table">
                <table id="tbFile" class="table table-striped table-bordered w-100 text-center">
                    <thead>
                    <tr style="background-color: #1c5691;color: white">
                        <th style="width:10%;vertical-align: inherit;">
                            <spring:message code='work.list.stt' />
                        </th>
                        <th style="width:20%;vertical-align: inherit;">
                            <spring:message code='file.attach' />
                        </th>

                        <th style="width:20%;vertical-align: inherit;">
                            <spring:message code='work.list.function'/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${lstFile.size() >0}">
                            <c:forEach items="${lstFile}" var="lst">
                                <tr>
                                    <td>${lst.rownum}</td>
                                    <td><p style="text-align: left" >${lst.fileName}, ${lst.fileSize}</p></td>
                                    <td><button type="button" onclick="deleteOldRow(this,'${lst.fileId}')" class="btn btn-info">X</button></td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr id="nodata">
                                <td colspan="100"><spring:message code='main.index.datastatus'/></td>
                            </tr>
                        </c:otherwise>
                    </c:choose>

                    </tbody>

                </table>
            </div>
        </div>
    </div>
    <button type="button" class="btn btn-success" onclick="editP()"><spring:message code='emp.list.edit' /></button>
    <button  type="button" class="btn btn-primary" onclick="back()"><spring:message code='work.detail.back' /></button>


</div>
<script type="text/javascript" src="${_ctx}/resources/js/plugins/file-upload/js/file_upload.js"></script>
<script>
    function back() {
        location.href = "/project/project/detail.hs?pjId=${pjId}&dpId=${dpId}&<%=backSearch%>";
    }

    function editP(){
        $(window).unbind('beforeunload');
        on();
        var settings = {
            "url": "/API/dayProject/EditFile",
            "method": "POST",
            "headers": {
                "Content-Type": "application/json"
            },
            "data": JSON.stringify({
                pjId: ${pjId},
                dpId: ${dpId}
            }),
        };
        $.ajax(settings).done(function (response) {
            location.href = "/project/project/detail.hs?pjId=${pjId}&dpId=${dpId}&title=success&message=sucess&<%=backSearch%>";
            off();
        });
    }

    $(document).ready(function (){
        updateSumFileSize();
    });




</script>
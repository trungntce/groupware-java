<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="kr.co.hs.emp.dto.EmpDTO" %>
<%@ page import="kr.co.hs.common.security.UserDetail" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    String backSearch="start_date1="+start_date1+"&end_date1="+end_date1+"&levelDept1="+levelDeptGet1+"&translationStatus1="+translationStatusGet1+"&approval1="+approval1+"&optionSearch1="+optionSearchGet1+"&inputSearch1="+inputSearchGet1+"&displayStartPra1="+displayStartPra1;
%>
<style>
    /*the container must be positioned relative:*/
    .autocomplete {
        position: relative;
        display: inline-block;
    }

    input[type=text] {
        width: 100%;
    }

    .autocomplete-items {
        position: absolute;
        border: 1px solid #d4d4d4;
        border-bottom: none;
        border-top: none;
        z-index: 99;
        /*position the autocomplete items to be the same width as the container:*/
        top: 100%;
        left: 0;
        right: 0;
    }

    .autocomplete-items div {
        padding: 10px;
        cursor: pointer;
        background-color: #fff;
        border-bottom: 1px solid #d4d4d4;
    }

    /*when hovering an item:*/
    .autocomplete-items div:hover {
        background-color: #e9e9e9;
    }

    /*when navigating through the items using the arrow keys:*/
    .autocomplete-active {
        background-color: DodgerBlue !important;
        color: #ffffff;
    }

    #text,
    #textAccounting {
        display: none;
        color: red
    }
</style>
<%
    String start_date = request.getParameter("start_date");
    if (start_date == null || start_date == "null") {
        start_date = java.time.LocalDate.now().toString();
    }
    String end_date = request.getParameter("end_date");
    if (end_date == null || end_date == "null") {
        end_date = java.time.LocalDate.now().toString();
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
    String approval = request.getParameter("approval");
    if (approval == null || approval == "null") {
        approval = "";
    }
    request.setAttribute("approval", approval);
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
%>

<div class="content" id="content">
    <h1 class="page-header cursor-default">
        <spring:message code='project.index'/> - <spring:message code='project.title'/> <br>
        <small>
            <spring:message code='project.smalltitle'/>
        </small>
    </h1>
    <%
        String userAgent =request.getHeader("User-Agent").toUpperCase();
        if(userAgent.indexOf("MOBILE") > -1) {
    %>
    <div class="row">
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse">
                <div class="panel-heading ui-sortable-handle">
                    <h4 class="panel-title">
                        <spring:message code='work.add.information' />
                    </h4>
                </div>
                
                <div class="panel-body">
                    <form autocomplete="off" id="myform" class="form-inline" action="">
                        <table class="table mb-0">
                            <tbody>
                                <tr>
                                    <td style="background: #f0f4f6; width: 110px;">
                                        <spring:message code='project.add.nameproject' />
                                    </td>
                                    <td>
                                        <div class="col-sm-12 col-md-6 pl-0">
                                            <input id="title" maxlength="100"
                                                placeholder="<spring:message code='project.add.placeholder.projectname' />"
                                                name="title" class="form-control form-control-sm mr-1 w-100"
                                                type="text" value="${projectMainDTO.title}">  
                                        </div>
                                    </td>
                                </tr>
                                <c:choose>
                                    <c:when test="${empDTO.adminYn=='Y'}">
                                        <tr>
                                            <td style="background: #f0f4f6;">
                                                <spring:message code='project.add.pic' />
                                            </td>
                                            <td>
                                                <div class="col-sm-12 col-md-6 pl-0">
                                                    <input readonly class="form-control form-control-sm mr-1 w-100" id="myInput" maxlength="200"
                                                    type="text" name="myInput" placeholder="<spring:message code='project.add.placeholder.empname'/>" value="${projectMainDTO.leaderProjectName}">
                                                    <p id="text">WARNING! no data</p>
                                                </div>
                                                
                                            </td>
                                        </tr>

                                        <tr>
                                            <td style="background: #f0f4f6;">
                                                <spring:message code='project.add.picaccounting' />
                                            </td>
                                            <td>
                                                <div class="autocomplete col-sm-12 col-md-6 pl-0">
                                                    <input maxlength="200"
                                                           class="form-control form-control-sm mr-1 w-100"
                                                           id="myInputAccount" type="text" name="myInputAccount"
                                                           placeholder="<spring:message code='project.add.placeholder.empname' />" value="${projectMainDTO.leaderAccountingName}">
                                                    <p id="textAccounting">WARNING! no data</p>
                                                </div>
                                            </td>
                                        </tr>
                                    <c:otherwise>
                                        <tr>
                                            <td style="background: #f0f4f6;">
                                                <spring:message code='project.add.pic' />
                                            </td>
                                            <td>
                                                <div class="col-sm-12 col-md-6 pl-0">
                                                    <input readonly class="form-control form-control-sm mr-1 w-100" id="myInput" maxlength="200"
                                                    type="text" name="myInput" placeholder="<spring:message code='project.add.placeholder.empname'/>" value="${projectMainDTO.leaderProjectName}" disabled>
                                                    <p id="text">WARNING! no data</p>
                                                </div>
                                                
                                            </td>
                                        </tr>

                                        <tr>
                                            <td style="background: #f0f4f6;">
                                                <spring:message code='project.add.picaccounting' />
                                            </td>
                                            <td>
                                                <div class="autocomplete col-sm-12 col-md-6 pl-0">
                                                    <input maxlength="200"
                                                           class="form-control form-control-sm mr-1 w-100"
                                                           id="myInputAccount" type="text" name="myInputAccount"
                                                           placeholder="<spring:message code='project.add.placeholder.empname' />" value="${projectMainDTO.leaderAccountingName}">
                                                    <p id="textAccounting">WARNING! no data</p>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>

                                <tr>
                                    <td style="background: #f0f4f6;">
                                        <spring:message code='work.add.date' />
                                    </td>
                                    <td class="row">
                                        <div class="col-sm-12 col-md-3">
                                            <span><spring:message code='work.add.start' />:</span>
                                            <input readonly id="projectstart" name="projectstart" class="form-control form-control-sm mr-1" type="text" value="${projectMainDTO.projectStartDate}">
                                        </div>
                                        <div class="col-sm-12 col-md-3">
                                            <span><spring:message code='work.add.end' />:</span>
                                            <input readonly id="projectend" name="projectend" class="form-control form-control-sm mr-1" type="text" value="${projectMainDTO.projectEndDate}">
                                        </div>
                                        <div class="col-sm-12 col-md-2">
                                            <button type="button" onclick="resetdate()" class="btn btn-primary">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16"
                                                     height="16" fill="currentColor"
                                                     class="bi bi-bootstrap-reboot" viewBox="0 0 16 16">
                                                    <path
                                                        d="M1.161 8a6.84 6.84 0 1 0 6.842-6.84.58.58 0 1 1 0-1.16 8 8 0 1 1-6.556 3.412l-.663-.577a.58.58 0 0 1 .227-.997l2.52-.69a.58.58 0 0 1 .728.633l-.332 2.592a.58.58 0 0 1-.956.364l-.643-.56A6.812 6.812 0 0 0 1.16 8z" />
                                                    <path
                                                        d="M6.641 11.671V8.843h1.57l1.498 2.828h1.314L9.377 8.665c.897-.3 1.427-1.106 1.427-2.1 0-1.37-.943-2.246-2.456-2.246H5.5v7.352h1.141zm0-3.75V5.277h1.57c.881 0 1.416.499 1.416 1.32 0 .84-.504 1.324-1.386 1.324h-1.6z" />
                                                </svg>
                                            </button>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td colspan="100">
                                        <button type="button" class="btn btn-success" onclick="editP('${projectMainDTO.pjId}')">
                                            <spring:message code='work.add.edit' />
                                        </button>
                                        <button type="button" class="btn btn-primary" onclick="back()">
                                            <spring:message code='work.add.back' />
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
    <%
    } else {
    %>
    <div class="row">
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse">
                <div class="panel-heading ui-sortable-handle">
                    <h4 class="panel-title">
                        <spring:message code='work.add.information' />
                    </h4>
                </div>

                <div class="panel-body">
                    <form autocomplete="off" id="myform" class="form-inline" action="">
                        <table class="table mb-0">
                            <tbody>
                                <tr>
                                    <td style="background: #f0f4f6; width: 110px;">
                                        <spring:message code='project.add.nameproject' />
                                    </td>
                                    <td>
                                        <div class="col-sm-12 col-md-6 pl-0">
                                            <input id="title" maxlength="100"
                                                placeholder="<spring:message code='project.add.placeholder.projectname' />"
                                                name="title" class="form-control form-control-sm mr-1 w-100"
                                                type="text" value="${projectMainDTO.title}">  
                                        </div>
                                    </td>
                                </tr>

                            <c:choose>
                                <c:when test="${empDTO.adminYn=='Y'}">
                                    <tr>
                                        <td style="background: #f0f4f6;">
                                            <spring:message code='project.add.pic' />
                                        </td>
                                        <td>
                                            <div class="col-sm-12 col-md-6 pl-0">
                                                <input readonly class="form-control form-control-sm mr-1 w-100" id="myInput" maxlength="200"
                                                type="text" name="myInput" placeholder="<spring:message code='project.add.placeholder.empname'/>" value="${projectMainDTO.leaderProjectName}">
                                                <p id="text">WARNING! no data</p>
                                            </div>
                                            
                                        </td>
                                    </tr>

                                    <tr>
                                        <td style="background: #f0f4f6;">
                                            <spring:message code='project.add.picaccounting' />
                                        </td>
                                        <td>
                                            <div class="autocomplete col-sm-12 col-md-6 pl-0">
                                                <input maxlength="200"
                                                       class="form-control form-control-sm mr-1 w-100"
                                                       id="myInputAccount" type="text" name="myInputAccount"
                                                       placeholder="<spring:message code='project.add.placeholder.empname' />" value="${projectMainDTO.leaderAccountingName}">
                                                <p id="textAccounting">WARNING! no data</p>
                                            </div>
                                        </td>
                                    </tr>
                                <c:otherwise>
                                    <tr>
                                        <td style="background: #f0f4f6;">
                                            <spring:message code='project.add.pic' />
                                        </td>
                                        <td>
                                            <div class="col-sm-12 col-md-6 pl-0">
                                                <input readonly class="form-control form-control-sm mr-1 w-100" id="myInput" maxlength="200"
                                                type="text" name="myInput" placeholder="<spring:message code='project.add.placeholder.empname'/>" value="${projectMainDTO.leaderProjectName}" disabled>
                                                <p id="text">WARNING! no data</p>
                                            </div>
                                            
                                        </td>
                                    </tr>

                                    <tr>
                                        <td style="background: #f0f4f6;">
                                            <spring:message code='project.add.picaccounting' />
                                        </td>
                                        <td>
                                            <div class="autocomplete col-sm-12 col-md-6 pl-0">
                                                <input maxlength="200"
                                                       class="form-control form-control-sm mr-1 w-100"
                                                       id="myInputAccount" type="text" name="myInputAccount"
                                                       placeholder="<spring:message code='project.add.placeholder.empname' />" value="${projectMainDTO.leaderAccountingName}">
                                                <p id="textAccounting">WARNING! no data</p>
                                            </div>
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>

                            <tr>
                                <td style="background: #f0f4f6;">
                                    <spring:message code='work.add.date' />
                                </td>
                                <td class="row">
                                    <div class="col-sm-12 col-md-3">
                                        <span><spring:message code='work.add.start' />:</span>
                                        <input readonly id="projectstart" name="projectstart" class="form-control form-control-sm mr-1" type="text" value="${projectMainDTO.projectStartDate}">
                                    </div>
                                    <div class="col-sm-12 col-md-3">
                                        <span><spring:message code='work.add.end' />:</span>
                                        <input readonly id="projectend" name="projectend" class="form-control form-control-sm mr-1" type="text" value="${projectMainDTO.projectEndDate}">
                                    </div>
                                    <div class="col-sm-12 col-md-2">
                                        <button type="button" onclick="resetdate()" class="btn btn-primary">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16"
                                                 height="16" fill="currentColor"
                                                 class="bi bi-bootstrap-reboot" viewBox="0 0 16 16">
                                                <path
                                                    d="M1.161 8a6.84 6.84 0 1 0 6.842-6.84.58.58 0 1 1 0-1.16 8 8 0 1 1-6.556 3.412l-.663-.577a.58.58 0 0 1 .227-.997l2.52-.69a.58.58 0 0 1 .728.633l-.332 2.592a.58.58 0 0 1-.956.364l-.643-.56A6.812 6.812 0 0 0 1.16 8z" />
                                                <path
                                                    d="M6.641 11.671V8.843h1.57l1.498 2.828h1.314L9.377 8.665c.897-.3 1.427-1.106 1.427-2.1 0-1.37-.943-2.246-2.456-2.246H5.5v7.352h1.141zm0-3.75V5.277h1.57c.881 0 1.416.499 1.416 1.32 0 .84-.504 1.324-1.386 1.324h-1.6z" />
                                            </svg>
                                        </button>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td colspan="100%">
                                    <button type="button" class="btn btn-success" onclick="editP('${projectMainDTO.pjId}')"><spring:message code='work.add.edit' /></button>
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
    <%
        }
    %>
</div>
<script>
    var backsreach="<%=backSearch%>";
</script>
<script type="text/javascript" src="${_ctx}/resources/js/project/add.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
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
        max-height: 500px;
        overflow-x: hidden;
        overflow-y: auto;
    }

    .autocomplete-items div {
        padding: 10px;
        cursor: pointer;
        background-color: #fff;
        border-top: 1px solid #d4d4d4;
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
            <div id="content" class="content">
                <h3 class="page-header cursor-default">
                    <spring:message code='project.index' /> -
                    <spring:message code='project.title' /> <br>
                    <small>
                        <spring:message code='project.smalltitle' />${langCd}
                    </small>
                </h3>
                <%
                    String userAgent =request.getHeader("User-Agent").toUpperCase();
                    if(userAgent.indexOf("MOBILE") > -1) {
                %>
                <div class="row">
                    <div class="col-xl-12 ui-sortable">
                        <div class="panel panel-inverse" style="">
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
                                                    <input id="title"
                                                        placeholder="<spring:message code='project.add.placeholder.projectname' />"
                                                        name="title" class="form-control form-control-sm mr-1 col-sm-12 col-md-6"
                                                        type="text">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="background: #f0f4f6;">
                                                    <spring:message code='project.add.pic' /> 
                                                </td>
                                                <td>
                                                    <div class="autocomplete w-100">
                                                        <input
                                                            class="form-control form-control-sm mr-1 col-sm-12 col-md-6" id="myInput" maxlength="200"
                                                            type="text" name="myInput" placeholder="<spring:message code='project.add.placeholder.empname' />">
                                                        <p id="text">WARNING! no data</p>
                                                    </div>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="background: #f0f4f6;">
                                                    <spring:message code='project.add.picaccounting' />
                                                </td>
                                                <td>
                                                    <div class="autocomplete col-sm-12 col-md-6">
                                                        <input
                                                            class="form-control form-control-sm mr-1" maxlength="200"
                                                            id="myInputAccount" type="text" name="myInputAccount"
                                                            placeholder="<spring:message code='project.add.placeholder.empname' />">
                                                        <p id="textAccounting">WARNING! no data</p>
                                                    </div>
                                                </td>
                                            </tr>

                                            <c:choose>
                                                <c:when test="${addFrom == '1'}">
                                                    <tr>
                                                        <td style="background: #f0f4f6;">
                                                            <spring:message code='dayproject.adddayproject.amount' />
                                                        </td>
                                                        <td>
                                                            <input id="project_price" min="0" placeholder="<spring:message code='project.add.placeholder.amount' />" name="project_price" class="form-control form-control-sm mr-1 col-sm-12 col-md-6" type="number">
                                                        </td>
                                                    </tr>
                                                </c:when>
                                            </c:choose>

                                            <tr>
                                                <td style="background: #f0f4f6;">
                                                    <spring:message code='work.add.date' />
                                                </td>
                                                <td>
                                                    <div class="col-sm-12 float-start">
                                                        <span><spring:message code='work.add.start' />:</span>
                                                        <br>
                                                        <input readonly id="projectstart" name="projectstart" class="form-control form-control-sm mr-1 col-sm-12 col-md-6" type="text" >
                                                    </div>
                                                    <div class="col-sm-12 float-start">
                                                        <span><spring:message code='work.add.end' />:</span>
                                                        <br>
                                                        <input readonly id="projectend" name="projectend" class="form-control form-control-sm mr-1 col-sm-12 col-md-6" type="text" >
                                                    </div>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td colspan="100%">
                                                    <button type="button" class="btn btn-success" onclick="addP('${langCd}')">
                                                        <spring:message code='work.add.addnew' />
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
                        <div class="panel panel-inverse" style="">
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
                                                        type="text">  
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
                                                        <div class="autocomplete col-sm-12 col-md-6 pl-0">
                                                            <input class="form-control form-control-sm mr-1 w-100" id="myInput" maxlength="200"
                                                                   type="text" name="myInput" placeholder="<spring:message code='project.add.placeholder.empname' />">
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
                                                                   placeholder="<spring:message code='project.add.placeholder.empname' />">
                                                            <p id="textAccounting">WARNING! no data</p>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:when>

                                            <c:otherwise>
                                                <tr>
                                                    <td style="background: #f0f4f6;">
                                                        <spring:message code='project.add.pic' />
                                                    </td>
                                                    <td>
                                                        <div class="col-sm-12 col-md-6 pl-0">
                                                            <input readonly class="form-control form-control-sm mr-1 w-100" id="myInput" maxlength="200"
                                                                    type="text" name="myInput" placeholder="<spring:message code='project.add.placeholder.empname'/>"  value="${empInfor.empName}" disabled>
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
                                                                   placeholder="<spring:message code='project.add.placeholder.empname' />">
                                                            <p id="textAccounting">WARNING! no data</p>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:otherwise>
                                        </c:choose>

                                        <c:choose>
                                            <c:when test="${addFrom == '1'}">
                                                <tr>
                                                    <td style="background: #f0f4f6;">
                                                        <spring:message code='dayproject.adddayproject.amount' />
                                                    </td>
                                                    <td>
                                                        <div class="col-sm-12 col-md-6 pl-0">
                                                            <input id="project_price" name="project_price" min="0"
                                                                placeholder="<spring:message code='project.add.placeholder.amount' />"
                                                                class="form-control form-control-sm mr-1 w-100" type="number">
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:when>
                                        </c:choose>

                                        <tr>
                                            <td style="background: #f0f4f6;">
                                                <spring:message code='work.add.date' />
                                            </td>
                                            <td class="row">
                                                <div class="col-sm-12 col-md-3">
                                                    <span><spring:message code='work.add.start' />:</span>
                                                    <input readonly id="projectstart" name="projectstart" class="form-control form-control-sm mr-1" type="text">
                                                </div>
                                                <div class="col-sm-12 col-md-3">
                                                    <span><spring:message code='work.add.end' />:</span>
                                                    <input readonly id="projectend" name="projectend" class="form-control form-control-sm mr-1" type="text" >
                                                </div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td colspan="100%">
                                                <button type="button" class="btn btn-success" onclick="addP('${langCd}')"><spring:message code='work.add.addnew' /></button>
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
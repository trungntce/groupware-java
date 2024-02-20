<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<%@page import="kr.co.hs.sboard.dto.SBoardMainDTO" %>
<%@ page import="java.util.ArrayList" %>
<link href="${_ctx}/resources/css/maincss/css/theme-animate.css" rel="stylesheet">
<link href="${_ctx}/resources/css/maincss/css/theme-elements.css" rel="stylesheet">
<link href="${_ctx}/resources/css/maincss/css/theme.css" rel="stylesheet">
<%
    ArrayList<SBoardMainDTO> model2 = (ArrayList<SBoardMainDTO>) request.getAttribute("mainlst");
    int idpass = (int) request.getAttribute("idDmtPass");
    int getstyle = (int) request.getAttribute("getstyle");
%>
<style>
    .map1 {
        float: left;
    }
</style>
<div id="content" class="content">
    <h3 class="page-title"><spring:message code='sboard.main.bigtitle'/><small> <spring:message code='sboard.topic.smalltitle'/></small></h3>
    <!-- html -->

    <%
        String bien="";
        if(getstyle==1){bien="/board/freestyle.hs?idDmt="+idpass;}
        if(getstyle==2){bien="/board/gallery.hs?idDmt="+idpass;}
        if(getstyle==3){bien="/board/event.hs?idDmt="+idpass;}
    %>
    <a href="<%=bien%>">
        <button style="margin-bottom: 10px;background-color: green" type="button" class="btn btn-success"><spring:message code='topic.list'/>
        </button>
    </a>

    <div>
        <%
            for (SBoardMainDTO item : model2) {
                if (item.getIdDmt() == idpass) {
        %>
        <h1 class="badge badge-info" style="font-size: 15px;"><%=item.getNameDmt() %>
        </h1><br>
        <% break;
        }
        }%>

        <c:forEach items="${mainlst}" var="lstMain">
            <c:choose>
                <c:when test="${lstMain.idDmt == idDmtPass}">
                    <div class="map1 col-xs-6 col-md-3 animation masonry-item">
                        <div class="pgl-property">
                            <div class="property-thumb-info">
                                <div class="property-thumb-info-image">
                                    <img alt="" style="height: 250px" class="img-responsive" src="${_ctx}${lstMain.imgCover}">
                                    <span class="property-thumb-info-label">
                                        <span class="label price">HOT</span>
                                    </span>
                                </div>
                                <div class="property-thumb-info-content">
                                    <h3 style="min-height: 51px"><a href="/board/detail.hs?id=${lstMain.id}">${lstMain.title}</a></h3>
                                </div>
                                <div class="amenities clearfix">
                                    <ul class="pull-left">
                                        <li><strong>posted by:</strong> ${lstMain.createBy} </li>
                                    </ul>
                                    <ul class="pull-right">
                                        <li><i class="fa fa-clock"></i> ${lstMain.createDate}</li>
                                        <li><i class="fa fa-eye"></i> ${lstMain.viewCount}</li>
                                    </ul>
                                </div>
                                <c:choose>
                                    <c:when test="${lstMain.idBoardType == 3}">
                                        <div class="amenities clearfix">
                                            <div class="progress">
                                                <div class="progress-bar bg-danger" role="progressbar w-100" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                                            </div>
                                        </div>
                                        <div class="amenities clearfix" style="text-align: center;font-weight: bold;font-style: oblique;font-size: 15px">${lstMain.startDate} - ${lstMain.endDate}</div>
                                    </c:when>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </c:when>
            </c:choose>
        </c:forEach>

    </div>
    <!-- End Main -->
</div>




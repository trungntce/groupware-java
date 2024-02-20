<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<link href="${_ctx}/resources/css/maincss/css/theme-animate.css" rel="stylesheet">
<link href="${_ctx}/resources/css/maincss/css/theme-elements.css" rel="stylesheet">
<link href="${_ctx}/resources/css/maincss/css/theme.css" rel="stylesheet">
<style>
    .map1 {
        float: left;
    }
</style>
<div id="content" class="content">
    <a href="/board/topic.hs?boardType=${boardType}">
        <button style="margin-bottom: 10px" type="button" class="btn btn-success"><spring:message code='main.newtopic'/>
        </button>
    </a>
    <%
        request.setAttribute("count", 0);
    %>
    <c:forEach items="${mainDMT}" var="lstDMT">
        <c:set var = "count"  value = "0"/>
        <div>
            <h1 class="badge badge-info" style="font-size: 15px;">${lstDMT.nameDmt}</h1><br>
            <c:forEach items="${mainlst}" var="lstMain" >
                <c:choose>
                    <c:when test="${lstMain.idDmt == lstDMT.id && count < 4}">
                        <c:set var = "count"  value = "${count + 1}"/>
                        <div class="map1 col-xs-6 col-md-3 animation masonry-item">
                            <div class="pgl-property">
                                <div class="property-thumb-info">
                                    <div class="property-thumb-info-image">
                                        <img alt="" style="height: 250px" class="img-responsive"
                                             src="${_ctx}${lstMain.imgCover}">
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
                                                    <div class="progress-bar bg-danger" role="progressbar"
                                                         style="width: 100%" aria-valuenow="100" aria-valuemin="0"
                                                         aria-valuemax="100"></div>
                                                </div>
                                            </div>
                                            <div class="amenities clearfix"
                                                 style="text-align: center;font-weight: bold;font-style: oblique;font-size: 15px">
                                                    ${lstMain.startDate} - ${lstMain.endDate}
                                            </div>
                                        </c:when>

                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </c:when>
                </c:choose>
            </c:forEach>
            <div style="clear: both"></div>
            <div class="map1 col-xs-6 col-md-3 animation masonry-item" style="margin-bottom: 25px">
                <a href="/board/list.hs?boardType=${boardType}&&idDmt=${lstDMT.id}">
                    <button type="button" class="btn-warning"><spring:message code='main.seeall'/> >></button>
                </a>
            </div>
            <div style="clear: both"></div>
        </div>
    </c:forEach>

    <!-- End Main -->
</div>




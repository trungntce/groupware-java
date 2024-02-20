<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:set var="_ctx" value="${pageContext.request.contextPath == '/' ? '' : pageContext.request.contextPath }" scope="application"/>
<c:set var="_lang" value="${pageContext.response.locale}" scope="session"/>
<c:set var="_translationOption" value="Y" scope="session"/>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title><spring:message code='header.left.logo.title1'/> <spring:message code='header.left.logo.title2'/></title>
        <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
        <link id="favicon" rel="shortcut icon" href="${_ctx}/resources/img/favicon/favicon.ico" type="image/x-icon">
        <%@ include file="/WEB-INF/layout/common/commonCSS.jspf"%>
        <%@ include file="/WEB-INF/layout/common/commonJS.jspf"%>
        <%@ include file="/WEB-INF/layout/common/tableResponsive.jspf"%>
        <link href="${_ctx}/resources/css/common.css" rel="stylesheet" />
    </head>
    <body>
        <div id="page-loader" class="fade show">
            <div class="material-loader">
                <svg class="circular" viewBox="25 25 50 50">
                <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10"></circle>
                </svg>
                <div class="message">Loading...</div>
            </div>
        </div>
        <div id="page-container" class="fade page-sidebar-fixed page-header-fixed">
            <tiles:insertAttribute name="header" />
            <tiles:insertAttribute name="sidebar" />
            <tiles:insertAttribute name="body" />
        </div>
    </body>
    <script>
        $('#inputSearch').keypress(
            function (event) {
                if (event.which == '13') {
                    event.preventDefault();
                    searchMethod();
                }
            }
        );
    </script>
</html>
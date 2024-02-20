<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:set var="_ctx" value="${pageContext.request.contextPath == '/' ? '' : pageContext.request.contextPath }" scope="application"/>
<c:set var="_lang" value="${pageContext.response.locale}" scope="session"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>Vietko Group | Login</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta content="" name="description" />
    <meta content="" name="author" />
    <meta name="MobileOptimized" content="320">
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link href="${_ctx}/resources/logintemp/Content/themes/assets/fonts/font.css" rel="stylesheet" type="text/css" />
    <link href="${_ctx}/resources/logintemp/Content/themes/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link href="${_ctx}/resources/logintemp/Content/themes/assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />

    <!-- END GLOBAL MANDATORY STYLES -->

    <!-- BEGIN THEME STYLES -->
    <link href="${_ctx}/resources/logintemp/Content/themes/assets/css/style-metronic.css" rel="stylesheet" type="text/css" />
    <link href="${_ctx}/resources/logintemp/Content/themes/assets/css/style.css" rel="stylesheet" type="text/css" />
    <link href="${_ctx}/resources/logintemp/Content/themes/assets/css/style-responsive.css" rel="stylesheet" type="text/css" />
    <link href="${_ctx}/resources/logintemp/Content/themes/assets/css/plugins.css" rel="stylesheet" type="text/css" />
    <link href="${_ctx}/resources/logintemp/Content/themes/assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color" />
    <link href="${_ctx}/resources/logintemp/Content/themes/assets/css/pages/login-soft.css" rel="stylesheet" type="text/css" />
    <link href="${_ctx}/resources/logintemp/Content/themes/assets/css/custom.css" rel="stylesheet" type="text/css" />
    <link href="${_ctx}/resources/js/bootstrap-toastr/toastr.min.css" rel="stylesheet" />

    <style>
        .nounderline:hover {
            text-decoration: none;
        }
    </style>
    <!-- END THEME STYLES -->
    <link rel="shortcut icon" href="Icon.ico" />
</head>

<body>
<tiles:insertAttribute name="body" />
</body>
</html>

<script type="text/javascript" src="${_ctx}/resources/js/bootstrap-toastr/toastr.min.js"></script>
<script type="text/javascript" src="${_ctx}/resources/js/bootstrap-toastr/jquery.validate.js"></script>
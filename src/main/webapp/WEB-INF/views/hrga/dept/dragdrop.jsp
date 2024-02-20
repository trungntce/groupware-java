<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
        
           
        <script type="text/javascript" src="${_ctx}/resources/js/bundles/jquery.nestable.js"></script>

        <script type="text/javascript" src="${_ctx}/resources/js/bundles/sortable-nestable.js"></script>
            <style>
                .dd-handle {
                    margin-left: 33px !important;
                }
            </style>
            <div id="content" class="content">
                <h1 class="page-header cursor-default">
                    <spring:message code='emp.list.bigtitle'/> - <spring:message code='dragdrop.bigtitle'/> <spring:message code='dragdrop.smalltitle' />
                    <br>
                    <small>
<%--                        <spring:message code='emp.list.smalltitle.detail'/>--%>
                    </small>
                </h1>
             
                <div class="col-xl-12">
                    <div class="panel panel-inverse">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <spring:message code='dragdrop.bigtitle' /><spring:message code='dragdrop.smalltitle' />
                            </h4>
                        </div>
                        <div class="panel-body">
                            <div style="float: right">
                                <button style="margin-bottom: 5px;" class="btn btn-primary open_slide_btn" type="button">
                                    <spring:message code='dragdrop.unlock' />
                                </button>
                                <button style="margin-bottom: 5px; display: none;" class="btn btn-danger close_slide_btn" type="button">
                                    <spring:message code='dragdrop.block' />
                                </button>
                            </div>
                            <div id="main-content" style="pointer-events: none;">
                                <div class="container-fluid">
                                    <div class="row clearfix" style="width: 100%">
                                        <div class="col-lg-6 col-md-12">
                                            <div class="card">
                                                <div class="body" style="background: #DEE1E6">
                                                    <div class="clearfix m-b-20">
                                                        <div class="dd" style="font-weight: bold;">
                                                            <div id="load-detail">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="clear: both"></div>
            </div>
            <script>
                var springMSR = "<spring:message code='dragdrop.alertpermission'/>";
                var springMS_ = "<spring:message code='dragdrop.alert'/>";
            </script>

            <script type="text/javascript" src="${_ctx}/resources/js/common/dragdrop.js"></script>
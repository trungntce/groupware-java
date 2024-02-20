<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
            <style>
                #content #mainContent #contents img {
                    height: auto !important;
                    width: 100% !important;
                }
            </style>
            <div id="content" class="content">
                <svg style="float: left" xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="blue"
                    class="bi bi-book-half" viewBox="0 0 16 16">
                    <path
                        d="M8.5 2.687c.654-.689 1.782-.886 3.112-.752 1.234.124 2.503.523 3.388.893v9.923c-.918-.35-2.107-.692-3.287-.81-1.094-.111-2.278-.039-3.213.492V2.687zM8 1.783C7.015.936 5.587.81 4.287.94c-1.514.153-3.042.672-3.994 1.105A.5.5 0 0 0 0 2.5v11a.5.5 0 0 0 .707.455c.882-.4 2.303-.881 3.68-1.02 1.409-.142 2.59.087 3.223.877a.5.5 0 0 0 .78 0c.633-.79 1.814-1.019 3.222-.877 1.378.139 2.8.62 3.681 1.02A.5.5 0 0 0 16 13.5v-11a.5.5 0 0 0-.293-.455c-.952-.433-2.48-.952-3.994-1.105C10.413.809 8.985.936 8 1.783z">
                    </path>
                </svg>
                <h3
                    style="margin-top: 10px;margin-left: 10px;float: left;font-weight: 700;font-size: 14px;color: darkblue;font-family: 'Times New Roman';  text-transform: uppercase;">
                    ${lstDetail.nameDmt}</h3>
                <div style="clear: both"></div>

                <div>
                    <div class="col-2" style="float: left;text-align: center;margin-top: 90px;"><img style="width: 90%"
                            src="/resources/img/cover/Artboard1.png"></div>
                    <div class="col-8" style="float: left;" id="mainContent">
                        <%--sua xoa--%>
                            <div style="font-size: initial;float: right">
                                <a href="/board/editBoardPublic.hs?id=${lstDetail.id}&idstyle=${lstDetail.idBoardType}"><i
                                        class="fas fa-edit">
                                        <spring:message code='gallery.bigtitleedit' />
                                    </i>
                                </a>
                                <a href="/board/Delete.hs?id=${lstDetail.id}"
                                    onclick="return confirm('Are you sure you want to delete this item ?')">
                                    <i class="fas fa-trash" style="margin-left: 10px;color: gray">
                                        <spring:message code='dept.list.delete' />
                                    </i>
                                </a>
                            </div>
                            <div style="clear: both"></div>

                            <div class="tieude" style="text-align: center;">
                                <h1>${lstDetail.title}</h1>
                            </div>


                            <%--noi dung--%>
                                <div id="contents" style="margin-top: 30px">
                                    <c:choose>
                                        <c:when test="${fn:length(lstDetail.startDate) >0}">
                                            <div>
                                                <div class="amenities clearfix">
                                                    <div class="progress">
                                                        <div class="progress-bar bg-danger" role="progressbar"
                                                            style="width: 100%" aria-valuenow="100" aria-valuemin="0"
                                                            aria-valuemax="100"></div>
                                                    </div>
                                                </div>
                                                <div class="amenities clearfix"
                                                    style="text-align: center;font-weight: bold;font-style: oblique;font-size: 15px;margin-bottom: 10px">
                                                    ${fn:replace(lstDetail.startDate, '-', '.')}
                                                    - ${fn:replace(lstDetail.endDate, '-', '.')}
                                                </div>
                                            </div>
                                        </c:when>
                                    </c:choose>

                                    ${lstDetail.content}
                                </div>


                                <%-- file, link--%>
                                    <c:choose>
                                        <c:when test="${fn:length(lstDetail.attachFile) >0}">
                                            <section id="bo_v_file">
                                                <ul>
                                                    <li>
                                                        <i class="fa fa-folder-open" aria-hidden="true"></i>
                                                        <a href="${lstDetail.attachFile}" class="view_file_download"
                                                            download="">

                                                            <c:set var="nameFile"
                                                                value="${fn:split(lstDetail.attachFile, '/')}" />
                                                            <strong>${nameFile[fn:length(nameFile)-1]}</strong>
                                                        </a>
                                                        <br>

                                                    </li>
                                                </ul>
                                            </section>
                                        </c:when>
                                    </c:choose>

                                    <section id="bo_v_link">
                                        <ul>
                                            <c:choose>
                                                <c:when test="${fn:length(lstDetail.link1) >0}">
                                                    <li>
                                                        <i class="fa fa-link" aria-hidden="true"></i>
                                                        <a href="${lstDetail.link1}" target="_blank">
                                                            <strong>${lstDetail.link1}</strong>
                                                        </a>
                                                        <br>

                                                    </li>
                                                </c:when>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${fn:length(lstDetail.link2) >0}">
                                                    <li>
                                                        <i class="fa fa-link" aria-hidden="true"></i>
                                                        <a href="${lstDetail.link2}" target="_blank">
                                                            <strong>${lstDetail.link2}</strong>
                                                        </a>
                                                        <br>
                                                    </li>
                                                </c:when>
                                            </c:choose>
                                        </ul>
                                    </section>


                                    <%-- comment--%>
                                        <div class="commentS" style="margin-top: 10px">
                                            <div class="card">
                                                <h5 style="font-family: cursive;margin-left: 10px;margin-top: 10px;">
                                                    <spring:message code='gallery.comment' />
                                                </h5>

                                                <div class="body">
                                                    <ul class="comment-reply list-unstyled">
                                                        <c:forEach items="${lstCm}" var="lstComment">
                                                            <li class="row clearfix" style="margin-bottom: 10px">
                                                                <div class="icon-box col-md-2 col-4"><img
                                                                        class="img-fluid img-thumbnail"
                                                                        src="/resources/Upload/images/avatar2.jpg"
                                                                        alt="Awesome Image"></div>
                                                                <div class="text-box col-md-10 col-8 p-l-0 p-r0">
                                                                    <h5 class="m-b-0">${lstComment.empName} </h5>
                                                                    <p>${lstComment.contentComment} </p>
                                                                    <ul class="list-inline">
                                                                        <li><a href="">${lstComment.createDate}</a></li>
                                                                        <li>
                                                                            <div class="reply">
                                                                                <button class="btn btn-link"
                                                                                    onclick="return theFunction('${lstComment.empName}');"><span>
                                                                                        <i class="fa fa-reply"></i>
                                                                                        reply</span>
                                                                                </button>
                                                                                <a onclick="return confirm('Are you sure you want to delete this comment ?')"
                                                                                    href="/board/DeleteComment.hs?id=${lstComment.id}&idbaiviet=${lstDetail.id}">
                                                                                    <i class="fas fa-trash"
                                                                                        style="margin-left: 10px;color: red">
                                                                                        Delete</i>
                                                                                </a>
                                                                            </div>

                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="card">
                                                <div class="body">
                                                    <div class="comment-form">
                                                        <form class="row clearfix">

                                                            <div class="col-sm-12">
                                                                <div class="form-group">
                                                                    <textarea id="myTextarea" rows="4"
                                                                        class="form-control no-resize"
                                                                        placeholder="Please type what you want..."></textarea>
                                                                </div>
                                                                <button type="button" onclick="saveCom()"
                                                                    class="btn btn-block btn-primary">SUBMIT
                                                                </button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                    </div>
                    <div class="col-2" style="float: left;text-align: center;margin-top: 90px"><img style="width: 90%"
                            src="/resources/img/cover/Artboard2.png"></div>
                </div>

            </div>


            <script>
                $(document).ready(function () {
                    if ('${title}' != '') {
                        if ('${title}' == 'success') {
                            //toastr['${title}']('${message}');
                            toastr.success('', "<spring:message code='notification.index.success' />", { timeOut: 1000 });
                        } else {
                            toastr.error('', "<spring:message code='notification.index.error' />", { timeOut: 1000 });
                        }
                    }
                });

                function theFunction(nameEmp) {
                    document.getElementById("myTextarea").value = nameEmp + " >>";
                    document.getElementById("myTextarea").focus();

                }

                function saveCom() {
                    var contents = $('#myTextarea').val();
                    if (contents.trim() != "") {
                        var settings = {
                            "url": "/board/addComment",
                            "method": "POST",
                            "headers": {
                                "Content-Type": "application/json"
                            },
                            "data": JSON.stringify({
                                "idBoardMain": "${lstDetail.id}",
                                "contentComment": contents,
                            }),
                        };
                        $.ajax(settings).done(function (response) {
                            location.href = "/board/detail.hs?id=${lstDetail.id}&title=success&message=Add comment sucess";
                        });
                    } else {
                        toastr['error'](' Please input contents comment !');
                    }

                }

            </script>
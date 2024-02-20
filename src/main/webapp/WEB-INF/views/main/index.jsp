<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
        <link href="${_ctx}/resources/css/maincss/css/layout.css" rel="stylesheet" />
        <link href="${_ctx}/resources/css/maincss/css/style.css" rel="stylesheet" />
        <style>
            @media (max-width: 1200px) {
                .hidden-column {
                    display: none;
                }
                .dash_left,
                .dash_right {
                    width: 100%;
                }
                .addnew {
                    padding-left: 17%;
                }
            }
            
            @media (max-width: 1400px) {
                .addnew {
                    display: none;
                }
                .calendar_detail.hidden-column {
                    display: none;
                }
            }
        </style>

        <!-- begin #content -->
        <div id="content" class="content">

            <!-- begin page-header -->

            <!-- end page-header -->
            <div class="dashboard_wrap">
                <!-- 좌측영역 -->
                <div class="dash_left">
                    <!-- 내정보 -->
                    <div class="dash_con my_box">
                        <h2 class="dash_con_title">
                            <spring:message code='main.index.myinfor' />
                        </h2>
                        <div class="my_info">
                            <h3 style="padding-top: 26px;">VIETKO</h3>
                            <p class="user_photo">
                                <img style="margin-top: 30%" src="/resources/css/maincss/images/avata1.jpg" alt="${emp.empName}">
                            </p>
                            <ul style="margin-right: 51px;">
                                <li><strong>${emp.empName}</strong>&nbsp;&nbsp;<span>${emp.positionName}</span></li>
                                <li>${emp.deptName}</li>
                            </ul>
                        </div>

                    </div>
                    <!-- 내정보 // -->
                    <!-- 진행중인 업무 -->
                    <div class="dash_con busy_box">
                        <h2 class="dash_con_title">
                            <spring:message code='main.index.daywork' />
                        </h2>
                        <ul class="busy_list">
                            <c:set var="count_daywork" value="0" />
                            <c:forEach items="${daywork}" var="list">
                                <li>
                                    <a href="/work/mydaywork/detail.hs?id=${list.dayWorkId}&deptLevel=1&txtSeach=&displayStartPra=0">[${list.empName}]:${list.title}</a>
                                </li>
                                <c:set var="count_daywork" value="${count_daywork + 1}" />
                            </c:forEach>
                            <c:choose>
                                <c:when test="${count_daywork==0}">
                                    <spring:message code='main.index.datastatus' />
                                </c:when>
                            </c:choose>
                        </ul>


                    </div>
                    <!-- 진행중인 업무 // -->
                </div>
                <!-- 좌측영역 -->

                <!-- 중앙영역 -->
                <div class="dash_center">
                    <!-- 공지사항 -->
                    <div class="dash_con notice_box hidden-column">
                        <h2 class="dash_con_title">
                            <spring:message code='main.index.notice' />
                        </h2>
                        <ul class="notice_list">
                            <!-- 새글 일때 -->
                            <c:set var="count_notice" value="0" />
                            <c:forEach items="${board}" var="list">
                                <li>
                                    <a href="/board/notice/free/view.hs?boardId=${list.boardId}&deptLevel=1&txtSeach=&displayStartPra=0">[${list.empName}]:${list.title}</a><br>
                                </li>
                                <c:set var="count_notice" value="${count_notice + 1}" />
                            </c:forEach>
                            <c:choose>
                                <c:when test="${count_notice==0}">
                                    <spring:message code='main.index.datastatus' />
                                </c:when>
                            </c:choose>
                        </ul>
                    </div>

                    <div class="dash_con confer_box hidden-column">
                        <h2 class="dash_con_title">
                            <spring:message code='main.index.work' />
                        </h2>
                        <ul class="confer_list">
                            <c:set var="count_work" value="0" />
                            <c:forEach items="${work}" var="list">
                                <li>
                                    <a href="/work/mywork/detail.hs?id=${list.workId}&deptLevel=1&txtSeach=&displayStartPra=0">[${list.empName}]:${list.title}</a>
                                </li>
                                <c:set var="count_work" value="${count_work + 1}" />

                            </c:forEach>
                            <c:choose>
                                <c:when test="${count_work==0}">
                                    <spring:message code='main.index.datastatus' />
                                </c:when>
                            </c:choose>
                        </ul>

                    </div>
                    <!-- 회의일정 // -->
                    <!-- 메일함 -->
                    <div class="dash_con mail_box hidden-column">
                        <h2 class="dash_con_title">
                            <spring:message code='main.index.note' />
                        </h2>
                        <div class="mail_cont">
                            <!-- 메일함 탭 -->
                            <ul class="mail_tab">

                            </ul>

                            <div class="mail_inner_box">
                                <ul class="mail_list">
                                    <c:set var="count" value="0" />
                                    <c:forEach items="${note}" var="lstNote">
                                        <li>
                                            <a href="">${lstNote.contents}</a>
                                            <c:if test="${count==0}">
                                                <span class="new_icon"></span>
                                            </c:if>
                                            <a href="/main/deleteNote?id=${lstNote.id}" style="float: right"><i class="fas fa-trash" style="margin-left: 10px;color: gray;font-size: 10px;"></i></a>
                                        </li>
                                        <c:set var="count" value="${count + 1}" />
                                    </c:forEach>

                                    <c:choose>
                                        <c:when test="${count==0}">
                                            <p style="margin-left: 30px;"><spring:message code='main.index.datastatus' /></p>
                                        </c:when>
                                    </c:choose>

                                </ul>
                            </div>
                        </div>
                        <a class="open-AddBookDialog btn_more" data-toggle="modal" href="#exampleModal">Them</a>
                    </div>
                    <!-- 메일함 // -->
                    <!-- 쪽지함 -->
                    <div class="dash_con note_box hidden-column">
                        <h2 class="dash_con_title">Blank</h2>
                        <div class="note_cont">
                            <!-- 쪽지함 탭 -->
                            <ul class="mail_list">
                                <p style="margin-left: 30px;"><spring:message code='main.index.datastatus' /></p>
                            </ul>

                        </div>
                        <a href="" class="btn_more">them</a>
                    </div>
                    <!-- 쪽지함 // -->
                </div>
                <!-- 중앙영역 // -->

                <!-- 우측영역 -->
                <div class="dash_right">
                    <div class="dash_con calendar_box">
                        <h2 class="dash_con_title">
                            <spring:message code='main.index.calender' />
                        </h2>
                        <!-- 달력 -->
                        <div class="calendar_wrap">

                            <!-- 달력영역 -->
                            <div class="addnew" style="padding-top: 22%;padding-left: 21%">
                                <div id="datepicker"></div>
                            </div>

                        </div>
                        <!-- 달력 //-->
                        <!-- 일정리스트 -->
                        <div class="calendar_detail hidden-column">
                            <dl style="padding: 11px 4px !important;">

                                <dd>
                                    <div id="dong_ho">

                                        <div id="thoi_gian">
                                            <div>
                                                <span id="gio">00</span><span>
                                                    <spring:message code='main.index.hour' />
                                                </span>
                                            </div>
                                            <div>
                                                <span id="phut">00</span><span>
                                                    <spring:message code='main.index.minute' />
                                                </span>
                                            </div>
                                            <div>
                                                <span id="giay">00</span><span>
                                                    <spring:message code='main.index.second' />
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </dd>
                            </dl>
                        </div>
                        <!-- 일정리스트 // -->

                    </div>
                </div>
                <!-- 우측영역 // -->

            </div>

            <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">
                                <spring:message code='main.index.inputinfor' />
                            </h5>
                            <input type="text" hidden="" value="" name="" id="giatri" />
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form id="addform" name="addform" method="post">
                                <div class="form-group">
                                    <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
                                </div>

                            </form>

                        </div>

                        <div class="modal-footer">

                            <button type="button" class="btn btn-primary" onclick="addNote()">Add
                            </button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                            </button>
                        </div>
                    </div>
                </div>
            </div>


        </div>
        <!-- end #content -->

        <script>
            $(document).ready(function() {
                var datepicker = $("#datepicker").datepicker();
                if ('${title}' != '') {
                    if ('${title}' == 'success') {
                        //toastr['${title}']('${message}');
                        toastr.success('', "<spring:message code='notification.index.success' />", {
                            timeOut: 1000
                        });
                    } else {
                        toastr.error('', "<spring:message code='notification.index.error' />", {
                            timeOut: 1000
                        });
                    }
                }
                var Dem_gio = setInterval(Dong_ho, 1000);

            });

            function Dong_ho() {
                var gio = document.getElementById("gio");
                var phut = document.getElementById("phut");
                var giay = document.getElementById("giay");

                var Gio_hien_tai = new Date().getHours();
                var Phut_hien_tai = new Date().getMinutes();
                var Giay_hien_tai = new Date().getSeconds();

                gio.innerHTML = Gio_hien_tai;
                phut.innerHTML = Phut_hien_tai;
                giay.innerHTML = Giay_hien_tai;
            }

            function addNote() {
                if ($('#exampleFormControlTextarea1').val().trim() == "") {
                    toastr['error']('Please input value !');
                } else {
                    var settings = {
                        "url": "/main/addNote",
                        "method": "POST",
                        "headers": {
                            "Content-Type": "application/json"
                        },
                        "data": JSON.stringify({
                            "contents": $('#exampleFormControlTextarea1').val().trim(),
                        }),
                    };
                    $.ajax(settings).done(function(response) {
                        location.href = "/main/index.hs?title=success&message=Add new sucess";
                    });
                }
            }
        </script>
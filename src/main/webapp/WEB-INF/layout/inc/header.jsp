<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
        <%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
            <style>
                .error {
                    color: red;
                }

                #overlay {
                    position: fixed;
                    display: none;
                    width: 100%;
                    height: 100%;
                    top: 0;
                    left: 0;
                    right: 0;
                    bottom: 0;
                    background-color: rgba(0, 0, 0, 0.5);
                    z-index: 2;
                    cursor: pointer;
                }

                .loader-loading {
                    position: absolute;
                    top: 50%;
                    left: 50%;
                    -webkit-animation: spin 2s linear infinite;
                    /* Safari */
                    animation: spin 2s linear infinite;
                    transform: translate(-50%, -50%);
                    -ms-transform: translate(-50%, -50%);
                    border: 16px solid #f3f3f3;
                    border-radius: 50%;
                    border-top: 16px solid #3498db;
                    width: 120px;
                    height: 120px;

                }

                /* Safari */
                @-webkit-keyframes spin {
                    0% {
                        -webkit-transform: rotate(0deg);
                    }

                    100% {
                        -webkit-transform: rotate(360deg);
                    }
                }

                @keyframes spin {
                    0% {
                        transform: rotate(0deg);
                    }

                    100% {
                        transform: rotate(360deg);
                    }
                }
                @media (max-width: 1400px) {
                    #content{
                        margin-top: 100px;
                    }
                    .hidden-res{
                        display: none;
                    }
                    .lang-res{
                        margin-left: 120px;
                    }

                }

            </style>
            <tiles:importAttribute name="empAuthList" />
            <tiles:importAttribute name="empName" />
            <tiles:importAttribute name="empNameId" />
            <!-- begin #header -->
            <script src="${_ctx}/resources/js/myscript.js"></script>

            <div id="overlay">
                <div class="loader-loading"></div>
            </div>

            <div id="loader" class="header navbar-default">
                <!-- begin navbar-header -->
                <div class="navbar-header">
                    <a href="${_ctx}/main/index.hs" class="navbar-brand">

                        <span class="navbar-logo"></span>
                        <!-- 나중에 이미지로 변경해달라 할것. ${_ctx}/resources/img/logo/logo.png -->
                        <b>
                            <spring:message code='header.left.logo.title1' />
                        </b>
                        <spring:message code='header.left.logo.title2' />
                    </a>
                    <button type="button" class="navbar-mobile-toggler" onclick="menuResponsive()">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <ul class="navbar-nav navbar-left">
                    <li class="hidden-res">
                        <i class="fa fa-user-circle fa-2x"></i>
                        <spring:message code='lang.select.auth' />
                        <select name="change_emp_id" id="change_emp_id" style="margin-top: 15px; margin-right: 15px;">
                            ${empAuthList}
                            <c:forEach var="list" items="${empAuthList}">
                                <c:choose>
                                    <c:when test="${empName==list.empName}">
                                        <option value="${list.empId}" selected>${list.empName}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${list.empId}">${list.empName}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <form id="authChangeFrm" action="${_ctx}/editAuth.hs" method="GET">
                            <input type="hidden" name="empId" value="">
                            <input type="hidden" name="selectLangCd" value="${_lang}">
                        </form>
                    </li>
                    <li class="lang-res">
                        <spring:message code='lang.select.lang' />
                        <select name="lang" style="margin-top: 15px;">
                            <option value="ko">
                                <spring:message code='lang.select.lang.ko' />
                            </option>
                            <option value="en">
                                <spring:message code='lang.select.lang.en' />
                            </option>
                            <option value="vt">
                                <spring:message code='lang.select.lang.vt' />
                            </option>
                        </select>
                    </li>

                </ul>
                <ul class="navbar-nav navbar-right">
                    <%
                        String userAgent =request.getHeader("User-Agent").toUpperCase();
                        if(userAgent.indexOf("MOBILE") > -1) {
                    %>
                    <li class="dropdown">
                        <a href="" class="dropdown-toggle icon-menu" data-toggle="dropdown" aria-expanded="false">
                            <img src="${_ctx}/resources/img/theme/bell.png"title="Bell" width="30px" height="30px">
                            <div id="sum-notice">
                            </div>

                        </a>

                        <ul class="dropdown-menu notifications" style="top: 80%;">
                            <div id="icon-bell-notice">
                            </div>
                        </ul>
                    </li>

                    <li>
                        <a href="/attendance/attendance/checkattendance.hs?id=1">
                            <img src="${_ctx}/resources/img/theme/in.jpg" title="Check in" width="30px" height="30px">
                        </a>
                    </li>
                    <li>
                        <a href="/attendance/attendance/checkattendance.hs?id=2">
                            <img src="${_ctx}/resources/img/theme/out.jpg" title="Check out" width="30px" height="30px">
                        </a>
                    </li>
                    <li>
                        <a href="/work/mydaywork/list.hs">
                            <img src="${_ctx}/resources/img/theme/mywork.jpg"title="My work" width="30px" height="30px">
                        </a>
                    </li>
                    <li>
                        <a href="/logout.hs">
                            <img src="${_ctx}/resources/img/theme/logout.jpg"title="log out" width="30px" height="30px">
                        </a>
                    </li>
                    <% } else { %>
                    <li class="dropdown">
                        <a href="" id="ico-alarm" class="dropdown-toggle icon-menu" data-toggle="dropdown" aria-expanded="false">
                            <i class="far fa-bell" style="font-size: 18px;"></i>
                            <div id="sum-notice">
                            </div>

                        </a>

                        <ul class="dropdown-menu notifications" style="top: 80%;">
                            <div id="icon-bell-notice">
                            </div>
                        </ul>
                    </li>
                    <li>
                        <a href="/attendance/attendance/checkattendance.hs?id=1">
                            <i class="fa fa-chevron-circle-down"></i>
                            <label class="diplay-lable"><spring:message code='header.right.checkin.title' /></label>
                        </a>
                    </li>
                    <li>
                        <a href="/attendance/attendance/checkattendance.hs?id=2">
                            <i class="fa fa-chevron-circle-down"></i>
                            <label class="diplay-lable"><spring:message code='header.right.checkout.title' /></label>
                        </a>
                    </li>
                    <li>
                        <a href="/work/mydaywork/list.hs">
                            <i class="fa fa-chevron-circle-down"></i>
                            <label class="diplay-lable"><spring:message code='header.right.mywork.title' /></label>
                        </a>
                    </li>
                    <li>
                        <a href="/logout.hs">
                            <i class="fa fa-sign-out-alt"></i>
                            <label class="diplay-lable"><spring:message code='header.right.logout.title' /></label>
                        </a>
                    </li>
                    <% } %>
                </ul>
            </div>

            <script type="text/javascript">
                var totalNotice = 0;
                
                $("#ico-alarm").click(function(){
                    $("#favicon").attr("href", "${_ctx}/resources/img/favicon/favicon.ico")
                })
                
                $(document).ready(function () {
                    // 초기 접속시 정의된 언어 선택되도록.
                    $("select[name=lang]").val("${_lang}");
                    //$("select[name=change_emp_id]").val("${empId}");
                    // 언어 변경시 선택된 언어로 변경.
                    $("select[name=lang]").change(function () {
                        location.href = "/main/index.hs?lang=" + $(this).val();
                    });

                    $("select[name=change_emp_id]").change(function () {
                        console.log($(this).val());
                        $("input[name=empId]").val($(this).val());
                        $("#authChangeFrm").submit();
                    });
                    $.ajax({
                        type: 'GET',
                        url: '/api/bellnotice',
                        cache: false,
                        success: function (res) {
                            bellNotice(res.data1, res.data2);
                        },
                        failure: function (error) {
                            alert("Faillllllll");
                        }
                    });

                    var str = "";

                    async function bellNotice(ntc, ntc_) {
                        str = "";
                        $("#sum-notice").html('<span class="badge bg-primary badge-number">' + ((ntc_ > 99) ? "99+" : ntc_) + '</span>');
                        if(ntc_ > totalNotice && ntc_ > 0){
                            $("#favicon").attr("href", "${_ctx}/resources/img/favicon/favicon_notify.ico")
                            totalNotice = ntc_
                        }


                        str += '<li class="noti-header" style="padding: 5px 0px 9px 20px;"><strong style="color: #6c757d;"> <h5>You have ' + ntc_ + ' new Notifications </h5></strong></li>'
                        ntc.forEach(function (element) {
                            var valueStr = element['type']
                           if(valueStr=="TRANS" || valueStr=="PROJECT" || valueStr=="DAYPROJECT" || valueStr=="DAYPROJECTITEM"){
                               str += '<li class="li-content"> <a onclick="readNotice(' + element['noticeCd'] + ',' + element['preStr'] + ')" href="#"> <div class="media" style="' + element['htmlStrOpen'] + '"> <div class="media-body"> <p class="text"><b>' + element['empName'] + ':</b>' + element['message'] + '</p> <span class="timestamp">' + element['regDt'].slice(0, 19) + '</span> </div> </div> </a> </li>'
                           }else{
                               str += '<li class="li-content"> <a href="' + element['link'] + element['noticeCd'] + '&deptLevel=1&txtSeach=&displayStartPra=0"> <div class="media" style="' + element['htmlStrOpen'] + '"> <div class="media-body"> <p class="text"><b>' + element['empName'] + ':</b>' + element['message'] + '</p> <span class="timestamp">' + element['regDt'].slice(0, 19) + '</span> </div> </div> </a> </li>'
                           }

                        })
                        str += '<li class="noti-footer" style="padding: 15px 0px 10px 20px; border: none; font-size: 15px; font-weight: bold;"> <a href="/board/notice/list.hs" class="more">See all notifications</a> </li>'
                        $("#icon-bell-notice").html(str);
                    }

                    var pageRefresh = 180000;
                    setInterval(function () {
                        refresh();
                    }, pageRefresh);

                    function refresh() {
                        //$('#div1').load(location.href + " #div1");
                        //$('#div2').load(location.href + " #div2");
                        //location.reload();
                        $.ajax({
                            type: 'GET',
                            url: '/api/bellnotice',
                            cache: false,
                            success: function (res) {
                                bellNotice(res.data1, res.data2);
                            },
                            failure: function (error) {
                                alert("Faillllllll");
                            }
                        });
                    }
                });

                function readNotice(id, str) {

                    if (str === 'TRANS' || str === 'PROJECT' || str === 'DAYPROJECT' || str === 'DAYPROJECTITEM') {
                        var settings = {
                            "url": "/api/readnotice/trans",
                            "method": "POST",
                            "headers": {
                                "Content-Type": "application/json"
                            },
                            "data": JSON.stringify({
                                "noticeCd": id,
                                "type": str
                            }),
                        };
                        $.ajax(settings).done(function (response) {
                            if(str === 'TRANS'){
                                location.href = "/translation/locationtran.hs";
                            } else {
                                location.href = "/project/project/dayproject.hs?pjId=" + id;
                            }
                        });
                    }
                }
                function on() {
                    document.getElementById("overlay").style.display = "block";

                    // var = '<div id="loader-loading">aaaaaaaaaaa</div>';
                    // $("#overlay").html(str);
                }

                function off() {
                    document.getElementById("overlay").style.display = "none";
                }
                //var numberShowMenu = 1;
                function menuResponsive() {
                    var status = document.getElementById("sidebar");
                    if (status.style.display === "block") {
                        status.style.display = "none";
                    } else {
                        status.style.display = "block";
                    }
                }
                function enterCall(){
                    $(document).keypress(
                        function (event) {
                            if (event.which == '13') {
                                event.preventDefault();
                            }
                        }
                    );
                }
                
            </script>
            <!-- end #header -->
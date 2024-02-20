<%@ page import="org.springframework.format.datetime.standard.DateTimeContextHolder" %>
    <%@ page import="java.text.DateFormat" %>
        <%@ page import="java.text.SimpleDateFormat" %>
            <%@ page import="java.util.Date" %>
                <%@ page import="java.sql.Time" %>
                    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
                        <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
                            <style>
                                tr.odd {
                                    height: 100px;
                                }

                                tr.even {
                                    height: 100px;
                                }
                            </style>
                            <div id="content" class="content">

                                <h1 class="page-header cursor-default">
                                    <spring:message code='emp.list.bigtitle'/> - <spring:message code='attendance.title'/>
                                    <br>
                                    <small>
                                        <spring:message code='attendance.list.smalltitledetail'/>
                                    </small>
                                </h1>

                                <div style="margin-top: 10px">
                                    <div style="float: left;">
                                        <span style="font-weight: bold">
                                            <spring:message code='work.add.start' />:
                                        </span>
                                        <input readonly id="workstart" style="background-color: white;" name="workstart"
                                            class="form-control form-control-sm mr-1" type="text" value="${startdate}">
                                    </div>
                                    <div style="float: left;margin-left: 20px">
                                        <span style="font-weight: bold">
                                            <spring:message code='work.add.end' />:
                                        </span>
                                        <input readonly id="workend" name="workend" style="background-color: white;"
                                            class="form-control form-control-sm mr-1" type="text" value="${enddate}">
                                    </div>
                                    <div style="float: left;margin-left: 20px">
                                        <br>
                                        <button class="form-control btn btn-primary" type="button" onclick="inquiry()">
                                            <spring:message code='attendance.inquiry' />
                                        </button>
                                    </div>
                                    <div style="float: left;margin-left: 20px">
                                        <br>
                                        <button style="background-color: black" class="form-control btn btn-primary"
                                            type="button" onclick="summary()">
                                            <spring:message code='attendance.summary' />
                                        </button>
                                    </div>
                                </div>
                                <div style="clear: both"></div>
                                <!-- html -->
                                <div style="margin-top: 15px">
                                    <table id="attendace" class="table table-striped table-bordered"
                                        style="width:100%;text-align: center">
                                        <thead>
                                            <tr style="background-color: #1c5691;color: white">
                                                <th>
                                                    <spring:message code='attendance.no' />
                                                </th>
                                                <th>
                                                    <spring:message code='attendance.department' />
                                                </th>
                                                <th>
                                                    <spring:message code='attendance.employee' />
                                                </th>
                                                <th>
                                                    <spring:message code='attendance.date' />
                                                </th>
                                                <th>
                                                    <spring:message code='attendance.checkin' />
                                                </th>
                                                <th>
                                                    <spring:message code='attendance.checkout' />
                                                </th>
                                                <th>Ip user</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${listAtt}" var="lst">
                                                <tr>
                                                    <td>${lst.rownum}</td>
                                                    <td>${lst.deptName}</td>
                                                    <td>${lst.empName}</td>
                                                    <td>${lst.dateCheck}</td>
                                                    <td>${lst.checkinTime}</td>
                                                    <td>${lst.checkoutTime}</td>
                                                    <td>${lst.regIp}</td>
                                                </tr>
                                            </c:forEach>

                                        </tbody>

                                    </table>

                                </div>

                            </div>
                            <!-- script -->
                            <script>
                                $(document).ready(function () {
                                    var lang_dt;
                                    if ('${_lang}' == 'ko') {
                                        lang_dt = lang_kor;
                                    } else if ('${_lang}' == 'en') {
                                        lang_dt = lang_en;
                                    } else {
                                        lang_dt = lang_vt;
                                    }
                                    var workstart = $("#workstart").datepicker({ dateFormat: 'yy-mm-dd' });
                                    var workend = $("#workend").datepicker({ dateFormat: 'yy-mm-dd' });
                                    $('#attendace').DataTable({
                                        "order": [[0, "desc"]],
                                        'iDisplayLength': 10,
                                        "responsive": true, "lengthChange": false, "autoWidth": false,
                                        "language": lang_dt,

                                    });
                                    if ('${title}' != '') {
                                        if ('${title}' == 'success') {
                                            //toastr['${title}']('${message}');
                                            toastr.success('', "<spring:message code='notification.index.success' />", { timeOut: 1000 });
                                        } else {
                                            toastr.error('', "<spring:message code='notification.index.error' />", { timeOut: 1000 });
                                        }
                                    }
                                });
                                function inquiry() {
                                    location.href = "/attendance/attendance/attendance.hs?startdate=" + $("#workstart").val() + "&enddate=" + $("#workend").val();
                                }

                                function summary() {
                                    location.href = "/attendance/attendance/summaryuser.hs?startdate=" + $("#workstart").val() + "&enddate=" + $("#workend").val();
                                }




                            </script>
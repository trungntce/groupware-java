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
            <h3 class="page-title">
                <spring:message code='attendance.summary' /> (User)
            </h3>
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
                    <button style="background-color: black" class="form-control btn btn-primary" type="button"
                        onclick="summary()">
                        <spring:message code='attendance.summary' />
                    </button>
                </div>
            </div>
            <div style="clear: both"></div>
            <!-- html -->
            <div style="margin-top: 15px">
                <table id="attendace" class="table table-striped table-bordered" style="width:100%;text-align: center">
                    <thead>
                        <tr style="background-color: #1c5691;color: white">
                            <th>
                                <spring:message code='author.list.empid' />
                            </th>
                            <th>
                                <spring:message code='attendance.department' />
                            </th>
                            <th>
                                <spring:message code='work.detail.position' />
                            </th>
                            <th>
                                <spring:message code='attendance.employee' />
                            </th>
                            <th>
                                <spring:message code='attendance.ngaycongty' />
                            </th>
                            <th>
                                <spring:message code='attendance.ngaydilam' />
                            </th>
                            <th>
                                <spring:message code='attendance.disom' />
                            </th>
                            <th>
                                <spring:message code='attendance.dungio' />
                            </th>
                            <th>
                                <spring:message code='attendance.dimuon' />
                            </th>
                            <th>
                                <spring:message code='attendance.vesom' />
                            </th>
                            <th>
                                <spring:message code='attendance.vang' />
                            </th>


                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listAtt}" var="lst">

                            <tr>
                                <td>${lst.empCd}</td>
                                <td>${lst.deptName}</td>
                                <td>${lst.positionName}</td>
                                <td>${lst.empName}</td>
                                <td>${lst.ngaycongty}</td>
                                <td><a class="open-AddBookDialog" data-toggle="modal" href="#exampleModal"
                                        onclick="loadWorkingDay(${lst.empCd})"
                                        style="text-decoration: underline;">${lst.ngaydilam}</a></td>
                                <td><a class="open-AddBookDialog" data-toggle="modal" href="#exampleModal"
                                        onclick="loadEarlyDay(${lst.empCd})"
                                        style="text-decoration: underline;">${lst.disom}</a></td>
                                <td><a class="open-AddBookDialog" data-toggle="modal" href="#exampleModal"
                                        onclick="loadOntimeDay(${lst.empCd})"
                                        style="text-decoration: underline;">${lst.dungio}</a></td>
                                <td><a class="open-AddBookDialog" data-toggle="modal" href="#exampleModal"
                                        onclick="loadLateDay(${lst.empCd})"
                                        style="text-decoration: underline;">${lst.dimuon}</a></td>
                                <td><a class="open-AddBookDialog" data-toggle="modal" href="#exampleModal"
                                        onclick="loadLeaveEarly(${lst.empCd})"
                                        style="text-decoration: underline;">${lst.vesom}</a></td>
                                <td>${lst.vang}</td>

                            </tr>
                        </c:forEach>

                    </tbody>

                </table>

                <style>
                    .my-custom-scrollbar {
                        position: relative;
                        height: 800px;
                        overflow: auto;
                    }
                </style>
                <!-- Modal -->
                <div style="padding-right: 12% !important;" class="modal fade" id="exampleModal" tabindex="-1"
                    role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content" style="width: max-content;">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">
                                    <spring:message code='emp.list.information' />
                                </h5>
                                <input type="text" hidden="" value="" name="" id="giatri" />
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body table-wrapper-scroll-y my-custom-scrollbar">
                                <table id="rsatt" class="table table-striped table-bordered" style="text-align: center">
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
                                        <tr>
                                            <td>90</td>
                                            <td>Vietco Diamond</td>
                                            <td>admin</td>
                                            <td>2022-02-28</td>
                                            <td>2022-02-28 11:11:08.0</td>
                                            <td>2022-02-28 11:11:09.0</td>
                                            <td>192.168.1.89</td>
                                        </tr>
                                    </tbody>

                                </table>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close
                                </button>

                            </div>
                        </div>
                    </div>
                </div>


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
                    "order": [[0, "asc"]],
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
            function loadWorkingDay(empCd) {
                $.ajax({
                    type: "POST",
                    url: "/attendance/loadWorkingDay",
                    data: {
                        startdate: $("#workstart").val(),
                        enddate: $("#workend").val(),
                        empCd: empCd
                    },
                    success: function (response) {
                        var str = "";
                        for (var i = 0; i < response.length; i++) {
                            str += "<tr><td>" + response[i]["attendanceId"] + "</td>";
                            str += "<td>" + response[i]["deptName"] + "</td>";
                            str += "<td>" + response[i]["empName"] + "</td>";
                            str += "<td>" + response[i]["dateCheck"] + "</td>";
                            str += "<td>" + response[i]["checkinTime"] + "</td>";
                            str += "<td>" + response[i]["checkoutTime"] + "</td>";
                            str += "<td>" + response[i]["regIp"] + "</td></tr>";
                        }

                        $("#rsatt tbody").html(str);
                    },
                    error: function (error) {
                        toastr['error']('error !');
                    }
                })


            }

            function loadEarlyDay(empCd) {
                $.ajax({
                    type: "POST",
                    url: "/attendance/loadEarlyDay",
                    data: {
                        startdate: $("#workstart").val(),
                        enddate: $("#workend").val(),
                        empCd: empCd
                    },
                    success: function (response) {
                        var str = "";
                        for (var i = 0; i < response.length; i++) {
                            str += "<tr><td>" + response[i]["attendanceId"] + "</td>";
                            str += "<td>" + response[i]["deptName"] + "</td>";
                            str += "<td>" + response[i]["empName"] + "</td>";
                            str += "<td>" + response[i]["dateCheck"] + "</td>";
                            str += "<td>" + response[i]["checkinTime"] + "</td>";
                            str += "<td>" + response[i]["checkoutTime"] + "</td>";
                            str += "<td>" + response[i]["regIp"] + "</td></tr>";
                        }

                        $("#rsatt tbody").html(str);
                    },
                    error: function (error) {
                        toastr['error']('error !');
                    }
                })


            }

            function loadOntimeDay(empCd) {
                $.ajax({
                    type: "POST",
                    url: "/attendance/loadOntimeDay",
                    data: {
                        startdate: $("#workstart").val(),
                        enddate: $("#workend").val(),
                        empCd: empCd
                    },
                    success: function (response) {
                        var str = "";
                        for (var i = 0; i < response.length; i++) {
                            str += "<tr><td>" + response[i]["attendanceId"] + "</td>";
                            str += "<td>" + response[i]["deptName"] + "</td>";
                            str += "<td>" + response[i]["empName"] + "</td>";
                            str += "<td>" + response[i]["dateCheck"] + "</td>";
                            str += "<td>" + response[i]["checkinTime"] + "</td>";
                            str += "<td>" + response[i]["checkoutTime"] + "</td>";
                            str += "<td>" + response[i]["regIp"] + "</td></tr>";
                        }

                        $("#rsatt tbody").html(str);
                    },
                    error: function (error) {
                        toastr['error']('error !');
                    }
                })


            }
            function loadLateDay(empCd) {
                $.ajax({
                    type: "POST",
                    url: "/attendance/loadLateDay",
                    data: {
                        startdate: $("#workstart").val(),
                        enddate: $("#workend").val(),
                        empCd: empCd
                    },
                    success: function (response) {
                        var str = "";
                        for (var i = 0; i < response.length; i++) {
                            str += "<tr><td>" + response[i]["attendanceId"] + "</td>";
                            str += "<td>" + response[i]["deptName"] + "</td>";
                            str += "<td>" + response[i]["empName"] + "</td>";
                            str += "<td>" + response[i]["dateCheck"] + "</td>";
                            str += "<td>" + response[i]["checkinTime"] + "</td>";
                            str += "<td>" + response[i]["checkoutTime"] + "</td>";
                            str += "<td>" + response[i]["regIp"] + "</td></tr>";
                        }

                        $("#rsatt tbody").html(str);
                    },
                    error: function (error) {
                        toastr['error']('error !');
                    }
                })


            }
            function loadLeaveEarly(empCd) {
                $.ajax({
                    type: "POST",
                    url: "/attendance/loadLeaveEarly",
                    data: {
                        startdate: $("#workstart").val(),
                        enddate: $("#workend").val(),
                        empCd: empCd
                    },
                    success: function (response) {
                        var str = "";
                        for (var i = 0; i < response.length; i++) {
                            str += "<tr><td>" + response[i]["attendanceId"] + "</td>";
                            str += "<td>" + response[i]["deptName"] + "</td>";
                            str += "<td>" + response[i]["empName"] + "</td>";
                            str += "<td>" + response[i]["dateCheck"] + "</td>";
                            str += "<td>" + response[i]["checkinTime"] + "</td>";
                            str += "<td>" + response[i]["checkoutTime"] + "</td>";
                            str += "<td>" + response[i]["regIp"] + "</td></tr>";
                        }

                        $("#rsatt tbody").html(str);
                    },
                    error: function (error) {
                        toastr['error']('error !');
                    }
                })


            }

        </script>
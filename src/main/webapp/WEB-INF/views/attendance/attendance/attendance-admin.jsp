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
                        <spring:message code='attendance.selectdate'/>:
                    </span>
                    <br>
                    <input readonly id="workstart" style="background-color: white;" name="workstart" class="form-control form-control-sm mr-1" type="text" value="${startdate}">
                </div>
                <div style="float: left;margin-left: 20px">
                    <br>
                    <button class="form-control btn btn-primary" type="button" onclick="inquiry()">
                        <spring:message code='attendance.inquiry' />
                    </button>
                </div>

            </div>
            <div style="margin-top: 10px">
                <div style="float: right">
                    <br>
                    <button style="background-color: black" class="form-control btn btn-primary" type="button"
                        onclick="summary()">
                        <spring:message code='attendance.summary' />
                    </button>
                </div>
            </div>
            <div style="clear: both"></div>
            <!-- html -->
            <div class="m-table" style="position: relative;">
                <div style="margin-top: 15px">
                    <table id="attendace" class="table table-striped table-bordered"
                        style="width:100%;text-align: center">
                        <select style="top: 0;position: absolute;right: 400px;width: 300px;" id="levelDept"
                            name="levelDept" class="form-control">
                            <c:forEach items="${lstDeptLevel}" var="lst">
                                <option value="${lst.deptCd}">${lst.deptName}</option>
                            </c:forEach>
                        </select>
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
                    </table>
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
                var table = $('#attendace').DataTable({
                    "dom": '<"toolbar">frtip',
                    "order": [[4, "desc"]],
                    'iDisplayLength': 50,
                    "responsive": true, "lengthChange": false, "autoWidth": false,
                    "language": lang_dt,
                    "bProcessing": true,
                    "bServerSide": true,
                    "bStateSave": false,
                    "iDisplayStart": 0,
                    "fnDrawCallback": function () {
                    },
                    "sAjaxSource": "/springPaginationDataTablesAtt?startdate=" + $("#workstart").val() + "&enddate=" + $("#workend").val() + "&levelDept=" + $("#levelDept").val(),
                    "columns": [
                        {
                            "data": "rownum"
                        }
                        , {
                            "data": "deptName"
                        }, {
                            "data": "empName"
                        }, {
                            "data": "dateCheck"
                        }, {
                            "data": "checkinTime"
                        }, {
                            "data": "checkoutTime"
                        }, {
                            "data": "regIp"
                        }
                    ], "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                        var dateObj = new Date(aData["dateCheck"]);
                        var weekday = dateObj.toLocaleString("default", { weekday: "short" });
                        if (aData["checkinTime"] != "---" && parseInt(aData["checkinTime"].split(" ")[1].replaceAll(":", "")) <= 80500 && aData["checkoutTime"] == "---") {
                            $(nRow).css({ "background-color": "#c5c5c5" });
                        } else if (aData["checkinTime"] != "---" && aData["checkoutTime"] != "---" && parseInt(aData["checkinTime"].split(" ")[1].replaceAll(":", "")) <= 80500 && parseInt(aData["checkoutTime"].split(" ")[1].replaceAll(":", "")) >= 170000) {
                            $(nRow).css({ "background-color": "lightgreen" });
                        }else if (weekday=="Th 7" && aData["checkinTime"] != "---" && aData["checkoutTime"] != "---" && parseInt(aData["checkinTime"].split(" ")[1].replaceAll(":", "")) <= 80500 && parseInt(aData["checkoutTime"].split(" ")[1].replaceAll(":", "")) >= 120000) {
                            $(nRow).css({ "background-color": "lightgreen" });
                        }
                        else {
                            $(nRow).css({ "background-color": "red" });
                        }
                    }

                });
                if ('${title}' != '') {
                    if ('${title}' == 'success') {
                        //toastr['${title}']('${message}');
                        toastr.success('', "<spring:message code='notification.index.success' />", { timeOut: 1000 });
                    } else {
                        toastr.error('', "<spring:message code='notification.index.error' />", { timeOut: 1000 });
                    }
                }
                $('#levelDept').on('change', function () {

                    $('#attendace').DataTable().ajax.url("/springPaginationDataTablesAtt?startdate=" + $("#workstart").val() + "&enddate=" + $("#workend").val() + "&levelDept=" + $("#levelDept").val()).load();
                });
            });
            // function inquiry() {
            //     location.href = "/attendance/attendanceadmin/attendance.hs?startdate=" + $("#workstart").val() + "&enddate=" + $("#workend").val();
            // }

            function inquiry() {
                $('#attendace').DataTable().ajax.url("/springPaginationDataTablesAtt?startdate=" + $("#workstart").val() + "&enddate=" + $("#workend").val() + "&levelDept=" + $("#levelDept").val()).load();
                // location.href = "/attendance/attendanceadmin/attendance.hs?startdate=" + $("#workstart").val() + "&enddate=" + $("#workend").val() + "&levelDept=" + $("#levelDept").val();
            }

            function summary() {
                location.href = "/attendance/attendanceadmin/summaryadmin.hs";
            }
            $(window).resize(function () {
                var currentRight = $("#attendace_filter").width() * 1 + 50;
                $("#levelDept").css({ right: currentRight + "px" })
            })



        </script>
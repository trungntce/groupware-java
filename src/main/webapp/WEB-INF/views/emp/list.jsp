<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<style>
    tr.odd {
        height: 100px;
    }

    tr.even {
        height: 100px;
    }
    .dataTables_filter {
        display: none !important;
    }
</style>
<%
    String levelDeptGet = request.getParameter("levelDept");
    if (levelDeptGet == null || levelDeptGet == "null") {
        levelDeptGet = "1";
    }
    request.setAttribute("levelDeptGet", levelDeptGet);
    String optionSearchGet = request.getParameter("optionSearch");
    if (optionSearchGet == null || optionSearchGet == "null") {
        optionSearchGet = "";
    }
    request.setAttribute("optionSearchGet", optionSearchGet);
    String inputSearchGet = request.getParameter("inputSearch");
    if (inputSearchGet == null || inputSearchGet == "null") {
        inputSearchGet = "";
    }
    String displayStartPra = request.getParameter("displayStartPra");
    if (displayStartPra == null || displayStartPra == "null") {
        displayStartPra = "0";
    }
%>
<div id="content" class="content">
    <h1 class="page-header cursor-default">
        <spring:message code='emp.list.smalltitle'/> - <spring:message code='emp.list.bigtitle'/> 
        <br>
        <small>
            <spring:message code='emp.list.smalltitle.detail'/>
        </small>
    </h1>
    <div class="row">
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code='search.title' /></h3>
                </div>
                <div class="panel-body">
                    <form id="myform" class="form-inline" action="">
                        <table class="table mb-0">
                            <tbody>
                            <tr>
                                <td style="background: #f0f4f6;width: 90px;font-weight: bold"><spring:message code='work.list.dept'/></td>
                                <td>
                                    <select id="levelDept" name="levelDept" class="form-control w-100">
                                        <c:forEach items="${lstDeptLevel}" var="lst">
                                            <option value="${lst.deptCd}" ${lst.deptCd==levelDeptGet ? "selected" : "" }>${lst.deptName}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;width: 90px;font-weight: bold"><spring:message code='dateboard.notice.company.timkiem'/></td>
                                <td>
                                    <select id="optionSearch" name="optionSearch" class="custom-select custom-select-sm form-control form-control-sm m-2">
                                        <option value="emp_name"><spring:message code='dateboard.notice.company.empname'/></option>
                                        <option value="emp_id" ${optionSearchGet.equals("emp_id") ? "selected" : ""}><spring:message code='emp.list.empid'/></option>
                                        <option value="position_name" ${optionSearchGet.equals("position_name") ? "selected" : "" }><spring:message code='work.list.position'/></option>
                                        <option value="title" ${optionSearchGet.equals("title") ? "selected" : "" }><spring:message code='work.list.title'/></option>
                                    </select>
                                    <input id="inputSearch" name="inputSearch" class="form-control form-control-sm m-2" type="text" value="<%=inputSearchGet%>" />
                                    <button type="button" class="btn btn-sm btn-primary m-2" onclick="searchMethod()"><spring:message code='dateboard.notice.company.timkiem'/></button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div style="float: right">
        <button style="margin-bottom: 5px" class="open-AddBookDialog btn btn-info" type="button"
                data-toggle="modal" href="#" onclick="openModel()">
            <spring:message code='work.list.delete'/>
        </button>
        <button style="margin-bottom: 5px" class="btn btn-primary" onclick="addP()">
            <spring:message code='emp.list.add'/>
        </button>
    </div>
    <div style="clear: both"></div>
    <!-- html -->

    <div class="m-table" style="position: relative;">
        <table id="tbemployee" class="table table-striped table-bordered w-100 text-center">
            <thead>
                <tr style="background-color: #1c5691;color: white">
                    <th style="text-align: center;vertical-align: inherit;"><input type="checkbox" name="all" id="checkall"/></th>
                    <th class="all"><spring:message code='emp.list.no'/></th>
                    <th><spring:message code='emp.list.empname'/></th>
                    <th><spring:message code='emp.list.empid'/></th>
                    <th class="min-tablet"><spring:message code='emp.list.department'/></th>
                    <th class="min-tablet"><spring:message code='emp.list.position'/></th>
                    <th><spring:message code='emp.list.enterdate'/></th>
                    <th class="min-tablet"><spring:message code='emp.list.function'/></th>
                </tr>
            </thead>

        </table>
    </div>
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel"><spring:message code='confirm.delete' /></h5>
                    <input type="text" hidden="" value="" name="" id="giatri"/>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code='translation.close' /></button>
                    <button type="button" class="btn btn-primary" onclick="xoaW()"><spring:message code='work.list.delete' /></button>
                </div>
            </div>
        </div>
    </div>

</div>
<!-- script -->
<script>
    function openModel() {
        var lstChecked = "";
        var myTable = $('#tbemployee').dataTable();
        var rowcollection = myTable.$('input[name="xoa"]:checked', {"page": "all"});
        rowcollection.each(function (index, elem) {
            var checkbox_value = $(elem).val();
            lstChecked += checkbox_value + ",";
        });
        if (lstChecked.trim() == "") {
            alert("<spring:message code='notice.select.message' />");
        } else {
            $("#exampleModal").modal('show');
        }
    };
    $(document).ready(function () {
        var lang_dt;
        if ('${_lang}' == 'ko') {
            lang_dt = lang_kor;
        } else if ('${_lang}' == 'en') {
            lang_dt = lang_en;
        } else {
            lang_dt = lang_vt;
        }
        $('#checkall').change(function () {
            $('.cb-element').prop('checked', this.checked);
        });
        var table = $('#tbemployee').DataTable({
            'displayStart': <%=displayStartPra%>,
            "dom": '<"toolbar">frtip',
            "order": [[1, "desc"]],
            'iDisplayLength': 10,
            "responsive": true, "lengthChange": false, "autoWidth": false,
            "language": lang_dt,
            "bProcessing": true,
            "bServerSide": true,
            "bStateSave": false,
            "iDisplayStart": 0,
            "fnDrawCallback": function () {
            },
            "sAjaxSource": "/springPaginationDataTablesEmp?levelDept=" + $("#levelDept").val()+ "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val(),
            "columns": [
                {
                    "data": "empCd",
                    render: function (data, type, row) {
                        return ' <input class="cb-element" name="xoa" type="checkbox" value="'
                            + data + '" >';
                    }
                },
                {
                    "data": "rownum"
                }
                , {
                    "data": "empName"
                }, {
                    "data": "empId"
                }, {
                    "data": "deptName"
                }, {
                    "data": "positionName"
                }, {
                    "data": "enterDate"
                }
            ],
            "aoColumnDefs": [
                {
                    "aTargets": [7],
                    "mData": "empCd",
                    "mRender": function (data, type, full) {
                        return '<button type="button" onclick="detail('
                            + data
                            + ')" class="btn btn-success"  ><spring:message code="emp.list.viewprofile" /></button>';
                    }
                },
                {
                    "orderable": false,
                    "targets": 0
                }
            ]
        });

        if ('${title}' != '') {
            if ('${title}' == 'success') {
                //toastr['${title}']('${message}');
                toastr.success('', "<spring:message code='notification.index.success' />", {timeOut: 1000});
            } else {
                toastr.error('', "<spring:message code='notification.index.error' />", {timeOut: 1000});
            }
        }
        $('#tbemployee').on( 'page.dt', function () {
            var displayStartPra = $('#tbemployee').DataTable().page.info().page * 10;
            location.href = "/emp/list.hs?levelDept=" + $("#levelDept").val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&displayStartPra=" + displayStartPra;
        } );


    });

    function detail(id) {
        var displayStartPra = $('#tbemployee').DataTable().page.info().page * 10;
        location.href = "/emp/profile.hs?id=" + id + "&levelDept=" + $("#levelDept").val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&displayStartPra=" + displayStartPra;
    }

    function addP() {
        location.href = "/emp/addEmp.hs";
    }

    function xoaW() {
        var lstChecked = "";
        var myTable = $('#tbemployee').dataTable();
        var rowcollection = myTable.$('input[name="xoa"]:checked', {"page": "all"});
        rowcollection.each(function (index, elem) {
            var checkbox_value = $(elem).val();
            lstChecked += checkbox_value + ",";
        });

        if (lstChecked.trim() == "") {
            toastr['error']('Delete error !');
        } else {
            $.ajax({
                type: "POST",
                url: "/emp/delete.hs",
                data: {
                    'lstChecked': lstChecked,
                },
                success: function (response) {
                    location.href = "/emp/list.hs?title=success&message=Delete sucess";
                },
                error: function (error) {
                    toastr['error']('Delete error !');
                }
            })
        }
    }

    function searchMethod() {
        // $('#tbemployee').DataTable().ajax.url("/springPaginationDataTablesEmp?levelDept=" + $("#levelDept").val() +"&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val()).load();
        var displayStartPra = 0;
        location.href = "/emp/list.hs?levelDept=" + $("#levelDept").val() + "&optionSearch=" + $('#optionSearch').val() + "&inputSearch=" + $('#inputSearch').val() + "&displayStartPra=" + displayStartPra;
    }

</script>
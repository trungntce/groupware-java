<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<%

    String levelDeptGet = request.getParameter("levelDept");
    if (levelDeptGet == null || levelDeptGet == "null") {
        levelDeptGet = "1";
    }
    String optionSearchGet = request.getParameter("optionSearch");
    if (optionSearchGet == null || optionSearchGet == "null") {
        optionSearchGet = "";
    }
    String inputSearchGet = request.getParameter("inputSearch");
    if (inputSearchGet == null || inputSearchGet == "null") {
        inputSearchGet = "";
    }
    String displayStartPra = request.getParameter("displayStartPra");
    if (displayStartPra == null || displayStartPra == "null") {
        displayStartPra = "0";
    }
    String backSearch="levelDept="+levelDeptGet+"&optionSearch="+optionSearchGet+"&inputSearch="+inputSearchGet+"&displayStartPra="+displayStartPra;
%>
<style>
    select#account-select {
        width: 100%;
        padding: 5px;
    }
    td{
        vertical-align: inherit !important;
    }
    .tab {
        overflow: hidden;
        border: 1px solid #ccc;
        background-color: #f1f1f1;
        width: 160px;
    }

/* Style the buttons inside the tab */
    .tab button {
        background-color: inherit;
        float: left;
        border: none;
        outline: none;
        cursor: pointer;
        transition: 0.3s;
        font-size: 17px;
        width: 50%;
    }

/* Change background color of buttons on hover */
    .tab button:hover {
        background-color: #ddd;
    }

/* Create an active/current tablink class */
    .tab button.active {
        background-color: #ccc;
    }

/* Style the tab content */
    .tabcontent {
        display: none;
        padding: 6px 12px;
        border: 1px solid #ccc;
    }
    td {
        vertical-align: inherit !important;
    }
</style>
        <div id="content" class="content">
            <h3 class="page-title">
                <spring:message code='emp.list.smalltitle' /> - <spring:message code='emp.list.viewprofile' />
                <br>
                <small>
                    <spring:message code='detail.message' />
                </small>
            </h3>
            <!-- html -->
            <div style="float: right">
                <h4 class="panel-title">
                    <button type="button" class="btn btn-success" onclick="createSubAccount()">
                        <spring:message code='emp.profile.subacountcreate' />
                    </button>
                    <button class="btn btn-primary" onclick="back()">
                        <spring:message code='work.addtran.back' />
                    </button>
                </h4>
            </div>
            <div style="clear: both"></div>
            <div class="row">
                <div class="col-xl-12 ui-sortable">
                    <div class="panel panel-inverse" style="">
                        <div class="panel-heading ui-sortable-handle">
                            <h4 class="panel-title">
                                <spring:message code='emp.list.information' />
                            </h4>
                        </div>

                        <div class="panel-body">
                            <div class="tab">
                                <!-- <button class="tablinks" onclick="openAccount(event, 'main-account')" id="defaultOpen">Main Account</button>
                                <button class="tablinks" onclick="openAccount(event, 'sub-account')">Sub Account</button> -->

                                <select name="account-select" id="account-select">
                                    <c:forEach items="${empAllDTO}" var="list">
                                        <option value="${list.empCd}">${list.empName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <c:forEach items="${empAllDTO}" var="empDTO">
                                <div id="${empDTO.empCd}" class="tabcontent">
                                    <table class="table mb-0">
                                        <tbody>
                                            <tr>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.list.empid' /></td>
                                                <td style="width: 35%"> ${empDTO.empId}</td>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.list.empname' /></td>
                                                <td style="width: 35%">${empDTO.empName}</td>
                                            </tr>
                                            <tr>
                                                <td style="background: #f0f4f6;width: 15%; height: 90px;"><spring:message code='emp.list.department' /></td>
                                                <td style="width: 35%">${empDTO.deptName}</td>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.list.position' /></td>
                                                <td style="width: 35%">${empDTO.positionName}</td>
                                            </tr>
                                            <tr>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.list.language' /></td>
                                                <td style="width: 35%">${langCd}</td>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.list.address' /></td>
                                                <td style="width: 35%">${empDTO.addr}</td>
                                            </tr>
                                            <tr>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.list.detailaddress' /></td>
                                                <td style="width: 35%">${empDTO.addrDetail}</td>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.list.zipcode' /></td>
                                                <td style="width: 35%">${empDTO.zipCd}</td>
                                            </tr>
                                            <tr>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.list.tel' /></td>
                                                <td style="width: 35%">${empDTO.tel}</td>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.list.phone' /></td>
                                                <td style="width: 35%">${empDTO.phone}</td>
                                            </tr>
                                            <tr>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.list.birthday' /></td>
                                                <td style="width: 35%">${empDTO.birthDay}</td>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.profile.mail' /></td>
                                                <td style="width: 35%">${empDTO.mail}</td>
                                            </tr>
                                            <tr>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.profile.zalo' /></td>
                                                <td style="width: 35%">${empDTO.zaloId}</td>

                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.profile.status' /></td>
                                                <td style="width: 35%">${empDTO.empStatus}</td>
                                            </tr>
                                            <tr>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.list.admin' /></td>
                                                <td style="width: 35%">${empDTO.adminYn}</td>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.list.type' /></td>
                                                <td style="width: 35%">${empDTO.empType}</td>
                                            </tr>
                                            <tr>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.profile.translation' /></td>
                                                <td style="width: 35%">${empDTO.translationYn}</td>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.list.enterdate' /></td>
                                                <td style="width: 35%">${empDTO.enterDate}</td>
                                            </tr>
                                            <tr>
                                                <td style="background: #f0f4f6;width: 15%"><spring:message code='emp.detail.accouting'/></td>
                                                <td style="width: 35%">${empDTO.accountingYn}</td>
                                                <td style="width: 15%"></td>
                                                <td style="width: 35%"></td>
                                            </tr>
                                            <tr>
                                                <td colspan="4">
                                                    <button class="btn btn-warning" type="button" onclick="editP('${empDTO.empCd}')">
                                                        <spring:message code='emp.list.edit' />
                                                    </button>
                                                    <button class=" btn btn-danger" type="button" onclick="deleteP('${empDTO.empCd}')">
                                                        <spring:message code='emp.list.delete' />
                                                    </button>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <!-- script -->
        <script>
            $(document).ready(function () {
                $("select[name=account-select]").change(function () {
                    openAccount(event, $(this).val());
                })
                openAccount(null, $("#account-select").val());

                if ('${title}' != '') {
                    if ('${title}' == 'success') {
                        //toastr['${title}']('${message}');
                        toastr.success('', "<spring:message code='notification.index.success' />", { timeOut: 1000 });
                    } else {
                        toastr.error('', "<spring:message code='notification.index.error' />", { timeOut: 1000 });
                    }
                }
            });

            function back() {
                location.href = "/emp/list.hs?<%=backSearch%>";
            }

            function deleteP(id) {
                if(confirm('<spring:message code='confirm.delete' />')){
                    $.ajax({
                    type: "POST",
                    url: "/emp/deleteEmp.hs",
                    data: {
                        'empCd': id,
                    },
                    success: function (response) {
                        location.href = "/emp/list.hs?title=success&message=Delete sucess&<%=backSearch%>";
                    },
                    error: function (error) {
                        toastr['error']('Delete error !');
                    }
                })
                }
                
            }
            function createSubAccount(){
                location.href = "/emp/addSubEmp.hs?id=${EmpCdMain}&<%=backSearch%>"
            }
            function editP(id) {
                location.href = "/emp/editEmp.hs?empCd=" + id +"&<%=backSearch%>";
            }
            function openAccount(evt, cityName){
                var i, tabcontent, tablinks;
                tabcontent = document.getElementsByClassName("tabcontent");
                for (i = 0; i < tabcontent.length; i++) {
                    tabcontent[i].style.display = "none";
                }
                tablinks = document.getElementsByClassName("tablinks");
                for (i = 0; i < tablinks.length; i++) {
                    tablinks[i].className = tablinks[i].className.replace(" active", "");
                }
                document.getElementById(cityName).style.display = "block";
            };
            
        </script>
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
            ::placeholder {
                color: red !important;
            }
            td {
                vertical-align: inherit !important;
            }
        </style>
        <div id="content" class="content">
            <h3 class="page-title">
                <spring:message code='emp.list.smalltitle' /> - <spring:message code='emp.list.add' />
                <br>
                <small>
                    <spring:message code='detail.add' />
                </small>
            </h3>
            <!-- html -->
            <div class="row">
                <div class="col-xl-12 ui-sortable">
                    <div class="panel panel-inverse">
                        <div class="panel-heading ui-sortable-handle">
                            <h4 class="panel-title">
                                <spring:message code='emp.list.information' />
                            </h4>
                        </div>
                        <div class="panel-body">
                            <form id="myform" class="form-inline" action="">
                                <table class="table mb-0">
                                    <tbody>
                                        <tr>
                                            <td style="background: #f0f4f6; width: 90px;"><spring:message code='emp.list.empid' /></td>
                                            <td>${empDTO.empId}</td>
                                            <td style="background: #f0f4f6; width: 90px;"><spring:message code='emp.list.password' /></td>
                                            <td>******</td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.department' /></td>
                                            <td>
                                                <select id="dept" name="dept" class="custom-select custom-select-sm form-control form-control-sm mr-1 w-100">
                                                    <option><spring:message code='dateboard.notice.company.luachon' /></option>
                                                    <c:forEach items="${lstDept}" var="lstL">
                                                        <option value="${lstL.deptCd}">${lstL.deptName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.position' /></td>
                                            <td>
                                                <select id="position_cd" name="position_cd" class="custom-select custom-select-sm form-control form-control-sm mr-1 w-100">
                                                    <option><spring:message code='dateboard.notice.company.luachon' /></option>
                                                    <c:forEach items="${lstPo}" var="lstL">
                                                        <option value="${lstL.positionCd}">${lstL.positionName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.empname' /></td>
                                            <td><input id="name" name="name" class="form-control form-control-sm mr-1" type="text" value="${empDTO.empName}"></td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.profile.status' /></td>
                                            <td>
                                                <select name="status" id="status" class="custom-select custom-select-sm form-control form-control-sm mr-1">
                                                    <c:forEach items="${typeCodeLists}" var="typeCodeList">
                                                        <option value="${typeCodeList.CCodeValue}">${typeCodeList.CCodeName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.admin' /></td>
                                            <td><input class="form-check-input ms-1" type="checkbox" id="Admin" name="Admin"></td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.type' /></td>
                                            <td>
                                                <select name="gubun" id="gubun" class="custom-select custom-select-sm form-control form-control-sm mr-1 w-100">
                                                    <c:forEach items="${typeCodeListWorkType}" var="typeCodeList">
                                                        <option value="${typeCodeList.CCodeValue}">${typeCodeList.CCodeName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.profile.translation' /></td>
                                            <td><input class="form-check-input ms-1" type="checkbox" id="Translation" name="Translation"></td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.enterdate' /></td>
                                            <td><input readonly id="enterDate" name="enterDate" class="form-control form-control-sm mr-1" type="text"></td>
                                        </tr>

                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.detail.accouting' /></td>
                                            <td><input class="form-check-input ms-1" type="checkbox" id="accountingYn" name="accountingYn" ></td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.idnumber' /></td>
                                            <td>${empDTO.idNum}</td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.detailaddress' /></td>
                                            <td>${empDTO.addrDetail}</td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.zipcode' /></td>
                                            <td>${empDTO.zipCd}</td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.tel' /></td>
                                            <td>${empDTO.tel}</td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.phone' /></td>
                                            <td>${empDTO.phone}</td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.birthday' /></td>
                                            <td>${empDTO.birthDay}</td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.profile.mail' /></td>
                                            <td>${empDTO.mail}</td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.profile.zalo' /></td>
                                            <td>${empDTO.zaloId}</td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.address' /></td>
                                            <td>${empDTO.addr}</td>
                                        </tr>
                                        
                                        <tr>
                                            <td colspan="4">
                                                <button type="button" class="btn btn-success" onclick="addp()"><spring:message code='emp.list.add' /></button>
                                                <button type="button" class="btn btn-primary" onclick="back()"><spring:message code='work.addtran.back' /></button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <!-- script -->
        <script>
            $(document).ready(function () {
                var BirthDay = $("#BirthDay").datepicker({ dateFormat: "yy-mm-dd",changeYear: true,yearRange: "-60:+60"});
                var enterDate = $("#enterDate").datepicker({ dateFormat: "yy-mm-dd",changeYear: true,yearRange: "-60:+60" });
                if ('${title}' != '') {
                    if ('${title}' == 'success') {
                        //toastr['${title}']('${message}');
                        toastr.success('', "<spring:message code='notification.index.success' />", { timeOut: 10000 });
                    } else {
                        toastr.error('', "<spring:message code='notification.index.error' />", { timeOut: 10000 });
                    }
                }

                $("#BirthDay").on("change",function(){
                    document.forms["myform"]["BirthDay"].focus();
                });
                $("#enterDate").on("change",function(){
                    document.forms["myform"]["enterDate"].focus();
                });

                $('#myform').validate({
                    rules: {
                        dept: {
                            required: true,
                        },
                        position_cd: {
                            required: true,
                        },
                        name: {
                            required: true,
                            minlength: 3,
                            maxlength: 50,
                        },
                        enterDate: {
                            required: true,
                        }
                    },

                    messages: {
                        dept: {
                            required: "Please input dept ",
                        },
                        position_cd: {
                            required: "Please input position ",
                        },
                        name: {
                            required: "Please input name ",
                            minlength: "Min Length is 3 character",
                            maxlength: "Max Length is 50 character",
                        },
                        enterDate: {
                            required: "Please input enterDate ",
                        }
                    },
                });
            });

            function back() {
                location.href = "/emp/profile.hs?id=${parentEmpId}&<%=backSearch%>";
            }

            function addp() {
                var isValid = $('#myform').valid();
                var gubun = '0';
                var status = '0';
                var tran = "N";
                var adm = "N";
                var acco = "N";

                if (isValid == true) {
                    console.log("DXD 1")
                    on();
                    if ($('#Translation').is(":checked")) {
                        tran = 'Y';
                    }
                    if ($('#Admin').is(":checked")) {
                        adm = 'Y';
                    }
                    if($('#accountingYn').is(":checked")){
                        acco = 'Y';
                    }

                    var settings = {
                        "url": "/emp/addsubemp",
                        "method": "POST",
                        "headers": {
                            "Content-Type": "application/json"
                        },
                        "data": JSON.stringify({
                            "empId": '${empDTO.empId}',
                            "empCd": '${empDTO.empCd}',
                            "deptCd": $("#dept").val(),
                            "positionCd": $("#position_cd").val(),
                            "empName": $("#name").val(),
                            "enterDate": $("#enterDate").val(),
                            "translationYn": tran,
                            "adminYn": adm,
                            "gubun": $("#gubun").val(),
                            "status": $("#status").val(),
                            "translationYn": tran,
                        }),
                    };
                    $.ajax(settings).done(function (response) {
                        location.href = "/emp/profile.hs?id="+'${parentEmpId}'+"&title=success&message=Update sucess&<%=backSearch%>";
                        off();
                    });
                } else {
                    off();
                }
            }

        </script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
        <% String levelDeptGet=request.getParameter("levelDept"); if (levelDeptGet==null || levelDeptGet=="null" ) {
            levelDeptGet="1" ; } String optionSearchGet=request.getParameter("optionSearch"); if (optionSearchGet==null
            || optionSearchGet=="null" ) { optionSearchGet="" ; } String
            inputSearchGet=request.getParameter("inputSearch"); if (inputSearchGet==null || inputSearchGet=="null" ) {
            inputSearchGet="" ; } String displayStartPra=request.getParameter("displayStartPra"); if
            (displayStartPra==null || displayStartPra=="null" ) { displayStartPra="0" ; } String backSearch="levelDept="
            +levelDeptGet+"&optionSearch="+optionSearchGet+" &inputSearch="+inputSearchGet+" &displayStartPra="+displayStartPra;
%>
<style>
    td{
        vertical-align: inherit !important;
    }
    .tab {
        overflow: hidden;
        border: 1px solid #ccc;
        background-color: #f1f1f1;
        width: 300px;
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
</style>
        <div id=" content" class="content">
            <h3 class="page-title">
                <spring:message code='emp.list.smalltitle' /> - <spring:message code='emp.list.edit' />
                <br>
                <small><spring:message code='detail.edit' /></small>
            </h3>
            <!-- html -->
            <div class="row">
                <div class="col-xl-12 ui-sortable">
                    <div class="panel panel-inverse" style="">
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
                                            <td style="background: #f0f4f6;width: 90px;"><spring:message code='emp.list.empid' /></td>
                                            <td>${empDTO.empId}</td>
                                            <td style="background: #f0f4f6;width: 90px;"><spring:message code='emp.list.password' /></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${empDTO.empParentCd == null}">
                                                        <input id="password" name="password" class="form-control form-control-sm w-100 mr-1" type="password">
                                                    </c:when>
                                                    <c:otherwise>
                                                        ****** <input type="hidden" id="password" name="passwork">
                                                    </c:otherwise>
                                                </c:choose>
                                                
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.department' /></td>
                                            <td>
                                                <select id="dept" name="dept" class="custom-select custom-select-sm form-control form-control-sm w-100 mr-1">
                                                    <option>--Choose--</option>
                                                    <c:forEach items="${lstDept}" var="lstL">
                                                        <option value="${lstL.deptCd}" ${lstL.deptCd==empDTO.deptCd? "selected" : "" }> ${lstL.deptName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.position' /></td>
                                            <td>
                                                <select id="position_cd" name="position_cd" class="custom-select custom-select-sm form-control form-control-sm w-100 mr-1">
                                                    <option>--Choose--</option>
                                                    <c:forEach items="${lstPo}" var="lstL">
                                                        <option value="${lstL.positionCd}" ${lstL.positionCd==empDTO.positionCd ? "selected" : "" }>${lstL.positionName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.empname' /></td>
                                            <td><input id="name" name="name" class="form-control form-control-sm w-100 mr-1" type="text" value="${empDTO.empName}"></td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.address' /></td>
                                            <td><input id="addr" name="addr" class="form-control form-control-sm w-100 mr-1" type="text" value="${empDTO.addr}"></td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.detailaddress' /></td>
                                            <td><textarea id="addrDetail" name="addrDetail" class="form-control" rows="2" cols="80">${empDTO.addrDetail}</textarea></td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.zipcode' /></td>
                                            <td><input id="zipCode" name="zipCode" class="form-control form-control-sm w-100 mr-1" type="text" value="${empDTO.zipCd}"></td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.tel' /></td>
                                            <td><input id="Tel" name="Tel" class="form-control form-control-sm w-100 mr-1" type="text" value="${empDTO.tel}"></td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.phone' /></td>
                                            <td><input id="phone" name="phone" class="form-control form-control-sm w-100 mr-1" type="text" value="${empDTO.phone}"></td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.idnumber' /></td>
                                            <td><input id="id_num" name="id_num" class="form-control form-control-sm w-100 mr-1" type="text" value="${empDTO.idNum}"></td>
                                            <td style="background: #f0f4f6;"></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.birthday' /></td>

                                            <td><input id="BirthDay" name="BirthDay" class="form-control form-control-sm w-100 mr-1" type="text" readonly value="${empDTO.birthDay}"></td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.profile.mail' /></td>
                                            <td><input id="email" name="email" class="form-control form-control-sm w-100 mr-1" type="email" value="${empDTO.mail}"></td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.profile.zalo' /></td>
                                            <td><input id="zalo" name="zalo" class="form-control form-control-sm w-100 mr-1" type="text" value="${empDTO.zaloId}">
                                            </td>

                                            <td style="background: #f0f4f6;"><spring:message code='emp.profile.status' /></td>
                                            <td>
                                                <select name="status" id="status"
                                                    class="custom-select custom-select-sm form-control form-control-sm w-100 mr-1">
                                                    <c:forEach items="${typeCodeLists}" var="typeCodeList">
                                                        <option value="${typeCodeList.CCodeValue}" ${typeCodeList.CCodeValue==empDTO.status? "selected" : "" }>${typeCodeList.CCodeName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.admin' /></td>
                                            <td><input style="margin-left: 5px" class="form-check-input" type="checkbox" id="Admin" name="Admin" ${empDTO.adminYn=="Y" ? "checked" : "" }></td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.type' /></td>
                                            <td>
                                                <select name="gubun" id="gubun"
                                                    class="custom-select custom-select-sm form-control form-control-sm w-100 mr-1">
                                                    <c:forEach items="${typeCodeListWorkType}" var="typeCodeList">
                                                        <option value="${typeCodeList.CCodeValue}" ${typeCodeList.CCodeValue==empDTO.gubun? "selected" : "" }>${typeCodeList.CCodeName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>

                                            <td style="background: #f0f4f6;"><spring:message code='emp.profile.translation' /></td>
                                            <td><input style="margin-left: 5px" class="form-check-input" type="checkbox" id="Translation" name="Translation" ${empDTO.translationYn=="Y" ? "checked" : "" }></td>

                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.enterdate' /></td>
                                            <td><input readonly id="enterDate" name="enterDate" class="form-control form-control-sm w-100 mr-1" type="text" value="${empDTO.enterDate}"></td>
                                        </tr>

                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.detail.accouting' /></td>
                                            <td colspan="3"><input style="margin-left: 5px"class="form-check-input" type="checkbox" id="accountingYn"name="accountingYn" ${empDTO.accountingYn=="Y" ? "checked" : "" }></td>
                                        </tr>

                                        <tr>
                                            <td colspan="4">
                                                <button type="button" class="btn btn-success" onclick="editP()"><spring:message code='emp.list.edit' /></button>
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
                var my_rules = null;
                $(document).ready(function () {
                    var BirthDay = $("#BirthDay").datepicker({ dateFormat: "yy-mm-dd",changeYear: true,yearRange: "-60:+60",  });
                    var enterDate = $("#enterDate").datepicker({ dateFormat: "yy-mm-dd",changeYear: true,yearRange: "-60:+60" });
                    if ('${title}' != '') {
                        if ('${title}' == 'success') {
                            //toastr['${title}']('${message}');
                            toastr.success('', "<spring:message code='notification.index.success' />", { timeOut: 1000 });
                        } else {
                            toastr.error('', "<spring:message code='notification.index.error' />", { timeOut: 1000 });
                        }
                    }

                    my_rules = {
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
                        addr: {
                            required: true,
                            minlength: 3,
                            maxlength: 100,
                        },

                        addrDetail: {
                            required: true,
                            minlength: 3,
                        },
                        zipCode: {
                            required: true,
                            minlength: 3,
                            maxlength: 50,
                        }, Tel: {
                            required: true,
                            minlength: 3,
                            maxlength: 50,
                        },
                        phone: {
                            required: true,
                            minlength: 3,
                            maxlength: 50,

                        },
                        id_num: {
                            required: true,
                            minlength: 3,
                            maxlength: 50,
                        }, BirthDay: {
                            required: true,
                        },
                        email: {
                            required: true,
                            minlength: 3,
                            maxlength: 50,
                        },
                        zalo: {
                            required: true,
                            minlength: 3,
                            maxlength: 50,
                        },
                        enterDate: {
                            required: true,
                        }
                    };


                    $('#myform').validate({
                        rules: my_rules,

                        messages: {
                            password: {
                                required: "Please input password ",
                                minlength: "Min Length is 3 character",
                                maxlength: "Max Length is 50 character",
                            },
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
                            addr: {
                                required: "Please input addr ",
                                minlength: "Min Length is 3 character",
                                maxlength: "Max Length is 100 character",
                            },
                            addrDetail: {
                                required: "Please input addrDetail ",
                                minlength: "Min Length is 3 character",

                            },
                            zipCode: {
                                required: "Please input zipCode ",
                                minlength: "Min Length is 3 character",
                                maxlength: "Max Length is 50 character",
                            },
                            Tel: {
                                required: "Please input Tel ",
                                minlength: "Min Length is 3 character",
                                maxlength: "Max Length is 50 character",
                            }, phone: {
                                required: "Please input phone ",
                                minlength: "Min Length is 3 character",
                                maxlength: "Max Length is 50 character",
                            },
                            id_num: {
                                required: "Please input id number ",
                                minlength: "Min Length is 3 character",
                                maxlength: "Max Length is 50 character",
                            }, BirthDay: {
                                required: "Please input BirthDay ",
                            }, email: {
                                required: "Please input email ",
                                minlength: "Min Length is 3 character",
                                maxlength: "Max Length is 50 character",
                            }, zalo: {
                                required: "Please input zalo ",
                                minlength: "Min Length is 3 character",
                                maxlength: "Max Length is 50 character",
                            }, enterDate: {
                                required: "Please input enterDate ",
                            }
                        },
                    });
                });

                var parentEmpId = '${empDTO.empParentCd}';

                if(parentEmpId == ""){
                    parentEmpId = '${empDTO.empCd}';
                }else{
                    parentEmpId = '${empDTO.empParentCd}'
                }
                function back() {
                    location.href = "/emp/profile.hs?id=" + parentEmpId + "&<%=backSearch%>";
                }

                function editP() {
                    var isValid = $('#myform').valid();
                    var gubun = '0';
                    var status = '0';
                    var tran = "N";
                    var adm = "N";
                    var acco = "N";

                    if (isValid == true) {
                        on();
                        if ($('#Translation').is(":checked")) {
                            tran = 'Y';
                        }
                        if ($('#Admin').is(":checked")) {
                            adm = 'Y';
                        }
                        if ($('#accountingYn').is(":checked")){
                            acco='Y';
                        }

                        var settings = {
                            "url": "/emp/emp/suaemp",
                            "method": "POST",
                            "headers": {
                                "Content-Type": "application/json"
                            },
                            "data": JSON.stringify({
                                "empCd": "${empDTO.empCd}",
                                "empPw": $("#password").val(),
                                "deptCd": $("#dept").val(),
                                "positionCd": $("#position_cd").val(),
                                "empName": $("#name").val(),
                                "addr": $("#addr").val(),
                                "addrDetail": $("#addrDetail").val(),
                                "zipCd": $("#zipCode").val(),
                                "tel": $("#Tel").val(),
                                "phone": $("#phone").val(),
                                "idNum": $("#id_num").val(),
                                "birthDay": $("#BirthDay").val(),
                                //"birthDay" :$("#BirthDay").val(),
                                "mail": $("#email").val(),
                                "zaloId": $("#zalo").val(),
                                "enterDate": $("#enterDate").val(),
                                //"enterDate" : $("#enterDate").val(),
                                "translationYn": tran,
                                "adminYn": adm,
                                "gubun": $("#gubun").val(),
                                "status": $("#status").val(),
                                "accountingYn": acco,
                            }),
                        };
                        $.ajax(settings).done(function (response) {
                            location.href = "/emp/profile.hs?id="+response+"&title=success&message=Update sucess&<%=backSearch%>";
                            off();
                        });
                    } else {
                        off();
                    }
                }

            </script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
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
                                            <td style="background: #f0f4f6; width: 90px;"><spring:message code='emp.list.empid' /></td>
                                            <td style="width: calc(50% - 90px)"><div id="userCheck"><input id="username" name="username" class="form-control w-100 form-control-sm mr-1"></div></td>
                                            <td style="background: #f0f4f6; width: 90px;"><spring:message code='emp.list.password' /></td>
                                            <td style="width: calc(50% - 90px)"><input id="password" name="password" class="form-control w-100 form-control-sm mr-1" type="password"></td>
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
                                                <select id="position_cd" name="position_cd" class="custom-select w-100 custom-select-sm form-control form-control-sm mr-1">
                                                    <option><spring:message code='dateboard.notice.company.luachon' /></option>
                                                    <c:forEach items="${lstPo}" var="lstL">
                                                        <option value="${lstL.positionCd}">${lstL.positionName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.empname' /></td>
                                            <td><input id="name" name="name" class="form-control w-100 form-control-sm mr-1" type="text"></td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.address' /></td>
                                            <td><input id="addr" name="addr" class="form-control w-100 form-control-sm mr-1" type="text"></td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.detailaddress' /></td>
                                            <td><textarea id="addrDetail" name="addrDetail" class="form-control w-100"></textarea></td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.zipcode' /></td>
                                            <td><input id="zipCode" name="zipCode" class="form-control w-100 form-control-sm mr-1" type="text"></td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.tel' /></td>
                                            <td><input id="Tel" name="Tel" class="form-control w-100 form-control-sm mr-1" type="text"></td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.phone' /></td>
                                            <td><input id="phone" name="phone" class="form-control w-100 form-control-sm mr-1" type="text"></td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.idnumber' /></td>
                                            <td colspan="3"><input id="id_num" name="id_num" class="form-control w-50 form-control-sm mr-1" type="text"></td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.birthday' /></td>
                                            <td><input readonly id="BirthDay" name="BirthDay" class="form-control w-100 form-control-sm mr-1" type="text" ></td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.profile.mail' /></td>
                                            <td><input id="email" name="email" class="form-control w-100 form-control-sm mr-1" type="email"></td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.profile.zalo' /></td>
                                            <td><input id="zalo" name="zalo" class="form-control w-100 form-control-sm mr-1" type="text"></td>

                                            <td style="background: #f0f4f6;"><spring:message code='emp.profile.status' /></td>
                                            <td>
                                                <select name="status" id="status" class="custom-select w-100 custom-select-sm form-control form-control-sm mr-1">
                                                    <c:forEach items="${typeCodeLists}" var="typeCodeList">
                                                        <option value="${typeCodeList.CCodeValue}">${typeCodeList.CCodeName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.admin' /></td>
                                            <td><input style="margin-left: 5px" class="form-check-input" type="checkbox" id="Admin" name="Admin" ></td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.type' /></td>
                                            <td>
                                                <select name="gubun" id="gubun" class="custom-select w-100 custom-select-sm form-control form-control-sm mr-1">
                                                    <c:forEach items="${typeCodeListWorkType}" var="typeCodeList">
                                                        <option value="${typeCodeList.CCodeValue}">${typeCodeList.CCodeName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.profile.translation' /></td>
                                            <td><input style="margin-left: 5px" class="form-check-input" type="checkbox" id="Translation" name="Translation" ></td>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.list.enterdate' /></td>
                                            <td><input readonly id="enterDate" name="enterDate" class="form-control w-100 form-control-sm mr-1" type="text"></td>
                                        </tr>

                                        <tr>
                                            <td style="background: #f0f4f6;"><spring:message code='emp.detail.accouting' /></td>
                                            <td colspan="3"><input class="form-check-input" type="checkbox" id="accountingYn" name="accountingYn" ></td>
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
                        toastr.success('', "<spring:message code='notification.index.success' />", { timeOut: 1000 });
                    } else {
                        toastr.error('', "<spring:message code='notification.index.error' />", { timeOut: 1000 });
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
                        username: {
                            required: true,
                            minlength: 3,
                            maxlength: 50,
                        },
                        password: {
                            required: true,
                            minlength: 3,
                            maxlength: 50,
                        },
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
                    },

                    messages: {
                        username: {
                            required: "Please input username ",
                            minlength: "Min Length is 3 character",
                            maxlength: "Max Length is 50 character",
                        },
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

            function back() {
                location.href = "/emp/list.hs";
            }

            function addp() {
                var isValid = $('#myform').valid();
                var gubun = '0';
                var status = '0';
                var tran = "N";
                var adm = "N";
                var acco = "N";
                var userNameSpaceCheck = $("#username").val();
                if (isValid == true) {
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
                    var preUserNameSpaceCheck = userNameSpaceCheck.includes(' ');
                    if (preUserNameSpaceCheck === true) {
                        alert("<spring:message code='emp.list.userCheckWiteSpace'/>");
                        off();
                        var place = '<input id="username" name="username" class="form-control w-100 form-control-sm mr-1" type="text" placeholder="<spring:message code="emp.list.userCheckWiteSpace"/>">';
                        $("#userCheck").html(place);
                        document.forms["myform"]["username"].focus();
                    }
                    else {
                        var settings = {
                            "url": "/emp/emp/addemp",
                            "method": "POST",
                            "headers": {
                                "Content-Type": "application/json"
                            },
                            "data": JSON.stringify({
                                "empId": $("#username").val(),
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
                                "accountingYn": acco,
                                "gubun": $("#gubun").val(),
                                "status": $("#status").val(),
                            }),
                        };
                        $.ajax(settings).done(function (response) {
                            if (response == "a") {
                                location.href = "/emp/list.hs?title=success&message=Add new sucess";
                                off();
                            } if (response == "b") {
                                alert("<spring:message code='emp.list.userCheckExit'/>");
                                off();
                                var place = '<input id="username" name="username" class="form-control form-control-sm mr-1 w-100" type="text" placeholder="<spring:message code="emp.list.userCheckExit"/>">';
                                $("#userCheck").html(place);
                                document.forms["myform"]["username"].focus();
                            }
                        });
                    }

                } else {
                    off();
                }
            }

        </script>
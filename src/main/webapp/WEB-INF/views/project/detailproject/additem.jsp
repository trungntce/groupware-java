<%@ page import="org.springframework.web.context.request.ServletRequestAttributes" %>
<%@ page import="org.springframework.web.context.request.RequestContextHolder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>

<%
    String start_date1 = request.getParameter("start_date1");
    if (start_date1 == null || start_date1 == "null") {
        start_date1 = "";
    }

    String end_date1 = request.getParameter("end_date1");
    if (end_date1 == null || end_date1 == "null") {
        end_date1 = "";
    }

    String levelDeptGet1 = request.getParameter("levelDept1");
    if (levelDeptGet1 == null || levelDeptGet1 == "null") {
        levelDeptGet1 = "1";
    }
    request.setAttribute("levelDeptGet1", levelDeptGet1);

    String translationStatusGet1 = request.getParameter("translationStatus1");
    if (translationStatusGet1 == null || translationStatusGet1 == "null") {
        translationStatusGet1 = "";
    }
    request.setAttribute("translationStatusGet1", translationStatusGet1);

    String approval1 = request.getParameter("approval1");
    if (approval1 == null || approval1 == "null") {
        approval1 = "";
    }
    request.setAttribute("approval1", approval1);

    String optionSearchGet1 = request.getParameter("optionSearch1");
    if (optionSearchGet1 == null || optionSearchGet1 == "null") {
        optionSearchGet1 = "";
    }
    request.setAttribute("optionSearchGet1", optionSearchGet1);

    String inputSearchGet1 = request.getParameter("inputSearch1");
    if (inputSearchGet1 == null || inputSearchGet1 == "null") {
        inputSearchGet1 = "";
    }

    String displayStartPra1 = request.getParameter("displayStartPra1");
    if (displayStartPra1 == null || displayStartPra1 == "null") {
        displayStartPra1 = "0";
    }


    //##########################################################################################################################

    String start_date = request.getParameter("start_date");
    if (start_date == null || start_date == "null") {
        start_date = "";
    }
    String end_date = request.getParameter("end_date");
    if (end_date == null || end_date == "null") {
        end_date = "";
    }
    String levelDeptGet = request.getParameter("levelDept");
    if (levelDeptGet == null || levelDeptGet == "null") {
        levelDeptGet = "1";
    }
    request.setAttribute("levelDeptGet", levelDeptGet);
    String translationStatusGet = request.getParameter("translationStatus");
    if (translationStatusGet == null || translationStatusGet == "null") {
        translationStatusGet = "";
    }
    request.setAttribute("translationStatusGet", translationStatusGet);
    String dayProjectStatus = request.getParameter("dayProjectStatus");
    if (dayProjectStatus == null || dayProjectStatus == "null") {
        dayProjectStatus = "";
    }
    request.setAttribute("dayProjectStatus", dayProjectStatus);
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
    String backSearch="start_date="+start_date+"&end_date="+end_date+"&levelDept="+levelDeptGet+"&translationStatus="+translationStatusGet+"&dayProjectStatus="+dayProjectStatus+"&optionSearch="+optionSearchGet+"&inputSearch="+inputSearchGet+"&displayStartPra="+displayStartPra+"&start_date1="+start_date1+"&end_date1="+end_date1+"&levelDept1="+levelDeptGet1+"&translationStatus1="+translationStatusGet1+"&approval1="+approval1+"&optionSearch1="+optionSearchGet1+"&inputSearch1="+inputSearchGet1+"&displayStartPra1="+displayStartPra1;

%>
<style>
    ::placeholder {
        color: red !important;
    }
    .currency:after {
        content: ' VND';
    }
    /*@media (max-width: 1333px){*/
    /*    .display-none{*/
    /*        display: none;*/
    /*    }*/
    /*}*/

</style>
<div id="content" class="content">
    <h3 class="page-title">
        <spring:message code='dayprojectitem.detailadd.index' />
        <br>
        <small>
            <spring:message code='detail.add' />
        </small>
    </h3>
    <!-- html -->
    <%
        String userAgent =request.getHeader("User-Agent").toUpperCase();
        if(userAgent.indexOf("MOBILE") > -1) {
    %>
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
                                <td style="background: #f0f4f6;width: 30%"><spring:message code='dayprojectitem.detailadd.projectname' /></td>
                                <td>${lstInfoProjectByID.title}</td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dayprojectitem.detailadd.namecategory' /></td>
                                <td>${dayProjectDTO.title}</td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dayprojectitem.detailadd.remain' /></td>
                                <td class="currency">${lstInfoProjectByID.remainAmount}</td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dayprojectitem.detailadd.spentstatus' /></td>
                                <td>
                                    <select id="spentType" name="spentType" class="form-control" style="width: 80%;">
                                        <option><spring:message code='search.selectoption.first'/></option>
                                        <option value="spent"><spring:message code='dayprojectitem.detailadd.spent' /></option>
                                        <option value="refund"><spring:message code='dayprojectitem.detailadd.refund' /></option>
                                    </select>
                                </td>
                            </tr>

                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dayprojectitem.detailadd.productname' /></td>
                                <td><textarea id="product_name" name="product_name" maxlength="1000" class="form-control" rows="2" cols="80"></textarea></td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"> <spring:message code='dayprojectitem.detailadd.price' /></td>
                                <td><input id="price" name="price" onchange="autoFillAmount()" class="form-control form-control-sm mr-1" type="number"></td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dayprojectitem.detailadd.ea' /></td>
                                <td><input id="ea" name="ea" onchange="autoFillAmount()" class="form-control form-control-sm mr-1" type="number"></td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"> <spring:message code='dayprojectitem.detailadd.amount' /></td>
                                <td><input  id="amount" name="amount" class="form-control form-control-sm mr-1" type="number"></td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dayprojectitem.detailadd.picture' /></td>
                                <td>
                                    <img  src="/resources/Upload/images/no-image.jpg" id="previewImg" height="150px" width="150px"><br><br>
                                    <input  id="img_path" name="img_path" onchange="readURL(this);" class="form-control" type="file" accept="image/*">
                                    <br>
                                    <p><spring:message code='dayprojectitem.detailadd.warning' /></p>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="100">
                                    <button type="button" class="btn btn-success" onclick="addP()"><spring:message code='emp.list.add' /></button>
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
    <%
    } else {
    %>
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
                                <td style="background: #f0f4f6;width: 15%"><spring:message code='dayprojectitem.detailadd.projectname' /></td>
                                <td style="width: 35%">${lstInfoProjectByID.title}</td>
                                <td style="background: #f0f4f6;width: 15%"><spring:message code='dayprojectitem.detailadd.namecategory' /></td>
                                <td style="width: 35%">${dayProjectDTO.title}</td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dayprojectitem.detailadd.spentstatus' /></td>
                                <td>
                                    <select id="spentType" name="spentType" class="form-control" style="width: 80%;">
                                        <option><spring:message code='search.selectoption.first'/></option>
                                        <option value="spent"><spring:message code='dayprojectitem.detailadd.spent' /></option>
                                        <option value="refund"><spring:message code='dayprojectitem.detailadd.refund' /></option>
                                    </select>
                                </td>
                                <td style="background: #f0f4f6;"><spring:message code='emp.list.department' /></td>
                                <td class="display-none">${lstInfoProjectByID.deptName}</td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='emp.list.position' /></td>
                                <td>${lstInfoProjectByID.positionName}</td>
                                <td style="background: #f0f4f6;"><spring:message code='emp.list.empname' /></td>
                                <td>${lstInfoProjectByID.empName}</td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dayprojectitem.detailadd.createdate' /></td>
                                <td>${lstInfoProjectByID.regDt}</td>
                                <td style="background: #f0f4f6;"><spring:message code='dayprojectitem.detailadd.remain' /></td>
                                <td class="currency">${lstInfoProjectByID.remainAmount}</td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dayprojectitem.detailadd.productname' /></td>
                                <td><textarea id="product_name" name="product_name" maxlength="1000" class="form-control" rows="2" cols="80"></textarea></td>
                                <td style="background: #f0f4f6;"> <spring:message code='dayprojectitem.detailadd.price' /></td>
                                <td><input id="price" name="price" onchange="autoFillAmount()" class="form-control form-control-sm mr-1" type="number"></td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dayprojectitem.detailadd.ea' /></td>
                                <td><input id="ea" name="ea" onchange="autoFillAmount()" class="form-control form-control-sm mr-1" type="number"></td>
                                <td style="background: #f0f4f6;"> <spring:message code='dayprojectitem.detailadd.amount' /></td>
                                <td><input id="amount" name="amount" class="form-control form-control-sm mr-1" type="number"></td>
                            </tr>
                            <tr>
                                <td style="background: #f0f4f6;"><spring:message code='dayprojectitem.detailadd.picture' /></td>
                                <td>
                                    <img  src="/resources/Upload/images/no-image.jpg" id="previewImg" height="150px" width="150px"><br><br>
                                    <input  id="img_path" name="img_path" onchange="readURL(this);" class="form-control" type="file" accept="image/*">
                                    <br><br>
                                    <p><spring:message code='dayprojectitem.detailadd.warning' /></p>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <button type="button" id="addNew" class="btn btn-success" onclick="addP()"><spring:message code='emp.list.add' /></button>
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
    <%
        }
    %>

</div>
<!-- script -->
<script>
    $(document).ready(function () {
        $('#myform').validate({
            rules: {
                product_name: {
                    required: true,
                    minlength: 3,
                    maxlength: 1000,
                },
                price: {
                    required: true,
                },
                ea: {
                    required: true,
                },
                amount: {
                    required: true,
                },
                spentType: {
                    required: true,
                },
                img_path: {
                    required: true,
                }
            },

            messages: {
                product_name: {
                    required: "Please input product name ",
                    minlength: "Min Length is 3 character",
                    maxlength: "Max Length is 1000 character",
                },
                price: {
                    required: "Please input price ",
                },
                ea: {
                    required: "Please input quantity",
                },
                amount: {
                    required: "Please input amount ",
                },
                spentType: {
                    required: "Please input spent type ",
                },
                img_path: {
                    required: "Please upload images ",
                }

            },

        });

        $( "#img_path" ).change(function() {
            $("#addNew").focus();
        });



    });

    function back() {
         location.href = "/project/project/detail.hs?pjId=${pjId}&dpId=${dpId}&<%=backSearch%>";
    }
    function addP() {
        var isValid = $('#myform').valid();
        var eaValid = $("#ea").val();
        var priceValid =  $("#price").val();
        var amountValid = $("#amount").val();

        if (isValid == true && eaValid >= 0 && priceValid >= 0 && amountValid >= 0) {
            on();
            var amount=$("#amount").val();
            if($("#spentType").val()=="refund"){
                amount=-amount;
            }
            console.log(amount);
                if (window.FormData !== undefined) {
                    var fileUpload = $("#img_path").get(0);
                    var files = fileUpload.files;
                    var formData = new FormData();
                    formData.append("file", files[0]);
                    formData.append("productName", $("#product_name").val());
                    formData.append("price", $("#price").val());
                    formData.append("ea", $("#ea").val());
                    formData.append("amount", amount);
                    formData.append("dpId", '${dpId}');
                    formData.append("pjId", '${pjId}');
                    formData.append("spentType", $("#spentType").val());

                    $.ajax({
                        type: "POST",
                        url: "/API/dayProjectItem/addDayProjectItem",
                        data: formData,
                        contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
                        processData: false, // NEEDED, DON'T OMIT THIS
                        success: function (response) {
                            location.href = "/project/project/detail.hs?pjId=${pjId}&dpId=${dpId}&title=success&message=Add new sucess&<%=backSearch%>";
                            off();
                        },
                        error: function (error) {
                        }

                    });

                }


            off();
        } else if (eaValid < 0 || priceValid < 0 || amountValid < 0){
            alert("<spring:message code='project.value.amount' />");
            off();
        }
        else {
            off();
        }
    }

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#previewImg').attr('src', e.target.result);
            };

            reader.readAsDataURL(input.files[0]);
        }
    }

    function autoFillAmount(){
        if($("#price").val().trim() !="" && $("#ea").val().trim() !=""){
            var amount=parseInt($("#price").val().trim())*parseInt($("#ea").val().trim());
            $("#amount").val(amount);
            $("#amount").focus();
        }
    }

    let x = document.querySelectorAll(".currency");
    for (let i = 0, len = x.length; i < len; i++) {
        let num = Number(x[i].innerHTML)
            .toLocaleString('en');
        x[i].innerHTML = num;
        x[i].classList.add("currSign");
    }
</script>
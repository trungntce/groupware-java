<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>

<div id="content" class="content">
    <h3 class="page-title">
        <spring:message code='emp.mypage.title' />
        <br>
        <small>
        <spring:message code='emp.mypage.small.title' />
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
                                    <td style="background: #f0f4f6;width: 15%">
                                        <spring:message code='emp.list.old.password' />
                                    </td>

                                    <td style="width: 35%"><input style="width: 40%;" id="old_emp_pw"
                                            name="old_emp_pw" class="form-control form-control-sm mr-1"
                                            type="password" value=""></td>
                                </tr>
                                <tr>
                                    <td style="background: #f0f4f6;width: 15%">
                                        <spring:message code='emp.list.new.password' />
                                    </td>

                                    <td style="width: 35%"><input style="width: 40%;" id="new_emp_pw"
                                                                  name="new_emp_pw" class="form-control form-control-sm mr-1"
                                                                  type="password" value=""></td>
                                </tr>
                                <tr>
                                    <td style="background: #f0f4f6;width: 15%">
                                        <spring:message code='emp.list.new.check.password' />
                                    </td>

                                    <td style="width: 35%"><input style="width: 40%;" id="new_emp_pw_check"
                                                                  name="new_emp_pw_check" class="form-control form-control-sm mr-1"
                                                                  type="password" value=""></td>
                                </tr>
                                <tr>
                                    <td style="width: 15%">
                                        <button type="button" class="btn btn-success">
                                            <spring:message code='emp.list.edit' />
                                        </button>
                                    </td>
                                    <td style="width: 35%"></td>
                                    <td style="width: 15%"></td>
                                    <td style="width: 35%"></td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function () {
    $(".btn-success").click(function(){
        var old_emp_pw = $("#old_emp_pw").val();
        var new_emp_pw = $("#new_emp_pw").val();
        var new_emp_pw_check = $("#new_emp_pw_check").val();

        if(old_emp_pw == ""){
            alert('<spring:message code='emp.detail.password.old.message' />');
            $("#old_emp_pw").focus();
            return false;
        }

        if(new_emp_pw == ""){
            alert('<spring:message code='emp.detail.password.new.message' />');
            $("#new_emp_pw").focus();
            return false;
        }

        if(new_emp_pw_check == ""){
            alert('<spring:message code='emp.detail.password.new.check.message' />');
            $("#new_emp_pw_check").focus();
            return false;
        }

        if(new_emp_pw != new_emp_pw_check){
            alert('<spring:message code='emp.detail.password.not.collect.message' />');
            return false;
        }

        $.post("/emp/mypage.hs", {
            'oldEmpPw' : old_emp_pw,
            'newEmpPw' : new_emp_pw
        }, function( data ) {
            if(data != "F"){
                alert('<spring:message code='emp.detail.password.change.success.message' />');
                location.reload();
            } else {
                alert('<spring:message code='emp.detail.password.change.fail.message' />!');
            }
        }, 'text');
    });
});
</script>
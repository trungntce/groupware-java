<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>


<div id="content" class="content">
    <h3 class="page-title">
        <spring:message code='author.list.smalltitle'/> - <spring:message code='author.list.bigtitle'/>
        <br>
        <small>
            <spring:message code='detail.message' />
        </small>
    </h3>
    <div style="text-align: right; padding-bottom: 10px;">
        <button emp-cd=${emcd} type="button" class="btn btn-purple do_update_btn">
            <spring:message code='author.list.update'/>
        </button>
        <button type="button" class="btn btn-primary" onclick="back()">
            <spring:message code='work.detail.back' />
        </button>
    </div>
    <div class="panel panel-inverse">
        <div class="panel-heading">
            <h4 class="panel-title"><spring:message code='emp.list.information'/></h4>
        </div>
        <div class="panel-body">
            <div class="panel-body">
                <table class="table mb-0">
                    <thead>
                        <tr>
                            <th style="text-align: center; vertical-align: inherit;"><spring:message code='author.list.fullname'/></th>
                            <th style="text-align: center; vertical-align: inherit;"><spring:message code='author.list.menuid'/></th>
                            <th style="text-align: center; vertical-align: inherit;"><spring:message code='author.list.menuname'/></th>
                            <th style="text-align: center; vertical-align: inherit;"><spring:message code='author.list.useyn'/></th>
                            <th style="text-align: center; vertical-align: inherit;"><spring:message code='author.list.custom'/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${menuList}" var="list" >
                            <tr class="sel1 board_view" >
                                <td emp-cd="${list.empCd}" style="text-align: center; vertical-align: inherit;">${list.empName}</td>
                                <td style="text-align: center; vertical-align: inherit;">${list.menuCd}</td>
                                <td style="text-align: center; vertical-align: inherit;">${list.menuName}</td>
                                <td style="text-align: center; vertical-align: inherit;">${list.useYn}</td>
                                <td style="text-align: center; vertical-align: inherit;">

                                    <c:if test = "${list.useYn == 'Y'}">
                                        <form>
                                            <input type="checkbox" name="addID" checked value=${list.menuCd}>
                                        </form>
                                    </c:if>
                                    <c:if test = "${list.useYn == 'N'}">
                                        <form>
                                            <input type="checkbox" name="addID" value=${list.menuCd}>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        $(".do_update_btn").click(function(){
            let checkbox = document.getElementsByName('addID');
            let str = "";
            let empCd = $(this).attr('emp-cd');
            for(var i = 0; i < checkbox.length; i++){
                if (checkbox[i].checked === true){
                    str += checkbox[i].value + ',';
                }
            }
            str = str.substring(0, str.length - 1);
            submitCustomUpdate(empCd, str);
        });
    });

    function submitCustomUpdate(empCd, str) {
        var form = {
            "empCd": empCd,
            "menuStr": str
        }
        $.ajax({
            type: 'POST',
            url: "${_ctx}/management/author/customUpdate.hs",
            cache: false,
            data: JSON.stringify(form),
            contentType: "application/json; charset=utf-8",

            success: function (resp) {
                        alert("OK!");
                        window.location.href = "/management/author/view.hs?empCd="+empCd+"&"+rememberUrl;
                    }
        })
    }

    var preUrl = window.location.search;
    var rememberUrl = preUrl.substring(preUrl.search('&')+1,100);


    function back(){
        location.href = '/management/author/list.hs?' + rememberUrl;
    }

</script>
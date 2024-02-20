<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>

<div id="content" class="content">
    <h3 class="page-title">
        <spring:message code='approval.page.setup.form.title' />
        <a href="/approval/setup/form/add.hs" class="btn btn-success ml-2"><i class="fas fa-plus mr-1"></i> <spring:message code='button.create.title'/></a><br>
        <small><spring:message code='detail.add' /></small>
    </h3>
    
    <div class="row">
        <!-- Tab search -->
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse" style="">
                <div class="panel-heading"><h3 class="panel-title"><spring:message code='search.title' /></h3></div>
                <div class="panel-body">
                    <form id="frmSearch" name="search" class="form-inline">
                        <input type="label" id="approvalStatus" name="approvalStatus" hidden="true">
                        <input type="label" id="dateFlag" name="dateFlag" hidden="true">
                        <table class="table mb-0">
                            <tbody>
                            <tbody>
                                <tr>
                                    <td style="background: #f0f4f6; width: 125px;font-weight: bold">
                                        <spring:message code='search.title.regisdate'/>
                                    </td>
                                    <td>
                                        <input readonly id="startDate" name="startDate" class="form-control form-control-sm mr-1 bg-white" type="text">~
                                        <input readonly id="endDate" name="endDate" class="form-control form-control-sm mr-1 bg-white" type="text">
                                        <button type="button" data-val="today" class="bnt-time-order btn btn-default form-control-sm mr-1 mt-1"><spring:message code='search.button.daychoose.inday'/></button>
                                        <button type="button" data-val="1week" class="bnt-time-order btn btn-default form-control-sm mr-1 mt-1"><spring:message code='search.button.daychoose.inweek'/></button>
                                        <button type="button" data-val="1month" class="bnt-time-order btn btn-default form-control-sm mr-1 mt-1"><spring:message code='search.button.daychoose.inmonth'/></button>
                                        <button type="button" data-val="3month" class="bnt-time-order btn btn-default form-control-sm mr-1 mt-1"><spring:message code='search.button.daychoose.in3month'/></button>
                                        <button type="button" data-val="6month" class="bnt-time-order btn btn-default form-control-sm mr-1 mt-1"><spring:message code='search.button.daychoose.in6month'/></button>
                                        <button type="button" data-val="1year" class="bnt-time-order btn btn-default form-control-sm mr-1 mt-1"><spring:message code='search.button.daychoose.in1year'/></button>
                                        <button type="button" data-val="all" class="bnt-time-order btn btn-default form-control-sm mr-1 mt-1"><spring:message code='search.button.daychoose.inall'/></button>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="background: #f0f4f6; font-weight: bold"><spring:message code='dateboard.notice.company.timkiem'/></td>
                                    <td>
                                        <select id="keywordType" name="keywordType" class="custom-select custom-select-sm form-control form-control-sm m-2">
                                            <option value="title" ${searchDTO.keywordType == 'title' ? "selected" : "" }><spring:message code='work.list.title'/></option>
                                        </select>
                                        <input id="keyword" name="keyword" class="form-control form-control-sm m-2" type="text" value="${searchDTO.keyword}"/>
                                        <button type="submit" class="btn btn-sm btn-primary m-2"><spring:message code='dateboard.notice.company.timkiem'/></button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>
        <!-- * Tab search -->

        <!-- Tab list -->
        <div class="col-xl-12 ui-sortable">
            <div class="panel panel-inverse">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code='common.list.title' /></h3>
                </div>
                <div class="panel-body">
                    <table class="table table-striped table-bordered w-100 text-center">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th><spring:message code='approval.field.name.title'/></th>
                                <th><spring:message code='approval.field.emp.id.title'/></th>
                                <th><spring:message code='approval.field.created.date.title'/></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${list.size() > 0}">
                            <c:forEach items="${list}" var="item" varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td>${item.formName}</td>
                                <td>${item.createId}</td>
                                <!-- <td>${item.useYn}</td> -->
                                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.createDate}" /></td>
                                <td>
                                    <a href="/approval/setup/form/edit.hs?formId=${item.formId}" class="btn btn-primary"><i class="fas fa-edit"></i></a>
                                </td>
                            </tr>
                            </c:forEach>
                            </c:if>
                            <c:if test="${list.size() == 0}">
                                <tr>
                                    <td colspan="5"><spring:message code="main.index.datastatus"/></td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>

                    <div class="mt-10px w-100">
                      <%-- 추후 필요에 따라 사용 --%>
                      <pg:paging page="${searchDTO}"/>
                    </div>
                </div>
            </div>
        </div>
        <!-- * Tab list -->
    </div>
</div>
<script type="text/javascript">
    initDate();
    function initDate() {
        $("#startDate").datepicker({autoclose: true, dateFormat: "yy-mm-dd"});
        $("#endDate").datepicker({autoclose: true, dateFormat: "yy-mm-dd"});
        $("#startDate").val("${searchDTO.startDate}");
        $("#endDate").val("${searchDTO.endDate}");
        $(".bnt-time-order").each(function(){
            if ($(this).attr("data-val") == "${searchDTO.dateFlag}"){
                $(this).addClass("active");
                $("#dateFlag").val($(this).attr("data-val"));
            }
        });
    };
    $('.bnt-time-order').click(function(){
        let date_flag = $(this).attr('data-val');
        $('#dateFlag').val(date_flag);
        let today = new Date();
        let startYear = "";
        let startMonth = "";
        let startDay = "";
        let startDate = "";
        let endYear = "";
        let endMonth = "";
        let endDay = "";
        let endDate = "";
        endYear = today.getFullYear();
        endMonth = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
        endDay = String(today.getDate()).padStart(2, '0');
        endDate = endYear + '-' + endMonth + '-' + endDay;
        switch (date_flag) {
            case "today" :
                break;
            case "1week" :
                today.setDate(today.getDate() - 7);
                break;
            case "1month" :
                today.setMonth(today.getMonth() - 1)
                break;
            case "3month" :
                today.setMonth(today.getMonth() - 3)
                break;
            case "6month" :
                today.setMonth(today.getMonth() - 6)
                break;
            case "1year" :
                today.setFullYear(today.getFullYear() - 1);
                break;
            default : break;
        }
        startYear = today.getFullYear();
        startMonth = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
        startDay = String(today.getDate()).padStart(2, '0');
        startDate = startYear + '-' + startMonth + '-' + startDay;
        if(date_flag == "all"){
            $("#startDate").val("");
            $("#endDate").val("");
        } else {
            $("#startDate").val(startDate);
            $("#endDate").val(endDate);
        }
        $('.bnt-time-order').each(function(){
            $(this).removeClass('active');
        });
        $(this).addClass('active');
    });
</script>
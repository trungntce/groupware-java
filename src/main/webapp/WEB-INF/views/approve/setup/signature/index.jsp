<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>

<style>
    .signature-ex{
        overflow-y: scroll;
        max-height: calc(100vh - 210px);
    }
</style>

<div id="content" class="content">
    <h3 class="page-title">
        <spring:message code='approval.page.setup.signature.title' /><br>
        <small><spring:message code='detail.add' /></small>
    </h3>

    <div class="row">
        <div class="col-xl-2 ui-sortable">
            <div class="panel panel-inverse">
                <div class="panel-heading"><h3 class="panel-title"><spring:message code='approval.tab.signature.history.title' /></h3></div>
                <div class="panel-body signature-ex">
                    <form:form modelAttribute="enableForm" method="POST" action="${_ctx}/approval/setup/signature/enable.hs">
                    <ul class="list-group">
                        <c:if test="${list.size() > 0}">
                            <c:forEach items="${list}" var="item" varStatus="loop">
                            <li class="list-group-item position-relative">
                                <form:radiobutton path="approvalSettingsId" value="${item.approvalSettingsId}" cssClass="position-absolute" style="left: 5px; top: 5px;"/>
                                <c:if test="${loop.index != 0}">
                                    <a href="${_ctx}/approval/setup/signature/remove.hs?approvalSettingsId=${item.approvalSettingsId}" class="fas fa-times-circle text-danger h5 position-absolute" style="right: 5px; top: 5px;"></a>
                                </c:if>
                                <img class="w-100" src="${_ctx}/${item.signatureImagePath}">
                            </li>
                            </c:forEach>
                        </c:if>
                    </ul>
                    </form:form>
                </div>
            </div>
        </div>

        <div class="col-xl-8 ui-sortable">
            <div class="panel panel-inverse">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code='approval.tab.signature.pad.title'/></h3>
                </div>
                <div class="panel-body">
                    <div class="wrapper-signature">
                      <canvas id="signature-pad" class="signature-pad border"></canvas>
                    </div>
                    <form id="frm-setting" method="POST">
                        <input type="hidden" name="base64">
                    </form>
                    <div class="mt-2">
                        <button class="btn btn-success" id="btn-save-png"><spring:message code='button.save.title'/></button>
                        <button class="btn btn-warning" id="btn-clear"><spring:message code='button.clean.title'/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var canvas = document.getElementById('signature-pad');

    // Adjust canvas coordinate space taking into account pixel ratio,
    // to make it look crisp on mobile devices.
    // This also causes canvas to be cleared.
    function resizeCanvas() {
        // When zoomed out to less than 100%, for some very strange reason,
        // some browsers report devicePixelRatio as less than 1
        // and only part of the canvas is cleared then.
        var ratio =  Math.max(window.devicePixelRatio || 1, 1);
        canvas.width = canvas.offsetWidth * ratio;
        canvas.height = canvas.offsetHeight * ratio;
        canvas.getContext("2d").scale(ratio, ratio);
    }

    window.onresize = resizeCanvas;
    resizeCanvas();

    var signaturePad = new SignaturePad(canvas, {
      backgroundColor: 'rgb(255, 255, 255)' // necessary for saving image as JPEG; can be removed is only saving as PNG or SVG
    });


    document.getElementById('btn-save-png').addEventListener('click', function () {
      if (signaturePad.isEmpty()) {
        return alert("Please provide a signature first.");
      }
      
      var data = signaturePad.toDataURL('image/png');
      $(`[name="base64"]`).val(data);
      $("#frm-setting").submit();
    });

    document.getElementById('btn-clear').addEventListener('click', function () {
      signaturePad.clear();
    });

    $(`[name="approvalSettingsId"]`).change(function(){
        $(`#enableForm`).submit()
    })
</script>
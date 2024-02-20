<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/layout/common/commonTags.jspf" %>
<script type="text/javascript" src="${_ctx}/resources/js/ckeditor/ckeditor.js"></script>
<link rel="stylesheet" type="text/css" href="${_ctx}/resources/js/treeview/treeview.min.css">
<style>
    .sign-img{
        width: 80px;
        height: 80px;
    }

    .jstree-contextmenu{
        z-index: 99999;
    }

    .file-ico-remove:hover{
        cursor: pointer;
        color: red;
    }
</style>
<form:form modelAttribute="form" id ="frmWrite" cssClass="form-inline">
<div id="content" class="content w-100">
    <h3 class="page-title">
        <spring:message code='common.update.title'/><br>
        <small><spring:message code='detail.add' /></small>
    </h3>

    <div class="row">
        <!-- Tab content approval -->
        <div class="col-xl-8 ui-sortable">
            <div class="panel panel-inverse">
                <div class="panel-heading">
                    <h3 class="panel-title">${form.title}</h3>
                </div>
                <div class="panel-body">
                    <table class="table mb-0">
                        <tr>
                            <td style="background: #f0f4f6;width: 15%"><spring:message code='approval.field.title.txt' /></td>
                            <td style="width: 85%"><form:input path="title" cssClass="form-control form-control-sm mr-1 w-100" type="text"/></td>
                        </tr>
                        <tr>
                            <td style="background: #f0f4f6;width: 15%"><spring:message code='approval.field.carbon.copy.title' /></td>
                            <td class="views-sign" style="width: 85%"></td>
                        </tr>
                        <tr>
                            <td style="background: #f0f4f6;width: 15%"><spring:message code='approval.field.cooperate.title' /></td>
                            <td class="supports-sign" style="width: 85%"></td>
                        </tr>
                        <tr>
                            <td style="background: #f0f4f6;width: 15%"><spring:message code='approval.field.content.title' /></td>
                            <td style="width: 85%"><form:textarea path="contents" cssClass="ckeditor form-control w-100"></form:textarea></td>
                        </tr>
                        <tr>
                            <td style="background: #f0f4f6;width: 15%">
                                <spring:message code='file.attach'/>
                                <input type="file" id="file" name="file" class="d-none">
                                <button type="button" class="btn btn-info btn-sm" id="btn-attach-file"><i class="fas fa-plus-circle"></i></button>
                            </td>
                            <td style="width: 85%">
                                <ul class="list-group list-group-horizontal ml-1" id="list-attach-file">
                                    <c:forEach items="${approvalFiles}" var="item" varStatus="loop">
                                        <li class="list-group-item position-relative float-left">
                                            <i class="fas fa-times position-absolute text-danger file-ico-remove" onclick="removeAttachFile(this)"></i>
                                            <div class="text-center"><i class="fas fa-link text-secondary h3"></i></div>
                                            <input type="hidden" name="idFiles" value="${item.fileId}"/>
                                            <a class="approval-attr-file" href="${_ctx}/upload/${item.filePath}/${item.fileHashName}"><small>${item.fileName}</small></a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <button type="button" class="btn btn-success btn-add" onclick="fnSubmit(9)"><spring:message code='work.add.addnew' /></button>
                                <button type="button" class="btn btn-success btn-draft" onclick="fnSubmit(6)"><spring:message code='button.save.title' /></button>
                                <button type="button" class="btn btn-primary btn-back" onclick="history.back()"><spring:message code='work.add.back' /></button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <form:hidden path="approvalStatus"/>
        </div>
        <!-- * Tab content approval -->

        <!-- Tab select signer -->
        <div class="col-xl-4 ui-sortable">
            <div class="panel panel-inverse">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <spring:message code='approval.tab.sequence.title' />
                    </h3>
                </div>
                <div class="panel-body">
                    <button type="button" data-toggle="modal" data-target="#modal-select-signer" class="btn btn-warning"><i class="fas fa-cogs"></i> <spring:message code='button.setup.title' /></button>
                    <table id="main-sequence-emp" class="table table-bordered mt-2 mb-0">
                        <thead>
                            <tr>
                                <th><spring:message code='approval.field.name.title'/></th>
                                <th><spring:message code='approval.field.dept.title'/></th>
                                <th><spring:message code='approval.field.role.title'/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${empSign.size() > 0}">
                                <c:forEach items="${empSign}" var="lst" varStatus="loop">
                                    <tr>               
                                        <td>${lst.empId}</td>
                                        <td>${lst.deptName}</td>
                                        <td>
                                            ${lst.approvalRoleName}
                                            <input type="hidden" name="empSign" value="${lst.empCd}">
                                            <input type="hidden" name="empSignRole" value="${lst.approvalRole}">
                                        </td>
                                    </tr>
                                </c:forEach>      
                            </c:if>
                            <c:if test="${empSign.size() == 0}">
                                <td class="text-center" colspan="3"><i class="fas fa-inbox"></i> <spring:message code="main.index.datastatus" /></td>                        
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <!-- * Tab select signer -->
    </div>
</div>
</form:form>

<!-- Modal -->
<div class="modal fade" id="modal-select-signer" tabindex="-1" aria-labelledby="modal-select-signer-label" aria-hidden="true">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modal-select-signer-label"><spring:message code='approval.tab.sequence.title' /></h5>
                <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body bg-light">
                <div class="row">
                    <div class="col-xl-6">
                        <div class="panel panel-inverse">
                            <div class="panel-heading"><h3 class="panel-title"><spring:message code='common.list.title' /></h3></div>
                            <div class="panel-body"><div class="tree-emp" id="list-tree"></div></div>
                        </div>
                    </div>
                    <div class="col-xl-6">
                        <div class="panel panel-inverse">
                            <div class="panel-heading">
                                <h3 class="panel-title"><spring:message code='approval.tab.sequence.title' /></h3>
                            </div>
                            <div class="panel-body">
                                <div id="modal-sequence-emp">
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th style="width: 130px;"><spring:message code='approval.field.role.title' /></th>
                                                <th><spring:message code='approval.field.name.title' /></th>
                                                <th style="width: 50px;"><spring:message code='common.remove.title' /></th>
                                                <th style="width: 80px;"></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td class="text-center" colspan="4"><i class="fas fa-inbox"></i> <spring:message code='main.index.datastatus' /></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="initSign()"><spring:message code='button.close.title' /></button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="initMainEmpSequence()"><spring:message code='button.save.title' /></button>
            </div>
        </div>
    </div>
</div>

<script>
    editor = CKEDITOR.replace('contents');
    var empSequence = {
        modal: {},
        main: {}
    }
    var dataEmp = []
    var dataTree = [];
    var dataSequence = {}
    var $tree;

    init()

    function init(){
        loadDataEmp()
        actionView()
    }
    
    function actionView(){
        $("#btn-attach-file").click(function(){
            $('#file').click()
        })

        $('#file').change(function(){
            var file = $(this).prop("files")[0];
            $("#list-attach-file").append(htmlItemFile(file))
            filesApi.uploads(file, xhrCallback, successCallback)
        })
    }
    
    function htmlItemFile(file){
        return `<li class="list-group-item position-relative float-left">
            <i class="fas fa-times position-absolute text-danger file-ico-remove" onclick="removeAttachFile(this)"></i>
            <div class="text-center"><i class="fas fa-link text-secondary h3"></i></div>
            <input type="hidden" name="idFiles" value="0"/>
            <a class="approval-attr-file" href="#"><small>`+file.name+`</small></a>
            <div class="progress" style="height: 2px;">
                <div class="progress-bar" role="progressbar" style="width: 0%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
            </div>
        </li>`
    }

    function xhrCallback(percent){
        $(".progress-bar").css("width", percent+"%")
    }

    function successCallback(fileDto){
        $(".progress").remove()
        $(`[name="idFiles"][value="0"]`).val(fileDto.fileId)
        $(`.approval-attr-file:last`).attr("href", `/upload/`+fileDto.filePath+`/`+fileDto.fileHashName)
    }

    function removeAttachFile(obj){
        obj.parentNode.remove()
    }

    async function loadDataEmp(){
        dataEmp = await emp.selectAll()
        if(dataEmp.length > 0){
            loadTree()
            initSign()
        }
    }

    function loadTree(){
        dataTree.push({id: "tree-D_1", parent: "#", icon: "fas fa-users", text: "Vietko Group", data: "node 0", li_attr: {"status": "value_status", "role": "dept"}});
        for(var i in dataEmp){
            const item = dataEmp[i];
            const idView = "tree-%d1".replace("%d1", item.publicCd)
            const idParent = "tree-%d1".replace("%d1", item.publicParentCd)
            const title = (item.deptName.indexOf("[") != -1) ? item.deptName.substring(0, item.deptName.indexOf("[")) : item.deptName;
            const icon = (item.deptName.indexOf("[") != -1) ? "fas fa-user" : null;
            const role = (item.deptName.indexOf("[") != -1) ? "emp" : "dept";
            dataTree.push({id: idView, parent: idParent, icon: icon, text: title, data: item, li_attr: {"status": "value_status", "role": role}});
        }

        $tree = $("#list-tree").jstree({
            "plugins": ["dnd", "state", "types"],
            "core" : {
                "themes": { "responsive": false },  
                "check_callback" : true,
                "data": dataTree
            },
            "types": {
                "default": { "icon": "fa fa-folder text-warning fa-lg" },
                "file": { "icon": "fa fa-file text-dark fa-lg" }
            },
            "plugins" : ["types","contextmenu"],
            "contextmenu": {
                "items": function ($node) {
                    return getMenuContext($node, this)
                }
            }
        })
    }

    function initSign(){
        for(var i in dataEmp){
            var item = dataEmp[i];
            const empCd = item.publicCd.replace("E_", "")
            if($(`[name="empSign"][value="`+empCd+`"]`).length > 0){
                const index = $(`[name="empSign"]`).index($(`[name="empSign"][value="`+empCd+`"]`))
                item['role_approve'] = $(`[name="empSignRole"]:eq(`+index+`)`).val()
                empSequence.main[item.publicCd] = item
            }
        }
        empSequence.modal = empSequence.main
        initModalEmpSequence()
    }

    function initModalEmpSequence(){
        if(Object.keys(empSequence.modal).length > 0){
            $("#modal-sequence-emp table tbody").html("")
            for(var publicCd in empSequence.modal){
                var data = empSequence.modal[publicCd];
                var htmlItem = `<tr emp-cd="`+publicCd+`">
                    <td>
                        <select class="emp-role" data-emp="`+publicCd+`" onchange="changeRoleApprove(this)">
                        <c:forEach items="${approvalRole}" var="lst" varStatus="loop">
                            <option value="${lst.CCodeValue}">${lst.CCodeName}</option>
                        </c:forEach>
                        </select>
                    </td>
                    <td>`+data.deptName.split("[")[0]+`</td>
                    <td><i class="fas fa-times text-danger" onclick="removeItem(this)"></i></td>
                    <td>
                        <i class="ico-down h4 fas fa-arrow-alt-circle-down" onclick="downPosition(this)" />
                        <i class="ico-up h4 fas fa-arrow-alt-circle-up" onclick="upPosition(this)" />
                    </td>
                </tr>`
                $("#modal-sequence-emp table tbody").append(htmlItem)
                $(`.emp-role[data-emp="`+publicCd+`"]`).val(empSequence.modal[publicCd]['role_approve'])
            }
        }else{
            $("#modal-sequence-emp table tbody").html(`<tr><td class="text-center" colspan="4"><i class="fas fa-inbox"></i><spring:message code='main.index.datastatus' /></td></tr>`)
        }
    }

    function initMainEmpSequence(){
        if(Object.keys(empSequence.modal).length > 0){
            var viewItems = []
            var signItems = []
            var supportItems = []
            $("#main-sequence-emp tbody").html("")

            for(var publicCd in empSequence.modal){
                const item = empSequence.modal[publicCd]
                var role = $("[emp-cd='"+publicCd+"'] .emp-role").find("option:selected").val();
                var roleName = $("[emp-cd='"+publicCd+"'] .emp-role").find("option:selected").html();   
                const itemSign = {
                    empSign: publicCd.replace("E_", ""),
                    empSignRole: role,
                    empSignRoleName: roleName,
                    positionName: item.positionName,
                    name: item.deptName.split("[")[0],
                    dept: item.deptName.split("[")[1].replace("]", "")
                }
                switch(role*1){
                    case 1:
                        signItems.push(itemSign)
                        break
                    case 2:
                        viewItems.push(itemSign)
                        break
                    default:
                        supportItems.push(itemSign)
                        break
                }
            }
            if(supportItems.length > 0){
                $(".supports-sign").html("")
                supportItems.forEach((el, i) => {
                    var htmlItem = `
                        <tr>
                            <td>`+el.name+`</td>
                            <td>`+el.dept+`</td>
                            <td>
                                `+el.empSignRoleName+`
                                <input type="hidden" name="empSign" value="`+el.empSign+`">
                                <input type="hidden" name="empSignRole" value="`+el.empSignRole+`">
                            </td>
                        </tr>`
                    $(".supports-sign").append(`<h4><span class="badge badge-secondary">`+el.name+`</span></h4>`)
                    $("#main-sequence-emp tbody").append(htmlItem)
                })
            }

            if(signItems.length > 0){
                signItems.forEach((el, i) => {
                    var htmlItem = `
                        <tr>
                            <td>`+el.name+`</td>
                            <td>`+el.dept+`</td>
                            <td>
                                `+el.empSignRoleName+`
                                <input type="hidden" name="empSign" value="`+el.empSign+`">
                                <input type="hidden" name="empSignRole" value="`+el.empSignRole+`">
                            </td>
                        </tr>`
                    $("#main-sequence-emp tbody").append(htmlItem)

                })
            }

            if(viewItems.length > 0){
                $(".views-sign").html("")
                viewItems.forEach((el, i) => {
                    var htmlItem = `
                        <tr>
                            <td>`+el.name+`</td>
                            <td>`+el.dept+`</td>
                            <td>
                                `+el.empSignRoleName+`
                                <input type="hidden" name="empSign" value="`+el.empSign+`">
                                <input type="hidden" name="empSignRole" value="`+el.empSignRole+`">
                            </td>
                        </tr>`
                    $(".views-sign").append(`<h4><span class="badge badge-info">`+el.name+`</span></h4>`)
                    $("#main-sequence-emp tbody").append(htmlItem)
                })
            }
            $(".btn-add").removeAttr("disabled")
            $(".btn-draft").removeAttr("disabled")
        }else{
            $("#main-sequence-emp tbody").html(`<tr><td class="text-center" colspan="4"><i class="fas fa-inbox"></i> <spring:message code='main.index.datastatus' /></td></tr>`)
            $(".btn-add").attr("disabled", "disabled")
            $(".btn-draft").attr("disabled", "disabled")
        }
    }

    function removeItem(obj){
        var publicCd = obj.parentNode.parentNode.getAttribute("emp-cd")
        if(empSequence.modal[publicCd] != null){
            delete empSequence.modal[publicCd]
        }
        initModalEmpSequence()
    }
    function fnSubmit(status){
        if($(`[name="empSign"]`).length == 0){
            alert("<spring:message code='common.message.setup.sign.sequence.title'/>")
            return
        }
        $('#approvalStatus').val(status);
        $('#frmWrite').submit();
    }

    function changeRoleApprove(obj){
        var publicCd = obj.dataset.emp
        empSequence.modal[publicCd].role_approve = obj.value * 1
    }

    function getMenuContext($node, tree) {
        if ($node['li_attr']['role'] == "dept") {
            return {
                "approve_dept": {
                    "separator_before": false,
                    "separator_after": false,
                    "label": "<spring:message code='approval.option.add.dept.approve'/>",
                    "action": function (obj) {
                        var childs = $node['children_d']
                        for(var i = 0; i < childs.length; i++){
                            if(childs[i].indexOf("tree-E") == -1){
                                continue
                            }
                            addItemSign(tree.get_node(childs[i]), 1)
                        }
                        initModalEmpSequence()
                    }
                },
                "view_dept": {
                    "separator_before": false,
                    "separator_after": false,
                    "label": "<spring:message code='approval.option.add.dept.view'/>",
                    "action": function (obj) {
                        var childs = $node['children_d']
                        for(var i = 0; i < childs.length; i++){
                            if(childs[i].indexOf("tree-E") == -1){
                                continue
                            }
                            addItemSign(tree.get_node(childs[i]), 2)
                        }
                        initModalEmpSequence()
                    }
                },
                "cooperate_dept": {
                    "separator_before": false,
                    "separator_after": false,
                    "label": "<spring:message code='approval.option.add.dept.cooperate'/>",
                    "action": function (obj) {
                        var childs = $node['children_d']
                        for(var i = 0; i < childs.length; i++){
                            if(childs[i].indexOf("tree-E") == -1){
                                continue
                            }
                            addItemSign(tree.get_node(childs[i]), 3)
                        }
                        initModalEmpSequence()
                    }
                }
            }
        } else {
            return {
                "approve_emp": {
                    "separator_before": false,
                    "separator_after": false,
                    "label": "<spring:message code='approval.option.add.emp.approve'/>",
                    "action": function (obj) {
                        addItemSign($node, 1)
                        initModalEmpSequence()
                    }
                },
                "view_emp": {
                    "separator_before": false,
                    "separator_after": false,
                    "label": "<spring:message code='approval.option.add.emp.view'/>",
                    "action": function (obj) {
                        addItemSign($node, 2)
                        initModalEmpSequence()
                    }
                },
                "cooperate_emp": {
                    "separator_before": false,
                    "separator_after": false,
                    "label": "<spring:message code='approval.option.add.emp.cooperate'/>",
                    "action": function (obj) {
                        addItemSign($node, 3)
                        initModalEmpSequence()
                    }
                }
            }
        }
    }

    function addItemSign(node, roleApprove){
        if(node.li_attr.role == "emp"){
            var nodeData = node.data
            nodeData['role_approve'] = roleApprove
            if(empSequence.modal[nodeData.publicCd] == null){
                empSequence.modal[nodeData.publicCd] = nodeData
            }
        }
    }

    function downPosition(obj){
        var nodes = Array.prototype.slice.call(document.getElementsByClassName("ico-down"))
        var index = nodes.indexOf(obj)
        var nextIndex = index + 1
        var tmpEmpSl = []
        var arrKey = Object.keys(empSequence.modal)
        if(arrKey.length > 0 && index >= 0 && nextIndex >= 0 && index < arrKey.length && nextIndex < arrKey.length){
            var tmp = arrKey[index]
            arrKey[index] = arrKey[nextIndex]
            arrKey[nextIndex] = tmp
            var newSequence = {}
            for(var i = 0; i < arrKey.length; i++){
                newSequence[arrKey[i]] = empSequence.modal[arrKey[i]]
            }
            empSequence.modal = newSequence
            initModalEmpSequence()
        }
    }

    function upPosition(obj){
        var nodes = Array.prototype.slice.call(document.getElementsByClassName("ico-up"))
        var index = nodes.indexOf(obj)
        var nextIndex = index - 1
        var tmpEmpSl = []
        var arrKey = Object.keys(empSequence.modal)
        if(arrKey.length > 0 && index >= 0 && nextIndex >= 0 && index < arrKey.length && nextIndex < arrKey.length){
            var tmp = arrKey[index]
            arrKey[index] = arrKey[nextIndex]
            arrKey[nextIndex] = tmp
            var newSequence = {}
            for(var i = 0; i < arrKey.length; i++){
                newSequence[arrKey[i]] = empSequence.modal[arrKey[i]]
            }
            empSequence.modal = newSequence
            initModalEmpSequence()
        }
    }
</script>
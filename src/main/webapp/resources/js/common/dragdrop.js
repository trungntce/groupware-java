


$(document).ready(function () {

    var dragAndDropStatus = document.getElementById("main-content");
    $(".open_slide_btn").click(function () {
        $(this).hide();
        $(this).next().show();
        $(this).parent().parent().next().slideDown();
        dragAndDropStatus.style.pointerEvents = "all";
    });

    $(".close_slide_btn").click(function () {
        $(this).hide();
        $(this).prev().show();
        $(this).parent().parent().next().slideUp();
        dragAndDropStatus.style.pointerEvents = "none";
    });
    reLoadingData();
    $('.dd').nestable('serialize');
    $('.dd').on('change', function () {
        var $this = $(this);
        var serializedData = window.JSON.stringify($($this).nestable('serialize'));
        // showLog("The p element was dropped");
        $this.parents('div.body').find('textarea').val(serializedData);
        save();
    });
});


async function save() {
    var DataFinal = $('.dd').nestable('serialize');
    var DataFinal1 = $('.dd').nestable('serialize');

    var datas = {
        "children": DataFinal1
    }
    var MangEdit = [];

    var hihi = convertTreeToList(DataFinal);

    var numberScrollTop = await document.documentElement.scrollTop;

    for (var j = 0; j < hihi.length; j++) {
        var rs = findParents(datas, hihi[j]);
        var kq = rs[rs.length - 2];
        if (typeof kq == "undefined") {
            kq = "0";
        }
        MangEdit.push({ "publicCd": hihi[j], "publicParentCd": kq });
    }

    $.ajax({
        type: "POST",
        url: "/api/listenDragDrop",
        async: true,
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(MangEdit),
        success: function (response) {
            if (response === "It's OKAY") {
                getData(numberScrollTop);
            } else if (response === "Parent is not Dept") {
                reLoadingData();
                alert(springMS_);

            } else if (response === "permission") {
                location.href = "/board/dragdrop/dragdrop.hs";
                alert(springMSR);
            }
        },
        error: function (error) {
            toastr['error']('error !');
        }
    })
}

const getData = async (id) => {
    let preGetData = await reLoadingData();
    console.log(id);
    let scrollNumber = await id;
    //document.documentElement.scrollTop = bbb;
    $("body,html").animate({scrollTop:scrollNumber});

}

function reLoadingData(){
    let generateHtml_ = '</div >';

    $.ajax({
        type: 'GET',
        url: '/api/findAllDeptList',
        async:true,
        cache: false,
        success: function (res) {
            showDeptName(res, deptParentCd = "0", char = '');
        },
        failure: function (error) {
            alert("Faillllllll");
        }
    });

    let count = 0;
    let str = "";

    function showDeptName(deptName, deptParentCd = "0") {
        str += '<ol class="dd-list">';
        deptName.forEach(function (element) {
            // alert("a="+element['publicParentCd']+" b="+deptParentCd);
            if (element['publicParentCd'] == deptParentCd) {
                //console.log(element['publicParentCd'])
                str += '<li class="dd-item" data-id=' + element['publicCd'] + '>' + element['subStr'] + element['deptName'] + generateHtml_;
                str = str.replaceAll('<ol class="dd-list"></li></ol>', '');
                $("#load-detail").html(str);
                showDeptName(deptName, element['publicCd']);
            }
        })
        str += "</li></ol>";
    }
    $("#load-detail").html(str);
}

function findParents(node, searchForName) {
    // If current node name matches the search name, return
    // empty array which is the beginning of our parent result
    if (node.id === searchForName) {
        return []
    }

    // Otherwise, if this node has a tree field/value, recursively
    // process the nodes in this tree array
    if (Array.isArray(node.children)) {

        for (var treeNode of node.children) {

            // Recursively process treeNode. If an array result is
            // returned, then add the treeNode.name to that result
            // and return recursively
            const childResult = findParents(treeNode, searchForName)

            if (Array.isArray(childResult)) {
                return [treeNode.id].concat(childResult);
            }
        }
    }
}

function convertTreeToList(stack) {
    var array = [], hashMap = {};
    while (stack.length !== 0) {
        var node = stack.pop();
        if (typeof node.children == "undefined") {
            visitNode(node, array);

        } else {
            visitNode(node, array);
            for (var i = node.children.length - 1; i >= 0; i--) {
                stack.push(node.children[i]);
            }
        }
    }
    return array;
}
function visitNode(node, array) {
    array.push(node.id);

}
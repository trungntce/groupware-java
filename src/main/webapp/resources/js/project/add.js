$(document).ready(function () {
    console.log("chay dan");
    $.ajax({
        type: 'GET',
        url: '/API/selectEmpWithDept',
        cache: false,
        success: function (res) {
            autocomplete(document.getElementById("myInput"), res.result, 'p');
            autocomplete(document.getElementById("myInputAccount"), res.resultAccounting, 'a');
            arrayInput = res.result;
            arrayInputAccounting = res.resultAccounting;
        },
        failure: function (error) {
            alert("Fail");
        }
    });

    $("#projectstart").datepicker({
        dateFormat: "yy-mm-dd",             // 날짜의 형식
        changeMonth: true,                  // 월을 이동하기 위한 선택상자 표시여부
        minDate: 0,                       // 선택할수있는 최소날짜, ( 0 : 오늘 이전 날짜 선택 불가)
        onClose: function (selectedDate) {
            // 시작일(fromDate) datepicker가 닫힐때
            // 종료일(toDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
            $("#projectend").datepicker("option", "minDate", selectedDate);
        }
    });
    $("#projectend").datepicker({
        dateFormat: "yy-mm-dd",
        changeMonth: true,
        //minDate: 0, // 오늘 이전 날짜 선택 불가
        onClose: function (selectedDate) {
            // 종료일(toDate) datepicker가 닫힐때
            // 시작일(fromDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 종료일로 지정
            $("#projectstart").datepicker("option", "maxDate", selectedDate);
        }
    });
    $("#projectstart").on("change", function () {
        document.forms["myform"]["projectstart"].focus();
    });
    $("#projectend").on("change", function () {
        document.forms["myform"]["projectend"].focus();
    });
    $("#myInput").on("change", function () {
        console.log("DXD")
    });

    $('#myform').validate({
        rules: {
            title: {
                required: true,
                minlength: 6,
                maxlength: 100,
            },
            myInput: {
                required: true,
            },
            myInputAccount: {
                required: true,
            },
            project_price: {
                required: true,
            },
            projectstart: {
                required: true,
            },
            projectend: {
                required: true,
            },
        },
        messages: {
            title: {
                required: "Please input title",
                minlength: "Min Length is 6 character",
                maxlength: "Max Length is 100 character",
            },
            myInput: {
                required: "Please input content",
            },
            myInputAccount: {
                required: "Please input content",
            },
            project_price: {
                required: "Please input money ",
            },
            projectstart: {
                required: "Please input project start",
            },
            projectend: {
                required: "Please input project end",
            }
        },
    });
});

function resetdate() {
    $("#projectend").val('');
    $("#projectstart").val('');
    $("#projectend").focus();
    $("#projectstart").focus();
}
function back(){
    location.href="/project/project/list.hs?"+backsreach;
}


//add pic
function findArray(str, key) {
    var number = str.indexOf(key);
    return number;
}

var text = document.getElementById("text");
var textAccounting = document.getElementById("textAccounting");
var arrayInput;
var arrayInputAccounting;
var checkItem = 0;
function autocomplete(inp, arr, type) {

    var checkClick = 2;

    var currentFocus;
    inp.addEventListener("click", function (e) {
        
        this.value = ' ';
        var a, b, i, val = this.value;
        closeAllLists();
        if (!val) { return false; }
        currentFocus = -1;
        a = document.createElement("DIV");
        a.setAttribute("id", this.id + "autocomplete-list");
        a.setAttribute("class", "autocomplete-items");
        this.parentNode.appendChild(a);
        var checkItem = 0;
        for (i = 0; i < arr.length; i++) {
            if (findArray(arr[i].toUpperCase(), val.toUpperCase()) >= 0) {
                b = document.createElement("DIV");
                var numberFirt = arr[i].toUpperCase().indexOf(val.toUpperCase());
                b.innerHTML = arr[i].substr(0, numberFirt);
                b.innerHTML += "<strong>" + arr[i].substr(numberFirt, val.length) + "</strong>";
                b.innerHTML += arr[i].substr((numberFirt + val.length));

                b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                b.addEventListener("click", function (e) {
                    inp.value = this.getElementsByTagName("input")[0].value;
                    closeAllLists();
                });
                a.appendChild(b);
                checkItem = 1;
            }
        }
        if (type == 'p') {
            if (checkItem == 0) {
                text.style.display = "block";
            } else {
                text.style.display = "none"
            }
        } else {
            if (checkItem == 0) {
                textAccounting.style.display = "block";
            } else {
                textAccounting.style.display = "none"
            }
        }
        console.log(this.id)
        $('#'+this.id).val('');
    });
    inp.addEventListener("input", function (e) {
        var a, b, i, val = this.value;
        closeAllLists();
        if (!val) { return false; }
        currentFocus = -1;
        a = document.createElement("DIV");
        a.setAttribute("id", this.id + "autocomplete-list");
        a.setAttribute("class", "autocomplete-items");
        this.parentNode.appendChild(a);
        var checkItem = 0;
        for (i = 0; i < arr.length; i++) {
            if (findArray(arr[i].toUpperCase(), val.toUpperCase()) >= 0) {
                b = document.createElement("DIV");
                var numberFirt = arr[i].toUpperCase().indexOf(val.toUpperCase());
                b.innerHTML = arr[i].substr(0, numberFirt);
                b.innerHTML += "<strong>" + arr[i].substr(numberFirt, val.length) + "</strong>";
                b.innerHTML += arr[i].substr((numberFirt + val.length));

                b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                b.addEventListener("click", function (e) {
                    inp.value = this.getElementsByTagName("input")[0].value;
                    closeAllLists();
                });
                a.appendChild(b);
                checkItem = 1;
            }
        }
        if (type == 'p') {
            if (checkItem == 0) {
                text.style.display = "block";
            } else {
                text.style.display = "none"
            }
        } else {
            if (checkItem == 0) {
                textAccounting.style.display = "block";
            } else {
                textAccounting.style.display = "none"
            }
        }
    });
    inp.addEventListener("keydown", function (e) {
        var x = document.getElementById(this.id + "autocomplete-list");
        if (x) x = x.getElementsByTagName("div");
        if (e.keyCode == 40) {
            currentFocus++;
            addActive(x);
        } else if (e.keyCode == 38) {
            currentFocus--;
            addActive(x);
        } else if (e.keyCode == 13) {
            e.preventDefault();
            if (currentFocus > -1) {
                if (x) x[currentFocus].click();
            }
        }
    });
    function addActive(x) {
        if (!x) return false;
        removeActive(x);
        if (currentFocus >= x.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = (x.length - 1);
        x[currentFocus].classList.add("autocomplete-active");
    }
    function removeActive(x) {
        for (var i = 0; i < x.length; i++) {
            x[i].classList.remove("autocomplete-active");
        }
    }
    function closeAllLists(elmnt) {
        var x = document.getElementsByClassName("autocomplete-items");
        for (var i = 0; i < x.length; i++) {
            if (elmnt != x[i] && elmnt != inp) {
                x[i].parentNode.removeChild(x[i]);
            }
        }
    }
    document.addEventListener("click", function (e) {
        var valGet=e.path[0]["id"];
        if(valGet!='myInput' && valGet!='myInputAccount' ){
            closeAllLists(e.target);
        }
            
    });
}
//end add pic

function addP(langCd) {
    on();
    var empExitCheck = $('#myInput').val();
    var empExitCheckAccounting = $('#myInputAccount').val();
    checkItem = 0;
    var checkItemAccouting = 0;

    for (var i = 0; i < arrayInput.length; i++) {
        if (arrayInput[i].toUpperCase() == empExitCheck.toUpperCase()) {
            checkItem = 2;
            text.style.display = "none";
        }
    }

    for (var i = 0; i < arrayInputAccounting.length; i++) {
        if (arrayInputAccounting[i].toUpperCase() == empExitCheckAccounting.toUpperCase()) {
            checkItemAccouting = 2;
            textAccounting.style.display = "none"
        }
    }

    if (checkItem != 2 && $('#myInput').val() != '') {
        text.style.display = "block"
    }

    if (checkItemAccouting != 2 && $('#myInputAccount').val() != '') {
        textAccounting.style.display = "block"
    }

    var isValid = $('#myform').valid();
    var amount = $("#project_price").val();
    if (isValid == true && checkItem == 2 && checkItemAccouting == 2 && amount >= 0) {

        var settings = {
            "url": "/API/mainproject/add",
            "method": "POST",
            "headers": {
                "Content-Type": "application/json"
            },
            "data": JSON.stringify({
                "title": $("#title").val(),
                "leaderProjectName": $("#myInput").val(),
                "leaderAccountingName": $('#myInputAccount').val(),
                "projectStartDate": $("#projectstart").val(),
                "projectEndDate": $("#projectend").val(),
                "str": "Nạp tiền lần đầu",
                "advanceAmount": $("#project_price").val(),
            }),
        };
        $.ajax(settings).done(function (response) {
            console.log(response);
            location.href = "/project/project/list.hs?title=success&message=Add new sucess&"+backsreach;
            off();
        });
    } else if(amount < 0){
        if(langCd == 'vt')
        {
            alert("Không được nhập giá trị âm");
        }
        if(langCd == 'en')
        {
            alert("Do not enter negative values");
        }
        if(langCd == 'ko')
        {
            alert("마이너스 값을 입력하면 안됩니다");
        }
        off();
    }
    else {
        off();
    }
}

function editP(id) {
    on();
    var empExitCheck = $('#myInput').val();
    var empExitCheckAccounting = $('#myInputAccount').val();
    checkItem = 0;
    var checkItemAccouting = 0;

    for (var i = 0; i < arrayInput.length; i++) {
        if (arrayInput[i].toUpperCase() == empExitCheck.toUpperCase()) {
            checkItem = 2;
            text.style.display = "none";
        }
        if (arrayInput[i].toUpperCase() == empExitCheckAccounting.toUpperCase()) {
            checkItemAccouting = 2;
            textAccounting.style.display = "none"
        }
    }

    if (checkItem != 2) {
        text.style.display = "block"
    }

    if (checkItemAccouting != 2) {
        textAccounting.style.display = "block"
    }

    var isValid = $('#myform').valid();
    if (isValid == true && checkItem == 2 && checkItemAccouting == 2) {

        var settings = {
            "url": "/API/mainproject/edit",
            "method": "POST",
            "headers": {
                "Content-Type": "application/json"
            },
            "data": JSON.stringify({
                "pjId": id,
                "title": $("#title").val(),
                "leaderProjectName": $("#myInput").val(),
                "leaderAccountingName": $('#myInputAccount').val(),
                "projectStartDate": $("#projectstart").val(),
                "projectEndDate": $("#projectend").val()
            }),
        };
        $.ajax(settings).done(function (response) {
            console.log(response);
            location.href = "/project/project/list.hs?title=success&message=Edit new sucess&"+backsreach;
            off();
        });
    }
    else {
        off();
    }
}
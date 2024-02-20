function addNewRow(){
    var max_count = 10;
    var rowCount = document.getElementById('tbFile').rows.length;
    if(rowCount<=max_count){
        i=i+1;
        var table = document.getElementById("tbFile");
        var row = table.insertRow(-1);
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        cell1.innerHTML = i;
        cell2.innerHTML = '<input type="file" onchange="uploadTempFile(this,'+i+')" class="form-control-file" id="multipartFile'+i+'" name="multipartFile"><p hidden style="text-align: left" id="hienthi'+i+'">ten file, 20MB</p>';
        cell3.innerHTML = '<button type="button" onclick="deleteRow(this,'+i+')" class="btn btn-info">X</button>';

        var row = document.getElementById("nodata");
        if(row !=null){
            row.parentNode.removeChild(row);
        }
    }else{
            if(langCd=='en'){
                alert("Maximum only for uploading 10 files");
            }else if(langCd=='vt'){
                alert("Tối đa chỉ để tải lên 10 tệp");
            }else{
                alert("최대 10개 파일 업로드만 가능");
            }


    }

}
function deleteRow(btn,i) {
    //xoa file trong thu muc va database
    var fileInput = document.getElementById('multipartFile'+i);
    if($('#multipartFile'+i).val().trim() !=''){
        var filename = fileInput.files[0].name;
        $.ajax({
            type: "POST",
            url: urls+"deleteFile",
            data: {
                filename: filename,
            },
            success: function (response) {
                if(urls=="/API/dayProject/"){
                    updateSumFileSize();
                }
            },
            error: function (error) {
            }
        });
    }

    //insert dong nodata
    var rowCount = document.getElementById('tbFile').rows.length;
   if(rowCount==2){
       i=0;
       var table = document.getElementById("tbFile");
       var row = table.insertRow(-1);
       var cell1 = row.insertCell(0);
       if(langCd=='en'){
           cell1.innerHTML = "No Data";
       }else if(langCd=='vt'){
           cell1.innerHTML = "Không có nội dung hiển thị";
       }else{
           cell1.innerHTML = "조회된 내용이 없습니다";
       }

       row.setAttribute("id","nodata");
       cell1.colSpan=100;
   }

   //xoa dong
    var rowDelete = btn.parentNode.parentNode;
    rowDelete.parentNode.removeChild(rowDelete);


}
function deleteOldRow(btn,fileId){
    // /xoa file trong thu muc va database
        $.ajax({
            type: "POST",
            url: urls+"deleteOldFile",
            data: {
                fileId: fileId,
            },
            success: function (response) {
                if(urls=="/API/dayProject/"){
                    updateSumFileSize();
                }
            },
            error: function (error) {
            }
        });
    //insert dong nodata
    var rowCount = document.getElementById('tbFile').rows.length;
    if(rowCount==2){
        i=0;
        var table = document.getElementById("tbFile");
        var row = table.insertRow(-1);
        var cell1 = row.insertCell(0);
        if(langCd=='en'){
            cell1.innerHTML = "No Data";
        }else if(langCd=='vt'){
            cell1.innerHTML = "Không có nội dung hiển thị";
        }else{
            cell1.innerHTML = "조회된 내용이 없습니다";
        }
        row.setAttribute("id","nodata");
        cell1.colSpan=100;
    }

    //xoa dong
    var rowDelete = btn.parentNode.parentNode;
    rowDelete.parentNode.removeChild(rowDelete);



}

 function uploadTempFile(btn,i) {
    //lay toan bo filesize roi cong voi file moi nay xem ok ko?

//them vao temp database va temp path
     // Checking whether FormData is available in browser
     if (window.FormData !== undefined) {
         var fileUpload = $("#multipartFile"+i).get(0);
         var files = fileUpload.files;

         if(urls=="/API/dayProject/"){
             var totalSize=SumFileSize();
             var filesize=((files[0].size)/1024)/1024;
             console.log(filesize);
             var sumTF=totalSize+filesize;
             if(sumTF>100){
                 alert(msg);
                 //insert dong nodata
                 var rowCount = document.getElementById('tbFile').rows.length;
                 if(rowCount==2){
                     i=0;
                     var table = document.getElementById("tbFile");
                     var row = table.insertRow(-1);
                     var cell1 = row.insertCell(0);
                     if(langCd=='en'){
                         cell1.innerHTML = "No Data";
                     }else if(langCd=='vt'){
                         cell1.innerHTML = "Không có nội dung hiển thị";
                     }else{
                         cell1.innerHTML = "조회된 내용이 없습니다";
                     }
                     row.setAttribute("id","nodata");
                     cell1.colSpan=100;
                 }
                 //xoa dong
                 var rowDelete = btn.parentNode.parentNode;
                 rowDelete.parentNode.removeChild(rowDelete);
             }else{
                 var formData = new FormData();
                 formData.append("file", files[0]);

                 //xu ly hidden
                 var fileSize=getFileSize(files);
                 let p = document.getElementById('hienthi'+i);
                 $("#multipartFile"+i).attr("hidden",true);
                 $('#hienthi'+i).html(files[0].name+", Size: "+fileSize);
                 p.removeAttribute("hidden");
                 //end xu ly hidden
                 formData.append("fileSize", fileSize);
                 $.ajax({
                     type: "POST",
                     url: urls+"uploadFile",
                     data: formData,
                     contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
                     processData: false, // NEEDED, DON'T OMIT THIS
                     success: function (response) {
                         if(response=="false"){
                             if(langCd=='en'){
                                 alert("File is malformed or not allowed to upload");
                             }else if(langCd=='vt'){
                                 alert("File không đúng định dạng hoặc không được phép tải lên");
                             }else{
                                 alert("파일 형식이 올바르지 않습니다.");
                             }
                             //insert dong nodata
                             var rowCount = document.getElementById('tbFile').rows.length;
                             if(rowCount==2){
                                 i=0;
                                 var table = document.getElementById("tbFile");
                                 var row = table.insertRow(-1);
                                 var cell1 = row.insertCell(0);
                                 if(langCd=='en'){
                                     cell1.innerHTML = "No Data";
                                 }else if(langCd=='vt'){
                                     cell1.innerHTML = "Không có nội dung hiển thị";
                                 }else{
                                     cell1.innerHTML = "조회된 내용이 없습니다";
                                 }
                                 row.setAttribute("id","nodata");
                                 cell1.colSpan=100;
                             }
                             //xoa dong
                             var rowDelete = btn.parentNode.parentNode;
                             rowDelete.parentNode.removeChild(rowDelete);
                         }
                         if(urls=="/API/dayProject/"){
                             updateSumFileSize();
                         }
                     },
                     error: function (error) {
                     }

                 })
             }
         }else{
             var formData = new FormData();
             formData.append("file", files[0]);

             //xu ly hidden
             var fileSize=getFileSize(files);
             let p = document.getElementById('hienthi'+i);
             $("#multipartFile"+i).attr("hidden",true);
             $('#hienthi'+i).html(files[0].name+", Size: "+fileSize);
             p.removeAttribute("hidden");
             //end xu ly hidden
             formData.append("fileSize", fileSize);
             $.ajax({
                 type: "POST",
                 url: urls+"uploadFile",
                 data: formData,
                 contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
                 processData: false, // NEEDED, DON'T OMIT THIS
                 success: function (response) {
                     if(response=="false"){
                         if(langCd=='en'){
                             alert("File is malformed or not allowed to upload");
                         }else if(langCd=='vt'){
                             alert("File không đúng định dạng hoặc không được phép tải lên");
                         }else{
                             alert("파일 형식이 올바르지 않습니다.");
                         }
                         //insert dong nodata
                         var rowCount = document.getElementById('tbFile').rows.length;
                         if(rowCount==2){
                             i=0;
                             var table = document.getElementById("tbFile");
                             var row = table.insertRow(-1);
                             var cell1 = row.insertCell(0);
                             if(langCd=='en'){
                                 cell1.innerHTML = "No Data";
                             }else if(langCd=='vt'){
                                 cell1.innerHTML = "Không có nội dung hiển thị";
                             }else{
                                 cell1.innerHTML = "조회된 내용이 없습니다";
                             }
                             row.setAttribute("id","nodata");
                             cell1.colSpan=100;
                         }
                         //xoa dong
                         var rowDelete = btn.parentNode.parentNode;
                         rowDelete.parentNode.removeChild(rowDelete);
                     }
                 },
                 error: function (error) {
                 }

             })
         }



     }




}

function getFileSize(files){
    var _size = files[0].size;
    var fSExt = new Array('Bytes', 'KB', 'MB', 'GB'),
        i=0;while(_size>900){_size/=1024;i++;}
    var exactSize = (Math.round(_size*100)/100)+' '+fSExt[i];
    return exactSize;
    //gioi han 50MB
}

$(window).on('beforeunload', function() {
    return 'Your own message goes here...';
});

$(function(){
    delete_file();
});

function delete_file(){
        $.ajax({
            type: "POST",
            url: urls+"reloadPage",
            success: function (response) {
            },
            error: function (error) {
            }
        });

}

function updateSumFileSize(){
    var totalFileSize=SumFileSize();
    $("#titlefileSmall").html(totalFileSize.toFixed(3));
}

 function SumFileSize(){
    var totalFileSize=0;
    var settings = {
        "url": "/API/dayProject/selectSummaryFileByID",
        "method": "POST",
        "async": false,
        "headers": {
            "Content-Type": "application/json"
        },
        "data": JSON.stringify({
            dpId: dpId
        }),
    };
    $.ajax(settings).done(function (response) {
        for (var i=0;i<response.length;i++){
            var fileSizeInit=response[i]["fileSize"];
            var fileSize=parseFloat(fileSizeInit.split(' ')[0]);
            var fileExtend=fileSizeInit.split(' ')[1];
            switch(fileExtend) {
                case 'Bytes':
                    fileSize=(fileSize/1024)/1024;
                    break;
                case 'KB':
                    fileSize=fileSize/1024;
                    break;
                case 'GB':
                    fileSize=fileSize*1024;
                    break;
                default:
                    fileSize=fileSize;
            }
            totalFileSize += fileSize;
        }
    });
    return totalFileSize;
}






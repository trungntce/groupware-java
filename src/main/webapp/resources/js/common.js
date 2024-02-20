function goPage(_page) {

	var _url = document.location.toString();

	if (_url.search(/(page=)+[0-9]+/g) > 0) { // 페이지가 있는경우
		_url = _url.replace(/(page=)+[0-9]+/g, "page=" + _page);
		document.location.href = _url;
	} else {
		if (_url.indexOf("?") > 0) {
			_url += "&page=" + _page;
		} else {
			_url += "?page=" + _page;
		}
		document.location.href = _url;
	}
};

var rest = {
    type: {
        FORM: "form",
        JSON: "json"
    },
    post: function(mUrl, mType, mData){
        if(mType == "json"){
            return $.ajax({
                "url": mUrl,
                "method": "POST",
                "cache": false,
                "timeout": 0,
                "headers": {
                    "Content-Type": "application/json",
                },
                "data": JSON.stringify(mData)
            });
        }else{
            var formData = new FormData();
            for(var key in mData){
                formData.append(key, mData[key]);
            }
            $.ajax({
                "async": true,
                "crossDomain": true,
                "url": mUrl,
                "method": "POST",
                "data": formData,
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data"
            });
        }
    },
    get: function(mUrl, mData){
        return $.ajax({
            "type": "GET",
            "url": mUrl,
            "data": mData,
            "contentType": "application/json; charset=utf-8",
        });
    }
}

const emp = {
    selectAll: async function(){
        const api = "/api/findAllDeptList"
        const param = {}
        const res = await $.when(rest.get(api, param));
        return res
    }
}

const filesApi = {
    uploads: function(file, xhrCallback, successCallback){
        const api = "/api/files/upload"
        var formData = new FormData();
        formData.append("file", file);
        $.ajax({
            "async": true,
            "crossDomain": true,
            "url": api,
            "method": "POST",
            "data": formData,
            "processData": false,
            "contentType": false,
            "mimeType": "multipart/form-data",
            xhr: function(){
                var xhr = new window.XMLHttpRequest();
                xhr.upload.addEventListener("progress", function(evt) {
                    if (evt.lengthComputable) {
                        var percentComplete = (evt.loaded / evt.total) * 100;
                        // Place upload progress bar visibility code here
                        xhrCallback(percentComplete)
                    }
                }, false);
                return xhr;
            },
            success: function(res){
                successCallback(JSON.parse(res))
            }
        })
    }
}
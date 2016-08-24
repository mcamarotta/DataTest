function getRequest(url, data, callback){
	var currentdate = new Date(); 
	var nocache = currentdate.getSeconds();
	   $.get(url + '?nocache=' + nocache, data, function(data, status){
	   	console.log('a');
        callback(data, status);
    });
}

function postRequest(url, data, callback){

    $.post(url, data, function(data, status){
        callback(data, status);
    });
}
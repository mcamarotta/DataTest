function getRequest(url, data, callback){
	   $.get(url, data, function(data, status){
	   	console.log('a');
        callback(data, status);
    });
}

function postRequest(url, data, callback){

    $.post(url, data, function(data, status){
        callback(data, status);
    });
}
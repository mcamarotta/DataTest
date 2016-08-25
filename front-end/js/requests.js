var urlBase = 'http://localhost:8080/front-end/';

function getRequest(partialUrl, data, callback){
	var url = urlBase + partialUrl;
	var currentdate = new Date(); 
	var nocache = currentdate.getSeconds();
	console.log('Get Request in: ' + url);
	$.get(url + '?nocache=' + nocache, data, function(data, status){
		console.log('a');
		callback(data, status);
	});
}

function postRequest(partialUrl, data, callback){
	var url = urlBase + partialUrl;
	console.log('Post Request in: ' + url);
	$.post(url, data, function(data, status){
		callback(data, status);
	});
}
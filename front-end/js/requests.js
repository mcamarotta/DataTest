function Requests(config) {
	this.config = config;
	this.defaultReturn = { "status": 0, "data": {} };
};

Requests.prototype.getRequest = function (partialUrl, data, callback) {
	var url = this.config.urlBase + partialUrl;
	var currentdate = new Date();
	var nocache = currentdate.getSeconds();
	console.log('Get Request in: ' + url);
	var promise = $.get(url + '?nocache=' + nocache, data);
	promise.done(function (data) {
		callback(data, 200);
	}).fail(function (message) {
		callback({ "message": "We couldn't access the algorithm url." }, 404);
	});
};

Requests.prototype.postRequest = function (partialUrl, data, callback) {
	var url = this.config.urlBase + partialUrl;
	console.log('Data posted in ' + url + ': ');
	console.log(data);
	$.ajax(url, {
    data : JSON.stringify(data),
    contentType : 'application/json',
    type : 'POST'})
	.then(function(data, status){
		console.log("Request returned with status: " + "[ " + status + " ] :");
		if (status === "success") console.log(data);
		callback(data, status);
	});

};
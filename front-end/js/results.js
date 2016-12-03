function Results(tblResults){
	this.tblResults = tblResults;	
	this.objResults = [];
};

Results.prototype.setResults = function(results){	
	this.objResults = results;

	this.updateTableResults();
};

Results.prototype.updateTableResults = function(){	
	var tblResults = this.tblResults;
	var htmlBody = '';
	for(var i in this.objResults){			
		var number = (1 + parseInt(i));		
		htmlBody += '<tr><td>'+number+'</td><td>'+ this.objResults[i].combination.join(', ')+'</td></tr>';
	}
	$(tblResults).find('tbody').html(htmlBody);

	$(tblResults).find('tfoot').find('tr').find('td').text(this.objResults.length +' combinations');
};

Results.prototype.reset = function(){
	this.objResults = [];
	this.updateTableResults();
};


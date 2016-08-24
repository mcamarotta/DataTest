var objResults = [];

function initResultsLogic(){
	//Todo: number of results
	objResults = [];
	updateTableResults();
}

function setResults(results){	
	objResults = results;
	$('#tdResultsFooter').text(objResults.length +' combinations');
	updateTableResults();	
}


function updateTableResults(){		
	var htmlBody = '';
	for(var i in objResults){			
		var number = (1 + parseInt(i));
		console.log(htmlBody);		
		htmlBody += '<tr><td>'+number+'</td><td>'+ objResults[i].combination.join(', ')+'</td></tr>';
	}
	$('#tblResults').find('tbody').html(htmlBody);
}
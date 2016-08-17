var ourTable = [];

function initVariablesLogic(){
	ourTable = {
		"header" : ["1", "2", "3", "4"],
		"rows" : [
		{"columns" : ["1A", "2A", "3A", "4A"] },
		{"columns" : ["1B", "2B", "3B", "4B"] },
		{"columns" : ["1C", "2C", "3C", "4C"] },
		{"columns" : ["1D", "2D", "3D", "4D"] }
		]		
	};

	updateHtmlTable();

}

// Bind to Table

function updateHtmlTable(){
	var table = $("#tblVariables");
	$(table).find("thead").html(getHtmlHeader());	
	$(table).find("tbody").html(getHtmlRows());
}

function updateModel(rowId, colId, value){
	if (rowId < this.ourTable.rows.length && colId < this.ourTable.rows[rowId].columns.length ){
		this.ourTable.rows[rowId].columns[colId] = value;	
	}
}

//Add, delete / rows, columns


function addRow(){
	this.ourTable.rows.push(this.getNewRow());
	updateHtmlTable();
}

function deleteRow(){
	if (this.ourTable.rows.length > 0){
		this.ourTable.rows.pop();
		updateHtmlTable();
	}
	else
		alert('Paraaa');

}

function addColumn(){
	this.ourTable.header.push((this.ourTable.header.length + 1).toString());
	for(var rowIndex in this.ourTable.rows){
		this.ourTable.rows[rowIndex].columns.push("");
	}
	updateHtmlTable();
}

function deleteColumn(){
	if (this.ourTable.header.length > 0){
		this.ourTable.header.pop();
		for(var rowIndex in this.ourTable.rows){
			if (this.ourTable.rows[rowIndex].columns.length > 0){
				this.ourTable.rows[rowIndex].columns.pop();
			}
		}
		updateHtmlTable();
	}
}


function getNewRow(){
	var colCount = this.ourTable.header.length;
	var newRow = {"columns" : [] };
	for(var i = 0; i < colCount ; i++){
		newRow.columns.push("");
	}
	return newRow;
}

function getHtmlHeader(){
	var headerHtml = 
	"<tr>";
	for(var headerIndex in this.ourTable.header){
		headerHtml += "<th>" + this.ourTable.header[headerIndex] + "</th>";
	}
	return headerHtml + "</tr>";
}

function getHtmlRows(){
	var rowsHtml = "";
	for(var rowIndex in this.ourTable.rows){
		var row = this.ourTable.rows[rowIndex];
		rowsHtml += "<tr>";
		for(var colIndex in row.columns){
			rowsHtml += "<td>" + getHtmlTextbox(rowIndex, colIndex,row.columns[colIndex])  + "</td>";
		}
		rowsHtml += "</tr>";
	}
	return rowsHtml;
}

function getHtmlTextbox(rowId, colId, value){
	return '<input type="text" class="form-control" ' +
	'data-rowIndex="'+rowId+'" data-colIndex="'+ colId +'" ' +
	'value="'+value+'" onkeyup="updateModel('+rowId+', '+colId+',this.value);" />';

}
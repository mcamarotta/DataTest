
function Variables(htmlTable, btnAddCol, btnRemoveCol, btnAddRow, btnRemoveRow) {

	this.getDefaultTable = function () {
		return {
			"header": ["1", "2", "3", "4"],
			"rows": [{ "columns": ["1A", "2A", "3A", "4A"] },
			{ "columns": ["1B", "2B", "3B", "4B"] },
			{ "columns": ["1C", "2C", "3C", "4C"] },
			{ "columns": ["1D", "2D", "3D", "4D"] }]
		};
	};

	this.ourTable = this.getDefaultTable();

	this.htmlTable = htmlTable;
	this.btnAddCol = btnAddCol;
	this.btnRemoveCol = btnRemoveCol;
	this.btnAddRow = btnAddRow;
	this.btnRemoveRow = btnRemoveRow;

	//Html helpers
	this.generateHtmlRows = function () {
		var rowsHtml = "";
		for (var rowIndex in this.ourTable.rows) {
			var row = this.ourTable.rows[rowIndex];
			rowsHtml += "<tr>";
			for (var colIndex in row.columns) {
				rowsHtml += "<td>" + this.generateHtmlTextbox(rowIndex, colIndex, row.columns[colIndex]) + "</td>";
			}
			rowsHtml += "</tr>";
		}
		return rowsHtml;
	};

	this.generateHtmlTextbox = function (rowId, colId, value) {
		return '<input type="text" class="form-control" ' +
			'data-rowIndex="' + rowId + '" data-colIndex="' + colId + '" ' +
			'value="' + value + '" onkeyup="updateModel(' + rowId + ', ' + colId + ',this.value);" />';
	};

	this.generateHtmlHeader = function () {
		var headerHtml =
			"<tr>";
		for (var headerIndex in this.ourTable.header) {
			headerHtml += "<th>" + this.ourTable.header[headerIndex] + "</th>";
		}
		return headerHtml + "</tr>";
	};
};

Variables.prototype.generateNewRow = function () {
	var colCount = this.ourTable.header.length;
	var newRow = { "columns": [] };
	for (var i = 0; i < colCount; i++) {
		newRow.columns.push("");
	}
	return newRow;
};

Variables.prototype.addRow = function () {
	this.ourTable.rows.push(this.generateNewRow());
	this.updateTableVariables();
};

Variables.prototype.deleteRow = function () {
	if (this.ourTable.rows.length > 1) {
		this.ourTable.rows.pop();
		this.updateTableVariables();
	}

};

Variables.prototype.addColumn = function () {
	this.ourTable.header.push((this.ourTable.header.length + 1).toString());
	for (var rowIndex in this.ourTable.rows) {
		this.ourTable.rows[rowIndex].columns.push("");
	}
	this.updateTableVariables();
};

Variables.prototype.deleteColumn = function () {
	if (this.ourTable.header.length > 1) {
		this.ourTable.header.pop();
		for (var rowIndex in this.ourTable.rows) {
			if (this.ourTable.rows[rowIndex].columns.length > 0) {
				this.ourTable.rows[rowIndex].columns.pop();
			}
		}
		this.updateTableVariables();
	}
};


//Update variables in table
Variables.prototype.updateTableVariables = function () {
	var table = this.htmlTable;
	$(table).find("thead").html(this.generateHtmlHeader());
	$(table).find("tbody").html(this.generateHtmlRows());
	$(table).find("tfoot tr td").attr('colspan', this.ourTable.header.length);
	$(table).find("tfoot tr td").text(this.ourTable.header.length + ' variables x ' + this.ourTable.rows.length + ' rows');

	$(this.btnRemoveCol).attr('disabled', this.ourTable.header.length == 0 ? 'disabled' : false);
	$(this.btnRemoveRow).attr('disabled', this.ourTable.rows.length == 0 ? 'disabled' : false);
};

Variables.prototype.reset = function () {
	this.ourTable = this.getDefaultTable();
	this.updateTableVariables();
};

Variables.prototype.getJson = function () {
	var dataToSend = [];
	// Loop table rows
	for (var headerItem in this.ourTable.header) {
		dataToSend.push({
			"variableName": this.ourTable.header[headerItem],
			"values": []
			//,"peso":[]
			//,"remove":[]
		});

		for (var row in this.ourTable.rows) {
			dataToSend[headerItem].values.push(this.ourTable.rows[row].columns[headerItem]);
		}
	}
	return dataToSend;
};

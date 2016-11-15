$(document).ready(function () {

	$(function () {
		$('a[href*="#"]:not([href="#"])').click(function () {
			if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
				var target = $(this.hash);
				target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
				if (target.length) {
					$('#areasContainer').animate({
						scrollTop: target.offset().top
					}, 1000);
					return false;
				}
			}
		});
	});

	initPage();
	var config;
	var variables;
	var algorithms;
	var results;
	var requests;
	var logic;

	///Start the application flow
	function initPage() {
		//Init logic instances
		config = new Config();
		variables = new Variables($('#tblVariables'), $("#btnAddCol"), $("#btnRemoveCol"), $("#btnAddRow"), $("#btnRemoveRow"));
		results = new Results($('#tblResults'));
		algorithms = new Algorithms(config);
		requests = new Requests(config);
		logic = new Logic(config, algorithms, results, variables, requests);

		//Dropdown Algorithms
		algorithms.buildDropDown($('#ddlAlgorithms'));
		if (algorithms.getAlgorithm($('#ddlAlgorithms')).usePairs === true)
			$($("#sidebarNavigation a")[2]).show();
		else
			$($("#sidebarNavigation a")[2]).hide();

		// Init the variables logic (first step)   		   		
		variables.updateTableVariables();

		//Create event handlers
		handleEvents();

	};

	function buildRequestJson() {
		var dataToSend = [];
		// Loop table rows
		for (var headerItem in ourTable.header) {
			dataToSend.push({ "values": [], "variableName": ourTable.header[headerItem] });

			for (var row in ourTable.rows) {
				dataToSend[headerItem].values.push(ourTable.rows[row].columns[headerItem]);
			}
		}
		return dataToSend;
	};

	function handleEvents() {
		//Event handlers
		$("#btnAddRow").on("click", function () { variables.addRow(); });
		$("#btnRemoveRow").on("click", function () { variables.deleteRow(); });
		$("#btnAddCol").on("click", function () { variables.addColumn(); });
		$("#btnRemoveCol").on("click", function () { variables.deleteColumn(); });
		$("#btnClear").on("click", function () { variables.reset(); });
		$("#btnNext").on("click", function () { logic.next(); });
		$("#btnBack").on("click", function () { logic.back(); });
	};
});

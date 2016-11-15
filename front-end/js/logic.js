function Logic(config, algorithms, results, variables, requests) {
    this.config = config;
    this.algorithms = algorithms;
    this.results = results;    
    this.variables = variables;
    this.currentStep = 0;
    this.requests = requests;        
};

Logic.prototype.startApplication = function () {    
    this.variables.reset();
    this.results.reset();
    this.pairs.reset();
    this.currentStep = 0;
    this.changeVisibility();
};

Logic.prototype.showError = function(message, callback){
    alert(message);
};

Logic.prototype.proccess = function () {
    var me = this;

    this.requests.getRequest(this.algorithms.getAlgorithm($('#ddlAlgorithms')).url, this.variables.getJson(), function (data, status) {
        if (status != 200){
            me.showError(data.message);
            return;
        }
        me.results.setResults(data);
        me.currentStep = 3;
        me.changeVisibility();
    });
};

Logic.prototype.next = function () {
    var usePairs = (this.algorithms.getAlgorithm($('#ddlAlgorithms')).usePairs === true);

    if (this.currentStep === 3) return;

    if (this.currentStep === 2 || (this.currentStep === 1 && !usePairs)) {
        this.proccess();
        return;
    }
    if (this.currentStep === 1 && usePairs) this.currentStep = 2;
    else this.currentStep++;
    this.changeVisibility();

};

Logic.prototype.back = function () {
    var usePairs = (this.algorithms.getAlgorithm($('#ddlAlgorithms')).usePairs === true);
    if (this.currentStep === 0)
        return;
    if (this.currentStep === 3) {
        if (usePairs) this.currentStep = 2;
        else this.currentStep = 1;
    } else this.currentStep--;
    this.changeVisibility();


};

Logic.prototype.changeVisibility = function () {
    var usePairs = (this.algorithms.getAlgorithm($('#ddlAlgorithms')).usePairs === true);
    var step = this.currentStep;
    $('#btnBack').show(); $('#btnNext').show();    
    $('.areaStep').hide(this.config.animationDuration);
    $('#btnNext').text('Next');
    switch (step) {
        case 0: //Algorithm
            $('#areaAlgorithm').show(this.config.animationDuration);
            $('#btnBack').hide();
            // this.scrollToElement($('#areaAlgorithm'));
            break;
        case 1: //Variables
            $('#areaVariables').show(this.config.animationDuration);
            if (!usePairs) $('#btnNext').text("Execute");
            break;
        case 2: // Pairs            
            $('#areaPairs').show(this.config.animationDuration);
            $('#btnNext').text("Execute");
            // this.scrollToElement($('#areaPairs'));
            break;
        case 3: // Results              
            $('#areaResults').show(this.config.animationDuration);
            // this.scrollToElement($('#areaResults'));
            $('#btnNext').hide();
            break;
    }

    $('#sidebarNavigation > a').each(function () { $(this).removeClass("active"); });
    $($('#sidebarNavigation > a')[this.currentStep]).addClass("active");
};

Logic.prototype.scrollToElement = function (target) {
    if (target.length) {
        $('#areasContainer').animate({
            scrollTop: target.offset().top
        }, 1000);
        return false;
    }
};










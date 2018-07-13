

(function() {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .directive('rowRuless', rowRuless);

    rowRuless.$inject = ['$compile'];

    function rowRuless($compile) {
        var directive = {
        		scope: {

      	          rowRule: "=", //import referenced model to our directives scope
      	          quantityKinds: "=",
      	          uoms:"=",
      	          someCtrlFn: '&callbackFn'
      	        },

          template : '<div style="border:2px solid; border-color:#337ab7;padding:6px;display: table;margin: 0 auto;width:100%;">'+

			          '<select ng-model="rowRule.firstOperator" name = "firstOperator" >'+
                         '<option value="IF">IF</option>'+
                       '</select>'+
				          '<select class="form-controls" style="width:120px; margin:10px;"  id="field_quantitykinds" name="quantitykinds" ng-model="rowRule.qk" ng-options="quantitykind for quantitykind in quantityKinds" required> ' +
				              '<option value="">-- Quantity kind --</option>' +
				          '</select>' +
				          '<div ng-show="editFormManual.quantitykinds.$invalid">' +
	                        '<p class="help-block"'+
	                            'ng-show="editFormManual.quantitykinds.$error.required">'+
	                            'This field is required.'+
	                        '</p>' +
	                    '</div>' +

	                    '<select style = "margin:10px;width:50px;" ng-model ="rowRule.secondOperator">'+
                                                 '<option value=">">></option>'+
                                                 '<option value="<"><</option>'+
                                                 '<option value="=">=</option>' +
                                                 '<option value="!=">!=</option>' +

                                               '</select>'+

            '<input  type="text" class="form-controls" style="width:90px;margin:10px;" name="ruleValue" id="field_ruleValue"'+
                            	                        'ng-model="rowRule.ruleValue"'+
                            	                            'required />' +

	                       '<select class="form-controls" style = "width:120px;margin:10px;" id="field_uoms" name="uoms" ng-model="rowRule.uom" ng-options="uom for uom in uoms" required>' +
                          				              '<option value="">-- Unit of Measurement --</option>' +
                          				          '</select>' +
                          				          '<div ng-show="editFormManual.uoms.$invalid">' +
                          	                        '<p class="help-block"'+
                          	                            'ng-show="editFormManual.uoms.$error.required">'+
                          	                            'This field is required.'+
                          	                        '</p>' +
                          	                    '</div>' +

                          '<select style="width:70px; margin:10px;" ng-model ="rowRule.thirdOperator">'+

                                                   '<option value="THEN">THEN</option>'+


                           '</select>'+


                            '<input  type="text" class="form-controls" style="width:200px;margin:10px;" name="Longitude" id="field_inffered_data"'+
                            	                        'ng-model="rowRule.infferedData"'+
                            	                            'required />' +






			          '</div>',


                 link: function(scope, element, attrs) {
                        scope.someCtrlFn({arg1: rowRule.index});
                },
            replace: true
        };

        return directive;

        function linkFunc(scope, elem, attr, ctrl) {

        }
    }
})();



(function() {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .directive('rowRule', rowRule);

    rowRule.$inject = ['$compile', '$rootScope', '$templateRequest', '$sce'];

    function rowRule($compile, $rootScope, $templateRequest, $sce) {
        return {
            restrict: "E",
            scope: {
              ruleRowId: '@'
            },
            link: function(scope, element, attrs) {

              var templateUrl = $sce.getTrustedResourceUrl('app/entities/reasoning/RowRuleTemplate.html');
              $templateRequest(templateUrl).then(function(template) {
                var linkFn = $compile(template);
                var htmlString = linkFn(scope);
                element.append(htmlString);
                console.log("success load template");

              }, function() {
               console.log("error load template");
                // An error has occurred
              });


            },
            controller: ['$rootScope', '$scope', '$element', function($rootScope, $scope, $element) {
                   console.log('con chim da da');
                   $scope.rowRule = {};
                   //$scope.contacts = $rootScope.GetContactTypes;
                   $scope.quantityKinds = $rootScope.rowQuantityKind;
                   $scope.uoms = $rootScope.rowUoms;



              $scope.Delete = function(e) {
                // console.log(e.currentTarget.id);

                console.log('deleeeeeee');
                //remove element and also destoy the scope that element
                $element.remove();
                $scope.$destroy();
                //$rootScope.controlCount--;

              }
            }]

          }
    }
})();



(function() {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .controller('RegisterRuleDetailController', RegisterRuleDetailController);

    RegisterRuleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RegisterRule', 'Reasoning'];

    function RegisterRuleDetailController($scope, $rootScope, $stateParams, previousState, entity, RegisterRule, Reasoning) {
        var vm = this;

        vm.registerRule = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fiestaReasonerEngineApp:registerRuleUpdate', function(event, result) {
            vm.registerRule = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

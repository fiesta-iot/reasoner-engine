(function() {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .controller('ReasoningDetailController', ReasoningDetailController);

    ReasoningDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Reasoning'];

    function ReasoningDetailController($scope, $rootScope, $stateParams, previousState, entity, Reasoning) {
        var vm = this;

        vm.reasoning = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fiestaReasonerEngineApp:reasoningUpdate', function(event, result) {
            vm.reasoning = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

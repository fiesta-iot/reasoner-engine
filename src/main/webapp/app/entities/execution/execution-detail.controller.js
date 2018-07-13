(function() {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .controller('ExecutionDetailController', ExecutionDetailController);

    ExecutionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Execution', 'RegisterRule'];

    function ExecutionDetailController($scope, $rootScope, $stateParams, previousState, entity, Execution, RegisterRule) {
        var vm = this;

        vm.execution = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('fiestaReasonerEngineApp:excutionUpdate', function(event, result) {
            vm.execution = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

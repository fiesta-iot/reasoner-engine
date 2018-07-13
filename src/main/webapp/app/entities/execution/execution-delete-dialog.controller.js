(function() {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .controller('ExecutionDeleteController',ExecutionDeleteController);

    ExecutionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Execution'];

    function ExecutionDeleteController($uibModalInstance, entity, Execution) {
        var vm = this;

        vm.execution = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Execution.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

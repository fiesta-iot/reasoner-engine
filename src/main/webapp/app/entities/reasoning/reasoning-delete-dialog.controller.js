(function() {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .controller('ReasoningDeleteController',ReasoningDeleteController);

    ReasoningDeleteController.$inject = ['$uibModalInstance', 'entity', 'Reasoning'];

    function ReasoningDeleteController($uibModalInstance, entity, Reasoning) {
        var vm = this;

        vm.reasoning = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Reasoning.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

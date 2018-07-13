(function() {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .controller('RegisterRuleDeleteController',RegisterRuleDeleteController);

    RegisterRuleDeleteController.$inject = ['$uibModalInstance', 'entity', 'RegisterRule'];

    function RegisterRuleDeleteController($uibModalInstance, entity, RegisterRule) {
        var vm = this;

        vm.registerRule = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RegisterRule.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

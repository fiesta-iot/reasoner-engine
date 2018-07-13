(function() {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .controller('ExecutionDialogController', ExecutionDialogController);

    ExecutionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Execution', 'RegisterRule', 'Sensor'];

    function ExecutionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Execution, RegisterRule, Sensor) {
        var vm = this;

        vm.execution = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.registerrules = RegisterRule.query();
        vm.executionRequest = {};
        vm.types = [
          "Current",
          "Range"
        ];

        vm.selectType = "Current";


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;

            //alert("demo:" + (vm.started > vm.ended))
            var started = new Date(vm.started);
            var ended = new Date(vm.ended);
            vm.executionRequest.registerRuleId = vm.execution.registerRule.id;

            if(vm.execution.type == "Range") {
                  vm.executionRequest.started = vm.execution.started;
                  vm.executionRequest.ended =  vm.execution.ended;




                  vm.executionRequest.executeType = 2;
                  if(started > ended) {
                           alert("Started time must be less than Ended time");
                   } else {

                      Execution.save(vm.executionRequest, onSaveSuccess, onSaveError);

                  }
            } else {
                    vm.executionRequest.executeType = 1;
                    Execution.save(vm.executionRequest, onSaveSuccess, onSaveError);

            }


        }

        function onSaveSuccess (result) {
            $scope.$emit('fiestaReasonerEngineApp:executionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


         $scope.selectType = function() {
            console.log(vm.type);
            vm.execution.type = vm.type;

         };

        vm.datePickerOpenStatus.created = false;
        vm.datePickerOpenStatus.updated = false;
        vm.datePickerOpenStatus.started = false;
        vm.datePickerOpenStatus.ended = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        $scope.selectRegister = function() {
            vm.execution.sensor = vm.execution.registerRule.sensor;
            vm.execution.ruleContent = vm.execution.registerRule.ruleContent;
            Sensor.getSensorData({id: vm.execution.registerRule.reasoning.sensorEndp}, function(response) {
               vm.execution.originalData = response.data;
            });

        }

    }
})();

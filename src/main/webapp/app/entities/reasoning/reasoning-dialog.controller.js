(function() {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .controller('ReasoningDialogController', ReasoningDialogController);

    ReasoningDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Reasoning',"Sensor","QuantityKinds"];

    function ReasoningDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Reasoning, Sensor, QuantityKinds) {
        var vm = this;

        vm.reasoning = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.sensors =  [];//Sensor.query();
        vm.reasoning.data = "";
        vm.selectedQuantityKind = "";

        vm.sensor = {};
        vm.quantityKinds = QuantityKinds.query( function(response) {

          if(vm.reasoning.id != null) {

                   //vm.reasoning.ruleContent = vm.registerRule.reasoning.content;

                   var quantityKind = vm.reasoning.quantityKind;
                   var shortQuantityKind = quantityKind.replace("http://purl.org/iot/vocab/m3-lite#","");
                   vm.selectedQuantityKind = shortQuantityKind;

                    Sensor.getByQuantityKind({quantityKind:vm.selectedQuantityKind}, function(response) {
                        vm.sensors = [];
                        vm.sensors = response;
                        vm.reasoning.sensor =  vm.reasoning.sensor;
                        vm.sensor = vm.reasoning.sensor;
                        console.log("vm.reasoning.sensor:" + vm.reasoning.sensor);
                        console.log("vm.sensor:" + vm.sensor);

                    });
             }

        });



        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });


        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;

            if (vm.reasoning.id !== null) {

                Reasoning.update(vm.reasoning, onSaveSuccess, onSaveError);
            } else {
                Reasoning.save(vm.reasoning, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fiestaReasonerEngineApp:reasoningUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        $scope.selectQuantitykind = function() {

            vm.sensors = [];
            if(vm.selectedQuantityKind  != "") {

              Sensor.getByQuantityKind({quantityKind:vm.selectedQuantityKind}, function(response) {
                vm.sensors = response;
              });
            }

         }

        $scope.selectSensor = function() {
           //console.log('hello');
           //alert(vm.reasoning.sensor);

           console.log(vm.sensor.sensor);
           console.log(vm.sensor.endp);

           vm.reasoning.sensorEndp = vm.sensor.endp;

           vm.reasoning.sensor = vm.sensor.sensor;

           vm.reasoning.latitude = vm.sensor.lat;
           vm.reasoning.longitude = vm.sensor.lng;
           vm.reasoning.quantityKind = vm.sensor.qk;
           vm.reasoning.unitOfMeasurement = vm.sensor.unit;

           console.log(vm.reasoning.sensorEndp);

            vm.reasoning.hashedSensor = vm.sensor.hashedSensor;


           Sensor.getSensorData({id : vm.reasoning.sensorEndp}, function(response) {
           vm.reasoning.sensorSampleData = response.data;
           });

           Sensor.getSensorMeta({id: vm.sensor.hashedSensor}, function(response) {

           vm.reasoning.sensorMeta = response.data;

           });



        }

        vm.datePickerOpenStatus.created = false;
        vm.datePickerOpenStatus.updated = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

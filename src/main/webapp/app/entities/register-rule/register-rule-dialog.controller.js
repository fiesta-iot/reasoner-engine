(function() {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .controller('RegisterRuleDialogController', RegisterRuleDialogController);

    RegisterRuleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RegisterRule', 'Reasoning', 'Sensor','QuantityKinds','UnitOfMesurements'];

    function RegisterRuleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RegisterRule, Reasoning, Sensor, QuantityKinds, UnitOfMesurements) {
        var vm = this;

        vm.registerRule = entity;
        vm.clear = clear;
        vm.save = save;
        vm.reasonings = Reasoning.query();

        vm.sensors = [];
        vm.requestRegisterRule = {};

        vm.quantityKinds = QuantityKinds.query( function(response) {
           if(vm.registerRule.id != null) {

           vm.registerRule.ruleContent = vm.registerRule.reasoning.content;
           var quantityKind = vm.registerRule.quantityKind;
           var shortQuantityKind = quantityKind.replace("http://purl.org/iot/vocab/m3-lite#","");
           vm.selectedQuantityKind = shortQuantityKind;

            Sensor.getByQuantityKind({quantityKind:vm.selectedQuantityKind}, function(response) {
                vm.sensors = response;
            });
          }
        });


        //vm.registerRule.hashedSensor = vm.registerRule.sensor;



        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }


        function editBinding () {


        }


        editBinding();

        function save () {
            vm.isSaving = true;

            vm.requestRegisterRule.id = vm.registerRule.id;
            vm.requestRegisterRule.name = vm.registerRule.name;
            vm.requestRegisterRule.description = vm.registerRule.description;
            vm.requestRegisterRule.sensor = vm.registerRule.sensor;
            vm.requestRegisterRule.quantityKind = vm.registerRule.quantityKind;
            vm.requestRegisterRule.unitOfMeasurement = vm.registerRule.unitOfMeasurement;
            vm.requestRegisterRule.latitude = vm.registerRule.latitude;
            vm.requestRegisterRule.longitude = vm.registerRule.longitude;
            vm.requestRegisterRule.ruleId = vm.registerRule.reasoning.id;
            if (vm.registerRule.id !== null) {
                RegisterRule.update(vm.requestRegisterRule, onSaveSuccess, onSaveError);
            } else {
                RegisterRule.save(vm.requestRegisterRule, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('fiestaReasonerEngineApp:registerRuleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        $scope.selectRule = function() {
           vm.registerRule.ruleContent = vm.registerRule.reasoning.content;
           var quantityKind = vm.registerRule.reasoning.quantityKind;
           var shortQuantityKind = quantityKind.replace("http://purl.org/iot/vocab/m3-lite#","");
           vm.selectedQuantityKind = shortQuantityKind;

            Sensor.getByQuantityKind({quantityKind:vm.selectedQuantityKind}, function(response) {
                           vm.sensors = response;
                            //vm.sensors[getIndexFromValue('Power')
            });

        }

        $scope.selectQuantitykind = function() {

            if(vm.selectedQuantityKind  != "") {

              Sensor.getByQuantityKind({quantityKind:vm.selectedQuantityKind}, function(response) {
                vm.sensors = response;
                 //vm.sensors[getIndexFromValue('Power')
              });
            }

         }
        $scope.selectSensor = function() {
                   //console.log('hello');
                   //alert(vm.reasoning.sensor);

                   console.log(vm.sensor.sensor);
                   console.log(vm.sensor.endp);

                   //vm.registerRule.sensorEndp = vm.sensor.endp;

                   vm.registerRule.sensor = vm.sensor.sensor;

                    vm.registerRule.latitude = vm.sensor.lat;
                    vm.registerRule.longitude = vm.sensor.lng;
                    vm.registerRule.quantityKind = vm.sensor.qk;
                    vm.registerRule.unitOfMeasurement = vm.sensor.unit;

                    vm.registerRule.hashedSensor = vm.sensor.hashedSensor;


                   //console.log(vm.reasoning.sensorEndp);

                   Sensor.getSensorData({id : vm.sensor.endp}, function(response) {
                      vm.registerRule.data = response.data;
                   });

                   Sensor.getSensorMeta({id: vm.sensor.sensor}, function(response) {

                    vm.registerRule.sensorMeta = response.data;

                   });

        }

    }
})();

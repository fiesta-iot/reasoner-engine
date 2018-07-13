(function() {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .controller('ReasoningNonExpertDialogController', ReasoningNonExpertDialogController);

    ReasoningNonExpertDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Reasoning', "Sensor", 'QuantityKinds', 'UnitOfMesurements', '$compile', '$rootScope'];

    function ReasoningNonExpertDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, Reasoning, Sensor, QuantityKinds, UnitOfMesurements, $compile, $rootScope) {
        var vm = this;

        vm.reasoning = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        //vm.sensors =  Sensor.query();
        vm.sensors = [];
        vm.reasoning.data = "";


        vm.sensor = {};

        vm.selectedQuantityKind = "";

        vm.rowQuantityKind = [];
        vm.rowUoms = [];


        vm.quantityKinds = QuantityKinds.query(); //[vm.reasoning.quantityKind];
        vm.uoms = UnitOfMesurements.query(); //[vm.reasoning.unitOfMeasurement]

        vm.datas = {
            quantityKind: null,
            unitofMesuarement: null,
            multipleSelect: [],
            option1: 'option-1'
        };



        var counter = 1;
        $scope.data = {
            fields: []
        }



        vm.rowQuantityKind = [vm.reasoning.quantityKind];
        vm.rowUoms = [vm.reasoning.unitOfMeasurement]

        $rootScope.rowQuantityKind = [vm.reasoning.quantityKind];
        $rootScope.rowUoms = [vm.reasoning.unitOfMeasurement]

        $timeout(function() {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {

             var rules = retriveValue($scope);
             console.log(rules);
             vm.isSaving = true;
             vm.reasoning.rules = rules;
             if (vm.reasoning.id !== null) {
                 Reasoning.update(vm.reasoning, onSaveSuccess, onSaveError);
             } else {
                 Reasoning.createRuleNonExpert(vm.reasoning, onSaveSuccess, onSaveError);
             }
        }

        function onSaveSuccess(result) {
            $scope.$emit('fiestaReasonerEngineApp:reasoningUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        $scope.selectSensor = function() {

            console.log(vm.sensor.unit);

            vm.reasoning.sensorEndp = vm.sensor.endp;

            vm.reasoning.sensor = vm.sensor.sensor;

            console.log(vm.reasoning.sensorEndp);

            vm.rowQuantityKind = [vm.reasoning.quantityKind];
            vm.rowUoms = [vm.reasoning.unitOfMeasurement]

            vm.reasoning.latitude = vm.sensor.lat;
            vm.reasoning.longitude = vm.sensor.lng;
            vm.reasoning.quantityKind = vm.sensor.qk;
            vm.reasoning.unitOfMeasurement = vm.sensor.unit;

            vm.reasoning.hashedSensor = vm.sensor.hashedSensor;


            Sensor.getSensorData({
                id: vm.reasoning.sensorEndp
            }, function(response) {
                vm.reasoning.sensorSampleData = response.data;
            });

            Sensor.getSensorMeta({
                id: vm.sensor.hashedSensor
            }, function(response) {

                vm.reasoning.sensorMeta = response.data;

            });


            vm.rowQuantityKind = [vm.sensor.shortQk];
            vm.rowUoms = [vm.sensor.shortUnit];

            $rootScope.rowQuantityKind = [vm.sensor.shortQk];
            $rootScope.rowUoms = [vm.sensor.shortUnit];


        }

        $scope.selectQuantitykind = function() {

            if (vm.selectedQuantityKind != "") {

                Sensor.getByQuantityKind({
                    quantityKind: vm.selectedQuantityKind
                }, function(response) {
                    vm.sensors = response;
                });
            }

        }



        var retriveValue = function() {

            var Rules = [];
            var ChildHeads = [$scope.$$childHead];
            var currentScope;
            while (ChildHeads.length) {
                currentScope = ChildHeads.shift();
                while (currentScope) {
                    console.log('currentScope:' + currentScope.qk);
                    console.log('currentScope:' + currentScope.secondOperator);
                    console.log('currentScope:' + currentScope.ruleValue);
                    console.log('currentScope:' + currentScope.uom);
                    console.log('currentScope:' + currentScope.thirdOperator);
                    console.log('infferedData:' + currentScope.infferedData);

                    if (currentScope.ruleValue !== undefined)
                        Rules.push({
                            qk: currentScope.qk,
                            secondOperator: currentScope.secondOperator,
                            ruleValue: currentScope.ruleValue,
                            uom: currentScope.uom,
                            thirdOperator: currentScope.thirdOperator,
                            infferedData: currentScope.infferedData
                        });

                    currentScope = currentScope.$$nextSibling;
                }
            }
            return Rules;
        }


        $scope.addmore = function() {
            var divElement = angular.element(document.querySelector('#contactTypeDiv'));
            var appendHtml = $compile('<row-Rule></row-Rule>')($scope);
            divElement.append(appendHtml);


        }

        vm.datePickerOpenStatus.created = false;
        vm.datePickerOpenStatus.updated = false;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .controller('ReasoningEditNonExpertDialogController', ReasoningEditNonExpertDialogController);

    ReasoningEditNonExpertDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Reasoning',"Sensor",'QuantityKinds', 'UnitOfMesurements'];

    function ReasoningEditNonExpertDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Reasoning, Sensor, QuantityKinds, UnitOfMesurements) {
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

        vm.rowQuantityKind =  [];
        vm.rowUoms = [];


        vm.quantityKinds = QuantityKinds.query(function(response) {
                       vm.sensors = [];
                        var quantityKind = vm.reasoning.quantityKind;
                            var shortQuantityKind = quantityKind.replace("http://purl.org/iot/vocab/m3-lite#","");
                            vm.selectedQuantityKind = shortQuantityKind;

                             Sensor.getByQuantityKind({quantityKind:vm.selectedQuantityKind}, function(response) {
                                 vm.sensors = response;
                                 vm.reasoning.sensor =  vm.reasoning.sensor;
                                 vm.sensor = vm.reasoning.sensor;
                                 console.log("vm.reasoning.sensor:" + vm.reasoning.sensor);
                                 console.log("vm.sensor:" + vm.sensor);
                             });

                             $rootScope.quantityKinds = vm.quantityKinds;

        });




        vm.uoms = UnitOfMesurements.query( function(response) {
           $rootScope.uoms = vm.uoms;
        });


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



        vm.rowQuantityKind =  [vm.reasoning.quantityKind];
        vm.rowUoms = [vm.reasoning.unitOfMeasurement]

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
                vm.reasoning.rules = $scope.data.fields;
                console.log(vm.reasoning);
                Reasoning.createRuleNonExpert(vm.reasoning, onSaveSuccess, onSaveError);
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

        $scope.selectSensor = function() {
           //console.log('hello');
           //alert(vm.reasoning.sensor);

           console.log(vm.sensor.unit);

           vm.reasoning.sensorEndp = vm.sensor.endp;

           vm.reasoning.sensor = vm.sensor.sensor;

           console.log(vm.reasoning.sensorEndp);

           vm.rowQuantityKind =  [vm.reasoning.quantityKind];
           vm.rowUoms = [vm.reasoning.unitOfMeasurement]

           vm.reasoning.latitude = vm.sensor.lat;
           vm.reasoning.longitude = vm.sensor.lng;
           vm.reasoning.quantityKind = vm.sensor.qk;
           vm.reasoning.unitOfMeasurement = vm.sensor.unit;

           vm.reasoning.hashedSensor = vm.sensor.hashedSensor;


           Sensor.getSensorData({id : vm.reasoning.sensorEndp}, function(response) {
           vm.reasoning.sensorSampleData = response.data;
           });

           Sensor.getSensorMeta({id: vm.sensor.hashedSensor}, function(response) {

           vm.reasoning.sensorMeta = response.data;

           });


           vm.rowQuantityKind =  [vm.sensor.shortQk];
           vm.rowUoms = [vm.sensor.shortUnit];

           $rootScope.rowQuantityKind = vm.rowQuantityKind;
           $rootScope.rowUoms = vm.rowUoms;


        }

    $scope.addRuleControl = function() {
    console.log('addRuleControl');

        var divElement = angular.element(document.querySelector('#contactTypeDiv'));
        var appendHtml = $compile('<row-Rule></row-Rule>')($scope);
        divElement.append(appendHtml);

     };

         $scope.selectQuantitykind = function() {
            vm.sensors = [];
            if(vm.selectedQuantityKind  != "") {
              Sensor.getByQuantityKind({quantityKind:vm.selectedQuantityKind}, function(response) {
                vm.sensors = response;
              });
            }
         }
         $scope.addmore = function () {

 var divElement = angular.element(document.querySelector('#contactTypeDiv'));
        var appendHtml = $compile('<row-Rule></row-Rule>')($scope);
        divElement.append(appendHtml);



        }
         $scope.ctrlFn = function(test) {
                console.log(test);
            }
        $scope.removeCurrentRule = function() {
        //console.log(field);
        console.log('con chim');

//            $scope.data.fields.push({
//                index: counter++
//            });
//            console.log($scope.data);
//            console.log($scope.data.fields.length);
        }

        vm.datePickerOpenStatus.created = false;
        vm.datePickerOpenStatus.updated = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

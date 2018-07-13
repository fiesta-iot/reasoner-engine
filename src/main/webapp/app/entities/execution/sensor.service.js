(function() {
    'use strict';
    angular
        .module('fiestaReasonerEngineApp')
        .factory('Sensor', Sensor);

    Sensor.$inject = ['$resource', 'DateUtils'];

    function Sensor ($resource, DateUtils) {
        var resourceUrl =  'api/sensors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},

            'getSensorData': { url:'api/sensors/data', method: 'POST'},
            'getSensorMeta': {url:'api/sensors/meta', method: 'POST'},
            'getByQuantityKind': {url:'api/sensors/getByQuantityKind', method: 'POST', isArray:true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.created = DateUtils.convertDateTimeFromServer(data.created);
                        data.updated = DateUtils.convertDateTimeFromServer(data.updated);
                        data.start = DateUtils.convertDateTimeFromServer(data.start);
                        data.end = DateUtils.convertDateTimeFromServer(data.end);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

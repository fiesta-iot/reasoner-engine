(function() {
    'use strict';
    angular
        .module('fiestaReasonerEngineApp')
        .factory('Execution', Execution);

    Execution.$inject = ['$resource', 'DateUtils'];

    function Execution ($resource, DateUtils) {
        var resourceUrl =  'api/executions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'execute': {method: 'POST', url: 'api/executions/execute'},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.created = DateUtils.convertDateTimeFromServer(data.created);
                        data.updated = DateUtils.convertDateTimeFromServer(data.updated);
                        data.started = DateUtils.convertDateTimeFromServer(data.started);
                        data.ended = DateUtils.convertDateTimeFromServer(data.ended);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

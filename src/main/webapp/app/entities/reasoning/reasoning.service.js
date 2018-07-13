(function() {
    'use strict';
    angular
        .module('fiestaReasonerEngineApp')
        .factory('Reasoning', Reasoning);

    Reasoning.$inject = ['$resource', 'DateUtils'];

    function Reasoning ($resource, DateUtils) {
        var resourceUrl =  'api/reasonings/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'createRuleNonExpert': { method: 'POST', url:'api/reasonings/nonExpert'},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.created = DateUtils.convertDateTimeFromServer(data.created);
                        data.updated = DateUtils.convertDateTimeFromServer(data.updated);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

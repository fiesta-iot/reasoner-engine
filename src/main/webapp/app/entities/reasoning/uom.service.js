(function() {
    'use strict';
    angular
        .module('fiestaReasonerEngineApp')
        .factory('UnitOfMesurements', UnitOfMesurements);

    UnitOfMesurements.$inject = ['$resource'];

    function UnitOfMesurements ($resource) {
        var resourceUrl =  'api/uoms/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

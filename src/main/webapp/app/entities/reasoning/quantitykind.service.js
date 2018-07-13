(function() {
    'use strict';
    angular
        .module('fiestaReasonerEngineApp')
        .factory('QuantityKinds', QuantityKinds);

    QuantityKinds.$inject = ['$resource'];

    function QuantityKinds ($resource) {
        var resourceUrl =  'api/quantityKinds/:id';

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

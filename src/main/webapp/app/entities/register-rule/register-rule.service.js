(function() {
    'use strict';
    angular
        .module('fiestaReasonerEngineApp')
        .factory('RegisterRule', RegisterRule);

    RegisterRule.$inject = ['$resource'];

    function RegisterRule ($resource) {
        var resourceUrl =  'api/register-rules/:id';

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

(function() {
    'use strict';
    angular
        .module('fiestaReasonerEngineApp')
        .factory('OpenAM', OpenAM);

    OpenAM.$inject = ['$resource', 'DateUtils'];

    function OpenAM ($resource, DateUtils) {
        var resourceUrl =  'api/account/:id';

        return $resource(resourceUrl, {}, {
            'getCurrentAccount': { method: 'GET', url:'api/getCurrentAccount'}

        });
    }
})();

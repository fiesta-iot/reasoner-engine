(function () {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();

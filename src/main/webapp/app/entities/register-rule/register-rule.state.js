(function() {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('register-rule', {
            parent: 'entity',
            url: '/register-rule?page&sort&search',
            data: {

                pageTitle: 'RegisterRules'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/register-rule/register-rules.html',
                    controller: 'RegisterRuleController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('register-rule-detail', {
            parent: 'entity',
            url: '/register-rule/{id}',
            data: {

                pageTitle: 'RegisterRule'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/register-rule/register-rule-detail.html',
                    controller: 'RegisterRuleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RegisterRule', function($stateParams, RegisterRule) {
                    return RegisterRule.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'register-rule',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('register-rule-detail.edit', {
            parent: 'register-rule-detail',
            url: '/detail/edit',
            data: {

            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/register-rule/register-rule-dialog.html',
                    controller: 'RegisterRuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RegisterRule', function(RegisterRule) {
                            return RegisterRule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('register-rule.new', {
            parent: 'register-rule',
            url: '/new',
            data: {

            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/register-rule/register-rule-dialog.html',
                    controller: 'RegisterRuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                ruleContent: null,
                                data: null,
                                sensor: null,
                                sensorMeta: null,
                                inferredData: null,
                                fullData: null,
                                userId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('register-rule', null, { reload: 'register-rule' });
                }, function() {
                    $state.go('register-rule');
                });
            }]
        })
        .state('register-rule.edit', {
            parent: 'register-rule',
            url: '/{id}/edit',
            data: {

            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/register-rule/register-rule-dialog.html',
                    controller: 'RegisterRuleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RegisterRule', function(RegisterRule) {
                            return RegisterRule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('register-rule', null, { reload: 'register-rule' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('register-rule.delete', {
            parent: 'register-rule',
            url: '/{id}/delete',
            data: {

            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/register-rule/register-rule-delete-dialog.html',
                    controller: 'RegisterRuleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RegisterRule', function(RegisterRule) {
                            return RegisterRule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('register-rule', null, { reload: 'register-rule' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

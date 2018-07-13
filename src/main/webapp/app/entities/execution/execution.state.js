(function() {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('execution', {
            parent: 'entity',
            url: '/execution?page&sort&search',
            data: {

                pageTitle: 'Executions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/execution/executions.html',
                    controller: 'ExecutionController',
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
                }]
            }
        })
        .state('execution-detail', {
            parent: 'entity',
            url: '/execution/{id}',
            data: {

                pageTitle: 'Execution'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/execution/execution-detail.html',
                    controller: 'ExecutionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Execution', function($stateParams, Execution) {
                    return Execution.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'execution',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('execution-detail.edit', {
            parent: 'execution-detail',
            url: '/detail/edit',
            data: {

            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/execution/execution-dialog-edit.html',
                    controller: 'ExecutionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Execution', function(Execution) {
                            return Execution.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('execution.new', {
            parent: 'execution',
            url: '/new',
            data: {

            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/execution/execution-dialog.html',
                    controller: 'ExecutionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                created: null,
                                updated: null,
                                started: null,
                                ended: null,
                                ruleContent: null,
                                originalData: null,
                                infferedData: null,
                                fullData: null,
                                userId: null,
                                sensor: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('execution', null, { reload: 'execution' });
                }, function() {
                    $state.go('execution');
                });
            }]
        })
        .state('execution.edit', {
            parent: 'execution',
            url: '/{id}/edit',
            data: {

            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/execution/execution-dialog.html',
                    controller: 'ExecutionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Execution', function(Execution) {
                            return Execution.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('execution', null, { reload: 'execution' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('execution.delete', {
            parent: 'execution',
            url: '/{id}/delete',
            data: {

            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/execution/execution-delete-dialog.html',
                    controller: 'ExecutionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Execution', function(Execution) {
                            return Execution.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('execution', null, { reload: 'execution' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

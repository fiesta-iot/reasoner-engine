(function() {
    'use strict';

    angular
        .module('fiestaReasonerEngineApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('reasoning', {
            parent: 'entity',
            url: '/reasoning?page&sort&search',
            data: {

                pageTitle: 'Reasonings'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reasoning/reasonings.html',
                    controller: 'ReasoningController',
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
        .state('reasoning-detail', {
            parent: 'entity',
            url: '/reasoning/{id}',
            data: {

                pageTitle: 'Reasoning'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reasoning/reasoning-detail.html',
                    controller: 'ReasoningDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Reasoning', function($stateParams, Reasoning) {
                    return Reasoning.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'reasoning',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('reasoning-detail.edit', {
            parent: 'reasoning-detail',
            url: '/detail/edit',
            data: {

            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reasoning/reasoning-dialog.html',
                    controller: 'ReasoningDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Reasoning', function(Reasoning) {
                            return Reasoning.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })


        .state('reasoning.new', {
            parent: 'reasoning',
            url: '/new',
            data: {

            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reasoning/reasoning-dialog.html',
                    controller: 'ReasoningDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                userId: null,
                                created: null,
                                updated: null,
                                content: null,
                                sensor: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('reasoning', null, { reload: 'reasoning' });
                }, function() {
                    $state.go('reasoning');
                });
            }]
        })
        .state('reasoning.newNonExpert', {
                    parent: 'reasoning',
                    url: '/newWithNonExpert',
                    data: {

                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                        $uibModal.open({
                            templateUrl: 'app/entities/reasoning/reasoning-dialog-non-expert.html',
                            controller: 'ReasoningNonExpertDialogController',
                            controllerAs: 'vm',
                            backdrop: 'static',
                            size: 'lg',
                            resolve: {
                                entity: function () {
                                    return {
                                        name: null,
                                        userId: null,
                                        created: null,
                                        updated: null,
                                        content: null,
                                        sensor: null,
                                        description: null,
                                        id: null
                                    };
                                }
                            }
                        }).result.then(function() {
                            $state.go('reasoning', null, { reload: 'reasoning' });
                        }, function() {
                            $state.go('reasoning');
                        });
                    }]
                })
        .state('reasoning.edit', {
            parent: 'reasoning',
            url: '/{id}/edit',
            data: {

            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reasoning/reasoning-dialog.html',
                    controller: 'ReasoningDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Reasoning', function(Reasoning) {
                            return Reasoning.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reasoning', null, { reload: 'reasoning' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reasoning.editNonExpert', {
                    parent: 'reasoning',
                    url: '/{id}/editNonExpert',
                    data: {

                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                        $uibModal.open({
                            templateUrl: 'app/entities/reasoning/reasoning-edit-non-expert.html',
                            controller: 'ReasoningEditNonExpertDialogController',
                            controllerAs: 'vm',
                            backdrop: 'static',
                            size: 'lg',
                            resolve: {
                                entity: ['Reasoning', function(Reasoning) {
                                    return Reasoning.get({id : $stateParams.id}).$promise;
                                }]
                            }
                        }).result.then(function() {
                            $state.go('reasoning', null, { reload: 'reasoning' });
                        }, function() {
                            $state.go('^');
                        });
                    }]
                })
        .state('reasoning.delete', {
            parent: 'reasoning',
            url: '/{id}/delete',
            data: {

            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reasoning/reasoning-delete-dialog.html',
                    controller: 'ReasoningDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Reasoning', function(Reasoning) {
                            return Reasoning.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reasoning', null, { reload: 'reasoning' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

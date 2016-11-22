(function() {
    'use strict';

    angular
        .module('myPharmacyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('drug', {
            parent: 'entity',
            url: '/drug?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Drugs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/drug/drugs.html',
                    controller: 'DrugController',
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
        .state('drug-detail', {
            parent: 'entity',
            url: '/drug/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Drug'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/drug/drug-detail.html',
                    controller: 'DrugDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Drug', function($stateParams, Drug) {
                    return Drug.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'drug',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('drug-detail.edit', {
            parent: 'drug-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drug/drug-dialog.html',
                    controller: 'DrugDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Drug', function(Drug) {
                            return Drug.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('drug.new', {
            parent: 'drug',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drug/drug-dialog.html',
                    controller: 'DrugDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('drug', null, { reload: 'drug' });
                }, function() {
                    $state.go('drug');
                });
            }]
        })
        .state('drug.edit', {
            parent: 'drug',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drug/drug-dialog.html',
                    controller: 'DrugDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Drug', function(Drug) {
                            return Drug.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('drug', null, { reload: 'drug' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('drug.delete', {
            parent: 'drug',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drug/drug-delete-dialog.html',
                    controller: 'DrugDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Drug', function(Drug) {
                            return Drug.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('drug', null, { reload: 'drug' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

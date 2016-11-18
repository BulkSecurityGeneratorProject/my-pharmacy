(function () {
    'use strict';

    angular
        .module('myPharmacyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {
        $stateProvider.state('experiments', {
                parent: 'app',
                url: '/experiment',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'app/experiments/experiments.html',
                        controller: 'ExperimentController',
                        controllerAs: 'vm'
                    }
                }, resolve: {
                    entity: function () {
                        return {
                            patient: null,
                            drug: null,
                            dosage: 1.1,
                            concentration1: 0.705,
                            concentration2: 1.469,
                            concentration3: 1.263,
                            concentration4: 1.130,
                            concentration5: 0.624,
                            time1: new Date("Thu Jan 01 1970 01:50:00 GMT+0200 (EET)"),
                            time2: new Date("Thu Jan 01 1970 04:20:00 GMT+0200 (EET)"),
                            time3: new Date("Thu Jan 01 1970 04:50:00 GMT+0200 (EET)"),
                            time4: new Date("Thu Jan 01 1970 05:50:00 GMT+0200 (EET)"),
                            time5: new Date("Thu Jan 01 1970 08:50:00 GMT+0200 (EET)")
                        }

                    }
                }
            }
        ).state('experiments.result', {
            parent: 'experiments',
            url: '/experiment/result',
            params: { experiment: null , stats:null },
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/experiments/experiments-result.html',
                    controller: 'ExperimentResultController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                x: null,
                                y: null,
                                xxs: [],
                                yys: []
                            }
                        }
                    }
                }).result.then(function () {
                    $state.go('^', {}, {reload: false});
                }, function () {
                    $state.go('^');
                }
                );

            }]

        })
    }})
();


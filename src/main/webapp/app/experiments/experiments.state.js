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
            }
        });
    }
})();


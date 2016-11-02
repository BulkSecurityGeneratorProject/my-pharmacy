(function () {
    'use strict';

    angular
        .module('myPharmacyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {
        $stateProvider.state('experiments', {
            abstract: true,
            parent: 'app'
        });
    }
})();


(function() {
    'use strict';
    angular
        .module('myPharmacyApp')
        .factory('Experiment', Experiment);

    Experiment.$inject = ['$resource'];

    function Experiment ($resource) {
        var resourceUrl =  'api/experiments/busulfan';

        return $resource(resourceUrl, {}, {
            'post': { method: 'POST'}
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('myPharmacyApp')
        .factory('Drug', Drug);

    Drug.$inject = ['$resource'];

    function Drug ($resource) {
        var resourceUrl =  'api/drugs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }

        });
    }
})();

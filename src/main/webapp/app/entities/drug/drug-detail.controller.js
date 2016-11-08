(function() {
    'use strict';

    angular
        .module('myPharmacyApp')
        .controller('DrugDetailController', DrugDetailController);

    DrugDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Drug'];

    function DrugDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Drug) {
        var vm = this;

        vm.drug = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('myPharmacyApp:drugUpdate', function(event, result) {
            vm.drug = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

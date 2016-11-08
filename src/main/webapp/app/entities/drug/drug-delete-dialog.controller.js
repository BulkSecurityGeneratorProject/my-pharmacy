(function() {
    'use strict';

    angular
        .module('myPharmacyApp')
        .controller('DrugDeleteController',DrugDeleteController);

    DrugDeleteController.$inject = ['$uibModalInstance', 'entity', 'Drug'];

    function DrugDeleteController($uibModalInstance, entity, Drug) {
        var vm = this;

        vm.drug = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Drug.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

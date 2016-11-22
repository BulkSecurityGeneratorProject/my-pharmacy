(function() {
    'use strict';

    angular
        .module('myPharmacyApp')
        .controller('DrugDialogController', DrugDialogController);

    DrugDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Drug'];

    function DrugDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Drug) {
        var vm = this;

        vm.drug = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }


        function save () {
            vm.isSaving = true;
            if (vm.drug.id !== null) {
                Drug.update(vm.drug, onSaveSuccess, onSaveError);
            } else {
                Drug.save(vm.drug, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myPharmacyApp:drugUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

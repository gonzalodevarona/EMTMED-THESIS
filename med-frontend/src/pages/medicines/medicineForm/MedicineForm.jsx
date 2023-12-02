import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { Button, Stack, MenuItem, Divider } from "@mui/material";
import { DevTool } from "@hookform/devtools";
import FormTextfield from '../../../components/form/FormTextfield';
import FormSelect from '../../../components/form/FormSelect';
import Header from '../../../components/Header';
import triggerInfoAlert from "../../../components/alerts/InfoAlert";
import WeightUnitService from '../../../services/weightUnitService';
import CountingUnitService from '../../../services/countingUnitService';
import MedicationBatchService from '../../../services/medicationBatchService';
import { refreshPage } from "../../../utils/CommonMethods";
import MedicineService from "../../../services/medicineService";
import MedicationBatchFormEmbedded from "../../medicationBatches/medicationBatchForm/MedicationBatchFormEmbedded";

function MedicineForm({ action, preloadedData, id }) {

  const [weightUnits, setWeightUnits] = useState([])
  const [countingUnits, setCountingUnits] = useState([])

  const [medicationBatches, setMedicationBatches] = useState([{ id: 1 }])


  useEffect(() => {
    async function fetchWeightUnits() {
      let data = await WeightUnitService.getWeightUnits();
      setWeightUnits(data);
    }
    async function fetchCountingUnits() {
      let data = await CountingUnitService.getCountingUnits();
      setCountingUnits(data);
    }


    fetchWeightUnits()
    fetchCountingUnits()
  }, [])


  async function addMedicine(medicine) {
    return await MedicineService.addMedicine(medicine);
  }

  async function editMedicine(medicine) {
    medicine.id = id;
    return await MedicineService.editMedicine(medicine);
  }

  const addMedicationBatch = () => {
    setMedicationBatches([...medicationBatches, { id: medicationBatches.length + 1 }]);
  };

  function deleteMedicationBatch(batchId) {
    const newBatches = medicationBatches.filter((batch) => batch.id !== batchId);
    setMedicationBatches(newBatches);
  }

  const updateMedicationBatch = (id, newValues) => {
    setMedicationBatches(prevState => {
      return prevState.map(batch => {
        if (batch.id === id) {
          return { ...batch, ...newValues };
        } else {
          return batch;
        }
      });
    });
  };

  const {
    handleSubmit,
    register,
    formState: { errors },
    control,
    setValue
  } = useForm({
    defaultValues: preloadedData
  });

  useEffect(() => {
    const totalQuantity = medicationBatches.reduce((total, batch) => total + (batch.quantity | 0), 0);
    setValue('quantity', totalQuantity)
  }, [medicationBatches])


  function addedSuccessfully() {
    triggerInfoAlert('success', 'El nuevo medicamento ha sido agregado', refreshPage)
  }
  function errorAdding() {
    triggerInfoAlert('error', 'Ha habido un error agregando el nuevo medicamento')
  }

  function editedSuccessfully() {
    triggerInfoAlert('success', 'El medicamento ha sido editada', refreshPage)
  }
  function errorEditing() {
    triggerInfoAlert('error', 'Ha habido un error editando el medicamento')
  }

  async function onSubmit(data) {
    data.batches = medicationBatches.map(obj => {
      let { id, ...rest } = obj;
      return rest;
    });
    data.countingUnit = { id: data.countingUnit };
    data.weightUnit = { id: data.weightUnit };


    switch (action) {
      case 'add':
        addMedicine(data)
          .then((result) => {
            console.log(result)
            addedSuccessfully()
          })
          .catch((error) => {
            errorAdding()
            console.error('Error en la operación:', error);
          });
        break;
      case 'edit':

        editMedicine(data)
          .then((result) => {
            if (result.status === 500) {
              errorEditing()
              console.error('Error en la operación:', result);
            } else {
              editedSuccessfully()
            }
          })
        break;
      default:
        break;
    }
  };

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        <Stack spacing={2} width={400} my={3}>

          <FormTextfield
            isRequired
            disabled={action === 'edit' ? true : false}
            label='Nombre'
            name='name'
            register={register}
            errors={errors} />

          <FormTextfield
            isRequired
            disabled={action === 'edit' ? true : false}
            label='Compuesto Activo'
            name='activePharmaceuticalIngredient'
            register={register}
            errors={errors} />

          <FormTextfield
            isRequired
            disabled={action === 'edit' ? true : false}
            label='Concentración'
            name='concentration'
            register={register}
            errors={errors} />

          <FormTextfield
            type="number"
            isRequired
            disabled
            label='Cantidad Total (cantidades de todos los lotes)'
            name='quantity'
            register={register}
            errors={errors} />

          {countingUnits.length > 0 &&
            <FormSelect
              name="countingUnit"
              label="Unidad de Conteo"
              disabled={action === 'edit' ? true : false}
              defaultValue={action === 'edit' ? preloadedData?.countingUnit.id : countingUnits[0].id}
              register={register}
              errors={errors}
            >

              {countingUnits.map(countingUnit => <MenuItem key={countingUnit.id} value={countingUnit.id}>
                {countingUnit.name}
              </MenuItem>)}
            </FormSelect>
          }

          <FormTextfield
            type="number"
            isRequired
            label='Peso'
            name='weight'
            register={register}
            errors={errors} />


          {weightUnits.length > 0 &&
            <FormSelect

              name="weightUnit"
              label="Unidad de Peso"
              defaultValue={action === 'edit' ? preloadedData?.weightUnit.id : weightUnits[0].id}
              register={register}
              errors={errors}
            >

              {weightUnits.map(weightUnit => <MenuItem key={weightUnit.id} value={weightUnit.id}>
                {weightUnit.name}
              </MenuItem>)}
            </FormSelect>
          }

          <Button onClick={addMedicationBatch} variant="contained" color="info">
            Agregar lote de medicamento
          </Button>

          <Divider />

          <Button type="submit" variant="contained" color="info">
            {action === 'add' ? 'Agregar medicamento' : 'Editar medicamento'}
          </Button>

        </Stack>

      </form>

      {medicationBatches.map((batch, index) => (


        < Stack my={4} key={batch.id} >
          <Header title='Agregar un lote de medicamento' />
          <MedicationBatchFormEmbedded
            action='add'
            addMedicationBatch={updateMedicationBatch}
            id={batch.id}
            deleteMedicationBatch={index === 0 ? null : deleteMedicationBatch} />

        </Stack >
      ))
      }


    </>
  )
}

export default MedicineForm
import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { Grid, Stack, MenuItem } from "@mui/material";
import { useKeycloak } from '@react-keycloak/web'
import { DevTool } from "@hookform/devtools";
import FormTextfield from '../../../components/form/FormTextfield';
import FormSelect from '../../../components/form/FormSelect';
import Header from '../../../components/Header';
import AddIcon from '@mui/icons-material/Add';
import FabSubmitButton from '../../../components/buttons/FabSubmitButton';
import FabActionButton from '../../../components/buttons/FabActionButton';
import triggerInfoAlert from "../../../components/alerts/InfoAlert";
import WeightUnitService from '../../../services/weightUnitService';
import CountingUnitService from '../../../services/countingUnitService';
import MedicationBatchService from '../../../services/medicationBatchService';
import MedicineService from "../../../services/medicineService";
import MedicationBatchFormEmbedded from "../../../pages/medicationBatches/medicationBatchForm/MedicationBatchFormEmbedded";
import { useNavigate } from "react-router-dom";

function MedicineForm({ action, preloadedData }) {

  const navigate = useNavigate();

  const { keycloak } = useKeycloak()

  const redirect = (path) => {
    navigate(path);
  };

  function fetchUser() {
    keycloak.loadUserProfile().then((profile) => {
      setValue('idNumberCreatedBy', profile.attributes.idNumber[0])
    })
  }


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

    if (action === 'edit') {
      let fetchedBatches = Object.values(preloadedData.batches);
      fetchedBatches = fetchedBatches.map(batch => ({ ...batch, existing: true }));
      setMedicationBatches(fetchedBatches);
    }


    fetchWeightUnits()
    fetchCountingUnits()
    fetchUser()
  }, [])

  async function addMedicine(medicine) {
    return await MedicineService.addMedicine(medicine);
  }

  async function editMedicine(medicine) {

    console.log(medicine);
    if (medicine.batches) {
      for (let i = 0; i < medicine.batches.length; i++) {
        medicine.batches[i].existing = undefined
        if (typeof medicine.batches[i].expirationDate !== 'string') {
          medicine.batches[i].expirationDate = `${medicine.batches[i].expirationDate[0]}-${medicine.batches[i].expirationDate[1]}-${medicine.batches[i].expirationDate[2]}`
        }

      }
    }
    console.log(medicine)
    return await MedicineService.editMedicine(medicine);
  }

  const addMedicationBatch = () => {
    if (action === 'add') {
      setMedicationBatches([...medicationBatches, { id: medicationBatches.length + 1 }]);
    } else {
      setMedicationBatches([
        ...medicationBatches,
        { id: medicationBatches[medicationBatches.length - 1].id + 1 }
      ]);

    }

  };

  async function deleteMedicationBatch(batchId) {
    const newBatches = medicationBatches.filter((batch) => batch.id !== batchId);
    setMedicationBatches(newBatches);

    if (action === 'edit') {
      await MedicationBatchService.deleteMedicationBatch(batchId)
    }
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
    setValue,
    reset
  } = useForm({
    defaultValues: preloadedData
  });

  useEffect(() => {
    if (medicationBatches.length > 0 && medicationBatches.every(medicationBatch => {
      return medicationBatch.hasOwnProperty('quantity');
    })) {
      const totalQuantity = medicationBatches.reduce((total, batch) => total + (batch.quantity | 0), 0);
      setValue('quantity', totalQuantity)
    }

  }, [medicationBatches])

  function resetForm() {
    reset()
    setMedicationBatches([{ id: 1 }])
    fetchUser()

  }


  function addedSuccessfully() {
    triggerInfoAlert('success', 'El nuevo medicamento ha sido agregado', resetForm)
  }
  function errorAdding() {
    triggerInfoAlert('error', 'Ha habido un error agregando el nuevo medicamento')
  }
  function errorAddingFields() {
    triggerInfoAlert('error', 'Ha habido un error agregando el nuevo medicamento, revisa los campos del formulario')
  }

  function editedSuccessfully() {
    triggerInfoAlert('success', 'El medicamento ha sido editado', () => redirect(-1))
  }
  function errorEditing() {
    triggerInfoAlert('error', 'Ha habido un error editando el medicamento')
  }

  async function onSubmit(data) {
    console.log(medicationBatches)
    data.batches = medicationBatches.map(obj => {
      obj.quantity = Number(obj.quantity);
      if (!obj.existing) {
        let { id, ...rest } = obj;
        return rest;
      } else {
        return obj;
      }
    });

    if (action === 'add') {
      data.countingUnit = { id: data.countingUnit };
      data.weightUnit = { id: data.weightUnit };
    }

    data.type = 'medicine'
    if (checkQuantity(data.batches) && data.quantity > 0) {

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

    } else {

      action === 'add' ? errorAddingFields() : errorEditing();
      return;
    }
  };

  useEffect(() => {
    console.log(medicationBatches)
  }, [medicationBatches])


  function checkQuantity(arr) {
    return !arr.some(obj => {
      return !(obj.hasOwnProperty('quantity') && obj.quantity > 0);
    });
  }


  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        <Stack spacing={2} width={400} my={3} alignItems='center'>

          <FormTextfield
            isRequired
            disabled
            InputLabelProps={{
              shrink: true,
            }}
            label='CC Responsable'
            name='idNumberCreatedBy'
            register={register}
            errors={errors} />

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
            InputLabelProps={{
              shrink: true,
            }}
            disabled
            label='Cantidad Total (cantidades de todos los lotes)'
            name='quantity'
            register={register}
            errors={errors} />

          {countingUnits.length > 0 &&
            <FormSelect
              name="countingUnit"
              required
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
              required
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

          <FabSubmitButton color='info' />

        </Stack>

      </form>

      <Header title='Lotes de Medicamentos' />

      <FabActionButton handleClick={addMedicationBatch} color='secondary' icon={<AddIcon />} />

      {medicationBatches.length > 0 &&
        <Grid container justifyContent='center' rowSpacing={4} >

          {medicationBatches.map((batch, index) => (


            <Grid key={batch.id} item xs={12} lg={4} sx={{ mt: 2 }}>
              <MedicationBatchFormEmbedded
                action={action === 'edit' && batch.quantity ? 'edit' : 'add'}
                addMedicationBatch={updateMedicationBatch}
                id={index + 1}
                preloadedData={action === 'edit' ? batch : undefined}
                deleteMedicationBatch={index === 0 ? null : deleteMedicationBatch} />
            </Grid>

          ))}
        </Grid>
      }


    </>
  )
}

export default MedicineForm
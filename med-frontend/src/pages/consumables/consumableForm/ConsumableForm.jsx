import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { Grid, Stack, MenuItem } from "@mui/material";
import { useKeycloak } from '@react-keycloak/web'
import { DevTool } from "@hookform/devtools";
import AddIcon from '@mui/icons-material/Add';
import FormTextfield from '../../../components/form/FormTextfield';
import FormSelect from '../../../components/form/FormSelect';
import Header from '../../../components/Header';
import triggerInfoAlert from "../../../components/alerts/InfoAlert";
import CountingUnitService from '../../../services/countingUnitService';

import BatchService from "../../../services/batchService";
import ConsumableService from "../../../services/consumableService";
import BatchFormEmbedded from "../../../pages/batches/batchForm/BatchFormEmbedded";
import FabActionButton from "../../../components/buttons/FabActionButton";
import FabSubmitButton from "../../../components/buttons/FabSubmitButton";

function ConsumableForm({ action, preloadedData }) {

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

  
  const [countingUnits, setCountingUnits] = useState([])

  const [batches, setBatches] = useState([{ id: 1 }])


  useEffect(() => {
   
    async function fetchCountingUnits() {
      let data = await CountingUnitService.getCountingUnits();
      setCountingUnits(data);
    }

    if (action === 'edit') {
      let fetchedBatches = Object.values(preloadedData.batches);
      fetchedBatches = fetchedBatches.map(batch => ({ ...batch, existing: true }));
      setBatches(fetchedBatches);
    }

    fetchCountingUnits()
    fetchUser()
  }, [])

  async function addConsumable(consumable) {
    console.log(consumable)
    return await ConsumableService.addConsumable(consumable);
  }

  async function editConsumable(consumable) {

    if (consumable.batches) {
      for (let i = 0; i < consumable.batches.length; i++) {
        consumable.batches[i].existing = undefined
        if (typeof consumable.batches[i].expirationDate !== 'string') {
          consumable.batches[i].expirationDate = `${consumable.batches[i].expirationDate[0]}-${consumable.batches[i].expirationDate[1]}-${consumable.batches[i].expirationDate[2]}`
        }

      }
    }
    console.log(consumable)
    return await ConsumableService.editConsumable(consumable);
  }

  const addBatch = () => {
    if (action === 'add') {
      setBatches([...batches, { id: batches.length + 1 }]);
    } else {
      setBatches([
        ...batches,
        { id: batches[batches.length - 1].id + 1 }
      ]);

    }

  };

  async function deleteBatch(batchId) {
    const newBatches = batches.filter((batch) => batch.id !== batchId);
    setBatches(newBatches);

    if (action === 'edit') {
      await BatchService.deleteBatch(batchId)
    }
  }



  const updateBatch = (id, newValues) => {
    setBatches(prevState => {
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
    reset,
    setValue
  } = useForm({
    defaultValues: preloadedData
  });

  useEffect(() => {
    if (batches.length > 0 && batches.every(medicationBatch => {
      return medicationBatch.hasOwnProperty('quantity');
    })) {
      const totalQuantity = batches.reduce((total, batch) => total + (batch.quantity | 0), 0);
      setValue('quantity', totalQuantity)
    }

  }, [batches])

  function resetForm() {
    reset()
    setBatches([{ id: 1 }])

  }



  function addedSuccessfully() {
    triggerInfoAlert('success', 'El nuevo insumo ha sido agregado', resetForm)
  }
  function errorAdding() {
    triggerInfoAlert('error', 'Ha habido un error agregando el nuevo insumo')
  }
  function errorAddingFields() {
    triggerInfoAlert('error', 'Ha habido un error agregando el nuevo insumo, revisa los campos del formulario')
  }

  function editedSuccessfully() {
    triggerInfoAlert('success', 'El insumo ha sido editado', () => redirect(-1))
  }
  function errorEditing() {
    triggerInfoAlert('error', 'Ha habido un error editando el insumo')
  }

  async function onSubmit(data) {
    console.log(batches)
    data.batches = batches.map(obj => {
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
    }

    data.type = 'consumable'
    if (checkQuantity(data.batches) && data.quantity > 0) {

      switch (action) {
        case 'add':
          addConsumable(data)
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

          editConsumable(data)
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
    console.log(batches)
  }, [batches])


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
            type="number"
            isRequired
            disabled
            InputLabelProps={{
              shrink: true,
            }}
            label='Cantidad Total (cantidades de todos los lotes)'
            name='quantity'
            register={register}
            errors={errors} />

          {countingUnits.length > 0 &&
            <FormSelect
              required
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

          <FabSubmitButton color='info' />

        </Stack>

      </form>
      <Header title='Lotes' />

      <FabActionButton handleClick={addBatch} color='secondary' icon={<AddIcon />} />

      {batches.length > 0 &&
        <Grid container justifyContent='center' rowSpacing={4} >

          {batches.map((batch, index) => (
            
            <Grid key={batch.id} item xs={12} lg={4} sx={{ mt: 2 }}>
              <BatchFormEmbedded
                action={action === 'edit' && batch.quantity ? 'edit' : 'add'}
                addBatch={updateBatch}
                id={action === 'add' ? index + 1 : batch.id}
                preloadedData={action === 'edit' ? batch : undefined}
                deleteBatch={index === 0 ? null : deleteBatch} />
            </Grid>
          ))}

        </Grid>

      }


    </>
  )
}

export default ConsumableForm
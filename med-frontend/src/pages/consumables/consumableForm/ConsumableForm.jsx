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

import { refreshPage } from "../../../utils/CommonMethods";
import BatchService from "../../../services/batchService";
import ConsumableService from "../../../services/consumableService";
import BatchFormEmbedded from "../../../pages/batches/batchForm/BatchFormEmbedded";

function ConsumableForm({ action, preloadedData, id }) {

  const [weightUnits, setWeightUnits] = useState([])
  const [countingUnits, setCountingUnits] = useState([])

  const [batches, setBatches] = useState([{ id: 1 }])


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
      setBatches(fetchedBatches);
    }


    fetchWeightUnits()
    fetchCountingUnits()
  }, [])

  async function addConsumable(consumable) {
    return await ConsumableService.addConsumable(consumable);
  }

  async function editConsumable(consumable) {

    console.log(consumable);
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


  function addedSuccessfully() {
    triggerInfoAlert('success', 'El nuevo medicamento ha sido agregado', refreshPage)
  }
  function errorAdding() {
    triggerInfoAlert('error', 'Ha habido un error agregando el nuevo medicamento')
  }
  function errorAddingFields() {
    triggerInfoAlert('error', 'Ha habido un error agregando el nuevo medicamento, revisa los campos del formulario')
  }

  function editedSuccessfully() {
    triggerInfoAlert('success', 'El medicamento ha sido editado', refreshPage)
  }
  function errorEditing() {
    triggerInfoAlert('error', 'Ha habido un error editando el medicamento')
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
      data.weightUnit = { id: data.weightUnit };
    }

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
                // errorEditing()
                console.error('Error en la operación:', result);
              } else {
                // editedSuccessfully()
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
        <Stack spacing={2} width={400} my={3}>

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

          <Button onClick={addBatch} variant="contained" color="info">
            Agregar lote
          </Button>

          <Divider />

          <Button type="submit" variant="contained" color="info">
            {action === 'add' ? 'Agregar consumible' : 'Editar consumible'}
          </Button>

        </Stack>

      </form>

      {batches.length > 0 && batches.map((batch, index) => (
        <Stack my={4} key={batch.id}>
          <Header title={action === 'add' ? 'Agregar un lote' : 'Editar un lote'} />
          <BatchFormEmbedded
            action={action === 'edit' && batch.quantity ? 'edit' : 'add'}
            addBatch={updateBatch}
            id={batch.id}
            preloadedData={action === 'edit' ? batch : undefined}
            deleteBatch={index === 0 ? null : deleteBatch} />
        </Stack>
      ))}


    </>
  )
}

export default ConsumableForm
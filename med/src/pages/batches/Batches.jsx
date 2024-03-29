import { useEffect, useState } from 'react'
import CustomTable from '../../components/tables/CustomTable'
import Header from '../../components/Header'
import { Box, Typography } from '@mui/material'
import { dateArrayToString } from '../../utils/EntityProcessingMethods';
import MedicationBatchService from '../../services/medicationBatchService';
import BatchService from '../../services/batchService';
import { useTranslation } from 'react-i18next';

function Batches() {
  const { t } = useTranslation();

  const [medicationBatches, setMedicationBatches] = useState([])
  const [batches, setBatches] = useState([])

  useEffect(() => {

    async function fetchData() {
      const allMedicationBatches = await MedicationBatchService.getAllMedicationBatchesByIsAvailable(true);
      const allBatches = await BatchService.getBatches();

      for (let medicationBatch of allMedicationBatches) {

        const id = medicationBatch.id;
        const medicineResponse = await MedicationBatchService.getMedicineByMedicationBatchId(id);
        const locationResponse = await MedicationBatchService.getLocationByMedicationBatchId(id);

        medicationBatch.location = locationResponse;
        medicationBatch.medicine = medicineResponse;

      }

      for (let batch of allBatches) {

        const id = batch.id;
        const consumableResponse = await BatchService.getConsumableByBatchId(id);
        const locationResponse = await BatchService.getLocationByBatchId(id);

        batch.location = locationResponse;
        batch.consumable = consumableResponse;
        batch.expirationDate = dateArrayToString(batch.expirationDate)

      }

      setMedicationBatches(allMedicationBatches)
      setBatches(allBatches)

    }

    fetchData()

  }, [])

  useEffect(() => {
    console.log(medicationBatches)
  }, [medicationBatches])

  useEffect(() => {
    console.log(batches)
  }, [batches])

  return (
    <>
      <Header title={"Lote de Medicamento"} />

      <Typography>Nota: Para agregar, editar o eliminar cualquier tipo de lote, se debe hacer desde el módulo del recurso respectivo (Medicamentos o Insumos).</Typography>

      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Medicamento', field: 'medicine.name' },
          { title: 'Vía de Administración', field: 'administrationRoute' },
          { title: 'Concentración', field: 'medicine.concentration' },
          { title: 'Cantidad', field: 'quantity', type: 'numeric' },
          { title: 'Semaforización', field: 'status', render: rowData => t(`batch.status.${rowData.status}`) },
          { title: 'Ubicación', field: 'location.name' },
          { title: 'CUM', field: 'cum' },
        ]}
        clickable
        singleEntity='lote de medicamento'
        entity='lotes-medicamentos'
        deleteable={false}
        editable={false}
        data={medicationBatches} />

      <Box my={5}></Box>


      <Header title={"Lote"} />

      <Typography>Nota: Para agregar, editar o eliminar cualquier tipo de lote, se debe hacer desde el módulo del recurso respectivo (Medicamentos o Insumos).</Typography>

      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Insumo', field: 'consumable.name' },
          { title: 'Fabricante', field: 'manufacturer' },
          { title: 'Vía de Administración', field: 'administrationRoute' },
          { title: 'Cantidad', field: 'quantity', type: 'numeric' },
          { title: 'Fecha de Vencimiento', field: 'expirationDate' },
          { title: 'Semaforización', field: 'status', render: rowData => t(`batch.status.${rowData.status}`) },
          { title: 'Ubicación', field: 'location.name' },

        ]}
        clickable
        singleEntity='lote'
        entity='lotes'
        deleteable={false}
        editable={false}
        data={batches} />

    </>
  )
}

export default Batches
import { useEffect, useState } from 'react'
import CustomTable from '../../components/CustomTable'
import Header from '../../components/Header'
import { Box, Button, Typography } from '@mui/material'
import { Link } from 'react-router-dom';
import MedicationBatchService from '../../services/medicationBatchService';
import BatchService from '../../services/batchService';


function Batches() {

  const [medicationBatches, setMedicationBatches] = useState([])
  const [batches, setBatches] = useState([])

  useEffect(() => {

    async function fetchData() {
      const allMedicationBatches = await MedicationBatchService.getMedicationBatches();
      const allBatches = await BatchService.getBatches();
      for (let medicationBatch of allMedicationBatches) {

        const id = medicationBatch.id;
        const response = await MedicationBatchService.getMedicineByMedicationBatchId(id);
        medicationBatch.medicine = response;

      }

      setMedicationBatches(allMedicationBatches)
      setBatches(allBatches)

    }

    fetchData()

  }, [])

  useEffect(() => {
    console.log(medicationBatches)
  }, [medicationBatches])


  return (
    <>
      <Header title={"Lote de Medicamento"} />

      <Typography>Nota: Para agregar, editar o eliminar cualquier tipo de lote, se debe hacer desde el modulo del recurso respectivo (Medicamentos o Consumibles)</Typography>

      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Medicamento', field: 'medicine.name' },
          { title: 'Vía de Administración', field: 'administrationRoute' },
          { title: 'Concentración', field: 'medicine.concentration' },
          { title: 'Cantidad', field: 'quantity', type: 'numeric' },
          { title: 'Semaforización', field: 'status' },
          { title: 'Ubicación', field: 'location.name' },
          { title: 'CUM', field: 'cum' },
        ]}
        singleEntity='lote de medicamento'
        entity='lotes-medicamentos'
        deleteable={false}
        editable={false}
        data={medicationBatches} />

      <Box my={5}></Box>


      <Header title={"Lote"} />

      <Typography>Nota: Para agregar, editar o eliminar cualquier tipo de lote, se debe hacer desde el modulo del recurso respectivo (Medicamentos o Consumibles)</Typography>

      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Fabricante', field: 'manufacturer ' },
          { title: 'Vía de Administración', field: 'administrationRoute' },
          { title: 'Fecha de Vencimiento', field: 'status' },
          { title: 'Semaforización', field: 'status' },
          { title: 'Ubicación', field: 'location.name' },
          
        ]}
        singleEntity='lote'
        entity='lotes'
        deleteable={false}
        editable={false}
        // data={batches} />
        />
    </>
  )
}

export default Batches
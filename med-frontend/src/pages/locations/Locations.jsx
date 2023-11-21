import {useEffect, useState} from 'react'
import CustomTable from '../../components/CustomTable'
import Header from '../../components/Header'
import { Box, Button } from '@mui/material'
import { Link } from 'react-router-dom';
import PharmacyService from '../../services/pharmacyService';
import DisposalStationService from '../../services/disposalStationService';
import triggerCannotDeleteAlert from '../../components/alerts/CannotDeleteAlert';

function Locations() {
  
  const [pharmacies, setPharmacies] = useState([])
  const [disposalStations, setDisposalStations] = useState([])

  useEffect(() => {

    async function fetchData(){
      const allPharmacies = await PharmacyService.getPharmacies();
      const allDisposalStations = await DisposalStationService.getDisposalStations();

      setPharmacies(allPharmacies)
      setDisposalStations(allDisposalStations)
    }

    fetchData()
    
  }, [])

  async function handleDeletePharmacy(id){
    const error = await PharmacyService.deletePharmacy(id);

    {error.status === 500? triggerCannotDeleteAlert('farmacia', id, 'No se puede eliminar una farmacia principal'): null}
  }

  async function handleDeleteDisposalStation(id){
    await DisposalStationService.deleteDisposalStation(id)
  }
  
  return (
    <>
      <Header title={"Farmacia"} />

      <Button
        component={Link}
        to="/farmacia/agregar"
        variant="contained"
        sx={{ px: 10, py: 1, mb: 2 }}
        color={'info'}>
        Agregar farmacia
      </Button>

      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Nombre', field: 'name' },
          { title: 'Categoria', field: 'category' }
        ]}
        singleEntity='farmacia'
        entity='farmacia'
        handleDelete={handleDeletePharmacy}
        data={pharmacies} />

      <Box my={5}></Box>

      

      <Header title={"Estación de desechos"} />

      <Button
        component={Link}
        to="/estaciondesechos/agregar"
        variant="contained"
        sx={{ px: 10, py: 1, mb: 2 }}
        color={'info'}>
        Agregar estación de desechos
      </Button>

      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Nombre', field: 'name' }
        ]}
        singleEntity='estación de desechos'
        entity='estaciondesechos'
        handleDelete={handleDeleteDisposalStation}
        data={disposalStations} />
    </>
  )
}

export default Locations
import {useEffect, useState} from 'react'
import CustomTable from '../../components/CustomTable'
import FabLink from '../../components/buttons/FabLink'
import AddIcon from '@mui/icons-material/Add';
import Header from '../../components/Header'
import { Box } from '@mui/material'
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

      <FabLink to="/farmacia/agregar" icon={<AddIcon/>} color='secondary'/ >



      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Nombre', field: 'name' },
          { title: 'Categoría', field: 'category' }
        ]}
        singleEntity='farmacia'
        entity='farmacia'
        handleDelete={handleDeletePharmacy}
        data={pharmacies} />

      <Box my={5}></Box>

      

      <Header title={"Estación de Desechos"} />

      <FabLink to="/estacion-desechos/agregar" icon={<AddIcon/>} color='secondary'/ >


      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Nombre', field: 'name' }
        ]}
        singleEntity='estación de desechos'
        entity='estacion-desechos'
        handleDelete={handleDeleteDisposalStation}
        data={disposalStations} />
    </>
  )
}

export default Locations
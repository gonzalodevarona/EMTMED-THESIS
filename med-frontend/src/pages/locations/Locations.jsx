import { useEffect, useState } from 'react'
import CustomTable from '../../components/tables/CustomTable'
import FabLink from '../../components/buttons/FabLink'
import AddIcon from '@mui/icons-material/Add';
import Header from '../../components/Header'
import { Box } from '@mui/material'
import PharmacyService from '../../services/pharmacyService';
import DisposalStationService from '../../services/disposalStationService';
import triggerCannotDeleteAlert from '../../components/alerts/CannotDeleteAlert';
import { useTranslation } from 'react-i18next';

function Locations() {
  const { t } = useTranslation();


  const [pharmacies, setPharmacies] = useState([])
  const [disposalStations, setDisposalStations] = useState([])

  useEffect(() => {

    async function fetchData() {
      const allPharmacies = await PharmacyService.getPharmacies();
      const allDisposalStations = await DisposalStationService.getDisposalStations();

      setPharmacies(allPharmacies.map(pharmacy => {
        return { ...pharmacy, category: t(`pharmacy.category.${pharmacy.category}`) }
      }));
      setDisposalStations(allDisposalStations)
    }

    fetchData()

  }, [])

  async function handleDeletePharmacy(id) {
    const error = await PharmacyService.deletePharmacy(id);
    { error.status === 500 ? triggerCannotDeleteAlert('farmacia', id, 'No se puede eliminar esta farmacia, puesto que es principal o bodega o está ligada a algún lote') : null }
  }

  async function handleDeleteDisposalStation(id) {
    const error = await DisposalStationService.deleteDisposalStation(id);
    { error.status === 500 ? triggerCannotDeleteAlert('farmacia', id, 'No se puede eliminar esta estación de desechos, puesto que está ligada a algún lote') : null }

  }

  return (
    <>
      <Header title={"Farmacia"} />

      <FabLink to="/farmacia/agregar" icon={<AddIcon />} color='secondary' />



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

      <FabLink to="/estacion-desechos/agregar" icon={<AddIcon />} color='secondary' />


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
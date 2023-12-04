import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import Dashboard from '../components/dashboard/Dashboard'
import Units from '../pages/units/Units'
import InventoryOrders from '../pages/inventoryOrders/InventoryOrders'
import { Typography } from '@mui/material'
import Test from '../components/form/Test'
import UnitFormPage from '../pages/units/unitForm/UnitFormPage'
import CountingUnitDetailed from '../pages/units/countingUnits/CountingUnitDetailed'
import WeightUnitDetailed from '../pages/units/weightUnits/WeightUnitDetailed'
import Locations from '../pages/locations/Locations'
import PharmacyDetailed from '../pages/locations/pharmacies/PharmacyDetailed'
import DisposalStationDetailed from '../pages/locations/disposalStations/DisaposalStationDetailed'
import PharmacyFormPage from '../pages/locations/pharmacies/pharmacyForm/PharmacyFormPage'
import InventoryOrderFormPage from '../pages/inventoryOrders/inventoryOrderFrom/InventoryOrderFormPage'
import DisposalStationFormPage from '../pages/locations/disposalStations/disposaStationForm/DisposalStationFormPage'
import InventoryOrderDetailed from '../pages/inventoryOrders/InventoryOrderDetailed'
import Medicines from '../pages/medicines/Medicines'
import MedicineDetailed from '../pages/medicines/MedicineDetailed'
import MedicineFormPage from '../pages/medicines/medicineForm/MedicineFormPage'
import Batches from '../pages/batches/Batches'
import MedicationBatchDetailed from '../pages/batches/medicationBatches/MedicationBatchDetailed'
import BatchDetailed from '../pages/batches/BatchDetailed'
import Consumables from '../pages/consumables/Consumables'

export default function Routes() {

    const router = createBrowserRouter(
        [
            {
                path: "/",
                element: <Dashboard />,
                children: [
                    {
                        path: "*",
                        element: <Typography>Error 404: Página no encontrada</Typography>
                    },
                    {
                        path: "404",
                        element: <Typography>Error 404: Página no encontrada</Typography>
                    },

                    // *** UNIDADES ***

                    {
                        path: "/unidades",
                        element: <Units />
                    },

                    // *** UNIDAD DE PESO ***

                    {
                        path: "/unidadespeso/:id",
                        element: <WeightUnitDetailed />
                    },
                    {
                        path: "/unidadespeso/agregar",
                        element: <UnitFormPage type='peso' action='add' />
                    },
                    {
                        path: "/unidadespeso/editar/:id",
                        element: <UnitFormPage type='peso' action='edit' />
                    },

                    // *** UNIDAD DE CONTEO ***

                    {
                        path: "/unidadesconteo/:id",
                        element: <CountingUnitDetailed />
                    },
                    {
                        path: "/unidadesconteo/agregar",
                        element: <UnitFormPage type='conteo' action='add' />
                    },
                    {
                        path: "/unidadesconteo/editar/:id",
                        element: <UnitFormPage type='conteo' action='edit' />
                    },



                    // *** UBICACIONES ***

                    {
                        path: "/ubicaciones",
                        element: <Locations />
                    },

                    // *** FARMACIA ***

                    {
                        path: "/farmacia/:id",
                        element: <PharmacyDetailed />
                    },
                    {
                        path: "/farmacia/agregar",
                        element: <PharmacyFormPage action='add' />
                    },
                    {
                        path: "/farmacia/editar/:id",
                        element: <PharmacyFormPage action='edit' />
                    },

                    // *** ESTACIÓN DE DESECHOS ***

                    {
                        path: "/estaciondesechos/:id",
                        element: <DisposalStationDetailed />
                    },
                    {
                        path: "/estaciondesechos/agregar",
                        element: <DisposalStationFormPage action='add' />
                    },
                    {
                        path: "/estaciondesechos/editar/:id",
                        element: <DisposalStationFormPage action='edit' />
                    },


                    // *** ORDENES DE INVENTARIO ***


                    {
                        path: "/ordenes-inventario",
                        element: <InventoryOrders />
                    },
                    {
                        path: "/ordenes-inventario/:id",
                        element: <InventoryOrderDetailed title='orden' />
                    },
                    {
                        path: "/ordenes-inventario/agregar",
                        element: <InventoryOrderFormPage action='add' />
                    },
                    {
                        path: "/ordenes-inventario/editar/:id",
                        element: <InventoryOrderFormPage action='edit' />
                    },


                    // *** MEDICAMENTOS ***


                    {
                        path: "/medicamentos",
                        element: <Medicines />
                    },
                    {
                        path: "/medicamentos/:id",
                        element: <MedicineDetailed title='medicamento' />
                    },
                    {
                        path: "/medicamentos/agregar",
                        element: <MedicineFormPage action='add' />
                    },
                    {
                        path: "/medicamentos/editar/:id",
                        element: <MedicineFormPage action='edit' />
                    },

                    // *** CONSUMIBLES ***


                    {
                        path: "/consumibles",
                        element: <Medicines />
                    },
                    {
                        path: "/consumibles/:id",
                        element: <MedicineDetailed title='medicamento' />
                    },
                    {
                        path: "/consumibles/agregar",
                        element: <MedicineFormPage action='add' />
                    },
                    {
                        path: "/consumibles/editar/:id",
                        element: <MedicineFormPage action='edit' />
                    },

                    // *** LOTES ***


                    {
                        path: "/lotes",
                        element: <Batches />
                    },
                    {
                        path: "/lotes-medicamentos/:id",
                        element: <MedicationBatchDetailed title='medicamento' />
                    },
                    {
                        path: "/lotes/:id",
                        element: <BatchDetailed title='medicamento' />
                    },

                    // *** UNIDAD DE PESO ***

                    {
                        path: "/test",
                        element: <Test />
                    },

                ]
            }
        ]
    )

    return (
        <RouterProvider router={router} />
    )
}
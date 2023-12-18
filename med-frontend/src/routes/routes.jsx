import { Navigate, RouterProvider, createBrowserRouter } from 'react-router-dom'
import Dashboard from '../components/dashboard/Dashboard'
import Units from '../pages/units/Units'
import InventoryOrders from '../pages/inventoryOrders/InventoryOrders'
import { Typography } from '@mui/material'
import UnitFormPage from '../pages/units/unitForm/UnitFormPage'
import Locations from '../pages/locations/Locations'
import PharmacyFormPage from '../pages/locations/pharmacyForm/PharmacyFormPage'
import InventoryOrderFormPage from '../pages/inventoryOrders/inventoryOrderFrom/InventoryOrderFormPage'
import DisposalStationFormPage from '../pages/locations/disposalStationForm/DisposalStationFormPage'
import InventoryOrderDetailed from '../pages/inventoryOrders/InventoryOrderDetailed'
import Medicines from '../pages/medicines/Medicines'
import MedicineDetailed from '../pages/medicines/MedicineDetailed'
import MedicineFormPage from '../pages/medicines/medicineForm/MedicineFormPage'
import Batches from '../pages/batches/Batches'
import MedicationBatchDetailed from '../pages/medicationBatches/MedicationBatchDetailed'
import BatchDetailed from '../pages/batches/BatchDetailed'
import Consumables from '../pages/consumables/Consumables'
import ConsumableDetailed from '../pages/consumables/ConsumableDetailed'
import ConsumableFormPage from '../pages/consumables/consumableForm/ConsumableFormPage'
import SupplyOrders from '../pages/supplyOrders/SupplyOrders'
import SupplyOrderDetailed from '../pages/supplyOrders/SupplyOrderDetailed'
import SupplyOrderFormPage from '../pages/supplyOrders/supplyOrderForm/SupplyOrderFormPage'
import { useKeycloak } from '@react-keycloak/web'
import SignIn from '../pages/SignIn'
import Home from '../pages/Home'

export default function Routes() {

    const { keycloak } = useKeycloak()

    const router = createBrowserRouter(

        keycloak.authenticated ?
            [
                {
                    path: "/",
                    element: <Dashboard />,
                    children: [

                        {
                            path: "*",
                            element: <Navigate to="/" />
                        },
                        {
                            path: "/",
                            element: <Home />
                        },
                        {
                            path: "404",
                            element: <Typography>Error 404: Página no encontrada</Typography>
                        },

                    ].concat(  (keycloak.hasRealmRole("ROLE_REGENT") || keycloak.hasRealmRole("ROLE_ADMIN")) ?

                        // *** UNIDADES ***
[
                        {
                            path: "/unidades",
                            element: <Units />
                        },

                        // *** UNIDAD DE PESO ***


                        {
                            path: "/unidades-peso/agregar",
                            element: <UnitFormPage type='peso' action='add' />
                        },
                        {
                            path: "/unidades-peso/editar/:id",
                            element: <UnitFormPage type='peso' action='edit' />
                        },

                        // *** UNIDAD DE CONTEO ***


                        {
                            path: "/unidades-conteo/agregar",
                            element: <UnitFormPage type='conteo' action='add' />
                        },
                        {
                            path: "/unidades-conteo/editar/:id",
                            element: <UnitFormPage type='conteo' action='edit' />
                        },



                        // *** UBICACIONES ***

                        {
                            path: "/ubicaciones",
                            element: <Locations />
                        },

                        // *** FARMACIA ***

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
                            path: "/estacion-desechos/agregar",
                            element: <DisposalStationFormPage action='add' />
                        },
                        {
                            path: "/estacion-desechos/editar/:id",
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

                        // *** ORDENES DE INVENTARIO ***


                        {
                            path: "/ordenes",
                            element: <SupplyOrders />
                        },
                        {
                            path: "/ordenes/:id",
                            element: <SupplyOrderDetailed title='orden' />
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
                            element: <Consumables />
                        },
                        {
                            path: "/consumibles/:id",
                            element: <ConsumableDetailed title='consumible' />
                        },
                        {
                            path: "/consumibles/agregar",
                            element: <ConsumableFormPage action='add' />
                        },
                        {
                            path: "/consumibles/editar/:id",
                            element: <ConsumableFormPage action='edit' />
                        },

                        // *** LOTES ***


                        {
                            path: "/lotes",
                            element: <Batches />
                        },
                        {
                            path: "/lotes-medicamentos/:id",
                            element: <MedicationBatchDetailed />
                        },
                        {
                            path: "/lotes/:id",
                            element: <BatchDetailed />
                        }]

                        : []

                    ).concat((keycloak.hasRealmRole("ROLE_PRACTITIONER") || keycloak.hasRealmRole("ROLE_ADMIN")) ?
                        [{
                            path: "/ordenes/agregar",
                            element: <SupplyOrderFormPage action='add' />
                        }] : []
                    )
                }
            ] :
            [
                {
                    path: "*",
                    element: <Navigate to="/login" />
                },
                {
                    path: "/login",
                    element: <SignIn />
                }
            ]
    )

    return (
        <RouterProvider router={router} />
    )
}
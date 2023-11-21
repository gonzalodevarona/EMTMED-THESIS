import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import Dashboard from '../components/dashboard/Dashboard'
import Units from '../pages/units/Units'
import Orders from '../pages/orders/Orders'
import DetailedView from '../components/DetailedView'
import { Typography } from '@mui/material'
import Test from '../components/form/Test'
import UnitFormPage from '../pages/units/unitForm/UnitFormPage'
import CountingUnitDetailed from '../pages/units/countingUnits/CountingUnitDetailed'

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
                    element: <DetailedView title='unidad de peso'/>
                },
                {
                    path: "/unidadespeso/agregar",
                    element: <UnitFormPage type='peso' action='add'/>
                },
                {
                    path: "/unidadespeso/editar/:id",
                    element: <UnitFormPage type='peso' action='edit'/>
                },

                // *** UNIDAD DE CONTEO ***

                {
                    path: "/unidadesconteo/:id",
                    element: <CountingUnitDetailed/>
                },
                {
                    path: "/unidadesconteo/agregar",
                    element: <UnitFormPage type='conteo' action='add'/>
                },
                {
                    path: "/unidadesconteo/editar/:id",
                    element: <UnitFormPage type='conteo' action='edit'/>
                },

                // *** ORDENES ***


                {
                    path: "/ordenes",
                    element: <Orders />
                },
                {
                    path: "/ordenes/:id",
                    element: <DetailedView title='orden'/>
                },
                {
                    path: "/ordenes/agregar",
                    element: <UnitFormPage type='conteo' action='add'/>
                },
                {
                    path: "/ordenes/editar/:id",
                    element: <UnitFormPage type='conteo' action='edit'/>
                },

                // *** UNIDAD DE PESO ***

                {
                    path: "/test",
                    element: <Test/>
                },

            ]
            }
        ]
    )

    return (
        <RouterProvider router={router} />
    )
}
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import Dashboard from '../components/dashboard/Dashboard'
import Units from '../pages/units/Units'
import Orders from '../pages/orders/Orders'
import DetailLayout from '../components/DetailLayout'
import { Typography } from '@mui/material'
import Test from '../components/form/Test'
import UnitForm from '../pages/units/UnitForm'

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
                    path: "/unidades",
                    element: <Units />
                },
                {
                    path: "/unidadespeso/:id",
                    element: <DetailLayout title='unidad de peso'/>
                },
                {
                    path: "/unidadespeso/agregar",
                    element: <UnitForm type='peso' action='add'/>
                },
                {
                    path: "/unidadespeso/editar/:id",
                    element: <UnitForm type='peso' action='edit'/>
                },
                {
                    path: "/unidadesconteo/:id",
                    element: <DetailLayout title='unidad de conteo'/>
                },
                {
                    path: "/unidadesconteo/agregar",
                    element: <UnitForm type='conteo' action='add'/>
                },
                {
                    path: "/unidadesconteo/editar/:id",
                    element: <UnitForm type='conteo' action='edit'/>
                },
                {
                    path: "/ordenes",
                    element: <Orders />
                },
                {
                    path: "/ordenes/:id",
                    element: <DetailLayout  title='orden'/>
                },
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
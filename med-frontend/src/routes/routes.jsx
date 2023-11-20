import { Navigate, RouterProvider, createBrowserRouter } from 'react-router-dom'
import Dashboard from '../components/dashboard/Dashboard'
import Units from '../pages/units/Units'
import Orders from '../pages/orders/Orders'
import MainLayout from '../components/MainLayout'
import DetailLayout from '../components/DetailLayout'

export default function Routes() {



    const router = createBrowserRouter(
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
                    path: "/unidades",
                    element: <Units />
                },
                {
                    path: "/ordenes",
                    element: <Orders />
                },
                {
                    path: "/t",
                    element: <DetailLayout singleData={{id:2, name:'melo'}}  title='test'/>
                },

            ]
            }
        ]
    )

    return (
        <RouterProvider router={router} />
    )
}
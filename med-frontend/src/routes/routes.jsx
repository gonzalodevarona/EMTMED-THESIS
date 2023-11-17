import { Navigate, RouterProvider, createBrowserRouter } from 'react-router-dom'
import Dashboard from '../components/dashboard/Dashboard'
import WeightUnit from '../pages/weightUnit/WeightUnit'

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
                    path: "/",
                    element: <WeightUnit />
                },

            ]
            }
        ]
    )

    return (
        <RouterProvider router={router} />
    )
}
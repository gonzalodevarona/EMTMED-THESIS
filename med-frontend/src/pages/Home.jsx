import { useEffect } from "react"
import { useKeycloak } from '@react-keycloak/web'
import Banner from "../components/Banner"
import welcome from '../assets/welcome.jpeg'
import { useDispatch } from "react-redux"
import { login } from "../features/auth/authReducer"

const Home = () => {

    const { keycloak } = useKeycloak()
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(login({ token: keycloak.token }))
        console.log(keycloak.tokenParsed)
        keycloak.loadUserProfile().then((profile) => {
            console.log(profile)
        })
    }, [])

    return (
        <>
            <Banner post={
                {
                    "image": welcome,
                    "title": "Bienvenido, personal del EMT",
                    "description": "En este sitio podrá administrar recursos como medicamentos, consumibles y ordenes de pacientes.",
                }
            } />
        </>
    )
}

export default Home


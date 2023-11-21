import DetailedView from "../../../components/DetailedView"
import Header from "../../../components/Header"
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { removeNullProperties, previousPage } from '../../../utils/CommonMethods';
import CountingUnitService from "../../../services/countingUnitService";
import { useNavigate } from "react-router-dom";
import triggerConfrirmationAlert from "../../../components/alerts/ConfirmationAlert";

function CountingUnitDetailed() {

    let { id } = useParams();
    const [countingUnit, setCountingUnit] = useState({})

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };

    async function handleDelete(){
        triggerConfrirmationAlert(
            `Eliminar unidad de conteo con ID ${id}`,
            "¿Estas seguro que quieres eliminarla?",
            "warning",
            "Borrar",
            async() => await CountingUnitService.deleteCountingUnit(id),
            `Unidad de conteo con ID ${id} eliminada con éxito.`,
            "success",
            previousPage,
            `Unidad de conteo con ID ${id} NO fue eliminada.`,
            "error"
        )

    }

    useEffect(() => {
        async function fetchData() {
            const entityData = await CountingUnitService.getCountingUnitById(id);

            if (entityData.status == 500 && entityData.error) {
                redirect('/404')
            } else {
                setCountingUnit(removeNullProperties(entityData))
            }

        }

        fetchData()
    }, [])

    return (
        <>
            <Header title={`unidad de conteo #${id}`} />
            <DetailedView entity='unidadesconteo' data={countingUnit} handleDelete={handleDelete}/>
        </>
    )
}

export default CountingUnitDetailed
import DetailedView from "../../../components/DetailedView"
import Header from "../../../components/Header"
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { removeNullProperties,  } from '../../../utils/CommonMethods';
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

    async function handleDelete() {
        triggerConfrirmationAlert({
            title: `Eliminar unidad de conteo con ID ${id}`,
            text: "¿Estas seguro que quieres eliminarla?",
            type: "warning",
            confirmText: "Borrar",
            action: async () => await CountingUnitService.deleteCountingUnit(id),
            successTitle: `Unidad de conteo con ID ${id} eliminada con éxito.`,
            successType: "success",
            successAction: redirect(-1),
            errorTitle: `Unidad de conteo con ID ${id} NO fue eliminada.`,
            errorType: "error"
        })

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
            <DetailedView deleteable editable entity='unidades-conteo' data={countingUnit} handleDelete={handleDelete} />
        </>
    )
}

export default CountingUnitDetailed
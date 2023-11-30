import DetailedView from "../../../components/DetailedView"
import Header from "../../../components/Header"
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { removeNullProperties, previousPage, capitalizeFirstLetter } from '../../../utils/CommonMethods';
import DisposalStationService from "../../../services/disposalStationService";
import { useNavigate } from "react-router-dom";
import triggerConfrirmationAlert from "../../../components/alerts/ConfirmationAlert";

function DisaposalStationDetailed() {

    let { id } = useParams();
    const [disposalStation, setDisposalStation] = useState({})

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };

    const entity = 'estación de desechos';

    async function handleDelete() {
        triggerConfrirmationAlert({
            title: `Eliminar ${entity} con ID ${id}`,
            text: "¿Estas seguro que quieres eliminarla?",
            type: "warning",
            confirmText: "Borrar",
            action: async () => await DisposalStationService.deleteDisposalStation(id),
            successTitle: `${capitalizeFirstLetter(entity)} con ID ${id} eliminada con éxito.`,
            successType: "success",
            successAction: previousPage,
            errorTitle: `${capitalizeFirstLetter(entity)} con ID ${id} NO fue eliminada.`,
            errorType: "error"
        })
    }

    useEffect(() => {
        async function fetchData() {
            const entityData = await DisposalStationService.getDisposalStationById(id);

            if (entityData.status == 500 && entityData.error) {
                redirect('/404')
            } else {
                setDisposalStation(removeNullProperties(entityData))
            }

        }

        fetchData()
    }, [])

    return (
        <>
            <Header title={`${entity} #${id}`} />
            <DetailedView deleteable editable entity='estaciondesechos' data={disposalStation} handleDelete={handleDelete} />
        </>
    )
}

export default DisaposalStationDetailed
import DetailedView from "../../../components/DetailedView"
import Header from "../../../components/Header"
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { removeNullProperties, previousPage, capitalizeFirstLetter } from '../../../utils/CommonMethods';
import PharmacyService from "../../../services/pharmacyService";
import { useNavigate } from "react-router-dom";
import triggerConfrirmationAlert from "../../../components/alerts/ConfirmationAlert";

function PharmacyDetailed() {

    let { id } = useParams();
    const [pharmacy, setPharmacy] = useState({})

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };

    const entity = 'farmacia';

    async function handleDelete() {
        triggerConfrirmationAlert({
            title: `Eliminar ${entity} con ID ${id}`,
            text: "¿Estas seguro que quieres eliminarla?",
            type: "warning",
            confirmText: "Borrar",
            action: async () => await PharmacyService.deletePharmacy(id),
            successTitle: `${capitalizeFirstLetter(entity)} con ID ${id} eliminada con éxito.`,
            successType: "success",
            successAction: previousPage,
            errorTitle: `${capitalizeFirstLetter(entity)} con ID ${id} NO fue eliminada.`,
            errorType: "error"
        })
    }



    useEffect(() => {
        async function fetchData() {
            const entityData = await PharmacyService.getPharmacyById(id);

            if (entityData.status == 500 && entityData.error) {
                redirect('/404')
            } else {
                delete entityData.sourceList;
                setPharmacy(removeNullProperties(entityData))
            }

        }

        fetchData()
    }, [])

    return (
        <>
            <Header title={`${entity} #${id}`} />
            <DetailedView entity={entity} data={pharmacy} handleDelete={handleDelete} />
        </>
    )
}

export default PharmacyDetailed
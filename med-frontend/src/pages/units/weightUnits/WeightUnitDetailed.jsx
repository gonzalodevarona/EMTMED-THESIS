import DetailedView from "../../../components/DetailedView"
import Header from "../../../components/Header"
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { removeNullProperties, previousPage } from '../../../utils/CommonMethods';
import WeightUnitService from "../../../services/weightUnitService";
import { useNavigate } from "react-router-dom";
import triggerConfrirmationAlert from "../../../components/alerts/ConfirmationAlert";

function WeightUnitDetailed() {

    let { id } = useParams();
    const [weightUnit, setWeightUnit] = useState({})

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };

    async function handleDelete() {
        triggerConfrirmationAlert({
            title: `Eliminar unidad de peso con ID ${id}`,
            text: "¿Estas seguro que quieres eliminarla?",
            type: "warning",
            confirmText: "Borrar",
            action: async () => await WeightUnitService.deleteWeightUnit(id),
            successTitle: `Unidad de peso con ID ${id} eliminada con éxito.`,
            successType: "success",
            successAction: previousPage,
            errorTitle: `Unidad de peso con ID ${id} NO fue eliminada.`,
            errorType: "error"
        })

    }

    useEffect(() => {
        async function fetchData() {
            const entityData = await WeightUnitService.getWeightUnitById(id);

            if (entityData.status == 500 && entityData.error) {
                redirect('/404')
            } else {
                setWeightUnit(removeNullProperties(entityData))
            }

        }

        fetchData()
    }, [])

    return (
        <>
            <Header title={`unidad de peso #${id}`} />
            <DetailedView deleteable editable entity='unidadespeso' data={weightUnit} handleDelete={handleDelete}/>
        </>
    )
}

export default WeightUnitDetailed
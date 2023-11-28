import DetailedView from "../../components/DetailedView"
import Header from "../../components/Header"
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { removeNullProperties, previousPage, capitalizeFirstLetter } from '../../utils/CommonMethods';
import { dateArrayToString } from '../../utils/EntityProcessingMethods';
import InventoryOrderService from "../../services/inventoryOrderService";
import { useNavigate } from "react-router-dom";
import triggerConfrirmationAlert from "../../components/alerts/ConfirmationAlert";

function InventoryOrderDetailed() {

    let { id } = useParams();
    const [inventoryOrder, setInventoryOrder] = useState({})

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };

    const entity = 'orden de inventario';

    async function handleDelete() {
        triggerConfrirmationAlert({
            title: `Eliminar ${entity} con ID ${id}`,
            text: "¿Estas seguro que quieres eliminarla?",
            type: "warning",
            confirmText: "Borrar",
            action: async () => await InventoryOrderService.deleteInventoryOrder(id),
            successTitle: `${capitalizeFirstLetter(entity)} con ID ${id} eliminada con éxito.`,
            successType: "success",
            successAction: previousPage,
            errorTitle: `${capitalizeFirstLetter(entity)} con ID ${id} NO fue eliminada.`,
            errorType: "error"
        })
    }

    useEffect(() => {
        async function fetchData() {
            const entityData = await InventoryOrderService.getInventoryOrderById(id);

            if (entityData.status == 500 && entityData.error) {
                redirect('/404')
            } else {
                entityData.source = `${entityData.source.id} - ${entityData.source.name}`
                entityData.destination = `${entityData.destination.id} - ${entityData.destination.name}`
                entityData.authoredOn = dateArrayToString(entityData.authoredOn);
                setInventoryOrder(removeNullProperties(entityData))
            }

        }

        fetchData()
    }, [])

    useEffect(() => {
      console.log(inventoryOrder)
    }, [inventoryOrder])
    

    return (
        <>
            <Header title={`${entity} #${id}`} />
            <DetailedView entity='orden-inventario' data={inventoryOrder} handleDelete={handleDelete} />
        </>
    )
}

export default InventoryOrderDetailed
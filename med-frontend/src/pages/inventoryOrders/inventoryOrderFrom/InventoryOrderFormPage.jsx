import { useEffect, useState } from 'react'
import InventoryOrderForm from './InventoryOrderForm';
import Header from '../../../components/Header';
import { useParams } from "react-router-dom";
import InventoryOrderService from '../../../services/inventoryOrderService'
import { removeNullProperties } from '../../../utils/CommonMethods'

function InventoryOrderFormPage({ action }) {

    let { id } = useParams();

    const [inventoryOrderData, setInventoryOrderData] = useState({})

    const entity = 'orden de inventario';


    useEffect(() => {

        async function fetchData() {
            let fetchedData = await InventoryOrderService.getInventoryOrderById(id);
            setInventoryOrderData(fetchedData);
        }

        if(action === 'edit'){
            fetchData();
        }
        
    }, [])
   

    
    return (
        <>
            {action === 'add' ?
                <Header title={`Agregar una ${entity}`} /> :
                <Header title={`Editar la ${entity} #${id}`} />
            }
            {action === 'edit' && pharmacyData.id &&
                <InventoryOrderForm id={id} action={action} preloadedData={removeNullProperties(inventoryOrderData)}/>
            }
            {action === 'add' && 
                <InventoryOrderForm  action={action} />
            }

        </>
    )
}

export default InventoryOrderFormPage
import { useEffect, useState } from 'react'
import SupplyOrderForm from './SupplyOrderForm';
import Header from '../../../components/Header';
import { useParams } from "react-router-dom";
import SupplyOrderService from '../../../services/supplyOrderService'
import { removeNullProperties } from '../../../utils/CommonMethods'
import { useNavigate } from "react-router-dom";

function SupplyOrderFormPage({ action }) {

    let { id } = useParams();

    const [supplyOrderData, setSupplyOrderData] = useState({})

    const entity = 'orden';

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };


    useEffect(() => {

        async function fetchData() {
            let fetchedData = await SupplyOrderService.getSupplyOrderById(id);
            console.log(fetchedData);
            if ((fetchedData.status == 500 && fetchedData.error) || fetchedData.status === 'COMPLETED') {
                redirect('/404')
            }
          
            setSupplyOrderData(fetchedData);
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
            {action === 'edit' && inventoryOrderData.id &&
                <SupplyOrderForm id={id} action={action} preloadedData={removeNullProperties(supplyOrderData)}/>
            }
            {action === 'add' && 
                <SupplyOrderForm  action={action} />
            }

        </>
    )
}

export default SupplyOrderFormPage
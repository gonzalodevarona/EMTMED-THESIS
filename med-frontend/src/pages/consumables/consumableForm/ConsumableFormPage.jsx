import { useEffect, useState } from 'react'
import ConsumableForm from './ConsumableForm';
import Header from '../../../components/Header';
import { useParams } from "react-router-dom";
import ConsumableService from '../../../services/consumableService'
import { removeNullProperties } from '../../../utils/CommonMethods'
import { useNavigate } from "react-router-dom";

function ConsumableFormPage({ action }) {

    let { id } = useParams();

    const [consumableData, setConsumableData] = useState({})

    const entity = 'consumible';

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };


    useEffect(() => {

        async function fetchData() {
            let fetchedData = await ConsumableService.getConsumableById(id);
            if (fetchedData.status == 500 && fetchedData.error) {
                redirect('/404')
            }
          
            setConsumableData(fetchedData);
        }

        if(action === 'edit'){
            fetchData();
        }
        
    }, [])
   

    
    return (
        <>
            {action === 'add' ?
                <Header title={`Agregar un ${entity}`} /> :
                <Header title={`Editar el ${entity} #${id}`} />
            }
            {action === 'edit' && consumableData.id &&
                <ConsumableForm id={id} action={action} preloadedData={removeNullProperties(consumableData)}/>
            }
            {action === 'add' && 
                <ConsumableForm  action={action} />
            }

        </>
    )
}

export default ConsumableFormPage
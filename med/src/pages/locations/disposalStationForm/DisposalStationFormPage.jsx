import { useEffect, useState } from 'react'
import DisposalStationForm from './DisposalStationForm';
import Header from '../../../components/Header';
import { useParams } from "react-router-dom";
import DisposalStationService from '../../../services/disposalStationService'
import { removeNullProperties } from '../../../utils/CommonMethods'

function DisposalStationFormPage({ action }) {

    let { id } = useParams();

    const [disposalStationData, setDisposalStationData] = useState({})

    const entity = 'estación de desechos';


    useEffect(() => {

        async function fetchData() {
            let fetchedData = await DisposalStationService.getDisposalStationById(id);
            setDisposalStationData(fetchedData);
        }

        if(action === 'edit'){
            fetchData();
        }
        
    }, [])
   

    
    return (
        <>
            {action === 'add' ?
                <Header title={`Agregar una ${entity}`} /> :
                <Header title={`Editar la ${entity} ${id}`} />
            }
            {action === 'edit' && disposalStationData.id &&
                <DisposalStationForm id={id} action={action} preloadedData={removeNullProperties(disposalStationData)}/>
            }
            {action === 'add' && 
                <DisposalStationForm  action={action} />
            }

        </>
    )
}

export default DisposalStationFormPage
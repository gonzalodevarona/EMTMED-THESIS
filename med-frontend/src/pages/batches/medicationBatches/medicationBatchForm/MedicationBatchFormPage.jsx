import { useEffect, useState } from 'react'
import MedicationBatchForm from './MedicationBatchForm';
import Header from '../../../components/Header';
import { useParams } from "react-router-dom";
import MedicationBatchService from '../../../services/medicationBatchService'
import { removeNullProperties } from '../../../utils/CommonMethods'
import { useNavigate } from "react-router-dom";

function MedicationBatchFormPage({ action }) {

    let { id } = useParams();

    const [medicationBatchData, setMedicationBatchData] = useState({})

    const entity = 'lote de medicamento';

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };


    useEffect(() => {

        async function fetchData() {
            let fetchedData = await MedicationBatchService.getMedicationBatchById(id);
            console.log(fetchedData);
            if (fetchedData.status == 500 && fetchedData.error) {
                redirect('/404')
            }
          
            setMedicationBatchData(fetchedData);
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
            {action === 'edit' && medicationBatchData.id &&
                <MedicationBatchForm id={id} action={action} preloadedData={removeNullProperties(medicationBatchData)}/>
            }
            {action === 'add' && 
                <MedicationBatchForm  action={action} />
            }

        </>
    )
}

export default MedicationBatchFormPage
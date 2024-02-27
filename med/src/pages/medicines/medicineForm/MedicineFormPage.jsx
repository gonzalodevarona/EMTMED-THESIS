import { useEffect, useState } from 'react'
import MedicineForm from './MedicineForm';
import Header from '../../../components/Header';
import { useParams } from "react-router-dom";
import MedicineService from '../../../services/medicineService'
import MedicationBatchService from '../../../services/medicationBatchService'
import { removeNullProperties } from '../../../utils/CommonMethods'
import { useNavigate } from "react-router-dom";

function MedicineFormPage({ action }) {

    let { id } = useParams();

    const [medicineData, setMedicineData] = useState({})

    const entity = 'medicamento';

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };


    useEffect(() => {

        async function fetchData() {
            let fetchedData = await MedicineService.getMedicineById(id);
            if (fetchedData.status == 500 && fetchedData.error ) {
                redirect('/404')
            }
            if (fetchedData.batches !== undefined) {
                for (let i = 0; i < fetchedData.batches.length; i++) {
                    if (fetchedData.batches[i].isAvailable === false) {
                        fetchedData.batches.splice(i, 1);
                        i--;
                    } else {
                        fetchedData.batches[i] = removeNullProperties(fetchedData.batches[i])
                        fetchedData.batches[i].location = await MedicationBatchService.getLocationByMedicationBatchId(fetchedData.batches[i].id)
                    }
                }
            }
            setMedicineData(fetchedData);
        }

        if(action === 'edit'){
            fetchData();
        }
        
    }, [])
   

    
    return (
        <>
            {action === 'add' ?
                <Header title={`Agregar un ${entity}`} /> :
                <Header title={`Editar el ${entity} ${id}`} />
            }
            {action === 'edit' && medicineData.id &&
                <MedicineForm action={action} preloadedData={removeNullProperties(medicineData)}/>
            }
            {action === 'add' && 
                <MedicineForm  action={action} />
            }

        </>
    )
}

export default MedicineFormPage
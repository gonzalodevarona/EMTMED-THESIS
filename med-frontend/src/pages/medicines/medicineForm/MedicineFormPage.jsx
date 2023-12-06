import { useEffect, useState } from 'react'
import MedicineForm from './MedicineForm';
import Header from '../../../components/Header';
import { useParams } from "react-router-dom";
import MedicineService from '../../../services/medicineService'
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
            if (fetchedData.status == 500 && fetchedData.error) {
                redirect('/404')
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
                <Header title={`Editar el ${entity} #${id}`} />
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
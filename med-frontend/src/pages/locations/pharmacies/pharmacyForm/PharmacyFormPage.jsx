import { useEffect, useState } from 'react'
import PharmacyForm from './PharmacyForm';
import Header from '../../../../components/Header';
import { useParams } from "react-router-dom";
import PharmacyService from '../../../../services/pharmacyService'
import { removeNullProperties } from '../../../../utils/CommonMethods'

function PharmacyFormPage({ action }) {

    let { id } = useParams();

    const [pharmacyData, setPharmacyData] = useState({})

    const entity = 'farmacia';


    useEffect(() => {

        async function fetchData() {
            let fetchedData = await PharmacyService.getPharmacyById(id);
            setPharmacyData(fetchedData);
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
                <PharmacyForm id={id} action={action} preloadedData={removeNullProperties(pharmacyData)}/>
            }
            {action === 'add' && 
                <PharmacyForm  action={action} />
            }

        </>
    )
}

export default PharmacyFormPage
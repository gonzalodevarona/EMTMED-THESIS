import { useEffect, useState } from 'react'
import UnitForm from './UnitForm';
import Header from '../../../components/Header';
import { useParams } from "react-router-dom";
import CountingUnitService from '../../../services/countingUnitService';
import WeightUnitService from '../../../services/weightUnitService';
import { removeNullProperties } from '../../../utils/CommonMethods'

function UnitFormPage({ type, action }) {

    let { id } = useParams();

    const [unitData, setUnitData] = useState({})


    useEffect(() => {

        async function fetchData() {
            let fetchedData;
            
            if (type === 'conteo') {
                fetchedData = await CountingUnitService.getCountingUnitById(id);
                
            } else {
                fetchedData = await WeightUnitService.getWeightUnitById(id);
            }

            setUnitData(fetchedData);
        }

        
        if(action === 'edit'){
            fetchData();
        }
        


    }, [])
   

    
    return (
        <>
            {action === 'add' ?
                <Header title={`Agregar una unidad de ${type}`} /> :
                <Header title={`Editar la unidad de ${type} #${id}`} />
            }
            {action === 'edit' && unitData.id &&
                <UnitForm id={id} type={type} action={action} preloadedData={removeNullProperties(unitData)}/>
            }
            {action === 'add' && 
                <UnitForm type={type} action={action} />
            }

        </>
    )
}

export default UnitFormPage
import { useEffect, useState } from 'react'
import ConsumableForm from './ConsumableForm';
import Header from '../../../components/Header';
import { useParams } from "react-router-dom";
import ConsumableService from '../../../services/consumableService'
import BatchService from '../../../services/batchService'
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

            if (fetchedData.batches !== undefined) {
                for (let i = 0; i < fetchedData.batches.length; i++) {
                    if (fetchedData.batches[i].isAvailable === false) {
                        fetchedData.batches.splice(i, 1);
                        i--;
                    } else {
                        fetchedData.batches[i] = removeNullProperties(fetchedData.batches[i])
                        fetchedData.batches[i].location = await BatchService.getLocationByBatchId(fetchedData.batches[i].id)
                    }
                }
            }
            setConsumableData(fetchedData);
        }

        if (action === 'edit') {
            fetchData();
        }

    }, [])
    useEffect(() => {

        console.log(consumableData)

    }, [consumableData])



    return (
        <>
            {action === 'add' ?
                <Header title={`Agregar un ${entity}`} /> :
                <Header title={`Editar el ${entity} ${id}`} />
            }
            {action === 'edit' && consumableData.id &&
                <ConsumableForm action={action} preloadedData={removeNullProperties(consumableData)}/>
            }
            {action === 'add' &&
                <ConsumableForm action={action} />
            }

        </>
    )
}

export default ConsumableFormPage
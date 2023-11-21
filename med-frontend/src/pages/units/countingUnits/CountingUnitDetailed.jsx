import DetailedView from "../../../components/DetailedView"
import Header from "../../../components/Header"
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { removeNullProperties } from '../../../utils/CommonMethods';
import CountingUnitService from "../../../services/countingUnitService";
import { useNavigate } from "react-router-dom";

function CountingUnitDetailed() {

    let { id } = useParams();
    const [countingUnit, setCountingUnit] = useState({})

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };

    useEffect(() => {
        async function fetchCountingUnit() {
            const entityData = await CountingUnitService.getCountingUnitById(id);

            if (entityData.status == 500 && entityData.error) {
                redirect('/404')
            } else {
                setCountingUnit(removeNullProperties(entityData))
            }

        }

        fetchCountingUnit()
    }, [])

    return (
        <>
            <Header title={`unidad de conteo #${id}`} />
            <DetailedView entity='unidadesconteo' data={countingUnit} />
        </>
    )
}

export default CountingUnitDetailed
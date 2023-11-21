import DetailedView from "../../../components/DetailedView"
import Header from "../../../components/Header"
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { removeNullProperties } from '../../../utils/CommonMethods';
import WeightUnitService from "../../../services/weightUnitService";
import { useNavigate } from "react-router-dom";

function WeightUnitDetailed() {

    let { id } = useParams();
    const [weightUnit, setWeightUnit] = useState({})

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };

    async function handleDelete(){
        const error = await WeightUnitService.deleteWeightUnit(id);
    }

    useEffect(() => {
        async function fetchData() {
            const entityData = await WeightUnitService.getWeightUnitById(id);

            if (entityData.status == 500 && entityData.error) {
                redirect('/404')
            } else {
                setWeightUnit(removeNullProperties(entityData))
            }

        }

        fetchData()
    }, [])

    return (
        <>
            <Header title={`unidad de peso #${id}`} />
            <DetailedView entity='unidadespeso' data={weightUnit} handleDelete={handleDelete}/>
        </>
    )
}

export default WeightUnitDetailed
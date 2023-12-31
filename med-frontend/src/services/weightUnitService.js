import axios from '../config/axios';

const entity = 'weightUnits'

const getWeightUnits = async () => {
    const res = await axios.get(`/${entity}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const getWeightUnitById = async (id) => {
    const res = await axios.get(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const addWeightUnit = async (weightUnit) => {
    const res = await axios.post(`/${entity}`, weightUnit)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const editWeightUnit = async (weightUnit) => {
    const res = await axios.put(`/${entity}`, weightUnit)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const deleteWeightUnit = async (id) => {
    const res = await axios.delete(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}


const WeightUnitService = {
    getWeightUnits,
    getWeightUnitById,
    addWeightUnit,
    editWeightUnit,
    deleteWeightUnit
}

export default WeightUnitService;
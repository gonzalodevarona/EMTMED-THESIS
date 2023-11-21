import axios from '../config/axios';

const entity = 'countingUnits'

const getCountingUnits = async () => {
    const res = await axios.get(`/${entity}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const getCountingUnitById = async (id) => {
    const res = await axios.get(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const addCountingUnit = async (countingUnit) => {
    const res = await axios.post(`/${entity}`, countingUnit)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const editCountingUnit = async (countingUnit) => {
    const res = await axios.put(`/${entity}`, countingUnit)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const deleteCountingUnit = async (id) => {
    const res = await axios.delete(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}


const CountingUnitService = {
    getCountingUnits,
    getCountingUnitById,
    addCountingUnit,
    editCountingUnit,
    deleteCountingUnit
}

export default CountingUnitService;
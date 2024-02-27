import axios from '../config/axios';

const entity = 'consumables'

const getConsumables = async () => {
    const res = await axios.get(`/${entity}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}
const getConsumablesNoBatches = async () => {
    const res = await axios.get(`/${entity}/noBatches`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const getConsumablesInStock = async () => {
    const res = await axios.get(`/${entity}/inStock`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const getConsumableById = async (id) => {
    const res = await axios.get(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const addConsumable = async (consumable) => {
    const res = await axios.post(`/${entity}`, consumable)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const editConsumable = async (consumable) => {
    const res = await axios.put(`/${entity}`, consumable)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const deleteConsumable = async (id) => {
    const res = await axios.delete(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}


const ConsumableService  = {
    getConsumables,
    getConsumablesNoBatches,
    getConsumablesInStock,
    getConsumableById,
    addConsumable,
    editConsumable,
    deleteConsumable
}

export default ConsumableService;
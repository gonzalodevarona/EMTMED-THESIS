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
const getConsumablesNoOrdersNoBatches = async () => {
    const res = await axios.get(`/${entity}/noOrdersNoBatches`)
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
    getConsumablesNoOrdersNoBatches,
    getConsumableById,
    addConsumable,
    editConsumable,
    deleteConsumable
}

export default ConsumableService;
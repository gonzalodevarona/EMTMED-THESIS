import axios from '../config/axios';

const entity = 'supplyOrders'

const getSupplyOrders = async () => {
    const res = await axios.get(`/${entity}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const getSupplyOrderById = async (id) => {
    const res = await axios.get(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const addSupplyOrder = async (supplyOrder) => {
    const res = await axios.post(`/${entity}`, supplyOrder)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const changeSupplyOrderStatus = async (id, orderStatus) => {
    const res = await axios.put(`/${entity}/${id}/${orderStatus}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const deleteSupplyOrder = async (id) => {
    const res = await axios.delete(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}


const SupplyOrderService = {
    getSupplyOrders,
    getSupplyOrderById,
    addSupplyOrder,
    changeSupplyOrderStatus,
    deleteSupplyOrder
}

export default SupplyOrderService;
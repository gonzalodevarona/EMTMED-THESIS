import axios from '../config/axios';

const entity = 'inventoryOrders'

const getInventoryOrders = async () => {
    const res = await axios.get(`/${entity}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const getInventoryOrderById = async (id) => {
    const res = await axios.get(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const addInventoryOrder = async (inventoryOrder) => {
    const res = await axios.post(`/${entity}`, inventoryOrder)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const changeInventoryOrderStatus = async (id, orderStatus) => {
    const res = await axios.put(`/${entity}/${id}/${orderStatus}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const editInventoryOrder = async (inventoryOrder) => {
    const res = await axios.put(`/${entity}`, inventoryOrder)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const deleteInventoryOrder = async (id) => {
    const res = await axios.delete(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}


const InventoryOrderService = {
    getInventoryOrders,
    getInventoryOrderById,
    addInventoryOrder,
    changeInventoryOrderStatus,
    editInventoryOrder,
    deleteInventoryOrder
}

export default InventoryOrderService;
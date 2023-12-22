import axios from '../config/axios';

const entity = 'batches'

const getBatches = async () => {
    const res = await axios.get(`/${entity}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const getBatchById = async (id) => {
    const res = await axios.get(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const getConsumableByBatchId = async (id) => {
    const res = await axios.get(`/${entity}/${id}/consumable`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const getLocationByBatchId = async (id) => {
    const res = await axios.get(`/${entity}/${id}/location`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const addBatch = async (batch) => {
    const res = await axios.post(`/${entity}`, batch)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const editBatch = async (batch) => {
    const res = await axios.put(`/${entity}`, batch)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const deleteBatch = async (id) => {
    const res = await axios.delete(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}


const BatchService = {
    getBatches,
    getBatchById,
    getConsumableByBatchId,
    getLocationByBatchId,
    addBatch,
    editBatch,
    deleteBatch
}

export default BatchService;
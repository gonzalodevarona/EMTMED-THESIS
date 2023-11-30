import axios from '../config/axios';

const entity = 'medicines'

const getMedicines = async () => {
    const res = await axios.get(`/${entity}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}
const getMedicinesNoOrdersNoBatches = async () => {
    const res = await axios.get(`/${entity}/noOrdersNoBatches`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const getMedicineById = async (id) => {
    const res = await axios.get(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const addMedicine = async (medication) => {
    const res = await axios.post(`/${entity}`, medicine)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const editMedicine = async (medicine) => {
    const res = await axios.put(`/${entity}`, medicine)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const deleteMedicine = async (id) => {
    const res = await axios.delete(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}


const MedicineService  = {
    getMedicines,
    getMedicinesNoOrdersNoBatches,
    getMedicineById,
    addMedicine,
    editMedicine,
    deleteMedicine
}

export default MedicineService;
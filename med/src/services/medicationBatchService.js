import axios from '../config/axios';

const entity = 'medicationBatches'

const getMedicationBatches = async () => {
    const res = await axios.get(`/${entity}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}
const getAllMedicationBatchesByIsAvailable = async (isAvailable) => {
    const res = await axios.get(`/${entity}/available/${isAvailable}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const getMedicationBatchById = async (id) => {
    const res = await axios.get(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const getMedicineByMedicationBatchId = async (id) => {
    const res = await axios.get(`/${entity}/${id}/medicine`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const getLocationByMedicationBatchId = async (id) => {
    const res = await axios.get(`/${entity}/${id}/location`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const addMedicationBatch = async (medicationBatch) => {
    const res = await axios.post(`/${entity}`, medicationBatch)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const editMedicationBatch = async (medicationBatch) => {
    const res = await axios.put(`/${entity}`, medicationBatch)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const deleteMedicationBatch = async (id) => {
    const res = await axios.delete(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}


const MedicationBatchService = {
    getMedicationBatches,
    getAllMedicationBatchesByIsAvailable,
    getMedicationBatchById,
    getMedicineByMedicationBatchId,
    getLocationByMedicationBatchId,
    addMedicationBatch,
    editMedicationBatch,
    deleteMedicationBatch
}

export default MedicationBatchService;
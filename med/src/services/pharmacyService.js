import axios from '../config/axios';

const entity = 'pharmacies'

const getPharmacies = async () => {
    const res = await axios.get(`/${entity}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const getPharmacyCategories = async () => {
    const res = await axios.get('/pharmacyCategories')
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const getPharmacyById = async (id) => {
    const res = await axios.get(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const addPharmacy = async (pharmacy) => {
    const res = await axios.post(`/${entity}`, pharmacy)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const editPharmacy = async (pharmacy) => {
    const res = await axios.put(`/${entity}`, pharmacy)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const deletePharmacy = async (id) => {
    const res = await axios.delete(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}


const PharmacyService = {
    getPharmacies,
    getPharmacyById,
    getPharmacyCategories,
    addPharmacy,
    editPharmacy,
    deletePharmacy
}

export default PharmacyService;
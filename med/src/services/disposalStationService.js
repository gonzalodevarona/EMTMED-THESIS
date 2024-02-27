import axios from '../config/axios';

const entity = 'disposalStations'

const getDisposalStations = async () => {
    const res = await axios.get(`/${entity}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const getDisposalStationById = async (id) => {
    const res = await axios.get(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const addDisposalStation = async (disposalStation) => {
    const res = await axios.post(`/${entity}`, disposalStation)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const editDisposalStation = async (disposalStation) => {
    const res = await axios.put(`/${entity}`, disposalStation)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}

const deleteDisposalStation = async (id) => {
    const res = await axios.delete(`/${entity}/${id}`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}


const DisposalStationService = {
    getDisposalStations,
    getDisposalStationById,
    addDisposalStation,
    editDisposalStation,
    deleteDisposalStation
}

export default DisposalStationService;
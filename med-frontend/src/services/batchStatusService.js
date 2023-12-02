import axios from '../config/axios';

const getBatchStatuses = async () => {
    const res = await axios.get('/batchStatuses')
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}


const BatchStatusService = {
    getBatchStatuses
}

export default BatchStatusService;
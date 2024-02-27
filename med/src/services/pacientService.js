import axios from '../config/axiosEMR';


const getPacientById = async (pacientInfo) => {
    const res = await axios.post(
        '/api/Pacient/GetByField',
        pacientInfo,
    )
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}



const PacientService = {

    getPacientById
}

export default PacientService;
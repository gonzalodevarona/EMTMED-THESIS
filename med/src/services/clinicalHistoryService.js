import axios from '../config/axiosEMR';


const addNoteToEMR = async (pacientId, note) => {
    const res = await axios.post(
        `/api/ClinicalHistory/${pacientId}`,
        note,
    )
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}



const ClinicalHistoryService = {

    addNoteToEMR
}

export default ClinicalHistoryService;
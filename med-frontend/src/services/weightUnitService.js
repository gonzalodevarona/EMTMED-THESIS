import axios from '../config/AxiosBackend';

const entity = 'weightUnits'

const getWeightUnits = async () => {
    const res = await axios.get(`/${entity}/`)
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}


const WeightUnitService = {
    getWeightUnits
}

export default WeightUnitService;
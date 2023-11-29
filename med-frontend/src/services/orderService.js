import axios from '../config/axios';

const getOrderStatuses = async () => {
    const res = await axios.get('/orderStatuses')
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}


const OrderService = {
    getOrderStatuses
}

export default OrderService;
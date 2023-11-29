import axios from '../config/axios';

const getInventoryOrderOperations = async () => {
    const res = await axios.get('/inventoryOrderOperations')
        .catch((error) => {
            console.log(error)
            return error.response;
        });

    return res.data
}


const InventoryOrderOperationService = {
    getInventoryOrderOperations
}

export default InventoryOrderOperationService;
import axios from '../config/axiosEMR';


const addNoteToEMR = async (pacientId, note) => {
    let token = 'eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJvVEs4ZlVYU0RwdW1MVFZJR3RvUEJmWkFhOXBUcjJqek1ZNjM4U0dsZ3c4In0.eyJleHAiOjE3MDI2MzI3NjIsImlhdCI6MTcwMjYzMjQ2MiwianRpIjoiY2Q0Zjg1ZTktZTY1Yi00OTIyLTlhZWQtNzdiMzdmNzJiMDEzIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL0VNVCIsImF1ZCI6IkVNVFJFU1QiLCJzdWIiOiIzOTRhMmMxNC1lYWE2LTQ5MDUtOGI0Yy0xYzIzMmE3NWQzNDYiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJmcm9udGVuZCIsInNlc3Npb25fc3RhdGUiOiIxNTQyN2E0ZC1jYzA0LTRmMjAtODg1Yi1mYzVkZDI0MTIzZWYiLCJhY3IiOiIxIiwic2NvcGUiOiJiYXNpY19hcHAgcHJvZmlsZSIsInJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiYWRtaW4iLCJ1bWFfYXV0aG9yaXphdGlvbiJdLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiIxMDA2MDE3NDc4In0.WdKK8RE10YgXnrVCrrwuSkPXC8l1PpkYQkyl-QPzIO7XWlRO4MX_cD0IS8PRHDvbnrDCtJ3Rl6mR3RHA_X6rzlW7v5l1IGq_Cs2Ej4nakkwLUz9W3Aicwi6lH1rTK386-83THK9dwfc8BHfS-umt1k2h8qyGy2qylaUO3HpmRt-o5AWuu__zaCANP5ag4J7Kli4m2yZJ9TdAkQWOVJixVQNBRTk5BtUgcYenPZd5XF4wXLBKAqv1ibNqjJrbtF7nT4XNGDo7rMvmX4Z_5R_ZV9ocQfE5eNJ4TNA8A0ue44bo0Xo3O8Bcf1Dkgm-zb2In9bIHLWiBU240kf2sQAKScA'
    const res = await axios.post(
        `/api/ClinicalHistory/${pacientId}`,
        note,
        {
            headers: { Authorization: `Bearer ${token}` }
        }
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
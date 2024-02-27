import Swal from 'sweetalert2';
import { capitalizeFirstLetter } from '../../utils/CommonMethods';

const triggerCannotDeleteAlert = (entity, id, text) => {
    Swal.fire({
        icon: 'error',
        title: `${capitalizeFirstLetter(entity)} con el ID ${id} NO pudo ser eliminado`,
        text: `${capitalizeFirstLetter(text)}`,
        showConfirmButton: true,
    })
};

export default triggerCannotDeleteAlert;

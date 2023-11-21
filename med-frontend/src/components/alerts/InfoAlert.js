import Swal from 'sweetalert2';
import { capitalizeFirstLetter } from '../../utils/CommonMethods';

const triggerInfoAlert = (icon, title, followingAction) => {
    Swal.fire({
        icon: {icon},
        title: capitalizeFirstLetter(title),
        showConfirmButton: true,
    }).then((result) => {
        followingAction()
    });
};

export default triggerInfoAlert;

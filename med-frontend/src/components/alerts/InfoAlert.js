import Swal from 'sweetalert2';
import { capitalizeFirstLetter } from '../../utils/CommonMethods';

const triggerInfoAlert = (icon, title, followingAction) => {
    Swal.fire({
        icon: icon,
        title: capitalizeFirstLetter(title),
        showConfirmButton: true,
        confirmButtonColor:'#0288d1'
    }).then((result) => {
        
        followingAction && followingAction()
    });
};

export default triggerInfoAlert;

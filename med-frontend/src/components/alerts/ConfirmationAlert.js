import Swal from 'sweetalert2';

const triggerConfrirmationAlert = ({title, text, type, confirmButtonColor, confirmText, action, successTitle, successType, successAction, errorTitle, errorType}) => {

    Swal.fire({
        title: title,
        text: text,
        type: type,
        showCancelButton: true,
        confirmButtonColor: confirmButtonColor || "#f44336",
        confirmButtonText: confirmText,
        cancelButtonText: "Cancelar",
        closeOnConfirm: false,
        closeOnCancel: false
    }).then((result) => {
        if (result.isConfirmed) {
            const error = action()
            if (error.status === 500) {
                Swal.fire({
                    title: errorTitle,
                    type: errorType,
                });
            } else {
                Swal.fire({
                    title: successTitle,
                    type: successType
                }).then((result) => {
                    successAction();
                });
            }
        } else {
            Swal.fire({
                title: errorTitle,
                type: errorType,
            });
        }
    })
};

export default triggerConfrirmationAlert;
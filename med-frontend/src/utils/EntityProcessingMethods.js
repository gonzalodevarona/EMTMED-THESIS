export function dateArrayToString(array) {
    // Crear una nueva fecha con los elementos del array
    let fecha = new Date(array[0], array[1] - 1, array[2], array[3], array[4], array[5]);

    // Opciones para el formato de la fecha
    let opciones = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        timeZoneName: 'short',
        hour12: false
    };

    // Convertir la fecha a un string legible
    let fechaString = fecha.toLocaleString('es-ES', opciones);

    return fechaString;
}

export function convertToLocalTimeZone(dateString) {
    // Crear un objeto de fecha a partir de la cadena de fecha ISO 8600
    let date = new Date(dateString);

    // Convertir la fecha a la zona horaria local y devolverla en formato ISO
    let localDate = new Date(date.getTime() - (date.getTimezoneOffset() * 60000)).toISOString();

    return localDate;
}
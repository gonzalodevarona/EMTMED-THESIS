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

import dayjs from 'dayjs';

export function convertDateObjectToDayjs(dateObject) {

    if (dayjs.isDayjs(dateObject)) {
        return dateObject;
    } else {
        // Asume que el objeto tiene la forma {0: año, 1: mes, 2: día, 3: hora, 4: minuto, 5: segundo}
        const date = dayjs().set('year', dateObject[0])
            .set('month', dateObject[1] - 1) // Los meses en JavaScript empiezan en 0
            .set('date', dateObject[2])
            .set('hour', dateObject[3])
            .set('minute', dateObject[4])
            .set('second', dateObject[5]);
        return date;
    }

}
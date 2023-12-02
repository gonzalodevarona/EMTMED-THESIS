export function dateArrayToString(array) {
    const year = array[0];
    const month = array[1] - 1;
    const day = array[2];
    const hour = array[3] || 0;
    const minute = array[4] || 0;
    const second = array[5] || 0;

    const fecha = new Date(year, month, day, hour, minute, second);

    const opciones = {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    };

    if (array.length > 3) {
        opciones.hour = '2-digit';
        opciones.minute = '2-digit';
        opciones.timeZoneName = 'short';
        opciones.hour12 = false;
    }

    const fechaString = fecha.toLocaleString('es-ES', opciones);

    return fechaString;
}


export function convertToLocalTimeZone(dateString) {
    // Crear un objeto de fecha a partir de la cadena de fecha ISO 8600
    let date = new Date(dateString);

    // Convertir la fecha a la zona horaria local y devolverla en formato ISO
    let localDate = new Date(date.getTime() - (date.getTimezoneOffset() * 60000)).toISOString();

    return localDate;
}

export function formatDateToYYYYMMDD(isoDate) {
    let date = new Date(isoDate);
    let year = date.getFullYear();
    let month = date.getMonth() + 1; // Los meses en JavaScript empiezan desde 0
    let day = date.getDate();

    return year + '-' + month + '-' + day;
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

export function calculateBatchStatus(expirationDate) {
    const currentDate = new Date();
    const sixMonthsInMilliseconds = 6 * 30 * 24 * 60 * 60 * 1000; // Approximately 6 months in milliseconds
    const oneYearInMilliseconds = 365 * 24 * 60 * 60 * 1000; // Approximately 1 year in milliseconds

    const timeDifference = expirationDate - currentDate;

    if (timeDifference < sixMonthsInMilliseconds) {
        return "RED"; // Less than 6 months
    } else if (timeDifference >= sixMonthsInMilliseconds && timeDifference < oneYearInMilliseconds) {
        return "YELLOW"; // Between 6 months and 1 year
    } else {
        return "GREEN"; // More than 1 year
    }
}


export function checkNull(object) {
    for (let key in object) {
        if (object.hasOwnProperty(key)) {
            if (object[key] === null) {
                return true;
            }
            if (typeof object[key] === 'object') {
                if (checkNull(object[key])) {
                    return true;
                }
            }
        }
    }
    return false;
}

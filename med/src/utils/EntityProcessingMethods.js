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
    let year = date.getUTCFullYear();
    // Asegurarse de que el mes y el día sean de dos dígitos
    let month = ('0' + (date.getUTCMonth() + 1)).slice(-2); // Los meses en JavaScript empiezan desde 0
    let day = ('0' + date.getUTCDate()).slice(-2);

    return year + '-' + month + '-' + day;
}


export function convertDateToISO(date) {
    
    date = date.toLowerCase();
    let parts = date.split(" de ");
    let months = {enero: '01', febrero: '02', marzo: '03', abril: '04', mayo: '05', junio: '06', julio: '07', agosto: '08', septiembre: '09', octubre: '10', noviembre: '11', diciembre: '12'};
    let dateISO = new Date(parts[2], months[parts[1]] - 1, parts[0]).toISOString();
    
    return dateISO;
}

import dayjs from 'dayjs';

export function convertDateObjectToDayjs(dateObject) {
    if (dayjs.isDayjs(dateObject)) {
        return dateObject;
    } else {
        // Asume que el objeto tiene la forma {0: año, 1: mes, 2: día, 3: hora, 4: minuto, 5: segundo}
        const date = dayjs().set('year', dateObject[0])
            .set('month', dateObject[1] - 1) // Los meses en JavaScript empiezan en 0
            .set('date', dateObject[2]);

        if (dateObject.length > 3) {
            date.set('hour', dateObject[3])
                .set('minute', dateObject[4])
                .set('second', dateObject[5]);
        }

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

export async function formatNoteForEmr(note) {
    let formattedNote = { nameFormat: "orden medicamentos", fields: [] }
    let attachments = { name: "Adjuntos", value: [] };

    for (let key in note) {
        if (key !== 'batchRequests' && key !== 'medicationBatchRequests' && key !== 'type' && note[key]) {
            formattedNote.fields.push({ name: key, value: note[key] });
        }
    }

    for (let item of note.medicationBatchRequests) {
        let tempAttachment = [];
        console.log(item.medicationBatch)
        if (item.quantity) tempAttachment.push({ name: 'quantity', value: item.quantity});
        if (item.medicationBatch.administrationRoute) tempAttachment.push({ name: 'administrationRoute', value: item.medicationBatch.administrationRoute});
        if (item.medicationBatch.manufacturer) tempAttachment.push({ name: 'manufacturer', value: item.medicationBatch.manufacturer});
        if (item.medicationBatch.supply.activePharmaceuticalIngredient) tempAttachment.push({ name: 'activePharmaceuticalIngredient', value: item.medicationBatch.supply.activePharmaceuticalIngredient});
        if (item.medicationBatch.supply.concentration) tempAttachment.push({ name: 'concentration', value: item.medicationBatch.supply.concentration});
        if (item.medicationBatch.supply.name) tempAttachment.push({ name: 'name', value: item.medicationBatch.supply.name});
        if (item.medicationBatch.supply.countingUnit.name) tempAttachment.push({ name: 'countingUnit', value: item.medicationBatch.supply.countingUnit.name});
       
        if (item.medicationBatch.supply.type) tempAttachment.push({ name: 'type', value: item.medicationBatch.supply.type});

        if (tempAttachment.length > 0) attachments.value.push(tempAttachment);
    }

    for (let item of note.batchRequests) {
        let tempAttachment = [];

        console.log(item)
        if (item.quantity) tempAttachment.push({ name: 'quantity', value: item.quantity});
        if (item.batch.administrationRoute) tempAttachment.push({ name: 'administrationRoute', value: item.batch.administrationRoute});
        if (item.batch.manufacturer) tempAttachment.push({ name: 'manufacturer', value: item.batch.manufacturer});
        if (item.batch.supply.activePharmaceuticalIngredient) tempAttachment.push({ name: 'activePharmaceuticalIngredient', value: item.batch.supply.activePharmaceuticalIngredient});
        if (item.batch.supply.concentration) tempAttachment.push({ name: 'concentration', value: item.batch.supply.concentration});
        if (item.batch.supply.name) tempAttachment.push({ name: 'name', value: item.batch.supply.name});
        if (item.batch.supply.countingUnit.name) tempAttachment.push({ name: 'countingUnit', value: item.batch.supply.countingUnit.name});
        if (item.batch.supply.type) tempAttachment.push({ name: 'type', value: item.batch.supply.type});

        if (tempAttachment.length > 0) attachments.value.push(tempAttachment);
    }
    if (attachments.value.length > 0) formattedNote.fields.push(attachments);

    return formattedNote;
}

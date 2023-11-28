export function dateArrayToString(array) {
    const tempDate = new Date(array[0], array[1] - 1, array[2], array[3], array[4], array[5], array[6]);
    const offsetColombia = -300;
    tempDate.setMinutes(tempDate.getMinutes() + offsetColombia);

    const optionsFormat = {
        year: 'numeric',
        month: 'numeric',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric',
        hour12: true,
        timeZoneName: 'short',
    };

    const dateTimeCol = tempDate.toLocaleString('es-CO', optionsFormat);
    return dateTimeCol;
}
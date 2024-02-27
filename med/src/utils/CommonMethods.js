export function capitalizeFirstLetter(text) {
  return text.charAt(0).toUpperCase() + text.slice(1);
}

export function refreshPage() {
  location.reload();
}


export function removeNullProperties(obj) {
  const newObj = {};

  for (const key in obj) {
    if (obj.hasOwnProperty(key)) {
      if (obj[key] !== null && typeof obj[key] === 'object') {
        // Si el valor es un objeto y no es nulo, llamamos recursivamente a la función
        const nestedObj = removeNullProperties(obj[key]);
        
        // Verificamos si el objeto anidado tiene propiedades después de eliminar las nulas
        if (Object.keys(nestedObj).length > 0) {
          newObj[key] = nestedObj;
        }
      } else if (obj[key] !== null) {
        // Si no es un objeto o si es un objeto nulo, simplemente lo copiamos
        newObj[key] = obj[key];
      }
    }
  }

  return newObj;
}

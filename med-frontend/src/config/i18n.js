import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import LanguageDetector from 'i18next-browser-languagedetector';

i18n
    // detect user language
    // learn more: https://github.com/i18next/i18next-browser-languageDetector
    .use(LanguageDetector)
    // pass the i18n instance to react-i18next.
    .use(initReactI18next)
    // init i18next
    // for all options read: https://www.i18next.com/overview/configuration-options
    .init({
        lng: 'es-419',
        debug: true,
        fallbackLng: 'en',
        interpolation: {
            escapeValue: false, // not needed for react as it escapes by default
        },
        resources: {
            "es-419": {
                translation: {
                    pharmacy: {
                        category: {
                            "WAREHOUSE": "BODEGA",
                            "PRINCIPAL": "PRINCIPAL",
                            "SECONDARY": "SECUNDARIA"
                        }
                    },
                    batch: {
                        status: {
                            "RED": "ROJO",
                            "YELLOW": "AMARILLO",
                            "GREEN": "VERDE"
                        }
                    },
                    order: {
                        status: {
                            "OPEN": "ABIERTA",
                            "PROCESSING": "PROCESANDO",
                            "COMPLETED": "COMPLETADA",
                            "CANCELLED": "CANCELADA",
                        },
                        type: {
                            "supplyOrder": "Orden de paciente",
                            "inventoryOrder": "Orden de inventario",
                        }
                    },
                    pacient: {
                        info: {
                            "id": "ID",
                            "firstName": "Primer nombre",
                            "middleName": "Segundo nombre",
                            "lastName": "Primer apellido",
                            "secondLastName": "Segundo apellido",
                            "idType": "Tipo de identificación",
                            "idNumber": "Número de identificación",
                            "age": "Edad",
                            "birthDate": "Fecha de nacimiento",
                            "gender": "Género",
                            "address": "Dirección",
                            "city": "Ciudad",
                            "neighborhood": "Barrio",
                            "locality": "Localidad",
                            "phoneNumber": "Número de contacto",
                            "guardianPhoneNumber": "Número de teléfono del acudiente",
                            "guardianName": "Nombre del acudiente",
                            "guardianRelationship": "Parentesco con el acudiente",
                            "occupation": "Ocupación",
                            "civilStatus": "Estado civil",
                            "ethnicGroup": "Grupo Étnico",
                            "ethnic": "Étnia",
                            "healthcareProvider": "EPS",
                            "healthcareType": "Régimen de la EPS"
                        }
                    },
                    inventoryOrder: {
                        info: {
                            "type": "Tipo",
                            "id": "Identificación",
                            "idNumberCreatedBy": "Número de ID del creador",
                            "destination": "Destino",
                            "note": "Nota",
                            "authoredOn": "Fecha de Autorización",
                            "practitionerId": "Autorizado por",
                            "status": "Estado",
                            "operation": "Operación",
                        },
                        operation: {
                            "ENTRY": "ENTRADA",
                            "TRANSFER": "TRASLADO",
                            "EXIT": "SALIDA"
                        }
                    },
                    supplyOrder: {
                        info: {
                            "type": "Tipo",
                            "id": "Identificación",
                            "idNumberCreatedBy": "Número de ID del creador",
                            "pacientId": "Número de ID del paciente",
                            "authoredOn": "Fecha de Autorización",
                            "practitionerId": "Autorizado por",
                            "status": "Estado",
                            "note": "Nota",
                            "diagnostic": "Diagnóstico",
                        },
                    },
                    supply: {
                        type: {
                            "consumable": "insumo",
                            "medicine": "medicamento",
                        }
                    },
                    consumable:
                    {
                        info: {
                            "type": "Tipo",
                            "id": "Identificación",
                            "idNumberCreatedBy": "Número de ID del creador",
                            "name": "Nombre",
                            "quantity": "Cantidad",
                            "countingUnit": "Unidad de conteo",
                            "concentration": "Concentración"
                        },
                        batch: {
                            "id": "Identificación",
                            "quantity": "Cantidad",
                            "manufacturer": "Fabricante",
                            "expirationDate": "Fecha de vencimiento",
                            "administrationRoute": "Ruta de administración",
                            "status": "Estado",
                            "isAvailable": "Disponible",
                            "location": "Ubicación",
                            "consumable-countBatches": "Cantidad de lotes del insumo",
                            "consumable-type": "Tipo de recurso",
                            "consumable-id": "ID de insumo",
                            "consumable-idNumberCreatedBy": "Número de ID del creador",
                            "consumable-name": "Nombre del insumo",
                            "consumable-quantity": "Cantidad de insumo",
                            "consumable-countingUnit": "Unidad de conteo del insumo"
                        }
                    },
                    medicine:
                    {
                        info:
                        {
                            "type": "Tipo",
                            "id": "Identificación",
                            "idNumberCreatedBy": "Número de ID del creador",
                            "name": "Nombre",
                            "quantity": "Cantidad",
                            "countingUnit": "Unidad de conteo",
                            "activePharmaceuticalIngredient": "Compuesto activo",
                            "concentration": "Concentración"
                        },
                        medicationBatch: {
                            "id": "Identificación",
                            "quantity": "Cantidad",
                            "manufacturer": "Fabricante",
                            "expirationDate": "Fecha de vencimiento",
                            "administrationRoute": "Ruta de administración",
                            "status": "Estado",
                            "isAvailable": "Disponible",
                            "location": "Ubicación",
                            "parentBatchId": "ID del lote padre",
                            "cum": "CUM",
                            "medicine-countBatches": "Cantidad de lotes del medicamento",
                            "medicine-type": "Tipo de recurso",
                            "medicine-id": "ID de medicamento",
                            "medicine-idNumberCreatedBy": "Número de ID creado por",
                            "medicine-name": "Nombre del medicamento",
                            "medicine-quantity": "Cantidad del medicamento",
                            "medicine-countingUnit": "Unidad de conteo del medicamento",
                            "medicine-activePharmaceuticalIngredient": "Compuesto activo del medicamento",
                            "medicine-concentration": "Concentración del medicamento"
                        }
                    },

                }
            }
        }
    });

export default i18n;
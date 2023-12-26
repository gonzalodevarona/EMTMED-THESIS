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
                        }
                    },
                    inventoryOrder: {
                        info:{
                            "type": "Tipo",
                            "id": "Identificación",
                            "idNumberCreatedBy": "Número de ID del creador",
                            "destination": "Destino",
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
                    supply: {
                        type: {
                            "consumable": "consumible",
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
                            "weight": "Peso",
                            "quantity": "Cantidad",
                            "quantity": "Cantidad",
                            "weightUnit": "Unidad de peso",
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
                            "consumable-countBatches": "Cantidad de lotes del consumible",
                            "consumable-type": "Tipo de consumible",
                            "consumable-id": "ID de consumible",
                            "consumable-idNumberCreatedBy": "Número de ID del creador",
                            "consumable-name": "Nombre del consumible",
                            "consumable-weight": "Peso del consumible",
                            "consumable-quantity": "Cantidad de consumible",
                            "consumable-weightUnit": "Unidad de peso del consumible",
                            "consumable-countingUnit": "Unidad de conteo del consumible"
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
                            "weight": "Peso",
                            "quantity": "Cantidad",
                            "quantity": "Cantidad",
                            "weightUnit": "Unidad de peso",
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
                            "medicine-type": "Tipo de medicamento",
                            "medicine-id": "ID de medicamento",
                            "medicine-idNumberCreatedBy": "Número de ID creado por",
                            "medicine-name": "Nombre del medicamento",
                            "medicine-weight": "Peso del medicamento",
                            "medicine-quantity": "Cantidad del medicamento",
                            "medicine-weightUnit": "Unidad de peso del medicamento",
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
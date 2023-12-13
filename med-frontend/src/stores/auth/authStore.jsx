import { configureStore } from '@reduxjs/toolkit'
import authReducer from "../../features/auth/authReducer"
import storage from 'redux-persist/lib/storage'
import { persistReducer, persistStore } from 'redux-persist'
import thunk from 'redux-thunk'
import { encryptTransform } from 'redux-persist-transform-encrypt'

const persistConfig = {
	key: 'root',
	storage: storage,
	transforms: [
		encryptTransform({
			secretKey: import.meta.env.VITE_SECRET_KEY,
			onError: () => { }
		})
	]
}

const persistedReducer = persistReducer(persistConfig, authReducer)

export const store = configureStore({
	reducer: {
		emt: persistedReducer
	},
	middleware: [thunk]
})

export const persistor = persistStore(store)
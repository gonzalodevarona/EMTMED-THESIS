import React from 'react'
import ReactDOM from 'react-dom/client'
import Routes from './routes/routes'
import { ReactKeycloakProvider } from '@react-keycloak/web'
import keycloak from './config/keycloak'
import { persistor, store } from './stores/auth/authStore'
import { Provider } from 'react-redux'
import { PersistGate } from 'redux-persist/integration/react'
import './config/i18n';

ReactDOM.createRoot(document.getElementById('root')).render(
  <ReactKeycloakProvider authClient={keycloak}>
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <Routes />
      </PersistGate>
    </Provider>
  </ReactKeycloakProvider>
)
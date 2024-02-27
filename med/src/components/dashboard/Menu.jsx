import * as React from 'react';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import ListSubheader from '@mui/material/ListSubheader';
import DashboardIcon from '@mui/icons-material/Dashboard';
import ListAltIcon from '@mui/icons-material/ListAlt';
import InventoryIcon from '@mui/icons-material/Inventory';
import VaccinesIcon from '@mui/icons-material/Vaccines';
import MedicationIcon from '@mui/icons-material/Medication';
import ReceiptIcon from '@mui/icons-material/Receipt';
import PostAddIcon from '@mui/icons-material/PostAdd';
import FmdGoodIcon from '@mui/icons-material/FmdGood';
import LooksOneIcon from '@mui/icons-material/LooksOne';
import TextFieldsIcon from '@mui/icons-material/TextFields';
import AssignmentIcon from '@mui/icons-material/Assignment';
import { Link } from 'react-router-dom';
import { Divider } from '@mui/material';
import { Home } from '@mui/icons-material';
import { useKeycloak } from '@react-keycloak/web';

export default function Menu({ mobile, toggleDrawer }) {

  let closeMenu = () => {
    if (mobile) {
      toggleDrawer();
    }
  };

  const { keycloak } = useKeycloak();

  return (
    <>
      < ListItemButton component={Link} to="/" onClick={closeMenu} >
        <ListItemIcon>
          <Home />
        </ListItemIcon>
        <ListItemText primary="Inicio" />
      </ListItemButton >


      {(keycloak.hasRealmRole("ROLE_ADMIN") || keycloak.hasRealmRole("ROLE_REGENT")) &&
        <ListItemButton component={Link} to="/ordenes" onClick={closeMenu}>
          <ListItemIcon>
            <ReceiptIcon />
          </ListItemIcon>
          <ListItemText primary="Ordenes de pacientes" />
        </ListItemButton>}

      {(keycloak.hasRealmRole("ROLE_ADMIN") || keycloak.hasRealmRole("ROLE_PRACTITIONER") || keycloak.hasRealmRole("ROLE_NURSE")) &&
        <ListItemButton component={Link} to="/ordenes/agregar" onClick={closeMenu}>
          <ListItemIcon>
            <PostAddIcon />
          </ListItemIcon>
          <ListItemText primary="Agregar Orden de Paciente" />
        </ListItemButton>}

      {(keycloak.hasRealmRole("ROLE_ADMIN") || keycloak.hasRealmRole("ROLE_REGENT")) &&
        <ListItemButton component={Link} to="/ordenes-inventario" onClick={closeMenu}>
          <ListItemIcon>
            <ListAltIcon />
          </ListItemIcon>
          <ListItemText primary="Ordenes de inventario" />
        </ListItemButton>}

      {(keycloak.hasRealmRole("ROLE_ADMIN") || keycloak.hasRealmRole("ROLE_REGENT")) &&
        <ListItemButton component={Link} to="/medicamentos" onClick={closeMenu}>
          <ListItemIcon>
            <MedicationIcon />
          </ListItemIcon>
          <ListItemText primary="Medicamentos" />
        </ListItemButton>}

      {(keycloak.hasRealmRole("ROLE_ADMIN") || keycloak.hasRealmRole("ROLE_REGENT")) &&
        <ListItemButton component={Link} to="/insumos" onClick={closeMenu}>
          <ListItemIcon>
            <VaccinesIcon />
          </ListItemIcon>
          <ListItemText primary="Insumos" />
        </ListItemButton>}

      {(keycloak.hasRealmRole("ROLE_ADMIN") || keycloak.hasRealmRole("ROLE_REGENT")) &&
        <ListItemButton component={Link} to="/lotes" onClick={closeMenu}>
          <ListItemIcon>
            <InventoryIcon />
          </ListItemIcon>
          <ListItemText primary="Lotes" />
        </ListItemButton>}

      {(keycloak.hasRealmRole("ROLE_ADMIN") || keycloak.hasRealmRole("ROLE_REGENT")) &&
        <ListItemButton component={Link} to="/ubicaciones" onClick={closeMenu}>
          <ListItemIcon>
            <FmdGoodIcon />
          </ListItemIcon>
          <ListItemText primary="Ubicaciones" />
        </ListItemButton>}

      {(keycloak.hasRealmRole("ROLE_ADMIN") || keycloak.hasRealmRole("ROLE_REGENT")) &&
        <ListItemButton component={Link} to="/unidades-conteo" onClick={closeMenu}>
          <ListItemIcon>
            <LooksOneIcon />
          </ListItemIcon>
          <ListItemText primary="Unidades de Conteo" />
        </ListItemButton>}

      {/* {(keycloak.hasRealmRole("ROLE_ADMIN") || keycloak.hasRealmRole("ROLE_REGENT")) &&
        <ListItemButton component={Link} to="/campos" onClick={closeMenu}>
          <ListItemIcon>
            <TextFieldsIcon />
          </ListItemIcon>
          <ListItemText primary="Campos adicionales" />
        </ListItemButton>} */}

      <Divider></Divider>

      <ListSubheader component="div" inset>
        Módulos
      </ListSubheader>

      <ListItemButton component={Link} to="http://localhost:3000/emr" onClick={closeMenu}>
        <ListItemIcon>
          <AssignmentIcon />
        </ListItemIcon>
        <ListItemText primary="Historia Clínica" />
      </ListItemButton>

      <ListItemButton component={Link} to="http://localhost:8080" onClick={closeMenu}>
        <ListItemIcon>
          <AssignmentIcon />
        </ListItemIcon>
        <ListItemText primary="Exámenes de Laboratorio" />
      </ListItemButton>

      <ListItemButton component={Link} to="http://localhost:5173/oap" onClick={closeMenu}>
        <ListItemIcon>
          <AssignmentIcon />
        </ListItemIcon>
        <ListItemText primary="Observaciones y procedimientos" />
      </ListItemButton>
    </>
  )

}


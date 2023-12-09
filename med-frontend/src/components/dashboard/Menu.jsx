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
import FmdGoodIcon from '@mui/icons-material/FmdGood';
import LooksOneIcon from '@mui/icons-material/LooksOne';
import BarChartIcon from '@mui/icons-material/BarChart';
import TextFieldsIcon from '@mui/icons-material/TextFields';
import AssignmentIcon from '@mui/icons-material/Assignment';
import { Link } from 'react-router-dom';
import { Divider } from '@mui/material';

export default function Menu({ mobile, toggleDrawer }) {

  let closeMenu = () => {
    if (mobile) {
      toggleDrawer();
    }
  };

  return (
    <>
      <React.Fragment>
        
        <ListItemButton component={Link} to="/ordenes" onClick={closeMenu}>
          <ListItemIcon>
            <ReceiptIcon />
          </ListItemIcon>
          <ListItemText primary="Ordenes de pacientes" />
        </ListItemButton>

        <ListItemButton component={Link} to="/ordenes-inventario" onClick={closeMenu}>
          <ListItemIcon>
            <ListAltIcon />
          </ListItemIcon>
          <ListItemText primary="Ordenes de inventario" />
        </ListItemButton>

        <ListItemButton component={Link} to="/medicamentos" onClick={closeMenu}>
          <ListItemIcon>
            <MedicationIcon />
          </ListItemIcon>
          <ListItemText primary="Medicamentos" />
        </ListItemButton>

        <ListItemButton component={Link} to="/consumibles" onClick={closeMenu}>
          <ListItemIcon>
            <VaccinesIcon />
          </ListItemIcon>
          <ListItemText primary="Consumibles" />
        </ListItemButton>

        <ListItemButton component={Link} to="/lotes" onClick={closeMenu}>
          <ListItemIcon>
            <InventoryIcon />
          </ListItemIcon>
          <ListItemText primary="Lotes" />
        </ListItemButton>

        <ListItemButton component={Link} to="/ubicaciones" onClick={closeMenu}>
          <ListItemIcon>
            <FmdGoodIcon />
          </ListItemIcon>
          <ListItemText primary="Ubicaciones" />
        </ListItemButton>

        <ListItemButton component={Link} to="/unidades" onClick={closeMenu}>
          <ListItemIcon>
            <LooksOneIcon />
          </ListItemIcon>
          <ListItemText primary="Unidades" />
        </ListItemButton>

        <ListItemButton component={Link} to="/campos" onClick={closeMenu}>
          <ListItemIcon>
            <TextFieldsIcon />
          </ListItemIcon>
          <ListItemText primary="Campos adicionales" />
        </ListItemButton>

        <Divider></Divider>

        <ListSubheader component="div" inset>
          Módulos
        </ListSubheader>

        <ListItemButton>
          <ListItemIcon>
            <AssignmentIcon />
          </ListItemIcon>
          <ListItemText primary="Historia clínica" />
        </ListItemButton>

        <ListItemButton>
          <ListItemIcon>
            <AssignmentIcon />
          </ListItemIcon>
          <ListItemText primary="Observaciones" />
        </ListItemButton>

        <ListItemButton>
          <ListItemIcon>
            <AssignmentIcon />
          </ListItemIcon>
          <ListItemText primary="Laboratorio" />
        </ListItemButton>

      </React.Fragment>
    </>
  )

}


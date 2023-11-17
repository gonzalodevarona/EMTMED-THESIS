import * as React from 'react';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import ListSubheader from '@mui/material/ListSubheader';
import DashboardIcon from '@mui/icons-material/Dashboard';
import InventoryIcon from '@mui/icons-material/Inventory';
import VaccinesIcon from '@mui/icons-material/Vaccines';
import MedicationIcon from '@mui/icons-material/Medication';
import ReceiptIcon from '@mui/icons-material/Receipt';
import FmdGoodIcon from '@mui/icons-material/FmdGood';
import LooksOneIcon from '@mui/icons-material/LooksOne';
import BarChartIcon from '@mui/icons-material/BarChart';
import TextFieldsIcon from '@mui/icons-material/TextFields';
import AssignmentIcon from '@mui/icons-material/Assignment';

export const mainListItems = (
  <React.Fragment>
    <ListItemButton>
      <ListItemIcon>
        <ReceiptIcon />
      </ListItemIcon>
      <ListItemText primary="Ordenes" />
    </ListItemButton>
    <ListItemButton>
      <ListItemIcon>
        <MedicationIcon />
      </ListItemIcon>
      <ListItemText primary="Medicamentos" />
    </ListItemButton>
    <ListItemButton>
      <ListItemIcon>
        <VaccinesIcon />
      </ListItemIcon>
      <ListItemText primary="Consumibles" />
    </ListItemButton>
    <ListItemButton>
      <ListItemIcon>
        <InventoryIcon />
      </ListItemIcon>
      <ListItemText primary="Lotes" />
    </ListItemButton>
    <ListItemButton>
      <ListItemIcon>
        <FmdGoodIcon />
      </ListItemIcon>
      <ListItemText primary="Ubicaciones" />
    </ListItemButton>
    <ListItemButton>
      <ListItemIcon>
        <LooksOneIcon />
      </ListItemIcon>
      <ListItemText primary="Unidades" />
    </ListItemButton>
    <ListItemButton>
      <ListItemIcon>
        <TextFieldsIcon />
      </ListItemIcon>
      <ListItemText primary="Campos adicionales" />
    </ListItemButton>
  </React.Fragment>
);

export const secondaryListItems = (
  <React.Fragment>
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
);
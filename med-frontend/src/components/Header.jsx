import { AppBar, Typography, Box } from '@mui/material'
import { capitalizeFirstLetter } from '../utils/CommonMethods';

function Header({ title }) {
    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static" sx={{ mb: 2, p: 1 }}>
                <Typography variant="h5" component="div" align='center' sx={{ flexGrow: 20 }}>
                    {capitalizeFirstLetter(title)}
                </Typography>
            </AppBar>
        </Box>
    )
}

export default Header;
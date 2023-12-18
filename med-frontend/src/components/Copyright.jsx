import Typography from '@mui/material/Typography'
import Link from '@mui/material/Link'

export default function Copyright(props) {
    return (
        <Typography variant="body2" color="text.secondary" align="center" {...props}>
            {'Copyright © '}
            <Link color="inherit" href="https://www.instagram.com/barcohospital">
                Emergency Medical Team
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    )
}
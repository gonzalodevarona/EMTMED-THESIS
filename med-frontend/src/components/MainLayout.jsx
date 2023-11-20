import { Table, TableContainer, TableHead, TableRow, TableCell, TableBody, Button, Paper, Stack } from "@mui/material"

import Header from "./Header"

const MainLayout = ({title, singleEntity, columnNames}) => {
    

    return (
        <>
            <Header title={title} />
            <Button variant="contained" color="secondary" sx={{  color: "white" }} >Agregar {singleEntity}</Button>
            
                <>
                    
                        
                        
                    
                </>
            
        </>
    )
}

export default MainLayout
import { Box, Button, Grid, Stack, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import Header from './Header';
import { useParams } from "react-router-dom";
import { capitalizeFirstLetter } from '../utils/CommonMethods';
import { Link } from 'react-router-dom';

function DetailLayout({ title }) {


    const [data, setData] = useState({})
    const [firstHalf, setFirstHalf] = useState([])
    const [secondHalf, setSecondHalf] = useState([])

    let { id } = useParams();




    useEffect(() => {

        function splitArray() {
            if (Object.keys(data).length > 3) {
                const half = Object.keys(data).length / 2;


                const tempFirstHalf = [];
                const tempSecondHalf = [];

                Object.entries(data).forEach(([key, value], index) => {
                    if (index < half) {
                        tempFirstHalf.push({ [key]: value });
                    } else {
                        tempSecondHalf.push({ [key]: value });
                    }
                });


                setFirstHalf(tempFirstHalf);
                setSecondHalf(tempSecondHalf);
            }
        }

        setData({ id: 1, name: 'benji' })

        splitArray();
    }, []);


    return (
        <>
            <Header title={`${title} #${id}`} />

            {Object.keys(data).length > 3 ? (
                <Grid container>
                    <Grid item xs={6}>
                        {firstHalf.map((item, index) => (
                            Object.entries(item).map(([key, value]) => (
                                <Stack m={3} key={key}>
                                    <Typography fontWeight='bold' variant='subtitle1'>{capitalizeFirstLetter(key)}</Typography>
                                    <Typography>{value}</Typography>
                                </Stack>
                            ))

                        ))}
                    </Grid>
                    <Grid item xs={6}>
                        {secondHalf.map((item, index) => (
                            Object.entries(item).map(([key, value]) => (
                                <Stack m={3} key={key}>
                                    <Typography fontWeight='bold' variant='subtitle1'>{capitalizeFirstLetter(key)}</Typography>
                                    <Typography>{value}</Typography>
                                </Stack>
                            ))

                        ))}
                    </Grid>

                </Grid>
            ) : (
                <Stack>
                    {Object.entries(data).map(([key, value]) => (
                        <Stack m={3} key={key}>
                            <Typography fontWeight='bold' variant='subtitle1'>{capitalizeFirstLetter(key)}</Typography>
                            <Typography>{value}</Typography>
                        </Stack>
                    ))}
                </Stack>
            )}

        
            <Button
                component={Link}
                to={`/unidadesconteo/editar/${id}`}
                variant="contained"
                sx={{ px: 9, py: 1, mr: 2 }}
                color={'info'}>
                Editar
            </Button>
            <Button sx={{ px: 8, py: 1, ml: 2 }} color='error' variant='contained'>
                Eliminar
            </Button>

        </>
    );
}

export default DetailLayout;

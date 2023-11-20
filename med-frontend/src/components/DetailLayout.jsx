import { Box, Button, Grid, Stack, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import Header from './Header';
import { capitalizeFirstLetter } from '../utils/CommonMethods';

function DetailLayout({ singleData, title }) {


    const [firstHalf, setFirstHalf] = useState([])
    const [secondHalf, setSecondHalf] = useState([])




    useEffect(() => {
        function splitArray() {
            if (Object.keys(singleData).length > 3) {
                const half = Object.keys(singleData).length / 2;


                const tempFirstHalf = [];
                const tempSecondHalf = [];

                Object.entries(singleData).forEach(([key, value], index) => {
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

        splitArray();
    }, []);


    return (
        <>
            <Header title={`${title} #${singleData.id}`} />

            {Object.keys(singleData).length > 3 ? (
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
                    {Object.entries(singleData).map(([key, value]) => (
                        <Stack m={3} key={key}>
                            <Typography fontWeight='bold' variant='subtitle1'>{capitalizeFirstLetter(key)}</Typography>
                            <Typography>{value}</Typography>
                        </Stack>
                    ))}
                </Stack>
            )}

            <Button sx={{ px: 9, py: 1, mr: 2 }} color='info' variant='contained'>
                Editar
            </Button>
            <Button sx={{ px: 8, py: 1, ml: 2 }} color='error' variant='contained'>
                Eliminar
            </Button>

        </>
    );
}

export default DetailLayout;

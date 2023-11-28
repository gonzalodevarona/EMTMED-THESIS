import { Box, Button, Grid, Stack, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import { capitalizeFirstLetter } from '../utils/CommonMethods';
import { Link } from 'react-router-dom';


function DetailedView({ data, entity, handleDelete }) {
  const [firstHalf, setFirstHalf] = useState([]);
  const [secondHalf, setSecondHalf] = useState([]);

  useEffect(() => {
    function splitArray() {
      if (data && Object.keys(data).length > 0 && Object.keys(data).length > 3) {
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

    splitArray();
  }, [data]);

  useEffect(() => {
    console.log(firstHalf)
  }, [firstHalf])
  useEffect(() => {
    console.log(secondHalf)
  }, [secondHalf])


  return (
    <>
      {data && Object.keys(data).length > 3 ? (
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
        to={`/${entity}/editar/${data.id}`}
        variant="contained"
        sx={{ px: 9, py: 1, mr: 2 }}
        color={'info'}>
        Editar
      </Button>
      <Button onClick={handleDelete} sx={{ px: 8, py: 1, ml: 2 }} color='error' variant='contained'>
        Eliminar
      </Button>
    </>
  );
}

export default DetailedView;



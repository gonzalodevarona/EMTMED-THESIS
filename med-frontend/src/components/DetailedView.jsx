import { Button, Grid, Stack, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import { capitalizeFirstLetter } from '../utils/CommonMethods';
import FabLink from '../components/buttons/FabLink'
import FabActionButton from '../components/buttons/FabActionButton'
import { Edit, Delete } from '@material-ui/icons/';


function DetailedView({ data, entity, handleDelete, deleteable, editable }) {
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


  return (
    <>
      <Stack alignItems='center'>
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

        <Stack direction='row' spacing={5}>
          {editable && <FabLink to={`/${entity}/editar/${data.id}`} icon={<Edit />} color='info' />}
          {deleteable && <FabActionButton color='error' handleClick={handleDelete} icon={<Delete />} />}
        </Stack>

      </Stack>
    </>
  );
}

export default DetailedView;



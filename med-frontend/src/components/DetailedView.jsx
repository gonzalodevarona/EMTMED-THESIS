import { Button, Grid, Stack, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import { capitalizeFirstLetter } from '../utils/CommonMethods';
import FabLink from '../components/buttons/FabLink'
import FabActionButton from '../components/buttons/FabActionButton'
import { Edit, Delete } from '@material-ui/icons/';
import { useTranslation } from 'react-i18next';



function DetailedView({ data, entity, handleDelete, deleteable, editable }) {
  const { t } = useTranslation();
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

    if (data.cum !== undefined) {
      let translatedObject = {};

      for (let key in data) {
        let translatedKey = t(`medicine.medicationBatch.${key}`);
        if (key === 'status') {
          translatedObject[translatedKey] = t(`batch.status.${data[key]}`);
        } else if (key === 'medicine-type') {
          translatedObject[translatedKey] = t(`supply.type.${data[key]}`);
        }
        else {
          translatedObject[translatedKey] = data[key];
        }
      }
      data = translatedObject;
    } else if (data.id !== undefined) {
      let translatedObject = {};

      for (let key in data) {
        let translatedKey = t(`consumable.batch.${key}`);
        if (key === 'status') {
          translatedObject[translatedKey] = t(`batch.status.${data[key]}`);
        } else if (key === 'consumable-type') {
          translatedObject[translatedKey] = t(`supply.type.${data[key]}`);
        }
        else {
          translatedObject[translatedKey] = data[key];
        }
      }
      data = translatedObject;
    }
    console.log(data)
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
                  <Typography m={3} key={key}>
                    <span style={{ fontWeight: 'bold' }}>{capitalizeFirstLetter(key)}:</span> {value}
                  </Typography>
                ))
              ))}
            </Grid>
            <Grid item xs={6}>
              {secondHalf.map((item, index) => (
                Object.entries(item).map(([key, value]) => (
                  <Typography m={3} key={key}>
                    <span style={{ fontWeight: 'bold' }}>{capitalizeFirstLetter(key)}:</span> {value}
                  </Typography>
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



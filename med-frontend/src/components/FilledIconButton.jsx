import * as React from "react";
import IconButton from "@mui/material/IconButton";


const variantBackgroundColor = {
  filled: "primary.main"
};

const variantColor = {
  filled: "white"
};

export default function FilledIconButton({ variant, ...other }) {
  return (
    <IconButton
      sx={{
        backgroundColor: variantBackgroundColor[variant],
        color: variantColor[variant],
        "&:hover": { backgroundColor: variantBackgroundColor[variant] }
      }}
      {...other}
    />
  );
}

// export default function IconButtons() {
//   return (
//     <Stack spacing={2} direction="row">
//       <FilledIconButton
//         variant="standard"
//         color="primary"
//         aria-label="add to shopping cart"
//       >
//         <AddShoppingCartIcon />
//       </FilledIconButton>
//       <FilledIconButton
//         variant="filled"
//         color="primary"
//         aria-label="add to shopping cart"
//       >
//         <AddShoppingCartIcon />
//       </FilledIconButton>
//     </Stack>
//   );
// }

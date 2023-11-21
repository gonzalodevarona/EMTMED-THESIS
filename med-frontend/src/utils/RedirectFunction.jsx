import { useNavigate } from "react-router-dom";

export default function RedirectFunction({ path }) {
  const navigate = useNavigate();

  const redirect = () => {
    navigate(path);
  };

  return (
   {redirect}
  );
}

import './App.css';
import { ThemeProvider } from '@emotion/react';
import darktheme from './theme/darktheme';
import Navbar from './Page/Navbar/Navbar';
import Home from './Page/Home/Home';
import Auth from './Page/Auth/Auth';
import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getUser } from './ReduxToolkit/Slices/AuthSlice';
import { fetchTasks } from './ReduxToolkit/Slices/TaskSlice';

function App() {
  const user = true;

  const dispatch = useDispatch();
    const {task, auth} = useSelector(store=>store);

    useEffect(() => {
        dispatch(fetchTasks({}));
        dispatch(getUser(auth.jwt || localStorage.getItem("jwt")));
    }, [auth.jwt]);

  return (
    <ThemeProvider theme={darktheme}>
      {auth.user ? <div>
        <Navbar />
        <Home />
      </div>
        : <Auth />  
    }
    </ThemeProvider>

  );
}

export default App;

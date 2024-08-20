import './App.css';
import { ThemeProvider } from '@emotion/react';
import darktheme from './theme/darktheme';
import Navbar from './Page/Navbar/Navbar';
import Home from './Page/Home/Home';
import Auth from './Page/Auth/Auth';
import { useState } from 'react';

function App() {
  const user = true;
  return (
    <ThemeProvider theme={darktheme}>
      {user ? <div>
        <Navbar />
        <Home />
      </div>
        : <Auth />  
    }
    </ThemeProvider>

  );
}

export default App;

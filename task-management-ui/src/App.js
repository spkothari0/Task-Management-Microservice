import './App.css';
import { ThemeProvider } from '@emotion/react';
import darktheme from './theme/darktheme';
import Navbar from './Page/Navbar/Navbar';
import Home from './Page/Home/Home';

function App() {
  return (
    <ThemeProvider theme={darktheme}>
      <Navbar/>
      <Home/>
    </ThemeProvider>
      
  );
}

export default App;

import logo from './logo.svg';
import './App.css';
import Header from './Header';
import FilmList from './FilmList';
import CreateFilmForm from './CreateFilmForm';

function App() {
  return (
    <>
      <Header/>
      <FilmList/>
      <CreateFilmForm/>
    </>
  );
}

export default App;

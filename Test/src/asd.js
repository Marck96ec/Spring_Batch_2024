import React from 'react';
import { createRoot } from 'react-dom/client';

const OPERATIONS = {
  ADD: "ADD",
  SUBTRACT: "SUBTRACT"
};

function App() {
  const [number, updateNumber] = React.useState(0);

  const [state, dispatch] = React.useReducer((state, action) => {
    /* implement the reducer which should update the state based on the action */
    switch (action) {
        case OPERATIONS.ADD:
            return state + number;
        case OPERATIONS.SUBTRACT:
            return state - number;

    }
    return state;
  }, 0);

  /* implement dispatches */
    const add = () => dispatch(OPERATIONS.ADD);
    const subtract = () => dispatch(OPERATIONS.SUBTRACT);


  const handleNumberChange = e => updateNumber(Number(e.target.value));

  return (
    <div>
      <div id="result">{state}</div>
      <div>
        <button id="add" onClick={add}>Add</button>
        <button id="subtract" onClick={subtract}>Subtract</button>
      </div>
      <div>
        <input type="text" value={number} onChange={handleNumberChange} />
      </div>
    </div>
  );
}

document.body.innerHTML = "<div id='root'></div>";
const root = createRoot(document.getElementById("root"));
root.render(<App />);
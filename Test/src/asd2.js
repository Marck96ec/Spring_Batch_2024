import React from 'react';
import { createRoot } from 'react-dom/client';

function PlayerStatus() {
  const [status, setStatus] = React.useState("online");
  const [counter, setCounter] = React.useState(0);

  // Toggle between the two status values - 'away' and 'online'
  function toggleStatus() {
    // Write your code here
    setStatus(status === 'online' ? 'away' : 'online');

  }

  // Implement effect hook below.
  // Update the counter when status changes.
    React.useEffect(() => {
        setCounter(counter + 1);
    }, [status]);

  return (
    <div>
      <h1>{status}</h1>
      <h3>{counter}</h3>
      <button onClick={toggleStatus}>Toggle status</button>
    </div>
  );
};

document.body.innerHTML = "<div id='root'></div>";
const rootElement = document.getElementById("root");
const root = createRoot(rootElement);
root.render(<PlayerStatus />);
setTimeout(() => console.log(rootElement.innerHTML), 300);
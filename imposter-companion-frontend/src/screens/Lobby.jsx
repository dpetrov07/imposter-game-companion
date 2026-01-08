import { useState } from "react";

/**
 * Lobby for displaying player setup screen.
 * 
 * - Displays list of players
 * - Collects new player names on input
 */
function Lobby({ game, onAddPlayer, loading }) {
  const [player, setPlayer] = useState("");

  /**
   * Handles submitting a new player.
   */
  function submitPlayer() {
    if(!player.trim()) return;
    onAddPlayer(player);
    setPlayer("");
  }

  return(
    <div>

      <p>
        Game ID: <code>{game.id}</code>
      </p>

      {/* Display current list of players in lobby. */}
      <p> Players: </p>
      <ul>
        {game.players.map((p) => (
          <li key={p.id}>{p.name}</li>
        ))}
      </ul>
      
      {/* Input for entering a new player. */}
      <input
        value={player}
        onChange={(e) => setPlayer(e.target.value)}
        placeholder= "Player Name"
        disabled={loading}
      />

      {/* Button to request to add a new player. */}
      <button onClick={submitPlayer} disabled={loading}>
        Add Player
      </button>

    </div>
  );
}

export default Lobby;
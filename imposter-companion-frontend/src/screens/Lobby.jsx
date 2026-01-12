import { useState } from "react";

/**
 * Lobby for displaying player setup screen.
 * 
 * - Displays list of players
 * - Collects new player names on input
 */
function Lobby({ game, onAddPlayer, onStartGame, categories, loading }) {
  const [player, setPlayer] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("");

  /**
   * Handles submitting a new player.
   */
  function submitPlayer() {
    if(!player.trim()) return;
    onAddPlayer(player);
    setPlayer("");
  }

  return (
    <div>
      <p>
        Game ID: <code>{game.id}</code>
      </p>

      {/* Category selection dropdown menu. */}
      <select
        value={selectedCategory}
        onChange={(e) => setSelectedCategory(e.target.value)}
        disabled={loading}
      >
        <option value="">Categories</option>
        {categories.map((category) => (
          <option key={category.id} value={category.id}>
            {category.name}
          </option>
        ))}
      </select>

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
        placeholder="Player Name"
        disabled={loading}
      />

      {/* Button to request to add a new player. */}
      <button onClick={submitPlayer} disabled={loading}>
        Add Player
      </button>

      {/* Button to start the game */}
      <button
        onClick={() => onStartGame(selectedCategory)}
        disabled={loading || game.players.length < 3 || !selectedCategory}
      >
        Start Game
      </button>
    </div>
  );
}

export default Lobby;
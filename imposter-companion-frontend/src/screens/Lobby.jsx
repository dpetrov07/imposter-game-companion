import { useState } from "react";

/**
 * Lobby for displaying player setup screen.
 * 
 * - Displays list of players
 * - Collects new player names on input
 */
function Lobby({ game, onAddPlayer, onRemovePlayer, onStartGame, categories, loading }) {
  const [playerName, setPlayerName] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("");
  const [showCategories, setShowCategories] = useState(false);

  /**
   * Handles submitting a new player.
   */
  function submitPlayer() {
    if(!playerName.trim()) return;
    onAddPlayer(playerName);
    setPlayerName("");
  }

  return (
    <div className="screen">
      {/* <p>
        Game ID: <code>{game.id}</code>
      </p> */}

      {/* Category selection menu. */}
      <div className="category-selector">
        {/* Show category button. */}
        <button
          className="secondary-button"
          onClick={() => setShowCategories((open) => !open)}
          disabled={loading}
        >
        {/* Button text to show category or default text */}
          {selectedCategory
            ? `Selected Category: ${categories.find(c => c.id === selectedCategory)?.name}`
            : "Select Category"}
        </button>

        {/* Categories in dropdown menu. */}
        {showCategories && (
          <div className="category-menu">
            {categories.map((category) => (
              <button
                key={category.id}
                className="category-option"
                onClick={() => {
                  setSelectedCategory(category.id);
                  setShowCategories(false);
                }}
              >
                {category.name}
              </button>
            ))}
          </div>
        )}
      </div>

      {/* Display current list of players in lobby. */}
      <p> Players: </p>
      <ul className="player-list">
        {game.players.map((player) => (
          <li key={player.id} className="player-item">
            {player.name}

            {/* Button to remove player. */}
            <button
              className="remove-button"
              onClick={() => onRemovePlayer(player.id)}
              disabled={loading}
            >
              x
            </button>
          </li>
        ))}
      </ul>

      {/* Input for entering a new player. */}
      <input
        className="input"
        value={playerName}
        onChange={(e) => setPlayerName(e.target.value)}
        placeholder="Player Name"
        disabled={loading}
      />

      {/* Button to add a new player. */}
      <button
        className="secondary-button"
        onClick={submitPlayer}
        disabled={loading}
      >
        Add Player
      </button>

      {/* Button to start the game */}
      <button
        className="primary-button"
        onClick={() => onStartGame(selectedCategory)}
        disabled={loading || game.players.length < 3 || !selectedCategory}
      >
        Start Game
      </button>
    </div>
  );
}

export default Lobby;
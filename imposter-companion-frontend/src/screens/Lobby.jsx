import { useEffect, useState } from "react";
import { GAME_LIMITS } from "../constants";

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
  const [nameError, setNameError] = useState("");
  const [startGameError, setStartGameError] = useState("");

  const ERROR_TIMEOUT_MS = 2000;

  /**
   * Starts timer to clear nameError when present
   */
  useEffect(() => {
    if (!nameError) return;

    const timer = setTimeout(() => {
      setNameError("");
    }, ERROR_TIMEOUT_MS);

    return () => clearTimeout(timer);
  }, [nameError]);

  /**
   * Starts timer to clear startGameError when present
   */
  useEffect(() => {
    if (!startGameError) return;

    const timer = setTimeout(() => {
      setStartGameError("");
    }, ERROR_TIMEOUT_MS);

    return () => clearTimeout(timer);
  }, [startGameError]);

  /**
   * Handles submitting a new player.
   */
  function submitPlayer() {
    const errorMessage = validatePlayerName();
    if (errorMessage) {
      setNameError(errorMessage);
      return;
    }

    onAddPlayer(playerName.trim());
    setPlayerName("");
    setNameError("");
  }

  /**
   * Handles checking for valid added player names.
   * 
   * @return true if name is valid
   */
  function validatePlayerName() {
    const trimmedName = playerName.trim();
    if(!trimmedName) return "Name cannot be blank.";
    if(trimmedName.length > GAME_LIMITS.MAX_NAME_LENGTH) 
      return `Name cannot exceed ${GAME_LIMITS.MAX_NAME_LENGTH} characters`;

    const nameExists = game.players.some(
      (player) => player.name.toLowerCase() === trimmedName.toLowerCase()
    );
    if (nameExists) return "That name is already taken."

    return null;
  }

  /**
   * Handles checking for ability to start game.
   */
  function handleStartGame() {
    if (game.players.length < GAME_LIMITS.MIN_PLAYERS) {
      setStartGameError(`At least ${GAME_LIMITS.MIN_PLAYERS} players are required to start the game.`);
      return;
    }

    if (game.players.length > GAME_LIMITS.MAX_PLAYERS) {
      setStartGameError(`Maximum of ${GAME_LIMITS.MAX_PLAYERS} players reached.`);
      return;
    }

    if (!selectedCategory) {
      setStartGameError("Please select a category.");
      return;
    }

    onStartGame(selectedCategory);
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

      {/* Displays error when exists for adding player */}
      <div className="error-slot">
        <p className="error-text">{nameError || "\u00A0"}</p>
      </div>

      {/* Button to start the game */}
      <button
        className="primary-button"
        onClick={handleStartGame}
        disabled={loading}
      >
        Start Game
      </button>

      {/* Displays error when exists for starting game */}
      <div className="error-slot">
        <p className="error-text">{startGameError || "\u00A0"}</p>
      </div>

    </div>
  );
}

export default Lobby;
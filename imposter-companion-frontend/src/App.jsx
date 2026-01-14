import { useEffect, useState } from "react";
import { createGame, addPlayer, removePlayer, startGame, resetGame, getPlayerSecret, getCategories } from "./api/gameApi";
import { CreateGame, Lobby, RevealSecrets } from "./screens";
import { GAME_STATUS } from "./constants";

/**
 * Application controller of Imposter Companion game.
 * 
 * - Handles API calls
 * - Handles application flow and current displayed screen
 */
function App() {
  const [game, setGame] = useState(null);
  const [loading, setLoading] = useState(false);
  const [categories, setCategories] = useState([]);


  /**
   * Loads categories from database on app render
   */
  useEffect(() => {
    async function loadCategories() {
    try {
      const result = await getCategories();
      setCategories(result);
    } catch (err) {
      alert(err.message);
    }
  }

  loadCategories();
  }, [])

  /**
   * Creates a new game session from CreateGame screen.
   */
  async function handleCreateGame() {
    setLoading(true);
    try {
      const newGame = await createGame();
      setGame(newGame);
    } catch (err) {
      alert(err.message);
    } finally {
      setLoading(false);
    }
  }

  /**
   * Adds a new player to current game called from Lobby screen.
   */
  async function handleAddPlayer(playerName) {
    setLoading(true);
    try {
      const updatedGame = await addPlayer(game.id, playerName);
      setGame(updatedGame);
    } catch (err) {
      alert(err.message);
    } finally {
      setLoading(false);
    }
  }

  /**
   * Removes a player from current game called from Lobby screen.
   */
  async function handleRemovePlayer(playerId) {
    setLoading(true);
    try {
      const updatedGame = await removePlayer(game.id, playerId);
      setGame(updatedGame);
    } catch (err) {
      alert(err.message);
    } finally {
      setLoading(false);
    }
  }

  /**
   * Starts new game session if requirements met.
   */
  async function handleStartGame(categoryId) {
    setLoading(true);
    try {
      const startedGame = await startGame(game.id, categoryId);
      setGame(startedGame);
    } catch (err) {
      alert(err.message);
    } finally {
      setLoading(false);
    }
  }

  /**
   * Resets current game session.
   */
  async function handleResetGame() {
    setLoading(true);
    try {
      const resetedGame = await resetGame(game.id);
      setGame(resetedGame);
    } catch (err) {
      alert(err.message);
      throw err;
    } finally {
      setLoading(false);
    }
  }

  /**
   * Retrieves current player secret data.
   */
  async function handleRevealSecret(playerId) {
    setLoading(true);
    try {
      return await getPlayerSecret(playerId);
    } catch (err) {
      alert(err.message);
      throw err;
    } finally {
      setLoading(false);
    }
  }

  /**
   * Current screen selection logic.
   */
  const screen = !game ? (
    <CreateGame onCreate={handleCreateGame} loading={loading} />
  ) : game.status === GAME_STATUS.CREATED ? (
    <Lobby
      game={game}
      onAddPlayer={handleAddPlayer}
      onRemovePlayer={handleRemovePlayer}
      onStartGame={handleStartGame}
      categories={categories}
      loading={loading}
    />
  ) : game.status === GAME_STATUS.STARTED ? (
    <RevealSecrets
      game={game}
      onRevealSecret={handleRevealSecret}
      onResetGame={handleResetGame}
      loading={loading}
    />
  ) : null;

  /**
   * Base app layout.
   */
  return (
    <div className="app">
      <header className="app-header">
        <h1>Imposter Game Companion</h1>
      </header>

      <main className="app-main">
        {screen}
      </main>
    </div>
  );
}

export default App;
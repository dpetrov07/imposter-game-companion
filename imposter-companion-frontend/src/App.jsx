import { useState } from "react";
import { createGame, addPlayer } from "./api/gameApi";
import { CreateGame, Lobby } from "./screens";

/**
 * Application controller of Imposter Companion game.
 * 
 * - Handles API calls
 * - Handles application flow and current displayed screen
 */
function App() {
  const [game, setGame] = useState(null);
  const [loading, setLoading] = useState(false);

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
   * Adds a new player to current game from Lobby screen.
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
   * Starts new game session if requirements met.
   */
  // async function handleStartGame() {
  //   setLoading(true);
  //   try {
  //     const startedGame = await startGame(game.id, null);
  //   }
  // }

  /**
   * Current screen selection logic.
   */
  let screen;
  if (!game) {
    screen = ( 
    <CreateGame onCreate={handleCreateGame} loading={loading} />
    );
  } else {
    screen = (
      <Lobby game={game} onAddPlayer={handleAddPlayer} loading={loading} />
    );
  }

  /**
   * Base app layout.
   */
  return (
    <div className="app">
      <header>
        <h1>Imposter Game Companion</h1>
      </header>

      <main>
        {screen}
      </main>
    </div>
  );
}

export default App;
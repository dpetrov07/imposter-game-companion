import { useState } from "react";
import { PLAYER_ROLE } from "../constants";

/**
 * Screen to reveal all player secret info.
 * 
 * - Reveals each player secret one at a time.
 * - Displays different UI for imposter.
 */
function RevealSecrets({ game, onRevealSecret, onResetGame, loading }) {
  const [secret, setSecret] = useState(null);
  const [index, setIndex] = useState(0);

  const player = game.players[index];

  /**
   * Handles call to reveal secret for player.
   */
  async function revealPlayerSecret() {
    if (!player) return;

    const result = await onRevealSecret(player.id);
    setSecret(result);
  }

  function nextPlayer() {
    setSecret(null);
    setIndex(i => i + 1);
  }

  function resetGame() {
    setSecret(null);
    setIndex(0);
    onResetGame();
  }
  
  /**
   * Displays option to reset game after all players secrets shown.
   */
  if (!player) {
    return (
      <div className="screen">
        <p className="secret-info"> All players secrets have been revealed. </p>
        <button className="primary-button" onClick={resetGame} disabled={loading}>
          Back to Lobby
        </button>
      </div>
    )
  }

  return (
    <div className="screen">
      <p className="secret-info">
        Player {index + 1} of {game.players.length}: {player.name}
      </p>

      {/* Player screen with button to reveal secret. */}
      { !secret ? (
        <button className="primary-button" onClick={revealPlayerSecret} disabled={loading}>
          Reveal Secret
        </button>
      ) : (
        <>
          {/* Revealed word screen for imposter or normal player. */}
          { secret.playerRole === PLAYER_ROLE.IMPOSTER ? (
            <div className="secret">
              <p className="secret-role">IMPOSTER</p>
              <p className="secret-hint">
                Hint: <strong>{secret.secretWord}</strong>
              </p>
            </div>
          ) : secret.playerRole === PLAYER_ROLE.NORMAL ? (
            <div className="secret">
              <p className="secret-hint">
              Word: <strong>{secret.secretWord}</strong>
              </p>
            </div>
          ) : null}
          <button className="secondary-button" onClick={nextPlayer} disabled={loading}>
            Next Player
          </button>
        </>
      )}
    </div>
  )
}

export default RevealSecrets;
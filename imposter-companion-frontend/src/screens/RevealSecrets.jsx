import { useState } from "react";
import { PLAYER_ROLE } from "../constants";

/**
 * Screen to reveal all player secret info.
 * 
 * - Reveals each player secret one at a time.
 * - Displays different UI for imposter.
 */
function RevealSecrets({ game, onRevealSecret, loading }) {
  const [secret, setSecret] = useState(null);
  const [index, setIndex] = useState(0);

  const player = game.players[index];

  async function revealPlayerSecret() {
    if (!player) return;

    const result = await onRevealSecret(player.id);
    setSecret(result);
  }

  function nextPlayer() {
    setSecret(null);
    setIndex(i => i + 1);
  }
  if (!player) {
    return <p> All players secrets have been revealed. </p>
  }

  return (
    <div>
      <p>
        Player: { player.name }
      </p>

      { !secret ? (
        <button onClick={revealPlayerSecret} disabled={loading}>
          Reveal Secret
        </button>
      ) : (
        <>
          { secret.playerRole === PLAYER_ROLE.IMPOSTER ? (
            <p>
              IMPOSTER
              Hint: { secret.secretWord }
            </p>
          ) : secret.playerRole === PLAYER_ROLE.NORMAL ? (
            <p>
              Word: { secret.secretWord }
            </p>
          ) : null}
          <button onClick={nextPlayer} disabled={loading}>
            Next Player
          </button>
        </>
      )}
    </div>
  )
}

export default RevealSecrets;
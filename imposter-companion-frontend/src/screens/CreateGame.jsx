
/**
 * Opening screen of app to display create game option.
 * 
 * - Includes button to start game
 */
function CreateGame({ onCreate, loading }) {
  return (
    <div className="screen">
      <p className="intro-text">
        Imposter Game Companion to help you run an in-person game.
        Add at least 3 players, select a category, and pass 
        the device around to reveal each player's secret word.
      </p>

      {/* Button to request a new game session */}
      <button className="primary-button" onClick={onCreate} disabled={loading}>
        {loading ? "Loading..." : "Create Game"}
      </button>
    </div>
  );
}

export default CreateGame;

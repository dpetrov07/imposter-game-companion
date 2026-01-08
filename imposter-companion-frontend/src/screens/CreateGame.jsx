
/**
 * Opening screen of app to display create game option.
 * 
 * - Includes button to start game
 */
function CreateGame({ onCreate, loading }) {
  return (
    <div>

       {/* Button to request a new game session */}
      <button onClick={onCreate} disabled={loading}>
        {loading ? "Loading..." : "Create Game"}
      </button>

    </div>
  );
}

export default CreateGame;

const BASE_URL = "http://localhost:8080";

export async function getGame(gameId) {
  const result = await fetch(`${BASE_URL}/games/${gameId}`, { method: "GET"});
  if (!result.ok) throw new Error("Failed to retrieve game");
  return result.json();
}

export async function createGame() {
  const result = await fetch(`${BASE_URL}/games`, { method: "POST"});
  if (!result.ok) throw new Error("Failed to create game");
  return result.json();
}

export async function startGame(gameId, categoryId) {
  const result = await fetch(`${BASE_URL}/games/${gameId}/start`, { 
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      categoryId: categoryId,
    })
  });
  if (!result.ok) throw new Error("Failed to start game");
  return result.json();
}

export async function resetGame(gameId) {
  const result = await fetch(`${BASE_URL}/games/${gameId}/reset`, { method: "POST"});
  if (!result.ok) throw new Error("Failed to reset game");
  return result.json();
}

export async function addPlayer(gameId, playerName) {
  const result = await fetch(`${BASE_URL}/games/${gameId}/players`, { 
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      name: playerName,
    })
  });
  if (!result.ok) throw new Error("Failed to add player");
  return result.json();
}

export async function getPlayerSecret(playerId) {
  const result = await fetch(`${BASE_URL}/players/${playerId}/secret`, { method: "GET"});
  if (!result.ok) throw new Error("Failed to retrieve player secret");
  return result.json();
}

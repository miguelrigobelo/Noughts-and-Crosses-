# Noughts and Crosses

A classic noughts and crosses (tic-tac-toe) game written in Java with a Swing graphical interface. Play against a friend on the same computer, or take on an unbeatable computer opponent.

## Features

- Clean, clickable 3x3 board built with Java Swing
- **Two game modes:** player vs player, or player vs computer
- **Unbeatable AI** using the minimax algorithm (the best you can do is draw)
- Colour-coded X and O, with the winning line highlighted
- Live status bar showing whose turn it is and the final result
- "New game" button to restart at any time
- Single source file with no external dependencies

## Requirements

- Java Development Kit (JDK) 8 or later

## Getting Started

Clone the repository:

```bash
git clone https://github.com/miguelrigobelo/Noughts-and-Crosses-.git
cd noughts-and-crosses
```

Compile and run:

```bash
javac NoughtsAndCrosses.java
java NoughtsAndCrosses
```

## How to Play

1. The game starts with X. Click any empty square to place your mark.
2. By default you play as X against the computer, which plays as O.
3. To play against a friend, untick **Play against computer**. Players then take turns on the same screen.
4. Get three of your marks in a row (horizontally, vertically, or diagonally) to win.
5. If the board fills up with no winner, the game is a draw.
6. Click **New game** to start over.

## How the AI Works

The computer uses the [minimax algorithm](https://en.wikipedia.org/wiki/Minimax), which explores every possible sequence of moves from the current position and picks the one with the best guaranteed outcome. Scores are adjusted by depth, so the computer prefers winning sooner and losing later. Because tic-tac-toe has a small game tree, the full search runs instantly.

## Project Structure

```
.
├── NoughtsAndCrosses.java   # Game logic, AI, and Swing UI
└── README.md
```

## Possible Improvements

- Score tracker across multiple rounds
- Difficulty levels (e.g. an easy mode with random moves)
- Choose to play as O or let the computer move first
- Larger boards (4x4, 5x5) with alpha-beta pruning
- Sound effects and animations

## License

This project is licensed under the MIT License. Add a `LICENSE` file to your repository if you'd like to use this license.

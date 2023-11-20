## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

Team Name: Russell Sullivan (One Man Army)
  Difficulties encountered:
    -Initially, getting the pieces to move was difficult
    -Adding castling
    
    

PIECE RULES:
Pawn:
  -Can move 2 spaces forward only on its initial move (and if there is no piece in the way)
  -Can move 1 space forward after its initial move (and if there is no piece in the way)
  -Can move/kill enemy pieces diagnal to it
  
King:
  -Can 1 space in any direction as long as a friendly unit is not in the way
  -Can "castle"

Queen:
  -Can move diagonally, upward, downward, and sideways across the map as long as a piece
    is not blocking its path.

Bishop:
  -Can move diagonally across the map as long as a piece is not blocking its path.

Tower:
  -Can move sideways, upward, and downward across the map as long as a piece is not blocking   
    its path.

Knight:
  -Can only skip over pieces and onto opposite colored squares from what it is currently on.

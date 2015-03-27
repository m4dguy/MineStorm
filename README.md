# MineStorm
Recreation of the old Vectrex game in Java.

Uses a small vector-based game engine. This is mainly a programming exercise with the goal of creating a flexible, easily adaptable engine.
The engine is split into different interchangeable modules and will allow the recreation of other simple games (Berzerk anyone?).
Each module will be as independent and lightweight as possible, with the engine itself providing the core of the entire program.
Changes in the gane are handled through an event system, with each individual event being capable of modifying the game state.

An Android port of the final version is planned.
The finished engine will be ported to C++ using SDL and Lua.
# Interface Maker

InterfaceMaker is a modern plugin to handle and customize join items, hotbars and menus with a developer friendly API.

# Features

* Simple to understand configuration for server administrator.

* Complex but easy to use API to simplify item handling for inventories and hotbars on custom plugins like Minigames or Lobby/Hub Cores.

* HEX Support for everything to achieve a beautiful server design.

* Fast operations to achieve good performance results.

# How to (Server Admins)

You can get a copy of InterfaceMaker on MC-Market.

# How to (Developers)

Clone this repository, run `mvn package` and get the binaries from `./target/` folder.

## Creating a Menu

Create a new menu `new InterfaceMenu()` create items `new InterfaceItem()` set them `menu.setItem(slot, item)` and build it `menu.build(player)`.

Menus can be extended and use the `onBuild(player)` event. Items can be extended to use the `onInteract(player)` and `onClick(player, clickedInventory)` events.

## Creating a Hotbar

Get the API `InterfaceMaker.getAPI()` create a new menu `api.createHotbar()` create items `new InterfaceItem()` set them `hotbar.setItem(slot, item)` and build it `hotbar.build(player)`.

# Join us

<a href="https://discord.gg/gF36AT3"><img src="https://discord.com/assets/4ff060e44afc171e9622fbe589c2c09e.png" width=10% height=10%><img/><a/>

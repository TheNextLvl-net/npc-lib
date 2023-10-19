# NPC-Lib

A modern, lightweight NPC library tailored for Paper server environments, offering simplicity and versatility.

## Table of Contents

- [Installation](#installation)
- [Repository](#repository)
- [Getting started](#getting-started)
- [Static NPCs](#static-npcs)
- [User-Specific NPCs](#user-specific-npcs)

## Installation

To use the NPC-Lib, you need to install it as a plugin on your Minecraft server. Follow these steps:

1. Download the NPC-Lib [JAR file](https://hangar.papermc.io/TheNextLvl/NPC-Lib/versions).
2. Place the JAR file into your server's `plugins` directory.
3. Restart your Minecraft server.

The NPC-Lib plugin is now installed and ready to use.

## Repository

To seamlessly integrate our library into your project, please visit
our [repository](https://repo.thenextlvl.net/#/releases/net/thenextlvl/npc/api).
Select your preferred build tool and incorporate the version you intend to use.

### Gradle Example

For Gradle users, add the repository and dependency to your `build.gradle.kts`:

```kt
repositories {
    maven("https://repo.thenextlvl.net/releases")
}

dependencies {
    compileOnly("net.thenextlvl.npc:api:version")
}
```

**Note:** Be sure to replace **version** with the actual version number.
Also note that you shouldn't shade the API as it is already provided by the plugin.

## Getting Started

To get started with NPC-Lib, you'll need to access various components of the API. Here's how you can do that:

### Get the NPCProvider

```java
NPCProvider provider = Bukkit.getServicesManager().getRegistration(NPCProvider.class).getProvider();
```

The NPC Provider serves as the foundation for all the capabilities offered by the NPC API.

### Get the NPCLoader

```java
NPCLoader loader = provider.getNPCLoader();
```

The NPC Loader is responsible for the dynamic management of npcs, including loading, unloading, updating, and
teleporting them for specific players.

### Get the NPCRegistry

```java
NPCRegistry registry = provider.getNPCRegistry();
```

The NPC Registry plays a crucial role in managing static npcs.

### Get the NPCFactory

```java
NPCFactory factory = provider.getNPCFactory();
```

The factory is your go-to tool for creating new npcs.

### Creating NPCs

To create a npc, use the NPCFactory:

```java
NPC npc = factory.createNPC(location, profile, displayName);
```

**Note:** npc objects are not yet rendered, first you have to either register or load them.

### Static NPCs

Static npcs are npcs that are visible to all players, providing a consistent viewing experience for everyone.
They are most commonly used for general player interaction.
Importantly, the loading and unloading of static npcs are fully automated, requiring no manual intervention.

#### Registering

To add a static npc, simply call the register method from the NPCRegistry:

```java
registry.register(npc);
```

This will add the static npc for everyone.

#### Unregistering

To remove a static npc, call the unregister method from the NPCRegistry:

```java
registry.unregister(npc);
```

This will remove the static npc for everyone.

### User-Specific NPCs

User-Specific npcs are unique npcs that are exclusively visible to the players for whom they are loaded.
They are typically used to display personalized skins or names.
However, it's important to note that the loading and unloading of these npcs must be managed manually.

#### Loading

To load a user-specific npc, simply call the load method from the NPCLoader:

```java
loader.load(npc, player);
```

This will load the npc for the specified player.

#### Unloading

To remove a user-specific npc, call the unload method from the NPCLoader:

```java
loader.unload(npc, player);
```

This will remove the npc displayed for the specified player.

**Note:** In future updates, we are planning to merge the concepts of user-specific and static npcs to create an
even simpler and more streamlined method for loading and managing npcs.
This enhancement will provide a more straightforward way to handle npcs in your server.
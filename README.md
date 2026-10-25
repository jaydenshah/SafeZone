# SafeZone

A Minecraft server plugin for Paper and Spigot that allows server admins to set up protected zones where entity damage is cancelled.

## Commands

All plugin commands are grouped under the main `/safezone` command:

| Command | Description | Example |
|---|---|---|
| `/safezone` | Displays the help menu with all available subcommands. | `/safezone` |
| `/safezone add <name> <radius>` | Creates a new safe zone centered at your current location. | `/safezone add spawn 20` |
| `/safezone remove <name>` | Deletes an existing safe zone. | `/safezone remove spawn` |
| `/safezone list` | Displays all safe zones along with their coordinates, radius, and world. | `/safezone list` |
| `/safezone setradius <name> <radius>` | Updates the protection radius of an existing safe zone. | `/safezone setradius spawn 30` |

> Note: Safe zone creation commands must be executed by an in-game player to capture world location coordinates.

## Configuration `(config.yml)`

Safe zones are stored in the server's `plugins/SafeZone/config.yml` file. Here is an example of how data is formatted:

```yaml
safezones:
  spawn:
    world: world
    x: 0.5
    y: 64.0
    z: 0.5
    radius: 20
  market:
    world: world_nether
    x: 100.5
    y: 70.0
    z: -50.5
    radius: 15
```

## Requirements & Building

- Server Runtime: Java 21+ (Paper/Spigot 1.21.x or higher)
- Build Tool: Apache Maven

### Compiling from Source

1. Clone this repository:
   ```cmd
   git clone https://github.com/jaydenshah/SafeZone.git
   cd SafeZone
   ```
   
2. Build the plugin jar:
   ```cmd
   mvn clean package
   ```
   
3. Copy the compiled file from `target/SafeZone-1.0.0.jar` into your Minecraft server's plugin folder and restart the server.
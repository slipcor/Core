# Core

**The best library I ever built myself!**

The code in this repo is meant to centralize functions I use in my other plugins. I got sick of an amount of code duplication that became ridiculous. For [PVPStats](https://github.com/slipcor/PVPStats), about a quarter of the code was duplicated and used almost the same way in [TreeAssist](https://github.com/slipcor/TreeAssist) and [SpamHammer](https://github.com/slipcor/SpamHammer).

***

## License / TOS

Everyone who figures out how to hook into this is free to use it in their own plugin, I will look into a license, but right now I feel like it has to be "do what you want, I give you no warranty" type of deal.

You can use this in a premium plugin, but I might not have the time to help you out with your plugin if you run into a road bump, especially setting up maven or something else to get access to the dependency. You might have to go with including the jar with your plugin, which is fine, as I am always shading and moving the packages on my end!

***

## Features

- **Command handling** - checking for argument count and permission node, tab completion
- **Commentable Config** - caching of values from the YML, appending of comments, support of setting nodes via command
- **Debugger implementation** - can be used to filter based on java class and Strings like player names or something like `/<command> debug SLiPCoR` and only debuggers reacting to `SLiPCoR` will debug
- **Metrics Implementation**
- **Prefixed messaging**
- **Update checker** - can be ignored by third parties, my plugins are hardcoded on a webserver of mine. Just don't use this class and you're fine!

***

## Dependencies

- Spigot

***

## Changelog

- nothing to see here yet

***

## Thanks to

- FriendlyBaron formerly known as itsatacoshop247 for parts of the original plugin source, the stable foundation that to this day keeps TreeAssist strong
- Bradley Hilton for the Jenkins in time of need
- btilm305 for keeping the repo together in time of need

***
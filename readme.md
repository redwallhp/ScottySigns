# ScottySigns

<img align="right" src="http://i.imgur.com/OY3hr5j.jpg">

Want warp signs on your server? Don't want lame restrictions on what the sign can say (e.g. unsightly mandatory text lines)? Try ScottySigns, the latest in Minecraft transporter technology. One click and you'll beam down to your destination.

One could argue that ScottySigns is not actually a "warp sign" plugin, since no warp drive is involved at all. It's more of a...transporter sign plugin.

If you want to get fancy with the sign text, I recommend [SignText](https://github.com/redwallhp/SignText) as a companion plugin. Effortlessly edit signs that have already been placed, and use all the custom colors and formatting you wish.


## Commands

* `/scottysign-set <world> <x> <y> <z>` — Create a transporter sign. (Alias: /setwarpsign)
* `/scottysign-info` — Inspect a transporter sign to see where it leads.
* `/scottysign-remove` — Unregister a transporter sign. (Alias: /delwarpsign)
* `/scottysign-reload` — Reload the sign data from disk and run the cleanup routine


# Permissions

* `lolneigh.set`
* `lolneigh.info`
* `lolneigh.remove`
* `lolneigh.reload`


## Cleanup

ScottySigns are unbreakable, usually. If you try to break a registered transporter sign, the event will be canceled. However, there are some cases where signs can still go missing, and the plugin will think it's still registered. (e.g. WorldEdit mishaps, a block the sign is attached to being removed.)

On plugin load (i.e. server restarts), ScottySigns will verify that every registered transporter sign still exists in the world. If it can't find one, it will be unregistered. (This also happens when the reload command is run.) So if you accidentally break a sign, you can replace it in its spot and everything will work as before. Or the cleanup routine will remove it later.

Of course, if you want to make sure your players aren't breaking your signs, WorldGuard is essential.


## License

GNU Public License

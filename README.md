# Gist Templates IntelliJ Plugin

A plugin to use your gists as live templates !
[http://geowarin.github.io/gist-templates-plugin/](http://geowarin.github.io/gist-templates-plugin/)

## Installation

The plugin is available in `browse repositories` in intellij plugins
[http://plugins.jetbrains.com/plugin/7400](http://plugins.jetbrains.com/plugin/7400)

We spend a lot of time making it compatible with as much versions of JetBrains IDEs as possible.
Supported and tested versions are : itellij-11, intellij-12 and intellij-13.

It should also be compatible with other IDEs like WebStorm, Appcode, etc. if it is not the case, please [report the problem](https://github.com/geowarin/gist-templates-plugin/issues)

## Usage

1. Configure your github account in intellij

	![image](/images/github-settings.png)

2. Allow the plugin to use your github identification

	![image](/images/plugin-settings.png)

3. The gist templates appear in the code > generate menu

	![image](/images/generate.png)

	![image](/images/templates.png)

## Generate files from "New" dialog in project view

In version 0.3 you can

1. Access the new menu and select "Files from gist..."

	![image](/images/generateProject.png)

2. Then select a gist and the files you are interested in

	![image](/images/generateProject-2.png)

## Change log

* v0.3.2 : Fix compatibility issue in intellij 11 and minor GUI improvements
* v0.3.1 : Fix compatibility issue in intellij 12
* v0.3 : New feature >> ability to add one or several gist files directly from the "New" actions in project view !
* v0.2 : Templates are now lazy loaded. The menu appears more quickly and gists are always up to date.
* v0.12 : Fixes a crash in intellij 13
* v0.11 : Support for intellij 11 to 13
* v0.1 : basic support for user's github account and his favorites


## Road Map

* Support file type for smarter propositions
* Support variables in templates
* Support multiple files gists
* Assist user when creating a template from the IDE
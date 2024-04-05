# Checklist Tester

This is a checklist tester linking together the VDM-SL model
that runs through checklists and the simulator whilst providing
a user interface to set up the connection.

There are two components to this, the desktop application and the server, both of
them being written in [Kotlin Multiplatform](https://www.jetbrains.com/kotlin-multiplatform/).

## Desktop

Provides a graphical interface to create projects, and to run tests.

The UI is provided by [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/).

## Server

Hosts the VDMJ instance and communicates with the simulator plugin.

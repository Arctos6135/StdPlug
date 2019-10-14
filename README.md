# StdPlug
[![master Build Status](https://img.shields.io/travis/com/Arctos6135/StdPlug/master.svg?label=build%20%28master%29&logo=travis)](https://travis-ci.com/Arctos6135/StdPlug/branches)&nbsp;
[![Latest Build Status](https://img.shields.io/travis/com/Arctos6135/StdPlug.svg?label=build%20%28latest%29&logo=travis)](https://travis-ci.com/Arctos6135/StdPlug)&nbsp;
[![Latest Stable Release](https://img.shields.io/github/tag/Arctos6135/StdPlug.svg?logo=data:image/svg%2bxml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/Pgo8IURPQ1RZUEUgc3ZnIFBVQkxJQyAiLS8vVzNDLy9EVEQgU1ZHIDIwMDEwOTA0Ly9FTiIKICJodHRwOi8vd3d3LnczLm9yZy9UUi8yMDAxL1JFQy1TVkctMjAwMTA5MDQvRFREL3N2ZzEwLmR0ZCI+CjxzdmcgdmVyc2lvbj0iMS4wIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciCiB3aWR0aD0iMzIuMDAwMDAwcHQiIGhlaWdodD0iMzIuMDAwMDAwcHQiIHZpZXdCb3g9IjggOCAxNi4wMDAwMDAgMTYuMDAwMDAwIgogcHJlc2VydmVBc3BlY3RSYXRpbz0ieE1pZFlNaWQgbWVldCI+Cgo8ZyB0cmFuc2Zvcm09InRyYW5zbGF0ZSgwLjAwMDAwMCwzMi4wMDAwMDApIHNjYWxlKDAuMTAwMDAwLC0wLjEwMDAwMCkiCmZpbGw9IiNGRkZGRkYiIHN0cm9rZT0ibm9uZSI+CjxwYXRoIGQ9Ik05NyAyMjMgYy0xOCAtMTcgLTUgLTYxIDI2IC05MSAzOSAtMzggNDcgLTM5IDc1IC05IDI5IDMxIDI4IDM3IC0xMQo3NSAtMzEgMzAgLTc0IDQyIC05MCAyNXogbTgwIC0zNSBsMzMgLTMyIC0yMiAtMjMgLTIyIC0yMyAtMzMgMzIgYy0zMSAzMCAtNDEKNTcgLTI2IDcxIDE0IDE1IDM4IDYgNzAgLTI1eiIvPgo8cGF0aCBkPSJNMTEwIDIwMCBjMCAtNSA1IC0xMCAxMCAtMTAgNiAwIDEwIDUgMTAgMTAgMCA2IC00IDEwIC0xMCAxMCAtNSAwCi0xMCAtNCAtMTAgLTEweiIvPgo8L2c+Cjwvc3ZnPgo=)](https://github.com/Arctos6135/StdPlug/releases/latest)&nbsp;
[![MIT License](https://img.shields.io/github/license/Arctos6135/StdPlug.svg?logo=data:image/svg%2bxml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/Pgo8IURPQ1RZUEUgc3ZnIFBVQkxJQyAiLS8vVzNDLy9EVEQgU1ZHIDIwMDEwOTA0Ly9FTiIKICJodHRwOi8vd3d3LnczLm9yZy9UUi8yMDAxL1JFQy1TVkctMjAwMTA5MDQvRFREL3N2ZzEwLmR0ZCI+CjxzdmcgdmVyc2lvbj0iMS4wIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciCiB3aWR0aD0iMzIuMDAwMDAwcHQiIGhlaWdodD0iMzIuMDAwMDAwcHQiIHZpZXdCb3g9IjggOCAxNi4wMDAwMDAgMTYuMDAwMDAwIgogcHJlc2VydmVBc3BlY3RSYXRpbz0ieE1pZFlNaWQgbWVldCI+Cgo8ZyB0cmFuc2Zvcm09InRyYW5zbGF0ZSgwLjAwMDAwMCwzMi4wMDAwMDApIHNjYWxlKDAuMTAwMDAwLC0wLjEwMDAwMCkiCmZpbGw9IiNGRkZGRkYiIHN0cm9rZT0ibm9uZSI+CjxwYXRoIGQ9Ik0xMzkgMjIzIGMtMSAtNCAtMSAtMTEgMCAtMTUgMCAtNCAtMTAgLTEwIC0yMiAtMTMgLTEzIC00IC0yNCAtMTIKLTI1IC0xOCAwIC03IC00IC0yMiAtNyAtMzQgLTYgLTIwIC00IC0yMyAxOSAtMjMgMjggMCAzMiAxMCAxNiA0MCAtNyAxMyAtNgoxOSA1IDI0IDIzIDggMjEgLTcwIC0yIC04MyAtMTQgLTggLTggLTEwIDI3IC0xMCAzNSAwIDQxIDIgMjggMTAgLTI0IDEzIC0yNgo5MSAtMyA4MyAxMSAtNSAxMiAtMTEgNSAtMjQgLTE2IC0zMCAtMTIgLTQwIDE2IC00MCAyMyAwIDI1IDMgMTkgMjIgLTMgMTMgLTcKMjggLTcgMzUgLTEgNiAtMTIgMTQgLTI1IDE4IC0xMiAzIC0yMiAxMCAtMjAgMTYgMSA3IC00IDE0IC0xMCAxNiAtNyAzIC0xNCAxCi0xNCAtNHogbS0yNCAtNzMgYzMgLTUgLTEgLTEwIC0xMCAtMTAgLTkgMCAtMTMgNSAtMTAgMTAgMyA2IDggMTAgMTAgMTAgMiAwCjcgLTQgMTAgLTEweiBtOTAgMCBjMyAtNSAtMSAtMTAgLTEwIC0xMCAtOSAwIC0xMyA1IC0xMCAxMCAzIDYgOCAxMCAxMCAxMCAyCjAgNyAtNCAxMCAtMTB6Ii8+CjwvZz4KPC9zdmc+Cg==)](https://github.com/Arctos6135/StdPlug/blob/master/LICENSE)

## Overview

StdPlug is the Arctos 6135 "Standard" Shuffleboard Plugin. Currently, it offers these widgets:

| Name        | Description                                       | Accepted Data Types        |
| ----------- | ------------------------------------------------- | -------------------------- |
| Image       | Displays a static image when given its full path. | String                     |
| PIDVA Gains | Displays a set of PIDVA or PIDVA + DP gains.      | PIDVA Gains, PIDVADP Gains |

And these data types:

| Name          | Description                | Compatible Widgets | Default Widget |
| ------------- | -------------------------- | ------------------ | -------------- |
| PIDVA Gains   | A set of PIDVA gains.      | PIDVA Gains        | PIDVA Gains    |
| PIDVADP Gains | A set of PIDVA + DP gains. | PIDVA Gains        | PIDVA Gains    |

For more information about each widget or data type, see the Javadocs for the classes `com.arctos6135.stdplug.api.StdPlugWidgets` and `com.arctos6135.stdplug.api.StdPlugDataTypes` respectively.

## Usage

### Installation

The installation process is fairly simple. Grab the `StdPlug-<VERSION>.jar` from the [latest release](https://github.com/Arctos6135/StdPlug/releases/latest), and put it in the `Shuffleboard/plugins` directory in your home folder.
On Windows, this is `C:\Users\<USERNAME>\Shuffleboard\plugins`, and on Linux, this is `/home/<USERNAME>/Shuffleboard/plugins`.

Alternatively, you can also load it manually using File -> Plugins -> Load Plugin in the Shuffleboard menu.

### The API Library

To make the use of StdPlug on the robot side more convenient, we've developed an optional API library to be used with WPILib.

To use this library, first grab the `StdPlug-API-<VERSION>.jar` from the [latest release](https://github.com/Arctos6135/StdPlug/releases/latest), and put it somewhere in your robot project, say in the `lib` directory. Then, in `build.gradle`, under `dependencies`, add this line: `compile files('path/to/jar')`. Your new `dependencies` should look something like this:
```groovy
dependencies {
    compile wpi.deps.wpilib()
    compile wpi.deps.vendor.java()

    compile files('lib/StdPlug-API-0.1.0.jar')
    
    nativeZip wpi.deps.vendor.jni(wpi.platforms.roborio)
    nativeDesktopZip wpi.deps.vendor.jni(wpi.platforms.desktop)
    testCompile 'junit:junit:4.12'
}
```

The API library provides many helpful classes in the package `com.arctos6135.stdplug.api` that make the use of StdPlug a lot easier. Here's an example demonstrating how to send a set of PIDVA gains from the robot to Shuffleboard:
```java
PIDVAGains gains = new PIDVAGains(0.05, 0.02, 0.034, 0.1, 0.07);

Shuffleboard.getTab("My Tab")
        .add("A Set of Gains", gains)
        .withWidget(StdPlugWidgets.PIDVA_GAINS);
```

## Documentation
All classes in the public API are documented with Javadocs. For every release, you can find the Javadocs as `StdPlug-Doc-<VERSION>.zip`. 

## Thank You to Our Generous Sponsors:
<img src="https://dynamicmedia.zuza.com/zz/m/original_/3/a/3aae60b3-ff18-4be5-b2b1-e244943a85fb/TDSB_Gallery.png" alt="Toronto District School Board" height="200px"/>
<img src="https://developer.nordicsemi.com/.webresources/NordicS.jpg" alt="Nordic Semiconductors" height="170px"/>
<img src="https://upload.wikimedia.org/wikipedia/en/thumb/5/50/SNC-Lavalin_logo.svg/1280px-SNC-Lavalin_logo.svg.png" alt="SNC-Lavalin" height="170px"/>
<img src="https://user-images.githubusercontent.com/32781310/52970668-acd64780-3382-11e9-857f-85b829690e0c.png" alt="Scotia McLeod" height="200px"/>
<img src="https://kissmybutton.gr/wp-content/uploads/2017/09/ryver.png" alt="Ryver Inc." height="200px"/>
<img src="https://user-images.githubusercontent.com/32781310/52224389-eaf94480-2875-11e9-82ba-78ec58cd20cd.png" alt="The Maker Bean Cafe" height="200px"/>
<img src="http://connecttech.com/logo.jpg" alt="Connect Tech Inc." height="200px"/>
<img src="https://brafasco.com/media/wysiwyg/HDS_construction_industrial_BF_4C_pos.png" alt="HD Supply Brafasco" height="200px"/>
<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRqnEGnLesUirrtMQfhxLGUTZn2xkVWpbROlvmABI2Nk6HzhD1w" alt="Arbour Memorial" height="200px"/>

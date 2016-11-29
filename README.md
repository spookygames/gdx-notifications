# gdx-notifications

Cross-platform notifications for libgdx.

Desktop support thanks to [JCommunique](https://github.com/spfrommer/JCommunique) and [java-to-OS-notify](https://github.com/wokier/java-to-OS-notify).

## Setup

This library needs libgdx starting from version 1.5.5.

Add the pretty **bold** parts into your _build.gradle_ file:

<pre>
    repositories {
        <b>maven { url "http://dl.bintray.com/spookygames/oss" }</b>
    }
    
    ...
    
    project(":desktop") {
        
        ...
        
        dependencies {
            compile project(":core")
            ...
            <b>compile "net.spookygames.gdx:gdx-notifications-desktop-jcommunique:0.0.1"</b> <i>for Swing notifications</i>
            <i>OR</i>
            <b>compile "net.spookygames.gdx:gdx-notifications-desktop-os:0.0.1"</b> <i>for native notifications</i>
        }
    }
    
    project(":android") {
        
        ...
        
        dependencies {
            compile project(":core")
            ...
            <b>compile "net.spookygames.gdx:gdx-notifications-android:0.0.1"</b>
        }
    }
    
    project(":core") {
        
        ...
        
        dependencies {
            ...
            <b>compile "net.spookygames.gdx:gdx-notifications:0.0.1"</b>
        }
    }
</pre>

## Usage

### Initialization

We're talking platform-specific stuff here, so you'll need to initialize the magic in your specific initializers. Simply follow the indications from [libgdx's wiki](https://github.com/libgdx/libgdx/wiki/Interfacing-with-platform-specific-code).

Classes you'll have for this:
* `DesktopNotificationHandler` (desktop, whether it's from JCommunique or java-to-OS-notify)
* `AndroidNotificationHandler` (warning, notifications are not supported for android versions below 11)

And the base interface with two methods:
* `NotificationHandler`

### Notify

```java
NotificationHandler handler = <your_platform-specific_handler_here>;

NotificationParameters parameters = new NotificationParameters();

parameters.id = 12;
parameters.title = "Notification for the people";
parameters.text = "Lorem ipsum";

handler.showNotification(parameters);
```

### Remove notification

```java
NotificationHandler handler = <your_platform-specific_handler_here>;

// You could (should!) very well get the parameters object from above
// But it will go nice too if you create a new object
// Only the id matters actually
NotificationParameters parameters = new NotificationParameters();
parameters.id = 12;
	
handler.hideNotification(parameters);
```

## Platform support

- [x] Desktop (swing via JCommunique, native via java-to-OS-notify)
- [x] Android
- [ ] iOS
- [ ] HTML

# gdx-notifications
[ ![Download](https://api.bintray.com/packages/spookygames/oss/gdx-notifications/images/download.svg) ](https://bintray.com/spookygames/oss/gdx-notifications/_latestVersion)
[ ![Build Status](https://travis-ci.com/spookygames/gdx-notifications.svg?branch=master)](https://travis-ci.com/spookygames/gdx-notifications)

Cross-platform notifications for libgdx.

Desktop support thanks to [JCommunique](https://github.com/spfrommer/JCommunique) and [java-to-OS-notify](https://github.com/wokier/java-to-OS-notify).

See [here](https://github.com/SimonIT/gdx-notifications-app) for an example implementation.

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
            <b>compile "net.spookygames.gdx:gdx-notifications-desktop-jcommunique:0.0.2-SNAPSHOT"</b> <i>for Swing notifications</i>
            <i>OR</i>
            <b>compile "net.spookygames.gdx:gdx-notifications-desktop-os:0.0.2-SNAPSHOT"</b> <i>for native notifications</i>
        }
    }
    
    project(":android") {
        
        ...
        
        dependencies {
            compile project(":core")
            ...
            <b>compile "net.spookygames.gdx:gdx-notifications-android:0.0.2-SNAPSHOT"</b>
        }
    }
    
    project(":html") {
            
        ...
        
        dependencies {
            compile project(":core")
            ...
            
            <b>compile "net.spookygames.gdx:gdx-notifications-html-gwt:0.0.2-SNAPSHOT"</b>
            <b>compile "net.spookygames.gdx:gdx-notifications:0.0.2-SNAPSHOT:sources"</b>
            <b>compile "net.spookygames.gdx:gdx-notifications-html-gwt:0.0.2-SNAPSHOT:sources"</b> <i>for gwt notifications</i>
            <i>OR</i>
            <b>compile "net.spookygames.gdx:gdx-notifications-html-browser:0.0.2-SNAPSHOT"</b>
            <b>compile "net.spookygames.gdx:gdx-notifications:0.0.2-SNAPSHOT:sources"</b>
            <b>compile "net.spookygames.gdx:gdx-notifications-html-browser:0.0.2-SNAPSHOT:sources"</b> <i>for browser notifications</i>
        }
    }
    
    project(":core") {
        
        ...
        
        dependencies {
            ...
            <b>compile "net.spookygames.gdx:gdx-notifications:0.0.2-SNAPSHOT"</b>
        }
    }
</pre>


Add 

`<inherits name="net.spookygames.gdx.notifications.gdx_notifications_gwt"/>`

after

`<inherits name='com.badlogic.gdx.backends.gdx_backends_gwt'/>`

in your `GdxDefinition.gwt.xml`.

## Usage

### Initialization

We're talking platform-specific stuff here, so you'll need to initialize the magic in your specific initializers. Simply follow the indications from [libgdx's wiki](https://github.com/libgdx/libgdx/wiki/Interfacing-with-platform-specific-code).

Classes you'll have for this:
* `DesktopNotificationHandler` (desktop, whether it's from JCommunique or java-to-OS-notify)
* `AndroidNotificationHandler` (warning, notifications are not supported for android versions below 14)
* `HtmlNotificationHandler`

And the base interface with two methods:
* `NotificationHandler`

### Notify

```java
NotificationHandler handler = <your_platform-specific_handler_here>;
handler.showNotification(new NotificationParameters("Notification for the people", "Lorem ipsum", 12));
```

### Remove notification

```java
NotificationHandler handler = <your_platform-specific_handler_here>;

// You could (should!) very well get the parameters object from above
// But it will go nice too if you create a new object
// Only the id matters actually
handler.hideNotification(new NotificationParameters("", "", 12));
```

## Platform support

- [x] Desktop (swing via [JCommunique](https://github.com/spfrommer/JCommunique), native via [java-to-OS-notify](https://github.com/wokier/java-to-OS-notify))
- [x] Android
- [ ] iOS
- [x] HTML (gwt via [NotificationMole](http://www.gwtproject.org/javadoc/latest/com/google/gwt/user/client/ui/NotificationMole.html), browser via [Notification Web-API](https://developer.mozilla.org/en-US/docs/Web/API/Notification))

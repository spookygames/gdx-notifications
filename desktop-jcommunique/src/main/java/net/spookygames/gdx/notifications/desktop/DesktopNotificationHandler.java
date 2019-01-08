/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2017 Spooky Games
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.spookygames.gdx.notifications.desktop;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entry;
import com.notification.Notification;
import com.notification.NotificationFactory.Location;
import com.notification.NotificationListener;
import com.notification.NotificationManager;
import com.notification.manager.QueueManager;
import com.notification.types.TextNotification;
import com.notification.types.WindowNotification;
import com.theme.TextTheme;
import com.theme.WindowTheme;
import com.utils.Time;
import net.spookygames.gdx.notifications.NotificationHandler;
import net.spookygames.gdx.notifications.NotificationParameters;

import java.awt.*;
import java.util.Iterator;

import static java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT;

public class DesktopNotificationHandler implements NotificationHandler {

    private final IntMap<Notification> notifications = new IntMap<>();

    private final NotificationManager manager;

    private final WindowTheme windowTheme;
    private final TextTheme textTheme;

    private final boolean translucencySupported;

    public DesktopNotificationHandler() {
        super();
        this.windowTheme = buildWindowTheme();
        this.textTheme = buildTextTheme();
        this.manager = buildManager();

        // Determine if the GraphicsDevice supports translucency.
        translucencySupported = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().isWindowTranslucencySupported(TRANSLUCENT);
        if (!translucencySupported)
            windowTheme.opacity = 1.0d;
    }

    public static void main(String[] args) {

        // TODO
        //https://github.com/spfrommer/JCommunique/wiki
//
//		JCommunique also offers the following:
//
//		    IconUtils for reading and scaling icons from files
//
//		    Easy automatic Notification adjustment based on platform
//
//
//
//
//JCommunique offers several Notifications and NotificationManagers. If you're not sure what these are, take a look at the API Explanation section. Of course, you can always create your own Notifications and NotificationManagers.
//Notifications
//
//    TextNotification - displays a title and a subtitle with themed Fonts
//    IconNotification - same as TextNotification, but with an additional method for setting the ImageIcon
//    AcceptNotification - allows for user feedback via accept and decline buttons
//    ProgressNotification - shows a progress bar to the user
//
//NotificationManagers
//
//    SimpleManager - shows Notifications in one location with optional fading
//    QueueManager - scrolls Notifications with optional fading
//    SlideManager - slides Notifications to their standard locations from the side of the screen
//    SequenceManager - this queues up a number of Notifications which it will show one after another
//
        // TODO


        NotificationHandler handler = new DesktopNotificationHandler();

        for (int i = 0; i < 10; i++) {
            handler.showNotification(new NotificationParameters("Notification " + i, "Lorem ipsum", i));
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
            handler.hideNotification(new NotificationParameters("", "", i));
        }

    }

    @Override
    public void showNotification(NotificationParameters parameters) {
        // Creates a text notification
        TextNotification notification = new TextNotification() {
            @Override
            public void setOpacity(double opacity) {
                if (translucencySupported)
                    super.setOpacity(opacity);
            }
        };

        notification.setWindowTheme(windowTheme);
        notification.setTextTheme(textTheme);
        notification.setTitle(parameters.getTitle());
        notification.setSubtitle(parameters.getText());

        notification.setCloseOnClick(true);
        notification.addNotificationListener(new NotificationListener() {
            @Override
            public void actionCompleted(Notification notification, String action) {
                if (WindowNotification.HIDDEN/*CLICKED*/.equals(action)) {
                    // Remove from index
                    Iterator<Entry<Notification>> it = notifications.entries();
                    while (it.hasNext()) {
                        Entry<Notification> entry = it.next();
                        if (entry.value == notification) {
                            it.remove();
                            break;
                        }
                    }
                }
            }
        });

        notifications.put(parameters.getId(), notification);

        // The notification will disappear after 2 seconds, or after you click it
        manager.addNotification(notification, Time.seconds(10));
    }

    @Override
    public void hideNotification(NotificationParameters parameters) {
        Notification notification = notifications.get(parameters.getId());

        if (notification != null) {
            manager.removeNotification(notification);
        }
    }

    protected WindowTheme buildWindowTheme() {
        WindowTheme window = new WindowTheme();
        window.background = new Color(255, 255, 255);
        window.foreground = new Color(160, 205, 250);
        window.opacity = 0.8d;
        window.width = 300;
        window.height = 100;

        return window;
    }

    protected TextTheme buildTextTheme() {

        TextTheme text = new TextTheme();
        text.title = new Font("Arial", Font.BOLD, 22);
        text.subtitle = new Font("Arial", Font.PLAIN, 16);
        text.titleColor = new Color(10, 10, 10);
        text.subtitleColor = new Color(10, 10, 10);

        return text;
    }

    protected NotificationManager buildManager() {
        QueueManager manager = new QueueManager(Location.NORTHEAST);
//		manager.setFadeEnabled(true);	// this is a feature under testing - it doesn't look good across all platforms
        return manager;
    }
}

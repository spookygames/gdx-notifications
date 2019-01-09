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
package net.spookygames.gdx.notifications;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.utils.Array;
import com.google.gwt.core.client.JavaScriptObject;

public class HtmlNotificationHandler implements NotificationHandler {

    private boolean supported;
    private boolean hasPermission = false;
    private boolean permissionRequested = false;

    private Array<NotificationJS> notifications = new Array<NotificationJS>();

    private String icon;

    public HtmlNotificationHandler() {
        this.supported = isSupported();
        if (this.supported) {
            this.hasPermission = hasPermission();
        }
    }

    @Override
    public void showNotification(NotificationParameters parameters) {
        if (this.supported) {
            if (this.hasPermission) {
                this.notifications.add(showNotificationJSNI(parameters, this.icon));
            } else {
                if (!this.permissionRequested) {
                    this.permissionRequested = true;
                    this.requestPermission(new NotificationPermissionResult(parameters));
                }
            }
        }
    }

    @Override
    public void hideNotification(NotificationParameters parameters) {
        if (this.supported) {
            for (NotificationJS notification : this.notifications) {
                if (Integer.valueOf(notification.getTag()) == parameters.getId()) {
                    notification.close();
                    this.notifications.removeValue(notification, true);
                    break;
                }
            }
        }
    }

    /**
     * Sets the icon for all notifications
     * filetype internal: icon from assets
     * filetype external/absolute:
     * @param icon path to the icon
     */
    public void setIcon(String icon, Files.FileType type) {
        switch (type) {
            case Internal:
                this.icon = "assets/" + icon;
                break;
            case External:
            case Absolute:
                this.icon = icon;
                break;
        }
    }

    private native NotificationJS showNotificationJSNI(NotificationParameters parameters, String icon) /*-{
        return new $wnd.Notification(parameters.@net.spookygames.gdx.notifications.NotificationParameters::getTitle()(), {
            tag: parameters.@net.spookygames.gdx.notifications.NotificationParameters::getId()(),
            body: parameters.@net.spookygames.gdx.notifications.NotificationParameters::getText()(),
            icon: icon
        });
    }-*/;

    private native boolean isSupported() /*-{
        return "Notification" in $wnd;
    }-*/;

    private native boolean hasPermission() /*-{
        return $wnd.Notification.permission === "granted";
    }-*/;

    native public void requestPermission(NotificationPermissionResult result) /*-{
        $wnd.Notification.requestPermission(function (permissionStatus) {
            if (permissionStatus === 'granted') {
                result.@net.spookygames.gdx.notifications.HtmlNotificationHandler.NotificationPermissionResult::granted()();
            } else if (permissionStatus === 'denied') {
                result.@net.spookygames.gdx.notifications.HtmlNotificationHandler.NotificationPermissionResult::denied()();
            } else if (permissionStatus === 'default') {
                result.@net.spookygames.gdx.notifications.HtmlNotificationHandler.NotificationPermissionResult::unknown()();
            }
        });
    }-*/;

    private static class NotificationJS extends JavaScriptObject {
        protected NotificationJS() {
        }

        native final String getTag() /*-{
            return this.tag;
        }-*/;

        native final void close() /*-{
            this.close();
        }-*/;
    }

    private class NotificationPermissionResult {

        NotificationParameters parameters;

        NotificationPermissionResult(NotificationParameters parameters) {
            this.parameters = parameters;
        }

        public void granted() {
            hasPermission = true;
            showNotification(this.parameters);
        }

        public void denied() {
            hasPermission = false;
        }

        public void unknown() {
            permissionRequested = false;
            showNotification(this.parameters);
        }
    }
}

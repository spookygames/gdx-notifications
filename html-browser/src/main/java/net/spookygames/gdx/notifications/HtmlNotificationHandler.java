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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.google.gwt.core.client.JavaScriptObject;

public class HtmlNotificationHandler implements NotificationHandler {

    private boolean supported;

    private Array<NotificationJS> notifications = new Array<NotificationJS>();

    public HtmlNotificationHandler() {
        this.supported = isSupported();
    }

    @Override
    public void showNotification(NotificationParameters parameters) {
        if (this.supported) {
            this.notifications.add(showNotificationJSNI(parameters));
        }
    }

    @Override
    public void hideNotification(NotificationParameters parameters) {
        if (this.supported) {
            for (NotificationJS notification : this.notifications) {
                if (Integer.valueOf(notification.getTag()) == parameters.getId()) {
                    notification.close();
                    break;
                }
            }
        }
    }

    private native NotificationJS showNotificationJSNI(NotificationParameters parameters)/*-{
        return new Notification(parameters.@net.spookygames.gdx.notifications.NotificationParameters::getTitle()(), {
            tag: parameters.@net.spookygames.gdx.notifications.NotificationParameters::getId()(),
            body: parameters.@net.spookygames.gdx.notifications.NotificationParameters::getText()()
        });
    }-*/;

    private native boolean isSupported() /*-{
        return "Notification" in $wnd;
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
}

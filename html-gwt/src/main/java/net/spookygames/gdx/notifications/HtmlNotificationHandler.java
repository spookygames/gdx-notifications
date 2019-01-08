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


import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.utils.Array;
import com.google.gwt.user.client.ui.NotificationMole;

public class HtmlNotificationHandler implements NotificationHandler {

    private final GwtApplication application;

    private Array<NotificationMole> notificationMoles = new Array<NotificationMole>();

    public HtmlNotificationHandler(GwtApplication application) {
        this.application = application;
    }

    @Override
    public void showNotification(NotificationParameters parameters) {
        NotificationMole nm = new NotificationMole();
        application.getRootPanel().add(nm);
        nm.setTitle(parameters.getTitle());
        nm.setMessage(parameters.getText());
        nm.getElement().setId(String.valueOf(parameters.getId()));
        nm.show();
        notificationMoles.add(nm);
    }

    @Override
    public void hideNotification(NotificationParameters parameters) {
        for (NotificationMole mole : notificationMoles) {
            if (Integer.valueOf(mole.getElement().getId()) == parameters.getId()) {
                mole.hide();
                break;
            }
        }
    }
}

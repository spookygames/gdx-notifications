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
package net.spookygames.gdx.notifications.robovm;

import static net.spookygames.gdx.notifications.NotificationUtils.checkNotNull;

import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UILocalNotification;

import net.spookygames.gdx.notifications.NotificationHandler;
import net.spookygames.gdx.notifications.NotificationParameters;

public class RoboVMNotificationHandler implements NotificationHandler {

	public RoboVMNotificationHandler() {
		super();
	}

	@Override
	public void showNotification(NotificationParameters parameters) {

		checkNotNull(parameters, "parameters");
		checkNotNull(parameters.title, "parameters.title");
		checkNotNull(parameters.text, "parameters.text");

		UILocalNotification notification = new UILocalNotification();
		notification.setAlertTitle(parameters.title);
		notification.setAlertBody(parameters.text);

		UIApplication.getSharedApplication().presentLocalNotificationNow(notification);
	}

	@Override
	public void hideNotification(NotificationParameters parameters) {
		// Not supported
	}

	public void hideAllNotifications() {
		UIApplication.getSharedApplication().cancelAllLocalNotifications();
	}

}

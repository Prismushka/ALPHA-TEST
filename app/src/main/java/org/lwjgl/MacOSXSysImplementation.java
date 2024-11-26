package org.lwjgl;

import java.awt.Desktop;
import java.net.URI;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.lang.UnsatisfiedLinkError;

/**
 *
 * @author elias_naur <elias_naur@users.sourceforge.net>
 * @version $Revision$ $Id$
 */
final class MacOSXSysImplementation extends J2SESysImplementation {
	private static final int JNI_VERSION = 25;

	static {
		// Manually start the AWT Application Loop
		java.awt.Toolkit.getDefaultToolkit();
	}

	public int getRequiredJNIVersion() {
		return JNI_VERSION;
	}

	public boolean openURL(String url) {
		try {
			// Use Desktop to open the URL in the default browser
			Desktop.getDesktop().browse(new URI(url));
			return true;
		} catch (Exception e) {
			LWJGLUtil.log("Exception occurred while trying to invoke browser: " + e);
			return false;
		}
	}
}
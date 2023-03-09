package com.cedricverlinden.bazandpoort;

import com.cedricverlinden.bazandpoort.database.Database;

/**
 * End/resets everything in the plugin.
 */
public class End {

	Database database = Core.instance().database();

	/**
	 * Initiliaze the class and end/reset everything
	 */
	public End() {
		database.resetOnlinePlayers();
		database.disconnect();
	}
}

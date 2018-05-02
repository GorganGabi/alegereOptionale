package OptDist;

import java.util.Calendar;

public abstract class Distribution {
	Calendar date;
	Preference preference;
	
	abstract void setDate();
	abstract void export();
	abstract void start();
}

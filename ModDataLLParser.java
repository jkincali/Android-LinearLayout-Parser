import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ModDataLLParser implements Runnable {
	// ModDataLLParser object runs as a separate thread, not the main UI thread.
	// Handles things that might get blocked in efforts to update data for the app.
	
	public LinearLayout mLinearLayout;  // This is the pointer to the root layout object

	public TozGetData(LinearLayout mLL) {
		mLinearLayout = mLL; // Constructor saves a pointer to the root layout
	}
	
	public void resetDataCell(TextView x) {
		x.postInvalidate();
	}
	
	public void parseLayoutEntity(Object le) {
		if (le instanceof LinearLayout) {
			View v;
			int childcount = ((LinearLayout) le).getChildCount();
			for (int i=0; i < childcount; i++){
				v = ((LinearLayout) le).getChildAt(i);
				if (v instanceof TextView) {
				  // As is, the code is looking specifically for TextView, which will find
				  // all TextView objects as well as any objects based on classes extended
				  // from TextView. If forcing a redraw of the TextView object is not what
				  // is desired, then the code can be modified to suit here. (But remember
				  // this is not the UI thread so it cannot modify the object directly.)
				  resetDataCell((TextView) v);
				}
				else parseLayoutEntity(v);
			}
		}
	}
	
	@Override
    public void run() {
    // Make sure this thread is running low priority in the background.
		android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

    // Do something like get data from server and update global app data
		// ...
		
		// Then call the parser to get all the children. (In this case, each child
		// will be forced to redraw itself in order to display the new data.
		if (mLinearLayout != null) parseLayoutEntity(mLinearLayout);
	}

}

Android-LinearLayout-Parser
===========================

Parse an Android LinearLayout object for all children and sub-children.

Since this class implements Runnable, it runs as a background thread (not as the main UI thread).
This means it can do other things prior to the parsing of the layout.

For example, the code can be modified by adding a socket call to a server to retrieve data from a server on the internet.  Once this task has been completed, there is new data to be
displayed. A simple way to force the new data to be displayed is to have the layout automatically refresh itself.  But calling invalidate or postInvalidate on the root layout does not automatically
force the children to redraw themselves.  Therefore, it is necessary for each child to get a call to postInvalidate.

The ModDataLLParser can be instanciated from an OnCreate method of an Activity. It should be done after the view has been initialized (after a call to setContentView). A call to the code might look something like the following:

...

TableLayout mTableView = (TableLayout) findViewById(R.id.myroottable);

new Thread(new ModDataLLParser(mTableView)).start();

...

Note that the R.id.myroottable id would be specific to the layout id declared in your project's xml file:
android:id="@+id/myroottable"

The above example used TableLayout as the root layout class, which works because TableLayout is an extension of LinearLayout.

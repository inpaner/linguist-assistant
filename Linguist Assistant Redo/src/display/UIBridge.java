package display;

import uitextgen.UITEXTGEN;

public class UIBridge {
	UITEXTGEN ui;
	public UIBridge(UITEXTGEN ui)
	{
		this.ui=ui;
	}
	public UITEXTGEN getUI()
	{
		return ui;
	}
}

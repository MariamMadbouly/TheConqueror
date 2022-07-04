package engine;

import buildings.Building;

public interface PlayerListener 
{
	public void onBuild();
	public void onRecruit();
	public void onUpgrade();
	public void BuildArmy();
    public void OnSiege();
    
}

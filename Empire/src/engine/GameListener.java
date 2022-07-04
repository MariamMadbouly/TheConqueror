package engine;

import buildings.Building;

public interface GameListener
{
	public void BuildinProcess();
	public void UpgradeinProcess();
	public void RecruitinProcess();
	public void BuildingArmy();
	public void ChangeLocation();
	public void turnEnded();
	public void etzafetSiege();
	public void resolve();
}

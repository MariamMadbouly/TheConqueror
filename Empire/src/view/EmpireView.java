package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Building;
import buildings.EconomicBuilding;
import buildings.Farm;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;
import engine.City;
import engine.Game;
import engine.GameListener;
import engine.Player;
import exceptions.BuildingInCoolDownException;
import exceptions.FriendlyCityException;
import exceptions.FriendlyFireException;
import exceptions.MaxCapacityException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;
import exceptions.TargetNotReachedException;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Status;
import units.Unit;

public class EmpireView extends JFrame implements ActionListener,GameListener
{
	public JPanel panel;
	public JPanel top;
	public JPanel map=new JPanel();
	public GridBagConstraints gbc;
	public Game game;
	private JButton button;
	private JTextField playerName;
	public String name;
	JComboBox city;
	public JPanel unControlledCities=new JPanel();
	public JPanel controlledCities =new JPanel();
	public JPanel playerArmies=new JPanel(new GridLayout(1,0,20,10));
	public JPanel extra;
	public JPanel cView;
	public JPanel units=new JPanel(new GridLayout(0,2,20,10));
	public JPanel Army=new JPanel(new BorderLayout(30,50));;
	public JPanel ControlledArmies=new JPanel(new GridLayout(0,3,20,10));
	public ArrayList<JButton> citiesControlled= new ArrayList<JButton>();
	public ArrayList<JButton> citiesUnControlled=new ArrayList<JButton>();
	public ArrayList<JButton> armiesControlled=new ArrayList<JButton>();
	JPanel el=new JPanel(new GridLayout(1,3));
	public City buttonCity;
	public JButton ArmyButton; 
	int counter=0;
	String[]buildings= {"Select a Building","ArcheryRange","Barracks","Stable","Farm","Market"};
	JComboBox ToBuild =new JComboBox(buildings);
	JPanel build=new JPanel(new GridLayout(3,1,10,10));
	JFrame frameBuild=new JFrame();
	JPanel panelBuild=new JPanel();
	ArrayList<String> buildBuildings=new ArrayList<>();
	JButton cancelToBuild;
	JButton okToBuild;
	JButton m=new JButton("Build");
	JComboBox unitsToInitiate=new JComboBox();
	JPanel panelInitiateArmy=new JPanel();
	JPanel ControlledArmy = new JPanel (new FlowLayout (FlowLayout.LEADING,20,10));
	int[]index;
	JFrame frame=new JFrame();
	JFrame Target =new JFrame();
	JPanel TargetPanel=new JPanel();
	JComboBox armies;
	String cityToTarget;
	String cityToLaySiege;
	JFrame siege =new JFrame();
	JPanel SiegePanel=new JPanel();
	JComboBox armiesLaySiege;
	ArrayList<Integer> current;
	ArrayList<Integer> TargetArmy ;
	ArrayList<Integer> LayArmy;
	JFrame battle;
	JFrame battleArmy=new JFrame();
	JPanel battleArmyPanel=new JPanel();
	JComboBox armyAttack;
	ArrayList<Integer >indexBattleArmy=new ArrayList<>() ;
	int ans;
	ArrayList<City> cities=new ArrayList<> ();
	int cityaya=-2;
	City cua;
	JButton okInitiate;
	ArrayList<Army> armyEndTurn=new ArrayList<>();
	
	//Relocate
	JFrame relocate=new JFrame();
	JPanel relocte= new JPanel();
	JComboBox From;
	JComboBox To;
	Army Fromaya;
	Army Toaya;
	boolean Def=false;
	boolean cont=false;
	int indexf;
	JComboBox unit;
	//textarea of battlefield
	JTextArea dataAttack= new JTextArea();
	public JPanel BattleField=new JPanel(new BorderLayout());
	JComboBox unitManual;
	int counterA=0;
	boolean battlee=false;
	JButton BattleView;
	Army elbadrabo;
	Army am;
	String text="";
	JScrollPane scrollArea=new JScrollPane();
	JLabel InfoBackGround;
	ImageIcon image;
	
	public EmpireView()
	
	{
		
		dataAttack.setPreferredSize(new Dimension(200, getHeight()));
		dataAttack.setEditable(false);
		dataAttack.setFont(new Font("Consalas",Font.BOLD,14));
		dataAttack.setEditable(false);
		panel = new JPanel();
		setLayout(new BorderLayout());
		setTitle("Empire");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		image = new ImageIcon(new ImageIcon("ImageIcons/BackGround.jpg").getImage()
				.getScaledInstance(1280,660,Image.SCALE_SMOOTH));
				
				
		InfoBackGround=new JLabel();
		InfoBackGround.setLayout(new BorderLayout());
		panel.setLayout(new GridBagLayout());
	    gbc =new GridBagConstraints();
		
		panel.setBackground(Color.LIGHT_GRAY);
		
		playerName= new JTextField(20);
		playerName.setPreferredSize(new Dimension(80,30));
		String[] cities= {"Select A City","Rome","Cairo","Sparta"};
		city =new JComboBox(cities);
		city.setPreferredSize(new Dimension(130,40));
		JLabel label = new JLabel("Player name:  ");
		JLabel label2=new JLabel("Choose a City:");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Consolas",Font.BOLD,18));
		label2.setForeground(Color.BLACK);
		label2.setFont(new Font("Consolas",Font.BOLD,18));
		JLabel label1 = new JLabel("");
		label1.setPreferredSize(new Dimension(40,150));
		gbc.insets=new Insets (5,5,5,5);
		gbc.gridx=0;
		gbc.gridy=0;
		panel.add(label,gbc);
	    gbc.gridx=3;
		gbc.gridy=0;
		panel.add(playerName,gbc);
		gbc.gridx=0;
		gbc.gridy=5;
		panel.add(label2,gbc);
		gbc.gridx=3;
		gbc.gridy=5;
		panel.add(city,gbc);
		button = new JButton("Start Game");
		button.setPreferredSize(new Dimension(150,50));
		button.setForeground(Color.BLACK);
		button.setActionCommand("player name");
		button.addActionListener(this);
		button.setFocusable(false);
		button.setBounds(100, 50, 65, 15);
		gbc.gridx=3;
		gbc.gridy=8;
	    panel.add(label1,gbc);
		gbc.gridx=2;
		gbc.gridy=10;
		panel.add(button,gbc);
		InfoBackGround.add(panel);
		panel.setOpaque(false);
		add(InfoBackGround);
		setVisible(true);
		revalidate();
		repaint();
	}
	public void WorldMap()
	{ 
		InfoBackGround.removeAll();
		map.removeAll();
		map.revalidate();
		map.repaint();
		map.setLayout(new BorderLayout());
		extra=new JPanel(new BorderLayout(30,50));
		remove(panel);
		
		cityinfo();
		playerInfo();
		JButton l=new JButton("Controlled Armies");
		l.setActionCommand("Controlled Armies");
		l.addActionListener(this);
		l.setFont(new Font("Consolas",Font.BOLD,19));
		l.setForeground(Color.white);
		l.setBackground(Color.red);
		extra.add(l,BorderLayout.SOUTH);
		map.add(extra,BorderLayout.CENTER);
		map.setOpaque(false);
		extra.setOpaque(false);
		InfoBackGround.add(map);
		revalidate();
		repaint();
		
	}
	public void playerInfo()
	{
		top=new JPanel(new GridLayout(1,3));
		
		map.remove(top);
		map.revalidate();
		map.repaint();                         
		top.removeAll();
		top.revalidate();
		top.repaint();
		JPanel PlayerInfo=new JPanel();
		PlayerInfo.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		JLabel Pname= new JLabel("Player name: "+name);
		JLabel count= new JLabel("Turn: "+game.getCurrentTurnCount());
		JLabel food= new JLabel("Food: "+game.getPlayer().getFood());
		JLabel gold= new JLabel("Gold: "+game.getPlayer().getTreasury());
		Pname.setFont(new Font("Consolas",Font.BOLD,16));
		count.setFont(new Font("Consolas",Font.BOLD,16));
		food.setFont(new Font("Consolas",Font.BOLD,16));
		gold.setFont(new Font("Consolas",Font.BOLD,16));
		PlayerInfo.add(Pname);
		PlayerInfo.add(count);
		PlayerInfo.add(food);
		PlayerInfo.add(gold);
		JButton Return=new JButton(new ImageIcon("ImageIcons//back.png"));
		Return.setBorderPainted(false);
		Return.setContentAreaFilled(false);
		Return.setActionCommand("Home");
		Return.addActionListener(this);
		JPanel z=new JPanel(new FlowLayout(FlowLayout.LEADING));
		JPanel panelEndturn =new JPanel(new FlowLayout(FlowLayout.TRAILING));
		JButton endTurn=new JButton(new ImageIcon("ImageIcons//button.png"));
		endTurn.setBorderPainted(false);
		endTurn.setContentAreaFilled(false);
		endTurn.setActionCommand("EndTurn");
		endTurn.addActionListener(this);
		endTurn.setText("EndTurn");
		top.setOpaque(false);
		endTurn.setOpaque(false);
		z.setOpaque(false);
		PlayerInfo.setOpaque(false);
		panelEndturn.add(endTurn);
		z.add(Return);
		top.add(z);
		top.add(PlayerInfo);
		top.add(endTurn);
		map.add(top,BorderLayout.NORTH);
		revalidate();
		repaint();
		
	}
	
	public void cityinfo() 
	{
		map.removeAll();
		map.revalidate();
		map.repaint();
		extra.removeAll();
		extra.revalidate();
		extra.repaint();
		controlledCities.removeAll();
		unControlledCities.removeAll();
		unControlledCities.revalidate();
		unControlledCities.repaint();
		controlledCities.revalidate();
		controlledCities.repaint();
		playerInfo();
		controlledCities.setLayout(new GridLayout(0,3));
		controlledCities.setOpaque(false);
		unControlledCities.setOpaque(false);
		JLabel l=new JLabel("Your Cities: ");
		l.setForeground(Color.BLUE);
		l.setFont(new Font("Consolas",Font.BOLD,18));
		controlledCities.add(l);
		for(City c:game.getPlayer().getControlledCities()) 
		{
			String text =c.getName();
			JButton x=new JButton(new ImageIcon("ImageIcons//"+text+".png"));
			x.setText(text);
			x.setFont(new Font("Consolas",Font.BOLD,18));
			x.setBorderPainted(false);
			x.setContentAreaFilled(false);
			x.setActionCommand(c.getName());
			x.addActionListener(this);
			controlledCities.add(x);
			citiesControlled.add(x);
		}
		unControlledCities.setLayout(new GridLayout(0,3));
		JLabel l1=new JLabel("Uncontrolled Cities: ");
		l1.setForeground(Color.BLUE);
		l1.setFont(new Font("Consolas",Font.BOLD,18));
		unControlledCities.add(l1);
		extra.add(controlledCities,BorderLayout.NORTH);
		for(City c:game.getAvailableCities()) 
		{
			if(!(game.getPlayer().getControlledCities().contains(c))) 
			{
				
				String text=c.getName();
				JButton x=new JButton(new ImageIcon("ImageIcons//"+text+".png"));
				x.setText(text);
				x.setFont(new Font("Consolas",Font.BOLD,18));
				x.setBorderPainted(false);
				x.setContentAreaFilled(false);
				x.setFont(new Font("Consolas",Font.BOLD,18));
				x.setPreferredSize(new Dimension(100,100));
				unControlledCities.add(x);
				citiesUnControlled.add(x);
			}
		}
		extra.add(unControlledCities,BorderLayout.CENTER);
		revalidate();
		repaint();
		
	}
	
	public void cityView(City x)
	{
		cView=new JPanel(new BorderLayout(30,50));
		upgradeBuilding(x);
		revalidate();
		repaint();
		
	}
	
	public void updateUnits(Army a) 
	{
			for(Unit u:a.getUnits()) {
				String s="";
				JButton b2=new JButton();
				if (u instanceof Archer) 
				{
					s+= "Archer, ";
					b2.setActionCommand("Archer");
				}
				else 
				{
					if (u instanceof Cavalry) 
					{
						s+= "Cavalry, ";
						b2.setActionCommand("Cavalry");
					}
					else 
					{
						s+= "Infantry, ";
						b2.setActionCommand("Infantry");
					}
				}
				
				s+= "Unit Level: "+ u.getLevel()+", "+ "Solider Count: " + u.getCurrentSoldierCount()+""+ "Maximium Soldiers"+ u.getMaxSoldierCount();
				b2.setText(s);
				b2.addActionListener(this); 
				units.add(b2);
				revalidate();
				repaint();
				
			
			}
		
	}
	
	public String[] cities(ArrayList<City> a)
	{
		String[] b=new String[a.size()];
		for(int i=0;i<a.size();i++) 
		{
			b[i]=a.get(i).getName();
		}
		return b;
	}
	public City getCity(ArrayList<City> a) {
		City y=null;
		for(City x: a) {
			if(x.getName().equals(city.getSelectedItem())) {
				y=x;
				break;
			}
		}
		return y;
	}

	
	
//City buildings
	public void upgradeBuilding(City x)
	{
			
		//Border border=BorderFactory.CREAT;
		map.removeAll();
		map.revalidate();
		map.repaint();
	    playerInfo();
		cView.removeAll();
		cView.setOpaque(false);
		m.setFont(new Font("Consolas",Font.BOLD,19));
		m.setForeground(Color.white);
		m.setBackground(Color.red);
		m.setActionCommand("Build");
		m.addActionListener(this);
		build.removeAll();
		build.revalidate();
		build.repaint();
		build.add(m);
		build.setOpaque(false);
		m.setPreferredSize(new Dimension(150,200));
		cView.add(build,BorderLayout.EAST);
		JPanel Eco = new JPanel(new FlowLayout(FlowLayout.LEADING,100,30));
		JPanel Mil= new JPanel(new FlowLayout(FlowLayout.LEADING,100,30));
		JLabel a= new JLabel("Military Buildings: ");
		//a.setBorder(border);
		a.setBackground(Color.white);
		a.setFont(new Font("Consolas",Font.BOLD,20));
		a.setBackground(Color.red);
		a.setForeground(Color.red);
		JLabel b= new JLabel("Economical Buildings: ");
		b.setFont(new Font("Consolas",Font.BOLD,20));
		b.setBackground(Color.red);
		b.setForeground(Color.red);
		Mil.add(a);
		Eco.setOpaque(false);
		Mil.setOpaque(false);
		a.setOpaque(false);
		b.setOpaque(false);
		for(MilitaryBuilding bl :x.getMilitaryBuildings())
		{
			JPanel el=new JPanel(new GridLayout(3,1));
			el.setOpaque(false);
			Mil.setOpaque(false);
			String text="";
			String textRecruit="";
			JLabel y=new JLabel ();
			JButton upgrade=new JButton(new ImageIcon("ImageIcons//button2.png"));
			upgrade.setText("Upgrade "+bl.getUpgradeCost()+"$$            ");
			JButton recruit=new JButton(new ImageIcon("ImageIcons//button2.png"));
			upgrade.addActionListener(this);
			recruit.addActionListener(this);
			upgrade.setBorderPainted(false);
			upgrade.setContentAreaFilled(false);
			recruit.setBorderPainted(false);
			recruit.setContentAreaFilled(false);


			if(bl instanceof ArcheryRange)
			{
				upgrade.setActionCommand("upgrade_ArcheryRange");
				textRecruit="Recruit Archers "+bl.getRecruitmentCost()+" $$";
				recruit.setActionCommand("Recruit Archers");
				text+="Archry_Range:  Level "+bl.getLevel();
			}
			else {
				if(bl instanceof Barracks) 
				{
					upgrade.setActionCommand("upgrade_Barracks");
					textRecruit="Recruit Infantry "+bl.getRecruitmentCost()+" $$ ";
					recruit.setActionCommand("Recruit Infantry");
					text+="Barracks: Level "+bl.getLevel();
				}
				else
				{
					upgrade.setActionCommand("upgrade_Stable");
					textRecruit="Recruit Cavalry "+bl.getRecruitmentCost()+" $$";
					recruit.setActionCommand("Recruit Cavalry");
					text+="Stable: Level "+bl.getLevel();
				}
					
				}
			y.setText(text);
			y.setForeground(Color.blue);
			y.setFont(new Font("Consolas",Font.BOLD,18));
			recruit.setText(textRecruit);
			if(text!="") {
				el.add(y);
				el.add(upgrade);
				el.add(recruit);
			}
			Mil.add(el);
			}
		cView.add(Mil,BorderLayout.NORTH);
		Eco.add(b);
		for(EconomicBuilding bl :x.getEconomicalBuildings())
		{
			JPanel el=new JPanel(new GridLayout(3,1));
			el.setOpaque(false);
			String text="";
			JLabel y=new JLabel ();
			JButton upgrade=new JButton(new ImageIcon("ImageIcons//button2.png"));
			upgrade.setText("Upgrade "+bl.getUpgradeCost());
			upgrade.addActionListener(this);
			upgrade.setBorderPainted(false);
			upgrade.setContentAreaFilled(false);

			if(bl instanceof Farm)
			{
				upgrade.setActionCommand("upgrade_Farm");
				text+="Farm level "+bl.getLevel();
			}
			else {
					upgrade.setActionCommand("upgrade_Market");
					text+="Market level "+bl.getLevel();
				}
			y.setText(text);
			y.setForeground(Color.blue);
			y.setFont(new Font("Consolas",Font.BOLD,18));
			if(text!="") {
				el.add(y);
				el.add(upgrade);
			}
			Eco.add(el);
			}
		cView.add(Eco,BorderLayout.CENTER);
		JButton c=new JButton();
		String t="Armies";
		c.setActionCommand("Armies of "+x.getName());
		c.setFont(new Font("Consolas",Font.BOLD,19));
		c.setForeground(Color.WHITE);
		c.setBackground(Color.RED);
		c.addActionListener(this);
		c.setFocusable(false);
		c.setText(t);		
		cView.add(c,BorderLayout.SOUTH);
		cView.revalidate();
		cView.repaint();
		map.add(cView);
		revalidate();
		repaint();
		
		}
//City Army
	public void updateDefendingArmy(City c)
	{
		map.removeAll();
		map.revalidate();
		map.repaint();
	    playerInfo();
		Army.removeAll();
		Army.setOpaque(false);
		JLabel a=new JLabel("City's Defending Army");
		a.setFont(new Font("Consolas",Font.BOLD,28));
		a.setForeground(Color.red);
		JPanel DA= new JPanel(new GridLayout(3,4,20,10));
		Army.add(a,BorderLayout.NORTH);
		JLabel arch=new JLabel("Archer: ");
		JLabel inf =new JLabel("Infantry: ");
		JLabel cav=new JLabel("Cavalry: ");
		arch.setForeground(Color.blue);
		arch.setFont(new Font("Consolas",Font.BOLD,22));
		inf.setForeground(Color.blue);
		inf.setFont(new Font("Consolas",Font.BOLD,22));
		cav.setForeground(Color.blue);
		cav.setFont(new Font("Consolas",Font.BOLD,22));
		JTextArea arch1=new JTextArea();
		JTextArea cav1=new JTextArea();
		JTextArea inf1=new JTextArea();
		arch1.setEditable(false);
		cav1.setEditable(false);
		inf1.setEditable(false);
		String text="";
		int i=1;
		System.out.println(c.getDefendingArmy().getUnits().size());
		for(Unit u: c.getDefendingArmy().getUnits())
		{
			
			if(u instanceof Archer)
			{
				text+= "Unit "+i+": level "+u.getLevel()+", Current Soldier Count: "+u.getCurrentSoldierCount()+" Maximum soldier Count: "+u.getMaxSoldierCount()+"\n";
				i++;
				
			}
			
		}
		
		arch1.setOpaque(false);
		arch1.setBorder(BorderFactory.createLineBorder(Color.black,2));
		arch1.setText(text);
		JScrollPane f1=new JScrollPane(arch1);
		f1.setOpaque(false);
		f1.getViewport().setOpaque(false);
		DA.setOpaque(false);
		DA.add(arch);
		DA.add(f1);
		int i1=1;
		text="";
		for(Unit u: c.getDefendingArmy().getUnits())
		{
			if(u instanceof Cavalry)
			{
				text+= "Unit "+i1+": level "+u.getLevel()+", Current Soldier Count: "+u.getCurrentSoldierCount()+" Maximum Soldier Count: "+u.getMaxSoldierCount()+"\n";
				i1++;
			}
			
		}
		
		cav1.setText(text);
		cav1.setOpaque(false);
		cav1.setBorder(BorderFactory.createLineBorder(Color.black,2));
		JScrollPane f2=new JScrollPane(cav1);
		f2.setOpaque(false);
		f2.getViewport().setOpaque(false);
		DA.add(cav);
		DA.add(f2);
		i=1;
		text="";
		for(Unit u: c.getDefendingArmy().getUnits())
		{
			if(u instanceof Infantry)
			{
				text+= "Unit "+i+": level "+u.getLevel()+", Current Soldier Count: "+u.getCurrentSoldierCount()+" Maximum Soldier Count: "+u.getMaxSoldierCount()+"\n";
				i++;
				
			}
			
		}
		inf1.setText(text);
		inf1.setOpaque(false);

		inf1.setBorder(BorderFactory.createLineBorder(Color.black,2));
		JScrollPane f3=new JScrollPane(inf1);
		f3.setOpaque(false);
		f3.getViewport().setOpaque(false);
		DA.add(inf);
		DA.add(f3);
		Army.add(DA,BorderLayout.CENTER);
		
	// CONTROLLED ARMIES IN CITY
		JPanel playerArmy=new JPanel(new BorderLayout());
		playerArmy.setOpaque(false);
		JButton Initiate = new JButton("InitiateArmy");
		Initiate.setActionCommand("Initiate Army "+c.getName());
		Initiate.setFont(new Font("Consolas",Font.BOLD,19));
		Initiate.setForeground(Color.WHITE);
		Initiate.setBackground(Color.RED);
		Initiate.addActionListener(this);
		Initiate.setFocusable(false);
		playerArmy.add(Initiate,BorderLayout.NORTH);
		JTextArea t=new JTextArea();
		t.setEditable(false);
		int f=1;
		String text1="Player Controlled Armies in city "+"\n" + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~ "+"\n";
		for(Army a1:game.getPlayer().getControlledArmies())
		{
			if(a1.getCurrentLocation().equals(c.getName()))
			{
				int j=1;
				text1+="Army "+f+":"+"\n";
				for(Unit u:a1.getUnits())
				{
					if(u instanceof Cavalry)
						text1+="Unit "+j+": Cavalry ";
					else {
						if(u instanceof Archer)
							text1+="Unit "+j+": Archer ";
						else
							text1+="Unit "+j+": Infatnry ";
							
					}
					text1+=" level "+u.getLevel()+", Current Soldier Count is "+u.getCurrentSoldierCount()+" The Maximum soldier count is "+u.getMaxSoldierCount()+"\n";
					j++;
				}
				f++;
				
			}
		}
		
		t.setText(text1);
		t.setOpaque(false);
		t.setBorder(BorderFactory.createLineBorder(Color.black,2));
		JScrollPane f4=new JScrollPane(t);
		f4.setOpaque(false);
		f4.getViewport().setOpaque(false);
		playerArmy.add(f4,BorderLayout.CENTER);
		Army.add(playerArmy,BorderLayout.EAST);
		Army.revalidate();
		Army.repaint();
		map.add(Army);
		revalidate();
		repaint();
			
	}
	
	public void playerArmy()
	{
		map.removeAll();
		map.revalidate();
		map.repaint();
	    ControlledArmies.removeAll();
	    ControlledArmies.revalidate();
	    ControlledArmies.repaint();
	    ControlledArmies.setOpaque(false);
	    JPanel ArmiesInfo = new JPanel (new GridLayout(0,3,20,10));
	    ArmiesInfo.setOpaque(false);
	    JPanel actionButtons=new JPanel(new GridLayout(0,3,20,10));
	    actionButtons.setOpaque(false);
	    ControlledArmies.setLayout(new BorderLayout());
	    JPanel info = new JPanel (new FlowLayout (FlowLayout.LEADING,250,10));
	    info.setOpaque(false);
	    JButton idle = new JButton ("IDLE");
	    JButton marching =new JButton ("MARCHING");
	    JButton besieging =new JButton ("BESIEGING");
	    idle.setFont(new Font("Consolas",Font.BOLD,19));
	    idle.setForeground(Color.white);
	    idle.setBackground(Color.red);
	    marching.setFont(new Font("Consolas",Font.BOLD,19));
	    marching.setForeground(Color.white);
	    marching.setBackground(Color.red);
	    besieging.setFont(new Font("Consolas",Font.BOLD,19));
	    besieging.setForeground(Color.white);
	    besieging.setBackground(Color.red);
	    info.add(idle);
	    info.add(marching);
	    info.add(besieging);
	    ControlledArmies.add(info,BorderLayout.NORTH);
	    JTextArea idlee = new JTextArea();
	    JTextArea march = new JTextArea();
	    JTextArea besieg = new JTextArea();
		
	    idlee.setEditable(false);
	    march.setEditable(false);
	    besieg.setEditable(false);
	    idlee.setOpaque(false);
		idlee.setBorder(BorderFactory.createLineBorder(Color.black,2));
		march.setOpaque(false);
		march.setBorder(BorderFactory.createLineBorder(Color.black,2));
		besieg.setOpaque(false);
		besieg.setBorder(BorderFactory.createLineBorder(Color.black,2));
		int id=1;
		int im=1;
		int ib=1;
		String textIdle="";
		String textMarch="";
		String textBesieg="";
		boolean flagIdle =false;
		boolean flagMarch =false;
		boolean flagBesieg =false;
		textIdle="";
		textMarch="";
		textBesieg="";
		for(Army a:game.getPlayer().getControlledArmies())
		{
			if(a.getCurrentStatus().equals(Status.IDLE))
			{
				int j=1;
				textIdle+= "Army "+id+" Current Location is "+ a.getCurrentLocation()+"\n";
				for (Unit u: a.getUnits())
				{
					textIdle+= "Unit "+j+": level "+u.getLevel()+", Current Soldier Count: "+u.getCurrentSoldierCount()+" Maximum soldier Count: "+u.getMaxSoldierCount()+"\n";
					j++;
				}
				id++;
				flagIdle=true;
			}
			if(a.getCurrentStatus().equals(Status.MARCHING))
			{
				int j=1;
				textMarch+="Army "+im+" Target of Army is "+a.getTarget()+", Turns to reach target is "+a.getDistancetoTarget()+"\n";
				for (Unit u: a.getUnits())
				{
					textMarch+= "Unit "+j+": level "+u.getLevel()+", Current Soldier Count: "+u.getCurrentSoldierCount()+" Maximum soldier Count: "+u.getMaxSoldierCount()+"\n";
					j++;
				}
				im++;
				flagMarch=true;
			}
			if(a.getCurrentStatus().equals(Status.BESIEGING))
			{
				int j=1;
				int turns=0;
				String name=a.getCurrentLocation().toLowerCase();
				for(City c:game.getAvailableCities()) {
					if(c.getName().toLowerCase().equals(name)) {
						turns=c.getTurnsUnderSiege();
						break;
					}
				}
				textBesieg+="Army "+ib+", City undersiege is"+a.getCurrentLocation()+", turns undersiege: "+turns+": \n";
				for (Unit u: a.getUnits())
				{
					textBesieg+= "Unit "+j+": level "+u.getLevel()+", Current Soldier Count: "+u.getCurrentSoldierCount()+" Maximum soldier Count: "+u.getMaxSoldierCount()+"\n";
					j++;
				}
				ib++;
				flagBesieg=true;
			}
			
			}
		idlee.setText(textIdle);
		march.setText(textMarch);
		besieg.setText(textBesieg);
		JButton TargetCity=new JButton(new ImageIcon("ImageIcons//button2.png"));
		TargetCity.setText("Target For Army");
		TargetCity.setActionCommand("Target Army");
		TargetCity.addActionListener(this);
		TargetCity.setBorderPainted(false);
		TargetCity.setContentAreaFilled(false);
		actionButtons.add(TargetCity);	
		JButton LaySiege=new JButton(new ImageIcon("ImageIcons//button2.png"));
		LaySiege.setText("Lay Siege");
		LaySiege.setActionCommand("LaySiege");
		LaySiege.addActionListener(this);
		LaySiege.setBorderPainted(false);
		LaySiege.setContentAreaFilled(false);
		actionButtons.add(LaySiege);		
		JButton battle=new JButton(new ImageIcon("ImageIcons//button2.png"));
		battle.setText("Battle");
		battle.setActionCommand("battle");
		battle.addActionListener(this);
		battle.setBorderPainted(false);
		battle.setContentAreaFilled(false);
		actionButtons.add(battle);
		JButton Relocate=new JButton(new ImageIcon("ImageIcons//button2.png"));
		Relocate.setText("Relocate Army");
		Relocate.setActionCommand("Relocate Army");
		Relocate.addActionListener(this);
		Relocate.setBorderPainted(false);
		Relocate.setContentAreaFilled(false);
		actionButtons.add(Relocate);
		JScrollPane f1=new JScrollPane(idlee);
		f1.setOpaque(false);
		f1.getViewport().setOpaque(false);
		JScrollPane f2=new JScrollPane(march);
		f2.setOpaque(false);
		f2.getViewport().setOpaque(false);
		JScrollPane f3=new JScrollPane(besieg);
		f3.setOpaque(false);
		f3.getViewport().setOpaque(false);
		ArmiesInfo.add(idlee);
		ArmiesInfo.add(march);
		ArmiesInfo.add(besieg);
		ControlledArmies.add(ArmiesInfo,BorderLayout.CENTER);
		ControlledArmies.add(actionButtons,BorderLayout.SOUTH);
	    ControlledArmies.revalidate();
	    ControlledArmies.repaint();
	    playerInfo();
	    revalidate();
	    repaint();
	    
	}
	public void BattleView ()
	{
		
	}
	

	//Listeners from game
	
	public void BuildinProcess() 
	{
		System.out.println(buttonCity.getName());
		 playerInfo();
		 upgradeBuilding(buttonCity);
	}

	@Override
	public void UpgradeinProcess() 
	{
		upgradeBuilding(buttonCity);
		
		
	}

	@Override
	public void RecruitinProcess() 
	{
		upgradeBuilding(buttonCity);	
	}

	@Override
	public void BuildingArmy() {
		updateDefendingArmy(buttonCity);
		
		
	}

	public void ChangeLocation() {
		playerArmy();
		map.add(ControlledArmies,BorderLayout.CENTER);
		map.revalidate();
		map.repaint();
		revalidate();
		repaint();
	}
	public void etzafetSiege() {
		playerArmy();
		map.add(ControlledArmies,BorderLayout.CENTER);
		map.revalidate();
		map.repaint();
		revalidate();
		repaint();
		
	}
	
	public void battle(Army attacker , Army defender )
	{
		map.removeAll();
		map.revalidate();
		map.repaint();
		BattleField.removeAll();
		BattleField.revalidate();
		BattleField.repaint();
		dataAttack.removeAll();
		BattleField.setOpaque(false);
		map.setOpaque(false);
		dataAttack.setOpaque(false);
		JPanel info=new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
		info.setOpaque(false);
		JLabel Pname= new JLabel("Player name: "+name);
		JLabel count= new JLabel("Turn: "+game.getCurrentTurnCount());
		JLabel food= new JLabel("Food: "+game.getPlayer().getFood());
		JLabel gold= new JLabel("Gold: "+game.getPlayer().getTreasury());
		Pname.setFont(new Font("Consolas",Font.BOLD,16));
		count.setFont(new Font("Consolas",Font.BOLD,16));
		food.setFont(new Font("Consolas",Font.BOLD,16));
		gold.setFont(new Font("Consolas",Font.BOLD,16)); 
		info.add(Pname);
		info.add(count);
		info.add(food);
		info.add(gold);
		map.add(info,BorderLayout.NORTH);
		String text ="";
		String [] texts=new String[attacker.getUnits().size()]; 
		if(counterA % 2==0)
		{
			int i=0;
			for(Unit u: attacker.getUnits())
			{
				text="Unit "+i +" No of soldiers "+u.getCurrentSoldierCount()+" Level: "+u.getLevel();
				texts[i]=text;
				i++;
			}
			unitManual=new JComboBox(texts);
			unitManual.setPreferredSize(new Dimension(400,100));
		    unitManual.setOpaque(false);
		    
			JButton okTarget=new JButton("ok");
			okTarget.setActionCommand("OkAttack");
			okTarget.addActionListener(this);
			BattleField.setLayout(new BorderLayout());
			JPanel data =new JPanel(new FlowLayout(FlowLayout.LEADING,10,10));
			data.setOpaque(false);
			data.add(okTarget);
			data.add(unitManual);
			
			BattleField.add(data,BorderLayout.EAST);
			BattleField.add(dataAttack,BorderLayout.CENTER);
			JButton exit;
			if(attacker.getUnits().size()==0)
			{
				if(cua!=null) {
					cua.setTurnsUnderSiege(-1);
					cua.setUnderSiege(false);
				}
				
				counterA=0;
				exit=new JButton("Close Battle View");
				exit.setActionCommand("Exit");
				exit.addActionListener(this);
				game.getPlayer().getControlledArmies().remove(attacker);
				battlee=false;
				BattleField.add(exit,BorderLayout.SOUTH);
				cua=null;
				text="";
			}
			if(defender.getUnits().size()==0)
			{
				if(cua!=null) {
					cua.setTurnsUnderSiege(-1);
					cua.setUnderSiege(false);
				}
               
				counterA=0;
				exit=new JButton("Close Battle View");
				exit.setActionCommand("Exit");
				exit.addActionListener(this);
				game.occupy(attacker, defender.getCurrentLocation());
				battlee=true;
				BattleField.add(exit,BorderLayout.SOUTH);
				cua=null;
				text="";
				
			}
			BattleField.revalidate();
			BattleField.repaint();
			BattleField.setVisible(true);
			map.add(BattleField,BorderLayout.CENTER);
			revalidate();
			repaint();
			
			
		}
		
		
		
	}
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("player name"))
		{
		    name =playerName.getText();
		    if(playerName.getText().equals(" ")) {
		          JOptionPane.showMessageDialog(panel, "please enter your name","Invalid action",JOptionPane.WARNING_MESSAGE);
		          return;
		          
		    }
		    if(city.getSelectedItem().equals("Select A City"))
		    {
		    	 JOptionPane.showMessageDialog(panel, "please select a city","Invalid action",JOptionPane.WARNING_MESSAGE);
		          return;
		    }
		    try 
		    
			{
		    	
				game=new Game(playerName.getText(),(String)city.getSelectedItem());
				game.setListener(this);
				game.getPlayer().setListener(game);
			} 
			catch (IOException a) 
			{
				a.printStackTrace();
			}
		   
			WorldMap();
			counter=0;
			return;
		}
		
		
		for(City c :game.getPlayer().getControlledCities()) 
		{
			if(e.getActionCommand().equals(c.getName()))
			{
				buttonCity=null;
				extra.removeAll();
				extra.revalidate();
				extra.repaint();
				map.remove(extra);
				cityView(c);
				map.add(cView,BorderLayout.CENTER);
				map.add(new JLabel(""),BorderLayout.SOUTH);
				buttonCity=c;
				System.out.println(buttonCity.getName());
				counter=1;
				revalidate();
				repaint();
			
				
			}
			if(e.getActionCommand().equals("Armies of "+c.getName())){
				cView.removeAll();
				cView.revalidate();
				cView.repaint();
				map.remove(cView);
				updateDefendingArmy(c);
				map.add(Army);
				counter=2;
				revalidate();
				repaint();
				
			}
		}
			if(e.getActionCommand().equals("Home"))
			{
				if(counter==1)
					{
						map.remove(cView);
						map.removeAll();
						extra.removeAll();
						cityinfo();
						playerInfo();
						JButton l=new JButton("Controlled Armies");
						l.setActionCommand("Controlled Armies");
						l.addActionListener(this);
						l.setFont(new Font("Consolas",Font.BOLD,19));
						l.setForeground(Color.white);
						l.setBackground(Color.red);
						extra.add(l,BorderLayout.SOUTH);
						map.add(extra,BorderLayout.CENTER);
						counter=0;
					}
					else {
						if(counter==2) {
							map.remove(Army);
							cityView(buttonCity);
							map.add(cView,BorderLayout.CENTER);
							map.add(new JLabel(""),BorderLayout.SOUTH);
							counter=1;
							
						}
						else
						{
							map.removeAll();
							map.remove(ControlledArmies);
							extra.removeAll();
							cityinfo();
							playerInfo();
							JButton l=new JButton("Controlled Armies");
							l.setActionCommand("Controlled Armies");
							l.addActionListener(this);
							l.setFont(new Font("Consolas",Font.BOLD,19));
							l.setForeground(Color.white);
							l.setBackground(Color.red);
							extra.add(l,BorderLayout.SOUTH);
							map.add(extra,BorderLayout.CENTER);
							counter=0;
							
						}
						
				}
				revalidate();
				repaint();
			}
			
		int i=1;
		for(Army a:game.getPlayer().getControlledArmies()) 
		{
			if(e.getActionCommand().equals("Armies "+i)) 
			{
				map.remove(extra);
				updateUnits(a);
				map.add(units);
				revalidate();
				repaint();
			}
			i++;
		}
			
		
		if(e.getActionCommand().equals("Build"))
		{
			
			ToBuild.setPreferredSize(new Dimension(150,100));
			cancelToBuild=new JButton("Cancel");
			cancelToBuild.setActionCommand("Cancel Build");
			okToBuild=new JButton("Ok");
			okToBuild.setActionCommand("Ok Build");
			okToBuild.addActionListener(this);
			cancelToBuild.addActionListener(this);
			buildFrame();
			
		}
		if(e.getActionCommand().equals("Cancel Build"))
		{
			
			frameBuild.setVisible(false);
		}
		if(e.getActionCommand().equals("Ok Build")) 
		{
			String item=(String) ToBuild.getSelectedItem();
			try 
			{
				if(ToBuild.getSelectedIndex()==0)
				{
					JOptionPane.showMessageDialog(cView, "Please Select an item","Invalid action",JOptionPane.WARNING_MESSAGE);
					return;
				}
				else {
					if(buildBuildings.contains(item)) {
					
						JOptionPane.showMessageDialog(cView, "You already have this building","Invalid action",JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					else {	
						game.getPlayer().build(item, buttonCity.getName());
						buildBuildings.add(item);
						
				}
					frameBuild.setVisible(false);
				}

			} 
			catch (NotEnoughGoldException e1) 
			{
				JOptionPane.showMessageDialog(cView, "You don't have enough gold","Invalid action",JOptionPane.WARNING_MESSAGE);
				
			}
			
		}
		if(buttonCity != null)
		{
			for(MilitaryBuilding a:buttonCity.getMilitaryBuildings())
			{
				if(a instanceof ArcheryRange && e.getActionCommand().equals("upgrade_ArcheryRange"))
				{
					try 
					{
						game.getPlayer().upgradeBuilding(a);
					} 
					catch (NotEnoughGoldException e1) 
					{
						JOptionPane.showMessageDialog(cView, "You don't have enough gold","Invalid action",JOptionPane.WARNING_MESSAGE);
						
					}
					catch (BuildingInCoolDownException e1) 
					{
						JOptionPane.showMessageDialog(cView, "The building is cooling down, try next turn","Invalid action",JOptionPane.WARNING_MESSAGE);
					} 
					catch (MaxLevelException e1) 
					{
						JOptionPane.showMessageDialog(cView, "You have reached  the maximum level of the building","Invalid action",JOptionPane.WARNING_MESSAGE);
					}
				}
				
			if(a instanceof Barracks && e.getActionCommand().equals("upgrade_Barracks"))
			{
				try 
				{
					game.getPlayer().upgradeBuilding(a);
				} 
				catch (NotEnoughGoldException e1) 
				{
					JOptionPane.showMessageDialog(cView, "You don't have enough gold","Invalid action",JOptionPane.WARNING_MESSAGE);
				}
				catch (BuildingInCoolDownException e1) 
				{
					JOptionPane.showMessageDialog(cView, "The building is cooling down, try next turn","Invalid action",JOptionPane.WARNING_MESSAGE);
				} 
				catch (MaxLevelException e1) 
				{
					JOptionPane.showMessageDialog(cView, "You have reached  the maximum level of the building","Invalid action",JOptionPane.WARNING_MESSAGE);
				}
			}
			if(a instanceof Stable && e.getActionCommand().equals("upgrade_Stable"))
			{
				try 
				{
					game.getPlayer().upgradeBuilding(a);
				} 
				catch (NotEnoughGoldException e1) 
				{
					JOptionPane.showMessageDialog(cView, "You don't have enough gold","Invalid action",JOptionPane.WARNING_MESSAGE);
				}
				catch (BuildingInCoolDownException e1) 
				{
					JOptionPane.showMessageDialog(cView, "The building is cooling down, try next turn","Invalid action",JOptionPane.WARNING_MESSAGE);
				} 
				catch (MaxLevelException e1) 
				{
					JOptionPane.showMessageDialog(cView, "You have reached  the maximum level of the building","Invalid action",JOptionPane.WARNING_MESSAGE);
				}
				revalidate();
				repaint();
			}
			}
			for(EconomicBuilding a:buttonCity.getEconomicalBuildings())
			{
				if(a instanceof Farm && e.getActionCommand().equals("upgrade_Farm"))
				{
					try 
					{
						game.getPlayer().upgradeBuilding(a);
					} 
					catch (NotEnoughGoldException e1) 
					{
						JOptionPane.showMessageDialog(cView, "You don't have enough gold","Invalid action",JOptionPane.WARNING_MESSAGE);
					}
					catch (BuildingInCoolDownException e1) 
					{
						JOptionPane.showMessageDialog(cView, "The building is cooling down, try next turn","Invalid action",JOptionPane.WARNING_MESSAGE);
					} 
					catch (MaxLevelException e1) 
					{
						JOptionPane.showMessageDialog(cView, "You have reached  the maximum level of the building","Invalid action",JOptionPane.WARNING_MESSAGE);
					}
				}
				
			if(a instanceof Market && e.getActionCommand().equals("upgrade_Market"))
			{
				try 
				{
					game.getPlayer().upgradeBuilding(a);
				} 
				catch (NotEnoughGoldException e1) 
				{
					JOptionPane.showMessageDialog(cView, "You don't have enough gold","Invalid action",JOptionPane.WARNING_MESSAGE);
				}
				catch (BuildingInCoolDownException e1) 
				{
					JOptionPane.showMessageDialog(cView, "The building is cooling down, try next turn","Invalid action",JOptionPane.WARNING_MESSAGE);
				} 
				catch (MaxLevelException e1) 
				{
					JOptionPane.showMessageDialog(cView, "You have reached  the maximum level of the building","Invalid action",JOptionPane.WARNING_MESSAGE);
				}
			}
			revalidate();
			repaint();
			}
			if (e.getActionCommand().equals("Recruit Archers"))
			{
				try 
				{
	
					game.getPlayer().recruitUnit("Archer", buttonCity.getName());
				} 
				catch (BuildingInCoolDownException e1) 
				{
					JOptionPane.showMessageDialog(Army, "The building is cooling down, try next turn","Invalid action",JOptionPane.WARNING_MESSAGE);
					
				} 
				catch (MaxRecruitedException e1)
				{
					JOptionPane.showMessageDialog(Army, "You have recruited the maximum number of units for this building","Invalid action",JOptionPane.WARNING_MESSAGE);
	
				} 
				catch (NotEnoughGoldException e1) 
				{
					JOptionPane.showMessageDialog(Army, "You don't have enough gold","Invalid action",JOptionPane.WARNING_MESSAGE);
	
				}
			}
			if (e.getActionCommand().equals("Recruit Cavalry"))
			{
				try 
				{
					game.getPlayer().recruitUnit("Cavalry", buttonCity.getName());
				} 
				catch (BuildingInCoolDownException e1) 
				{
					JOptionPane.showMessageDialog(Army, "The building is cooling down, try next turn","Invalid action",JOptionPane.WARNING_MESSAGE);
					
				} 
				catch (MaxRecruitedException e1)
				{
					JOptionPane.showMessageDialog(Army, "You have recruited the maximum number of units for this building","Invalid action",JOptionPane.WARNING_MESSAGE);
	
				} 
				catch (NotEnoughGoldException e1) 
				{
					JOptionPane.showMessageDialog(Army, "You don't have enough gold","Invalid action",JOptionPane.WARNING_MESSAGE);
	
				}
			}
			if (e.getActionCommand().equals("Recruit Infantry"))
			{
				try 
				{
					game.getPlayer().recruitUnit("Infantry", buttonCity.getName());
				} 
				catch (BuildingInCoolDownException e1) 
				{
					JOptionPane.showMessageDialog(Army, "The building is cooling down, try next turn","Invalid action",JOptionPane.WARNING_MESSAGE);
					
				} 
				catch (MaxRecruitedException e1)
				{
					JOptionPane.showMessageDialog(Army, "You have recruited the maximum number of units for this building","Invalid action",JOptionPane.WARNING_MESSAGE);
	
				} 
				catch (NotEnoughGoldException e1) 
				{
					JOptionPane.showMessageDialog(Army, "You don't have enough gold","Invalid action",JOptionPane.WARNING_MESSAGE);
	
				}
			}
			if(e.getActionCommand().equals("Initiate Army "+buttonCity.getName()))
			{
				String [] a= {"Archer", "Infantry", "Cavalry"};
				int ans =JOptionPane.showOptionDialog(Army,
						"Please select the type of army you want to initiate", 
						"Intiate Army",
						JOptionPane.YES_NO_CANCEL_OPTION, 
						JOptionPane.INFORMATION_MESSAGE, 
						null,
						a, 
						0);
				String unitType="";
				if(ans !=-1)
					 unitType=a[ans];
				String[] options=new String[buttonCity.getDefendingArmy().getUnits().size()];
				index=new int[buttonCity.getDefendingArmy().getUnits().size()];
				int j=1;
				int z=0;
				boolean flag=false;
				for(Unit u:buttonCity.getDefendingArmy().getUnits())
				{
					if(unitType.equals("Archer")&&u instanceof Archer)
					{
						buttonCity.getDefendingArmy().getUnits().size();
						String y="Unit "+j+" in level: "+u.getLevel()+ " has "+u.getCurrentSoldierCount()+"soldiers";
						index[z]=j-1;
						options[z]=y;
						z++;
						flag=true;
					}
					if(unitType.equals("Infantry")&&u instanceof Infantry)
					{
						String y="Unit "+j+" in level: "+u.getLevel()+ " has "+u.getCurrentSoldierCount()+"soldiers";
						index[z]=j-1;
						options[z]=y;
						z++;
						flag=true;
					}
					if(unitType.equals("Cavalry")&&u instanceof Cavalry)
					{
						String y="Unit "+j+" in level: "+u.getLevel()+ " has "+u.getCurrentSoldierCount()+"soldiers";
						index[z]=j-1;
						options[z]=y;
						z++;
						flag=true;
					}
					j++;
					
				}
				if(!flag) {
					JOptionPane.showMessageDialog(Army, "No units to intiate Army with , you recruit first","Invalid action",JOptionPane.WARNING_MESSAGE);
					return;
				}
				unitsToInitiate.setSelectedIndex(-1);
				frame.revalidate();
				frame.repaint();
				okInitiate=new JButton("OK");
				okInitiate.setActionCommand("ok Initiate Army");
				okInitiate.addActionListener(this);
				panelInitiateArmy.removeAll();
				panelInitiateArmy.revalidate();
				panelInitiateArmy.repaint();
				panelInitiateArmy.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
				frame.setSize(500,500);
				frame.setLocationRelativeTo(null);
				unitsToInitiate=new JComboBox(options);
				unitsToInitiate.setPreferredSize(new Dimension(400,100));
				panelInitiateArmy.add(unitsToInitiate);
				panelInitiateArmy.add(okInitiate);
				frame.setLayout(new BorderLayout());
				panelInitiateArmy.revalidate();
				panelInitiateArmy.repaint();
				panelInitiateArmy.setVisible(true);
				frame.add(panelInitiateArmy);
				frame.setVisible(true);
				validate();
				repaint();
				
			}
			if(e.getActionCommand().equals("ok Initiate Army"))
				
			{
				int a=unitsToInitiate.getSelectedIndex();
				
				int armyIndex=index[a];
				Unit u=buttonCity.getDefendingArmy().getUnits().get(armyIndex);
			    game.getPlayer().initiateArmy(buttonCity, u);
			    frame.setVisible(false);
			    
			    
			}
			if(e.getActionCommand().equals("EndTurn"))
			{
				game.endTurn();
				for(City c:game.getAvailableCities())
				{
					ArrayList<Army> army=new ArrayList<>();
					if(c.getTurnsUnderSiege()==3)
					{
						c.setUnderSiege(false);
						counterA=0;
						
						
						String[]options= {"AutoResolve","Attack"};
						int ans= JOptionPane.showOptionDialog(ControlledArmies,
								"City "+c.getName()+" has been uder siege for more than 3 turns "+
										 "so you have to choose either to auto resolve or attack ",
								"Target City of Army",
								JOptionPane.YES_NO_CANCEL_OPTION, 
								JOptionPane.INFORMATION_MESSAGE, 
								null,
								options, 
								-1);
						if(ans ==0)
						{
							for(Army a:game.getPlayer().getControlledArmies())
							{
								if(a.getCurrentStatus().equals(Status.BESIEGING)&& a.getCurrentLocation().equals(c.getName())) {
									army.add(a);
								}
									
								}
							for(Army a:army)
							{
								try {
									c.setTurnsUnderSiege(-1);
									String text=game.autoResolve(a, c.getDefendingArmy());
									JOptionPane.showMessageDialog(ControlledArmies, text,"Result",JOptionPane.INFORMATION_MESSAGE);
									playerArmy();
									map.add(ControlledArmies,BorderLayout.CENTER);
									map.revalidate();
									map.repaint();
									revalidate();
									repaint();
									
									
								} catch (FriendlyFireException e1) 
								{
									e1.printStackTrace();
								}
								
							}
							}
						if(ans==1)
						{
							
							for(Army a:game.getPlayer().getControlledArmies())
							{
								if(a.getCurrentStatus().equals(Status.BESIEGING)&& a.getCurrentLocation().equals(c.getName())||a.getCurrentLocation().equals(c.getName())) {
									army.add(a);
								}
									
								}
							for(Army a:army)
								
							{
								c.setTurnsUnderSiege(-1);
								am=a;
								elbadrabo=c.getDefendingArmy();
							    battle(a,c.getDefendingArmy());
							    text="";
						}
						}
					
					
					}

				}
				
				if(game.isGameOver())
				{
					map.removeAll();
					map.setLayout(new GridBagLayout());
					JLabel l = new JLabel("Game over !!");
					JLabel l2;
					if(game.getPlayer().getControlledCities().size()==3)
					{
						l2=new JLabel("You Have Won >>");
					}
					else
						l2=new JLabel("You Have Lost >>");
					l.setFont(new Font("Consalas",Font.BOLD,22));
					l.setForeground(Color.RED);
					l2.setFont(new Font("Consalas",Font.BOLD,22));
					l2.setForeground(Color.RED);
					gbc.insets=new Insets (5,5,5,5);
					gbc.gridx=0;
					gbc.gridy=0;
					map.add(l2,gbc);
					gbc.gridx=0;
					gbc.gridy=1;
					map.add(l,gbc);
					map.revalidate();
					map.repaint();
					
				}
				
		    }
		
		if(e.getActionCommand().equals("Controlled Armies"))
		{
			if(game.getPlayer().getControlledArmies().size()==0)
			{
				JOptionPane.showMessageDialog(extra, "You don't have any armies yet","Invalid action",JOptionPane.WARNING_MESSAGE);
			}
			else {
				extra.removeAll();
				extra.revalidate();
				extra.repaint();
				map.remove(extra);
				playerArmy();
				map.add(ControlledArmies,BorderLayout.CENTER);
				counter=3;
				revalidate();
				repaint();
			}
		}
		if(e.getActionCommand().equals("Target Army"))
		{
			LayArmy=new ArrayList<>();
			String [] a= {"Cairo", "Rome", "Sparta"};
			int ans =JOptionPane.showOptionDialog(ControlledArmies,
					"Please select the city", 
					"Target City of Army",
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, 
					null,
					a, 
					-1);
			 cityToTarget="";
			if(ans !=-1)
				 cityToTarget=a[ans];
			
			String[]armiesAvaliable=new String[game.getPlayer().getControlledArmies().size()+1];
			armiesAvaliable[0]="please Select an item";
			int j=1;
			int z=0;
			Boolean flag =true;
			for(Army am :game.getPlayer().getControlledArmies())
			{
				if(!(am.getCurrentLocation().toLowerCase().equals(cityToTarget.toLowerCase())))
				{
					String text="";
					text="Army "+j+" "+am.getCurrentStatus()+", Location"+am.getCurrentLocation()+", having "+am.getUnits().size()+" units";
					armiesAvaliable[j]=text;
					j++;
					LayArmy.add(z);
					flag=false;
				}
				z++;
			}
			if(flag)
			{
				JOptionPane.showMessageDialog(ControlledArmies, "All if you armies is in "+cityToTarget,"Invalid action",JOptionPane.WARNING_MESSAGE);
				return;
			}
			armies = new JComboBox(armiesAvaliable);
			armies.setPreferredSize(new Dimension(400,100));
			JButton okTarget=new JButton("ok");
			okTarget.setActionCommand("OkTarget");
			okTarget.addActionListener(this);
			TargetPanel.removeAll();
			TargetPanel.revalidate();
			TargetPanel.repaint();
			TargetPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
			Target.setSize(500,500);
			Target.setLocationRelativeTo(null);
			TargetPanel.add(armies);
			TargetPanel.add(okTarget);
			Target.setLayout(new BorderLayout());
			TargetPanel.revalidate();
			TargetPanel.repaint();
			TargetPanel.setVisible(true);
			Target.add(TargetPanel);
			Target.setVisible(true);
			revalidate();
			repaint();
			
		}
		if(e.getActionCommand().equals("OkTarget"))
		{
			
			int a=armies.getSelectedIndex();
			if(a==0)
			 {
				 JOptionPane.showMessageDialog(ControlledArmies, "Please Select an Item","Invalid action",JOptionPane.WARNING_MESSAGE);
					return;
			 }
			int af=LayArmy.get(a-1);
			Army army =game.getPlayer().getControlledArmies().get(af);
		    game.targetCity(army, cityToTarget);
		    Target.setVisible(false);
		}
		if(e.getActionCommand().equals("LaySiege"))
		{
			current=new ArrayList<>();
			String [] a= {"Cairo", "Rome", "Sparta"};
			int ans =JOptionPane.showOptionDialog(ControlledArmies,
					"Please select the city", 
					"Target City of Army",
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, 
					null,
					a, 
					-1);
			cityToLaySiege="";
			if(ans !=-1)
				cityToLaySiege=a[ans];
			
			String[]armiesAvaliable=new String[game.getPlayer().getControlledArmies().size()+1];
			armiesAvaliable[0]="please Select an item";
			int j=1;
			int z=0;
			Boolean flag=false;
			for(Army am :game.getPlayer().getControlledArmies())
			{
				if(am.getCurrentLocation().toLowerCase().equals(cityToLaySiege.toLowerCase()))
				{
					String text="";
					text="Army "+j+" "+am.getCurrentStatus()+" having "+am.getUnits().size()+" units";
					armiesAvaliable[j]=text;
					current.add(z);                                    
					j++;
					flag=true;
					
				}
				z++;
			}

			if(!flag)
			{
				
				JOptionPane.showMessageDialog(ControlledArmies, "No armies have reached  "+cityToLaySiege+" yet","Invalid action",JOptionPane.WARNING_MESSAGE);
				return;
				
				
			}
			armiesLaySiege=new JComboBox (armiesAvaliable);
			armiesLaySiege.setPreferredSize(new Dimension(400,100));
			JButton ok=new JButton("ok");
			ok.setActionCommand("OkLaySiege");
			ok.addActionListener(this);
			SiegePanel.removeAll();
			SiegePanel.revalidate();
			SiegePanel.repaint();
			SiegePanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
			siege.setSize(500,500);
			siege.setLocationRelativeTo(null);
			SiegePanel.add(armiesLaySiege);
			SiegePanel.add(ok);
			siege.setLayout(new BorderLayout());
			SiegePanel.revalidate();
			SiegePanel.repaint();
			SiegePanel.setVisible(true);
			siege.add(SiegePanel);
			siege.setVisible(true);
			revalidate();
			repaint();
			
		}
		if(e.getActionCommand().equals("OkLaySiege"))
		{
			
			int a=armiesLaySiege.getSelectedIndex();
			if(a==0)
			 {
				 JOptionPane.showMessageDialog(ControlledArmies, "Please Select an Item","Invalid action",JOptionPane.WARNING_MESSAGE);
				 return;
			 }  
			int af=current.get(a-1);
			Army army =game.getPlayer().getControlledArmies().get(af);
		    City c=null;
		    for (City x:game.getAvailableCities())
		    {
		    	if(x.getName().toLowerCase().equals(cityToLaySiege.toLowerCase()))
		    	{
		    		c=x;
		    	}
		    }
		    try {
				game.getPlayer().laySiege(army, c);
			} catch (TargetNotReachedException e1) {
				return;
			
			} catch (FriendlyCityException e1) {
				JOptionPane.showMessageDialog(ControlledArmies, "You can not attack a friendly city","Invalid action",JOptionPane.WARNING_MESSAGE);
				
			}
		    siege.setVisible(false);
		}
		
		if(e.getActionCommand().equals("Relocate Army"))
		{
			String text="";
			String[]from = new String [game.getPlayer().getControlledArmies().size()+game.getAvailableCities().size()];
			int z=0;
			for(City c:game.getPlayer().getControlledCities())
			{
				text="Defending Army of "+c.getName();
				from[z]=text;
				z++;
			}
			int j=0;
			for(Army a : game.getPlayer().getControlledArmies())
			{
				text="Controlled Army "+j+"<< "+a.getCurrentLocation();
				j++;
				from[z]=text;
				z++;
			}
		
		From=new JComboBox (from);
		From.setPreferredSize(new Dimension(200,100));
		JButton ok=new JButton("next");
		ok.setActionCommand("OkFrom");
		ok.addActionListener(this);
		relocte.removeAll();
		relocte.revalidate();
		relocte.repaint();
		relocte.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		relocate.setSize(500,500);
		relocate.setLocationRelativeTo(null);
		relocte.add(From);
		relocte.add(ok);
		relocate.setLayout(new BorderLayout());
		relocte.revalidate();
		relocte.repaint();
		relocte.setVisible(true);
		relocate.add(relocte);
		relocate.setVisible(true);
		revalidate();
		repaint();
		}
		if(e.getActionCommand().equals("OkFrom"))
		{
			
			indexf =From.getSelectedIndex();
			int f=0;
			String text="";
			int z=0;
			int a=0;
			int b=0;
			String[]Toa = new String [game.getPlayer().getControlledArmies().size()+game.getAvailableCities().size()];
			if(indexf<game.getPlayer().getControlledCities().size())
			{
				for(City c:game.getPlayer().getControlledCities())
				{
					if(f!=indexf) {
						text="Defending Army of "+c.getName();
						Toa[z]=text;
						z++;
					}
					else
						Fromaya=c.getDefendingArmy();
					f++;
					
				}
				int j=0;
				for(Army ab : game.getPlayer().getControlledArmies())
				{
					text="Controlled Army "+j+"<< "+ab.getCurrentLocation();
					j++;
					Toa[z]=text;
					z++;
				}
				Def=true;
				
				
		}
			else
			{
				for(City c:game.getPlayer().getControlledCities())
				{
					text="Defending Army of "+c.getName();
					Toa[a]=text;
					a++;
				}
				for (Army am:game.getPlayer().getControlledArmies())
				{
					if(b!=(indexf - game.getPlayer().getControlledCities().size()))
					{
						text="Controlled Army "+b+"<< "+am.getCurrentLocation();
						Toa[a]=text;
						a++;
					}
					else
						Fromaya=am;
					b++;
					}
				cont=true;
				}
		To=new JComboBox (Toa);
		To.setPreferredSize(new Dimension(200,100));
		From.setPreferredSize(new Dimension(400,100));
		JButton ok=new JButton("next");
		ok.setActionCommand("OkTo");
		ok.addActionListener(this);
		relocte.add(To);
		relocte.add(ok);
		relocte.revalidate();
		relocte.repaint();
		revalidate();
		repaint();
			
			
		}
		if(e.getActionCommand().equals("OkTo"))
		{
			int indext =To.getSelectedIndex();
			if(Def)
			{
				if(game.getPlayer().getControlledCities().size()==1)
				{
					Toaya=game.getPlayer().getControlledArmies().get(indext);
				}
				else
				{
					if(indext==0)
					{
						if(indexf==0)
							Toaya=game.getPlayer().getControlledCities().get(1).getDefendingArmy();
						else
						{
							if(indexf==1)
								Toaya=game.getPlayer().getControlledCities().get(0).getDefendingArmy();
						}
							
					}
					else
						Toaya=game.getPlayer().getControlledArmies().get(indext-1);
						
				}
					
			}
			else {
				if(indext==0 && (game.getPlayer().getControlledCities().size()==1 || game.getPlayer().getControlledCities().size()==2))
				{
					Toaya=game.getPlayer().getControlledCities().get(0).getDefendingArmy();
				}
				else
				{
					if(indext==1&& (game.getPlayer().getControlledCities().size()==2))
					{
						Toaya=game.getPlayer().getControlledCities().get(1).getDefendingArmy();
					}
					else
					{
						if(indext<indexf)
						{
							Toaya=game.getPlayer().getControlledArmies().get(indext-game.getPlayer().getControlledCities().size());
									
						}
						else
							Toaya=game.getPlayer().getControlledArmies().get(indext-game.getPlayer().getControlledCities().size()+1);
					}
				}
				
			}
			String [] text=new String[Fromaya.getUnits().size()];
			boolean flag=false;
			String a="";
			int g=1;
			for (Unit u:Fromaya.getUnits())
			{
				a="Unit " +g+", Soldier Count is "+ u.getCurrentSoldierCount();
				if(u instanceof Archer)
					a+=" Type is Archer";
				if(u instanceof Infantry)
					a+=" Type is Infantry";
				if(u instanceof Cavalry)
					a+=" Type is Cavalry";
				text[g-1]=a;
				g++;
				flag=true;
			}
			if(!flag)
			{
				JOptionPane.showMessageDialog(ControlledArmies, "No Units to relocate with "+cityToLaySiege+" yet","Invalid action",JOptionPane.WARNING_MESSAGE);
				relocate.setVisible(false);
				return;
			}
			unit=new JComboBox (text);
			unit.setPreferredSize(new Dimension(400,100));
			JButton ok=new JButton("ok");
			ok.setActionCommand("OkRelocate");
			ok.addActionListener(this);
			relocte.add(unit);
			relocte.add(ok);
			relocte.revalidate();
			relocte.repaint();
			revalidate();
			repaint();
		}
		if(e.getActionCommand().equals("OkRelocate"))
		{
			if(Fromaya.getCurrentLocation().equals("onRoad") || Toaya.getCurrentLocation().equals("onRoad"))
			{
				JOptionPane.showMessageDialog(ControlledArmies, "Cannot complete action as one of the armies is on road","Invalid action",JOptionPane.WARNING_MESSAGE);
				relocate.setVisible(false);
				return;
			}
			int a=unit .getSelectedIndex();
			try {
				Toaya.relocateUnit(Fromaya.getUnits().get(a));
				if(Fromaya.getUnits().size()==0) {
					if(game.getPlayer().getControlledArmies().contains(Fromaya))
					{
						game.getPlayer().getControlledArmies().remove(Fromaya);
					}
				}
	
						
				playerArmy();
				map.add(ControlledArmies,BorderLayout.CENTER);
				map.revalidate();
				map.repaint();
				revalidate();
				repaint();
				}
				
			 catch (MaxCapacityException e1) {
				JOptionPane.showMessageDialog(ControlledArmies, "This Army has reached maximum capacity","Invalid action",JOptionPane.WARNING_MESSAGE);
			}
			relocate.setVisible(false);
		}
		
	
		
		if(e.getActionCommand().equals("battle"))
		{ 
			cities=new ArrayList();
			cua=null;
			
			String [] cit=new String[2];
			String[] options = {"Auto Resolve","Attack","Cancel"};
		
			
			int j=0;
			for(City c: game.getAvailableCities())
			{
				if(!game.getPlayer().getControlledCities().contains(c))
				{
					cities.add(c);
					cit[j]=c.getName();
					j++;
					
				}
			}
			cityaya=JOptionPane.showOptionDialog(ControlledArmies,
					"Please select the city", 
					"Battle",
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, 
					null,
					cit, 
					-1);
			if(cityaya==-1)
				return;
			ans =JOptionPane.showOptionDialog(ControlledArmies,
					"Do you want to autoresolve or attack ?", 
					"Battle",
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, 
					null,
					options, 
					-1);
			String [] armies=new String[game.getPlayer().getControlledArmies().size()];
			boolean flag=false;
			int z=0;
			int zi=0;
			for(Army a:game.getPlayer().getControlledArmies())
			{
				if(a.getCurrentLocation().equals(cit[cityaya]))
				{
					armies[z]="Army "+z+ "Having "+a.getUnits().size()+" units";
					indexBattleArmy.add(zi);
					z++;
				}
				zi++;
			}
			if(indexBattleArmy.size()==0)
			{
				JOptionPane.showMessageDialog(ControlledArmies, "No armies have reached  "+cit[cityaya]+" yet ","Invalid action",JOptionPane.WARNING_MESSAGE);
				return;
			}
			cua=cities.get(cityaya);
			armyAttack=new JComboBox (armies);
			armyAttack.setPreferredSize(new Dimension(400,100));
			battleArmy.setPreferredSize(new Dimension(400,100));
			JButton ok=new JButton("ok");
			ok.setActionCommand("Okmoto");
			ok.addActionListener(this);
			battleArmyPanel.removeAll();
			battleArmyPanel.revalidate();
			battleArmyPanel.repaint();
			battleArmyPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
			battleArmy.setSize(500,500);
			battleArmy.setLocationRelativeTo(null);
			battleArmyPanel.add(armyAttack);
			battleArmyPanel.add(ok);
			battleArmy.setLayout(new BorderLayout());
			battleArmyPanel.revalidate();
			battleArmyPanel.repaint();
			battleArmyPanel.setVisible(true);
			battleArmy.add(battleArmyPanel);
			battleArmy.setVisible(true);
			revalidate();
			repaint();
		}
		if(e.getActionCommand().equals("Okmoto"))	
		{
			
			int a = armyAttack.getSelectedIndex();
			int b=indexBattleArmy.get(a);
		    am=game.getPlayer().getControlledArmies().get(b);
			elbadrabo = cities.get(cityaya).getDefendingArmy();
			String text="";
			if(ans==0)
			{
				try {
					text=game.autoResolve(am, elbadrabo);
					JOptionPane.showMessageDialog(ControlledArmies, text,"Result",JOptionPane.INFORMATION_MESSAGE);
					playerArmy();
					map.add(ControlledArmies,BorderLayout.CENTER);
					map.revalidate();
					map.repaint();
					revalidate();
					repaint();
					
					
				} catch (FriendlyFireException e1) 
				{
					e1.printStackTrace();
				}
			}
				
				else
				{
					battle(am,elbadrabo);
				}
				
			
			battleArmy.setVisible(false);
		}
		if(e.getActionCommand().equals("OkAttack"))
		{
			Unit unit2 = elbadrabo.getUnits().get((int) (Math.random() * elbadrabo.getUnits().size()));
			int a=unit2.getCurrentSoldierCount();
			System.out.println(a);
			int ab=unitManual.getSelectedIndex();
			if(ab==-1)
			{
				JOptionPane.showMessageDialog(ControlledArmies,"No more units to fight with you can exit the battle view","Invalid action",JOptionPane.WARNING_MESSAGE);
				return;
			}
			Unit unit1 =am.getUnits().get(unitManual.getSelectedIndex());
			try {
				unit1.attack(unit2);
				
			} catch (FriendlyFireException e1) {
				return;
			}
			text+="Round "+counterA+":"+"Unit of the enemy lost "+(a-unit2.getCurrentSoldierCount())+"\n";
			
			if(elbadrabo.getUnits().size()==0)
			{
				text+=".........................."+"You have won";
				
				
			}
			
			counterA++;
			unit2 = elbadrabo.getUnits().get((int) (Math.random() * elbadrabo.getUnits().size()));
			unit1= am.getUnits().get((int) (Math.random() * am.getUnits().size()));
			a=unit1.getCurrentSoldierCount();
			try {
				unit2.attack(unit1);
				
			} catch (FriendlyFireException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			text+="Round "+counterA+":"+"Your unit has lost " +(a-unit1.getCurrentSoldierCount())+"\n";
			
			if(am.getUnits().size()==0)
			{
				
				
				text+=".........................."+"You Have Lost Battle";
				
				
				
			}

			counterA++;
			dataAttack.setText(text);
			JScrollPane a1= new JScrollPane(dataAttack);
			a1.setOpaque(false);
			a1.getViewport().setOpaque(false);
			BattleField.add(a1,BorderLayout.CENTER);
			revalidate();
			repaint();
			BattleField.revalidate();
			BattleField.repaint();
			battle(am,elbadrabo);
			
			
		}
		if (e.getActionCommand().equals("Exit"))
		{
			text="";
			BattleField.setVisible(false);
			playerArmy();
			map.add(ControlledArmies,BorderLayout.CENTER);
			map.revalidate();
			map.repaint();
			revalidate();
			repaint();
		}
		}
		
	
		
		
	}
	public void buildFrame() 
	{
		panelBuild.removeAll();
		panelBuild.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		frameBuild.setSize(400,300);
		frameBuild.setLocationRelativeTo(null);
		panelBuild.add(ToBuild);
		panelBuild.add(okToBuild);
		panelBuild.add(cancelToBuild);
		panelBuild.revalidate();
		panelBuild.repaint();
		frameBuild.setLayout(new BorderLayout());
		panelBuild.revalidate();
		panelBuild.repaint();
		panelBuild.setVisible(true);
		frameBuild.add(panelBuild);
		frameBuild.setVisible(true);
		revalidate();
		repaint();
		
		
	}
	public void turnEnded() {
		if(counter==0)
		{
			map.removeAll();
			extra.removeAll();
			cityinfo();
			playerInfo();
			JButton l=new JButton("Controlled Armies");
			l.setActionCommand("Controlled Armies");
			l.addActionListener(this);
			l.setFont(new Font("Consolas",Font.BOLD,19));
			l.setForeground(Color.white);
			l.setBackground(Color.red);
			extra.add(l,BorderLayout.SOUTH);
			map.add(extra,BorderLayout.CENTER);
			counter=0;
		}
		if(counter==1)
			upgradeBuilding(buttonCity);
		if(counter==2)
			updateDefendingArmy(buttonCity);
		if(counter==3) {
			playerArmy();
			map.add(ControlledArmies,BorderLayout.CENTER);
			map.revalidate();
			map.repaint();
			revalidate();
			repaint();
		}
			
		
	}
	
	public void resolve() 
	{
		
	}
	
	public static void main(String[] args)
	{
		EmpireView a =new EmpireView();
	}
	
	

	
	
	
	

	
}

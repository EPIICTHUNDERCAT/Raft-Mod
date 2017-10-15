package com.epiicthundercat.raft;

public class Reference {
	public static final String ID = "raft";
	public static final String VER = "0.0.7";
	public static final String NAME = "RaftMod";
	public static final String DEPENDENCIES = "after:toughasnails";
	
	
	public static final String CLIENT_PROXY = "com.epiicthundercat.raft.proxy.ClientProxy";
	public static final String SERVER_PROXY = "com.epiicthundercat.raft.proxy.CommonProxy";
	
	

    public static final String CONFIG_FILE = "config/raft.cfg";
	
    public static int Seagull_Spawn_Rate = 100;
    public static int Fish_Spawn_Rate = 100;
    public static int Shark_Spawn_Rate = 70;
    public static int Eel_Spawn_Rate = 70;
    
    public static int Seagull_Spawn_MIN_Amount = 5;
    public static int Fish_Spawn_MIN_Amount = 4;
    public static int Shark_Spawn_MIN_Amount = 1;
    public static int Eel_Spawn_MIN_Amount = 4;
    
    public static int Seagull_Spawn_MAX_Amount = 9;
    public static int Fish_Spawn_MAX_Amount = 8;
    public static int Shark_Spawn_MAX_Amount = 2;
    public static int Eel_Spawn_MAX_Amount = 4;
    
    
    public static int Coconut_Drink_Yield = 10;
    public static float Coconut_Hydration_Yield = 6.7f;
    
    public static int Tin_Can_Drink_Yield = 8;
    public static float Tin_Can_Hydration_Yield = 1f;  
    

    public static int Air_On_Scuba = 900;
    public static int Damage_If_Scuba_Air_Runs_Out = 2;
    
    
  
    public static boolean Is_Scrap_Placeable = true;
    public static boolean Is_Plank_Placeable = true;
    public static boolean Is_Thatch_Placeable = true;
    public static boolean Is_Barrel_Placeable = true;
	//public static float Damage_If_Scuba_Air_Runs_Out;
}
